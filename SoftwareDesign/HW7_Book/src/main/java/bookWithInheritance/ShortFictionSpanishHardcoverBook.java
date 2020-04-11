package bookWithInheritance;

public class ShortFictionSpanishHardcoverBook extends FictionSpanishHardcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Short Book");
    }
}
