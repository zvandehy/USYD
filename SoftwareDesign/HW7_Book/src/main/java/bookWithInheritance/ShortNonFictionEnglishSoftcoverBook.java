package bookWithInheritance;

public class ShortNonFictionEnglishSoftcoverBook extends NonFictionEnglishSoftcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Short Book");
    }
}
