package bookWithInheritance;

public class LongFictionEnglishSoftcoverBook extends FictionEnglishSoftcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Long Book");
    }
}
