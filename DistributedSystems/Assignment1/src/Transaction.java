
public class Transaction {
    private String sender;
    private String content;

    //constructor
    public Transaction(String txString) throws IllegalArgumentException {
        String header = txString.substring(0,3);
        String sender = txString.substring(3,11);
        String content = txString.substring(12);
        if (header.equals("tx|") && sender.matches("[a-z]{4}[0-9]{4}") && content.length() <= 70 && !content.contains("|")) {
            setSender(sender);
            setContent(content);
        }
        else throw new IllegalArgumentException("Sender must match uni-key format, content must contain 70 or less characters, and content may not contain '|'");
    }

    //getters and setters
    public void setSender(String sender) {
        this.sender = sender;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getSender() {return sender;}
    public String getContent() {return content;}

    public String toString() {
        return String.format("|%s|%70s|\n", sender, content);
    }

}
