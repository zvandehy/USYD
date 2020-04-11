package bookWithInheritance;

public class ShortFictionEnglishSoftcoverBook extends FictionEnglishSoftcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Short Book");
    }
}
