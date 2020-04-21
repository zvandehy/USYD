import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;

public class BlockchainClient {

    public static void main(String[] args) {

        if (args.length != 1) {
            return;
        }
        String configFileName = args[0];
        BlockchainClient bcc = new BlockchainClient();
        ServerInfoList pl = new ServerInfoList();
        pl.initialiseFromFile(configFileName);

        Scanner sc = new Scanner(System.in);
        String message = sc.nextLine(); //get user message
        while (!message.equals("sd")) {
            //process each message by delivering the message to the correct servers
            String request = "err";
            try {
                request = message.substring(0,2);
            } catch (Exception e) {
                continue;
            }
            switch(request) {
                case "ls":
                    if(message.equals("ls")) {
                        System.out.println(pl.toString());
                        break;
                    }
                    System.out.println("Unknown Command\n");
                    break;
                case "ad":
                    if(!message.substring(2,3).equals("|")) {
                        System.out.println("Unknown Command\n");
                        break;
                    }
                    try {
                        int ad_first = message.indexOf("|") + 1;
                        int ad_second = message.indexOf("|", ad_first);
                        String host = message.substring(ad_first, ad_second);
                        int port = Integer.parseInt(message.substring(ad_second + 1));
                        if(pl.addServerInfo(new ServerInfo(host, port))) {
                            System.out.println("Succeeded\n");
                        } else {
                            System.out.println("Failed\n");
                        }
                        break;
                    } catch (Exception e) {
                        System.out.println("Failed\n");
                    }
                    break;
                case "rm":
                    if(!message.substring(2,3).equals("|")) {
                        System.out.println("Unknown Command\n");
                        break;
                    }
                    try {
                        int rm_first = message.indexOf("|") + 1;
                        int rm_index = Integer.parseInt(message.substring(rm_first));
                        if (pl.removeServerInfo(rm_index)) {
                            System.out.println("Succeeded\n");
                        } else {
                            System.out.println("Failed\n");
                        }
                    } catch(Exception e) {
                        System.out.println("Failed\n");
                    }
                    break;
                case "up":
                    if(!message.substring(2,3).equals("|")) {
                        System.out.println("Unknown Command\n");
                        break;
                    }
                    try {
                        int up_first = message.indexOf("|");
                        int up_second = message.indexOf("|", up_first+1);
                        int up_third = message.indexOf("|", up_second+1);
                        int up_index = Integer.parseInt(message.substring(up_first+1,up_second));
                        String host = message.substring(up_second+1, up_third);
                        int port = Integer.parseInt(message.substring(up_third + 1));
//                        System.out.println("" + up_index + " " + host + " " + port);
                        if(pl.updateServerInfo(up_index, new ServerInfo(host, port))) {
                            System.out.println("Succeeded\n");
                        }else {
                            System.out.println("Failed\n");
                        }
                    } catch (Exception e) {
                        System.out.println("Failed\n");
                    }
                    break;
                case "cl":
                    if(message.equals("cl")) {
                        if(pl.clearServerInfo()) {
                            System.out.println("Succeeded\n");
                        }
                        else {
                            System.out.println("Failed\n");
                        }
                    } else {
                        System.out.println("Unknown Command\n");
                    }
                    break;

                case "tx":
                    if(!message.substring(2,3).equals("|")) {
                    System.out.println("Unknown Command\n");
                    break;
                    }
                    bcc.broadcast(pl, message);
                    break;
                case "pb":
                    try {
                        ArrayList<Integer> serverIndices = new ArrayList<Integer>();
                        int i=0;
                        int current = message.indexOf("|");
                        while(current !=-1) {
                            int next = message.indexOf("|", current+1);
                            if(next == -1) {
                                int index = Integer.parseInt(message.substring(current+1));
                                serverIndices.add(index);
                            } else {
                                int index = Integer.parseInt(message.substring(current+1, next));
                                serverIndices.add(index);
                            }
                            current = next;
                        }
                        if(serverIndices.isEmpty()) {
                            bcc.broadcast(pl, "pb");
                        } else {
                            bcc.multicast(pl, serverIndices, "pb");
                        }
                    } catch(Exception e) {
                        System.out.println("Unknown Command\n");
                    }
                    break;
                case "sd":
                    if(!message.equals("sd")) {
                        System.out.println("Unknown Command\n");
                        break;
                    }
                    break;
                default:
                    System.out.println("Unknown Command\n");
                    break;
            }
            message = sc.nextLine();//get new message
        }
    }

    public void unicast (int serverNumber, ServerInfo p, String message) {
        BlockchainClientRunnable bcr = new BlockchainClientRunnable(serverNumber, p.getHost(), p.getPort(), message);
        bcr.run();
        System.out.println(bcr.getReply());
    }

    public void broadcast (ServerInfoList pl, String message) {
        String allReplies = "";
        for(int i=0;i<pl.getServerInfos().size();i++) {
            ServerInfo serverInfo = pl.getServerInfos().get(i);
            if(serverInfo != null) {
                BlockchainClientRunnable bcr = new BlockchainClientRunnable(i, serverInfo.getHost(), serverInfo.getPort(), message);
                bcr.run();
                allReplies += bcr.getReply()+"\n";
            }
        }
        System.out.print(allReplies);
    }

    public void multicast (ServerInfoList serverInfoList, ArrayList<Integer> serverIndices, String message) {
        String allReplies = "";
        for(int i=0;i<serverInfoList.getServerInfos().size();i++) {
            if(serverIndices.contains(i)) {
                ServerInfo serverInfo = serverInfoList.getServerInfos().get(i);
                if(serverInfo != null) {
                    BlockchainClientRunnable bcr = new BlockchainClientRunnable(i, serverInfo.getHost(), serverInfo.getPort(), message);
                    bcr.run();
                    allReplies += bcr.getReply()+"\n";
                }
            }
        }
        System.out.print(allReplies);
    }

    // implement any helper method here if you need any
}