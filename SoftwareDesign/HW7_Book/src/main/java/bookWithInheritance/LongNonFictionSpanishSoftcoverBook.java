package bookWithInheritance;

public class LongNonFictionSpanishSoftcoverBook extends NonFictionSpanishSoftcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Long Book");
    }
}
