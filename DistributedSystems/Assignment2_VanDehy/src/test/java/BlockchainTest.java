import org.junit.Test;

import static org.junit.Assert.*;

public class BlockchainTest {

    /**
     * Purpose: Show that all invalid transactions do not get added
     * Input: Invalid # delimiters, invalid sender, invalid content
     * Expected: No transactions are added to pool, each method call returns false
     * Actual: No transactions are added to pool, each method call returns false
     */
    @Test
    public void addTransaction_invalid() {
        Blockchain bc = new Blockchain();
        assertFalse(bc.addTransaction("tx|one"));//not enough delimiters
        assertFalse(bc.addTransaction("tx|one|two|three"));//too many delimiters
        assertFalse(bc.addTransaction("xt|one|two")); //first argument not "tx"
        assertFalse(bc.addTransaction("tx||two")); //null sender
        assertFalse(bc.addTransaction("tx|one|")); //null content
        assertFalse(bc.addTransaction("tx|abcdzyxw|content")); //invalid sender
        assertFalse(bc.addTransaction("tx|1234abcd|content")); //invalid sender
        assertFalse(bc.addTransaction("tx|evan@7&8|content")); //invalid sender
        String invalidContent = "";
        for(int i=0;i<71;i++) invalidContent += "a";
        assertFalse(bc.addTransaction("tx|evan1234|" + invalidContent)); //invalid content
        assertTrue(bc.getPool().isEmpty());
        assertNull(bc.getHead());
    }

    /**
     * Purpose: Ensure Valid transactions work correctly
     * Input: txString with tx, valid sender, and valid content
     * Expected: method returns true and transaction added to pool
     * Actual: method returns true and transaction added to pool
     */
    @Test
    public void addTransaction() {
        Blockchain bc = new Blockchain();
        assertTrue(bc.addTransaction("tx|abcd1234|Any content below 70 characters"));
        assertTrue(bc.addTransaction("tx|test0001|1"));
        assertTrue(bc.addTransaction("tx|test0002|2"));
        assertEquals(3, bc.getPool().size());
        Transaction expected = new Transaction();

        expected.setContent("Any content below 70 characters");
        expected.setSender("abcd1234");
        assertTrue(bc.getPool().contains(expected));

        expected.setContent("1");
        expected.setSender("test0001");
        assertTrue(bc.getPool().contains(expected));

        expected.setContent("2");
        expected.setSender("test0002");
        assertTrue(bc.getPool().contains(expected));
    }

    /**
     * Purpose: Ensure commit modifies Blockchain correctly and that it is only valid when the hash starts with "A"
     * Input: a valid transaction
     * Expected: when the commit is false then no modification to the pool or head happens. When commit is true then pool is cleared and head is updated
     * Actual: when the commit is false then no modification to the pool or head happens. When commit is true then pool is cleared and head is updated
     */
    @Test
    public void commit() {
        Blockchain bc = new Blockchain();
        assertTrue(bc.addTransaction("tx|test0001|1"));
        assertEquals(1, bc.getPool().size());
        int nonce = 0;
        while(!bc.commit(nonce)) {
            //if false then nonce doesn't generate valid hash
            nonce++;
            assertEquals(1, bc.getPool().size());
            assertNull(bc.getHead());
            assertEquals(0, bc.getLength());
        }
        assertTrue(bc.getPool().isEmpty());
        assertNotNull(bc.getHead());
        Transaction expected = new Transaction();
        expected.setContent("1");
        expected.setSender("test0001");
        assertTrue(bc.getHead().getTransactions().contains(expected));
        assertEquals(1, bc.getLength());
    }
}