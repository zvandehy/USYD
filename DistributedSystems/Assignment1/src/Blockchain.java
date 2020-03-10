import java.util.ArrayList;

public class Blockchain {

    private Block head;
    private ArrayList<Transaction> pool;
    private int length;

    private final int poolLimit = 3;

    public Blockchain() {
        pool = new ArrayList<>();
        length = 0;
    }

    //getters and setters
    public Block getHead() {return head;}
    public ArrayList<Transaction> getPool() {return pool;}
    public int getLength() {return length;}
    public void setHead(Block head) {this.head=head;}
    public void setPool(ArrayList<Transaction> pool) {this.pool=pool;}
    public void setLength(int length) {this.length = length;}

    public String toString() {
        String cutOffRule = new String(new char[81]).replace("\0","-") + "\n";
        String poolString = "";
        for (Transaction tx : pool) {
            poolString += tx.toString();
        }

        String blockString = "";
        Block bl = head;
        while(bl != null) {
            blockString += bl.toString();
            bl = bl.getPreviousBlock();
        }

        return "Pool:\n" + cutOffRule + poolString + cutOffRule + blockString;
    }

    //add a transaction
    public int addTransaction(String txString) {
        Transaction transaction;
        try {
            transaction = new Transaction(txString);
        } catch(Exception e) {
            return 0;
        }
        pool.add(transaction);
        if(pool.size() >= 3) {
            commitPool();
            return 2;
        }
        return 1;
    }

    //add a new block to the blockchain and clear the pool
    private void commitPool() {
        Block newBlock = new Block();
        newBlock.setTransactions(pool);
        if(length == 0) {
            newBlock.setPreviousHash(new byte[32]);
            newBlock.setPreviousBlock(null);
        }
        else {
            newBlock.setPreviousBlock(head);
            newBlock.setPreviousHash(head.calculateHash());
        }
        setHead(newBlock);
        setLength(getLength()+1);
        setPool(new ArrayList<>());
    }
}
