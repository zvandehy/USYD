import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Base64;
import java.util.Stack;

public class CatchupRequestRunnable implements Runnable {

    ServerInfo peerServerInfo;
    Blockchain blockchain;

    public CatchupRequestRunnable(ServerInfo peerServerInfo, Blockchain blockchain) {
        this.peerServerInfo = peerServerInfo;
        this.blockchain = blockchain;
    }

    @Override
    public void run() {
        try {
            Socket blockSocket = new Socket(peerServerInfo.getHost(), peerServerInfo.getPort());


            PrintWriter printWriter = new PrintWriter(blockSocket.getOutputStream());

//        System.out.println("Initiating Catchup...");

            Stack<Block> newBlocks = new Stack<>();

//        System.out.println("Sending cu");
            // send the cu message
            printWriter.println("cu");
            printWriter.flush();

            //must use ObjectInputStream and ObjectOutputStream to transfer the block
            ObjectInputStream receiveBlockStream;
            receiveBlockStream = new ObjectInputStream(blockSocket.getInputStream());

            Block receivedBlock = (Block) receiveBlockStream.readObject();
//        System.out.println("Received: " + receivedBlock);

            receiveBlockStream.close();
            blockSocket.close();


            //we want to stop when received.previous = our head/last block
            //continue getting blocks until...
            //stop if the received block is null
            //stop if our head is not null and the received block = our head
            //otherwise received block should be added
            //don't request another block if the block before received is null (hash = "AAA...")
            //don't request another block if the block before received is the same as our head
            while (true) {

                //stop if the received block is null
                if (receivedBlock == null) {
//                System.out.println("BLOCK IS NULL");
                    break;
                }
                //stop if our head is not null and the received block = our head
                else if (blockchain.getHead() != null && Arrays.equals(receivedBlock.calculateHash(), blockchain.getHead().calculateHash())) {
                    break;
                }

                //otherwise received block should be added
                newBlocks.push(receivedBlock);

                //don't request another block if the block before received is null (hash = "AAA...")
                if (Base64.getEncoder().encodeToString(receivedBlock.getPreviousHash()).startsWith("AAAAA")) {
                    break;
                }
                //don't request another block if the block before received is the same as our head
                else if (blockchain.getHead() != null && Arrays.equals(receivedBlock.getPreviousHash(), blockchain.getHead().calculateHash())) {
                    break;
                }

                blockSocket = new Socket(peerServerInfo.getHost(), peerServerInfo.getPort());
                printWriter = new PrintWriter(blockSocket.getOutputStream());

//            System.out.println("sending: " + "cu|"+ Base64.getEncoder().encodeToString(receivedBlock.getPreviousHash()));
                printWriter.println("cu|" + Base64.getEncoder().encodeToString(receivedBlock.getPreviousHash()));
                printWriter.flush();

                receiveBlockStream = new ObjectInputStream(blockSocket.getInputStream());

                receivedBlock = (Block) receiveBlockStream.readObject();
                receiveBlockStream.close();
                blockSocket.close();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }

//        System.out.println("Changing my blockchain");
            //add the new blocks to the end of the blockchain
            while (newBlocks.size() > 0) {
                Block nextBlock = newBlocks.pop();
                nextBlock.setPreviousBlock(blockchain.getHead());
                blockchain.setHead(nextBlock);
                blockchain.setLength(blockchain.getLength() + 1);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
