package bookWithInheritance;

public class LongFictionEnglishHardcoverBook extends FictionEnglishHardcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Long Book");
    }
}
