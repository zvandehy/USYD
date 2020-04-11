package bookWithInheritance;

public class ShortFictionSpanishEBook extends FictionSpanishEBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Short Book");
    }
}
