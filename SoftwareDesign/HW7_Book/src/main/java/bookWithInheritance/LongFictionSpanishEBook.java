package bookWithInheritance;

public class LongFictionSpanishEBook extends FictionSpanishEBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Long Book");
    }
}
