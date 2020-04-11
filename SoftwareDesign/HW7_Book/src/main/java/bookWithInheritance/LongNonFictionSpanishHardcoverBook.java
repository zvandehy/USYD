package bookWithInheritance;

public class LongNonFictionSpanishHardcoverBook extends NonFictionSpanishHardcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Long Book");
    }
}
