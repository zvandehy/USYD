package bookWithInheritance;

public class MediumFictionSpanishHardcoverBook extends FictionSpanishHardcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Medium Book");
    }
}
