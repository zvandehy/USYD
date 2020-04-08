package au.edu.sydney.soft3202.tutorials.week7.chain;

public class Facade {

    private final AuthorisationLayer auth;

    public Facade(AuthorisationLayer auth) {
        this.auth = auth;
    }

    public void doSomething(Authorisation authorisation) {
        if (auth.authenticate(authorisation)) {
            System.out.println("Doing something");
        } else {
            System.out.println("Authorisation failed");
        }
    }
}
