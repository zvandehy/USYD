package bookWithInheritance;

public class ShortFictionEnglishHardcoverBook extends FictionEnglishHardcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Short Book");
    }
}
