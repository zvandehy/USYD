package bookWithInheritance;

public class ShortNonFictionEnglishHardcoverBook extends NonFictionEnglishHardcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Short Book");
    }
}
