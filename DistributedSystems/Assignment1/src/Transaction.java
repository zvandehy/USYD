
public class Transaction {
    private String sender;
    private String content;


    //Todo: Make sure that it's okay to use a constructor instead of the setters
    public Transaction(String sender, String content) throws IllegalArgumentException {
        if (sender.matches("[a-z]{4}[0-9]{4}") && content.length() <= 70 && !content.contains("|")) {
            this.sender = sender;
            this.content = content;
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
