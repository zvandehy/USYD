package bookWithInheritance;

public class LongFictionSpanishSoftcoverBook extends FictionSpanishSoftcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Long Book");
    }
}
