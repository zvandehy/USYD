package bookWithInheritance;

public class ShortNonFictionSpanishSoftcoverBook extends NonFictionSpanishSoftcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Short Book");
    }
}
