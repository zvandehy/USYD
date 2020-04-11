package bookWithInheritance;

public class MediumFictionEnglishHardcoverBook extends FictionEnglishHardcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Medium Book");
    }
}
