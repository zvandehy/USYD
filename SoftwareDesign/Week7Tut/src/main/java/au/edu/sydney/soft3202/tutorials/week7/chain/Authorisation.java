package au.edu.sydney.soft3202.tutorials.week7.chain;

public class Authorisation {
    private final int authNumber;
    private final String authString;
    private final boolean authBool;

    public Authorisation(int authNumber, String authString, boolean authBool) {
        this.authNumber = authNumber;
        this.authString = authString;
        this.authBool = authBool;
    }

    public int getAuthNumber() {
        return authNumber;
    }

    public String getAuthString() {
        return authString;
    }

    public boolean isAuthBool() {
        return authBool;
    }
}
