package bookWithInheritance;

public class ShortNonFictionChineseHardcoverBook extends NonFictionChineseHardcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Short Book");
    }
}
