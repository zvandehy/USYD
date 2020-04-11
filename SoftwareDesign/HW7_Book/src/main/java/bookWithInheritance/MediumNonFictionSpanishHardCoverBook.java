package bookWithInheritance;

public class MediumNonFictionSpanishHardCoverBook extends NonFictionSpanishHardcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Medium Book");
    }
}
