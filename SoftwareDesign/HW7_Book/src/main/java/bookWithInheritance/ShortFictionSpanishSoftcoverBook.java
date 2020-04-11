package bookWithInheritance;

public class ShortFictionSpanishSoftcoverBook extends FictionSpanishSoftcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Short Book");
    }
}
