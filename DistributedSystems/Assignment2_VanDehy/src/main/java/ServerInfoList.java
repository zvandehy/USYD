import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerInfoList {

    ArrayList<ServerInfo> serverInfos;

    public ServerInfoList() {
        serverInfos = new ArrayList<>();
    }

    public void initialiseFromFile(String filename) {
//        System.out.println(filename);
        FileReader file = null;
        try {
            file = new FileReader(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        BufferedReader reader = new BufferedReader(file);
        try {
            String line = reader.readLine();
            Integer serversNum = 0;
            Map<String, String> serverHosts = new HashMap<>();
            Map<String, String> serverPorts = new HashMap<>();
            while(line != null) {
                if(line.isEmpty()) {
                    line = reader.readLine();
                    continue;
                }
                else if(line.substring(0,7).equals("servers")) {
                    serversNum = Integer.parseInt(line.substring(line.length()-1));
//                    System.out.println("serversNum = " + serversNum);
                }
                else if(line.substring(0,6).equals("server") && line.substring(8,12).equals("host")) {
                    serverHosts.put(line.substring(6,7), line.substring(13));
//                    System.out.println("server" + line.substring(6,7) + " host " + line.substring(13));
                }
                else if(line.substring(0,6).equals("server") && line.substring(8,12).equals("port")) {
                    serverPorts.put(line.substring(6, 7), line.substring(13));
//                    System.out.println("server" + line.substring(6,7) + " port " + line.substring(13));
                }
                line = reader.readLine();
            }
            for(int i=0;i<serversNum;i++) {
                String host = serverHosts.get(String.valueOf(i));
                String portStr = serverPorts.get(String.valueOf(i));
                int port = 0;
                if (portStr != null) {
                    port = Integer.parseInt(portStr);
                }
                if((host != null && !host.equals("")) && (port >= 1024 && port <= 65535)) {
                    addServerInfo(new ServerInfo(host, port));
                } else {
                    addServerInfo(null);
                }
            }
//            System.out.println(serverInfos);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<ServerInfo> getServerInfos() {
        return serverInfos;
    }

    public void setServerInfos(ArrayList<ServerInfo> serverInfos) {
        this.serverInfos = serverInfos;
    }

    public boolean addServerInfo(ServerInfo newServerInfo) {
        String host = newServerInfo.getHost();
        int port = newServerInfo.getPort();
        if (host == null || host.equals("") || port < 1024 || port > 65535) {
            return false;
        }
        return serverInfos.add(newServerInfo);
    }

    public boolean updateServerInfo(int index, ServerInfo newServerInfo) {
        if(index >= serverInfos.size()) {
            return false;
        } else if(newServerInfo.getHost() == null || newServerInfo.getHost().equals("") || newServerInfo.getPort() < 1024 || newServerInfo.getPort() > 65535) {
            return false;
        } else {
            serverInfos.set(index, newServerInfo);
            return true;
        }
    }

    public boolean removeServerInfo(int index) {
        if(index >= serverInfos.size() || index < 0) {
            return false;
        } else {
            serverInfos.set(index, null);
            return true;
        }
    }

    public boolean clearServerInfo() {
        ArrayList<ServerInfo> newList = new ArrayList<>();
        boolean removed = false;
        for(int i=0;i<getServerInfos().size();i++) {
            if(getServerInfos().get(i) != null) {
                newList.add(getServerInfos().get(i));
            } else {
                removed = true;
            }
        }
        this.serverInfos = newList;
        return removed;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < serverInfos.size(); i++) {
            if (serverInfos.get(i) != null) {
                s += "Server" + i + ": " + serverInfos.get(i).getHost() + " " + serverInfos.get(i).getPort() + "\n";
            }
        }
        return s;
    }

    // implement any helper method here if you need any
}