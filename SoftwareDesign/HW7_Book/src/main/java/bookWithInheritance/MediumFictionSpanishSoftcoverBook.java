package bookWithInheritance;

public class MediumFictionSpanishSoftcoverBook extends FictionSpanishSoftcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Medium Book");
    }
}
