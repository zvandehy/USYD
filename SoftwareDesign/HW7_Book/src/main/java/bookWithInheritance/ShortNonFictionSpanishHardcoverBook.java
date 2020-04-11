package bookWithInheritance;

public class ShortNonFictionSpanishHardcoverBook extends NonFictionSpanishHardcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Short Book");
    }
}
