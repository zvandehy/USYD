import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ServerInfoListTest {

    /**
     * Purpose: Test invalid filename
     * Input: filename that doesn't exist
     * Expected: FileNotFoundException
     * Actual:  FileNotFoundException
     */
    @Test
    public void initialiseFromFile_FileNotFound() {
        ServerInfoList serverInfoList = new ServerInfoList();
        try {
            serverInfoList.initialiseFromFile("noFile");
        } catch(Exception e) {
            assertTrue(e instanceof FileNotFoundException);
        }
        assertTrue(serverInfoList.getServerInfos().isEmpty());
    }

    /**
     * Purpose: Ensure SetServerInfos sets serverInfos to the new list
     * Input: a list of valid server infos
     * Expected: the new serverInfos is the same as the input list
     * Actual: the new serverInfos is the same as the input list
     */
    @Test
    public void setServerInfos() {
        ServerInfoList serverInfoList = new ServerInfoList();
        assertTrue(serverInfoList.getServerInfos().isEmpty());
        ArrayList<ServerInfo> newServerInfoList = new ArrayList<>();
        newServerInfoList.add(new ServerInfo("localhost", 3456));
        newServerInfoList.add(new ServerInfo("localhost", 1234));
        newServerInfoList.add(new ServerInfo("127.0.0.1", 1234));
        serverInfoList.setServerInfos(newServerInfoList);
        assertEquals(newServerInfoList, serverInfoList.getServerInfos());
        newServerInfoList.remove(1);
        serverInfoList.setServerInfos(newServerInfoList);
        assertEquals(newServerInfoList, serverInfoList.getServerInfos());
        assertEquals("127.0.0.1", serverInfoList.getServerInfos().get(1).getHost());
    }

    /**
     * Purpose: Ensure that a valid ServerInfo is added to the ServerInfoList
     * Input: valid server infos
     * Expected: the Server Info is added to the Serverinfolist and method returns true
     * Actual: the server info is added to the serverinfo list and method returns true
     */
    @Test
    public void addServerInfo_Valid() {
        ServerInfoList serverInfoList = new ServerInfoList();
        assertTrue(serverInfoList.getServerInfos().isEmpty());

        ServerInfo serverInfo = new ServerInfo("localhost", 3456);
        assertTrue(serverInfoList.addServerInfo(serverInfo));
        assertEquals(serverInfoList.getServerInfos().get(0), serverInfo);

        serverInfo = new ServerInfo("localhost", 1024);
        assertTrue(serverInfoList.addServerInfo(serverInfo));
        assertEquals(serverInfoList.getServerInfos().get(1), serverInfo);

        serverInfo = new ServerInfo("localhost", 65535);
        assertTrue(serverInfoList.addServerInfo(serverInfo));
        assertEquals(serverInfoList.getServerInfos().get(2), serverInfo);
    }
    /**
     * Purpose: Ensure that an invalid ServerInfo is not added to the ServerInfoList
     * Input: invalid server infos
     * Expected: the Server Info is not added and the method returns false
     * Actual: the server info is not added and the method returns false
     */
    @Test
    public void addServerInfo_InValid() {
        ServerInfoList serverInfoList = new ServerInfoList();
        assertTrue(serverInfoList.getServerInfos().isEmpty());

        ServerInfo serverInfo = new ServerInfo("localhost", 0000);
        assertFalse(serverInfoList.addServerInfo(serverInfo));
        assertTrue(serverInfoList.getServerInfos().isEmpty());

        serverInfo = new ServerInfo("localhost", 1023);
        assertFalse(serverInfoList.addServerInfo(serverInfo));
        assertTrue(serverInfoList.getServerInfos().isEmpty());

        serverInfo = new ServerInfo("localhost", 65536);
        assertFalse(serverInfoList.addServerInfo(serverInfo));
        assertTrue(serverInfoList.getServerInfos().isEmpty());

        serverInfo = new ServerInfo("", 8000);
        assertFalse(serverInfoList.addServerInfo(serverInfo));
        assertTrue(serverInfoList.getServerInfos().isEmpty());

        serverInfo = new ServerInfo(null, 8000);
        assertFalse(serverInfoList.addServerInfo(serverInfo));
        assertTrue(serverInfoList.getServerInfos().isEmpty());
    }

    /**
     * Purpose: Ensure that a valid ServerInfo is updated in the ServerInfoList
     * Input: valid server infos
     * Expected: the Server Info is updated and method returns true
     * Actual: the server info is updated and method returns true
     */
    @Test
    public void updateServerInfo_Valid() {
        ServerInfoList serverInfoList = new ServerInfoList();
        assertTrue(serverInfoList.getServerInfos().isEmpty());

        ServerInfo serverInfo = new ServerInfo("localhost", 3456);
        assertTrue(serverInfoList.addServerInfo(serverInfo));
        assertEquals(serverInfoList.getServerInfos().get(0), serverInfo);

        serverInfo = new ServerInfo("localhost", 1024);
        assertTrue(serverInfoList.updateServerInfo(0, serverInfo));
        assertEquals(serverInfoList.getServerInfos().get(0), serverInfo);

        serverInfo = new ServerInfo("localhost", 65535);
        assertTrue(serverInfoList.updateServerInfo(0, serverInfo));
        assertEquals(serverInfoList.getServerInfos().get(0), serverInfo);
    }
    /**
     * Purpose: Ensure that an invalid ServerInfo is updated in the ServerInfoList
     * Input: invalid server infos
     * Expected: the Server Info is updated and method returns false
     * Actual: the server info is updated and method returns false
     */
    @Test
    public void updateServerInfo_InValid() {
        ServerInfoList serverInfoList = new ServerInfoList();
        assertTrue(serverInfoList.getServerInfos().isEmpty());

        //add 1 valid server info to be updated
        ServerInfo valid = new ServerInfo("localhost", 8000);
        serverInfoList.addServerInfo(valid);
        assertEquals(serverInfoList.getServerInfos().get(0), valid);

        ServerInfo serverInfo = new ServerInfo("localhost", 0000);//invalid port
        assertFalse(serverInfoList.updateServerInfo(0, serverInfo));
        assertEquals(serverInfoList.getServerInfos().get(0), valid);

        serverInfo = new ServerInfo("localhost", 1023); //invalid port
        assertFalse(serverInfoList.updateServerInfo(0, serverInfo));
        assertEquals(serverInfoList.getServerInfos().get(0), valid);

        serverInfo = new ServerInfo("localhost", 65536); //invalid port
        assertFalse(serverInfoList.updateServerInfo(0, serverInfo));
        assertEquals(serverInfoList.getServerInfos().get(0), valid);

        serverInfo = new ServerInfo("", 8000); //invalid host
        assertFalse(serverInfoList.updateServerInfo(0, serverInfo));
        assertEquals(serverInfoList.getServerInfos().get(0), valid);

        serverInfo = new ServerInfo(null, 8000); //invalid host
        assertFalse(serverInfoList.updateServerInfo(0, serverInfo));
        assertEquals(serverInfoList.getServerInfos().get(0), valid);

        serverInfo = new ServerInfo("localhost", 5656);//valid
        assertFalse(serverInfoList.updateServerInfo(1, serverInfo)); //invalid index
        assertEquals(serverInfoList.getServerInfos().get(0), valid);
    }

    /**
     * Purpose: remove null or valid instances of ServerInfoList at valid index
     * Input: an index < size of list
     * Expected: return true and set server info to null
     * Actual: return true and set server info to null
     */
    @Test
    public void removeServerInfo_Valid() {
        ServerInfoList serverInfoList = new ServerInfoList();
        assertTrue(serverInfoList.getServerInfos().isEmpty());
        ArrayList<ServerInfo> newServerInfoList = new ArrayList<>();
        newServerInfoList.add(new ServerInfo("localhost", 3456));
        newServerInfoList.add(new ServerInfo("localhost", 1234));
        newServerInfoList.add(new ServerInfo("127.0.0.1", 1234));
        serverInfoList.setServerInfos(newServerInfoList);
        assertEquals(newServerInfoList, serverInfoList.getServerInfos());
        assertNotNull(serverInfoList.getServerInfos().get(0));
        assertNotNull(serverInfoList.getServerInfos().get(1));
        assertNotNull(serverInfoList.getServerInfos().get(2));

        assertTrue(serverInfoList.removeServerInfo(0));//set 0 to null
        assertNull(serverInfoList.getServerInfos().get(0));
        assertNotNull(serverInfoList.getServerInfos().get(1));
        assertNotNull(serverInfoList.getServerInfos().get(2));

        assertTrue(serverInfoList.removeServerInfo(1));//set 1 to null
        assertNull(serverInfoList.getServerInfos().get(0));
        assertNull(serverInfoList.getServerInfos().get(1));
        assertNotNull(serverInfoList.getServerInfos().get(2));

        assertTrue(serverInfoList.removeServerInfo(2));//set 2 to null
        assertNull(serverInfoList.getServerInfos().get(0));
        assertNull(serverInfoList.getServerInfos().get(1));
        assertNull(serverInfoList.getServerInfos().get(2));

        //size doesn't change
        assertEquals(3, serverInfoList.getServerInfos().size());

    }
    /**
     * Purpose: don't remove at invalid index
     * Input: an index >= size of list
     * Expected: return false
     * Actual: return false
     */
    @Test
    public void removeServerInfo_InValid() {
        ServerInfoList serverInfoList = new ServerInfoList();
        assertTrue(serverInfoList.getServerInfos().isEmpty());
        ArrayList<ServerInfo> newServerInfoList = new ArrayList<>();
        newServerInfoList.add(new ServerInfo("localhost", 3456));
        newServerInfoList.add(new ServerInfo("localhost", 1234));
        newServerInfoList.add(new ServerInfo("127.0.0.1", 1234));
        serverInfoList.setServerInfos(newServerInfoList);
        assertEquals(newServerInfoList, serverInfoList.getServerInfos());
        assertNotNull(serverInfoList.getServerInfos().get(0));
        assertNotNull(serverInfoList.getServerInfos().get(1));
        assertNotNull(serverInfoList.getServerInfos().get(2));

        assertFalse(serverInfoList.removeServerInfo(-1));//invalid index
        assertNotNull(serverInfoList.getServerInfos().get(0));
        assertNotNull(serverInfoList.getServerInfos().get(1));
        assertNotNull(serverInfoList.getServerInfos().get(2));

        assertFalse(serverInfoList.removeServerInfo(-1000));//invalid index
        assertNotNull(serverInfoList.getServerInfos().get(0));
        assertNotNull(serverInfoList.getServerInfos().get(1));
        assertNotNull(serverInfoList.getServerInfos().get(2));

        assertFalse(serverInfoList.removeServerInfo(4));//invalid index
        assertNotNull(serverInfoList.getServerInfos().get(0));
        assertNotNull(serverInfoList.getServerInfos().get(1));
        assertNotNull(serverInfoList.getServerInfos().get(2));

        assertFalse(serverInfoList.removeServerInfo(400));//invalid index
        assertNotNull(serverInfoList.getServerInfos().get(0));
        assertNotNull(serverInfoList.getServerInfos().get(1));
        assertNotNull(serverInfoList.getServerInfos().get(2));

        //size doesn't change
        assertEquals(3, serverInfoList.getServerInfos().size());
    }

    /**
     * Purpose: clears null values from list and returns true. Return false when no null values present
     * Input: a list with null values
     * Expected: a smaller list and return true. Return false when called again
     * Actual: a smaller list and returns true. Returns false when called again
     */
    @Test
    public void clearServerInfo() {
        ServerInfoList serverInfoList = new ServerInfoList();
        assertTrue(serverInfoList.getServerInfos().isEmpty());
        ArrayList<ServerInfo> newServerInfoList = new ArrayList<>();
        newServerInfoList.add(new ServerInfo("localhost", 3456));
        newServerInfoList.add(new ServerInfo("localhost", 1234));
        newServerInfoList.add(new ServerInfo("127.0.0.1", 1234));
        serverInfoList.setServerInfos(newServerInfoList);
        assertEquals(newServerInfoList, serverInfoList.getServerInfos());
        assertNotNull(serverInfoList.getServerInfos().get(0));
        assertNotNull(serverInfoList.getServerInfos().get(1));
        assertNotNull(serverInfoList.getServerInfos().get(2));

        assertTrue(serverInfoList.removeServerInfo(0));//set 0 to null
        assertTrue(serverInfoList.removeServerInfo(1));//set 1 to null

        assertEquals(3, serverInfoList.getServerInfos().size()); //size is 3 with null values
        assertTrue(serverInfoList.clearServerInfo()); //successfully remove null
        assertEquals(1, serverInfoList.getServerInfos().size()); //size is now 1
        assertEquals(newServerInfoList.get(2), serverInfoList.getServerInfos().get(0)); //the third item from before is now first

        assertFalse(serverInfoList.clearServerInfo());
    }
}