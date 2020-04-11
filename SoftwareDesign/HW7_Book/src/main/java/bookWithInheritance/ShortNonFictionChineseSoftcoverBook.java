package bookWithInheritance;

public class ShortNonFictionChineseSoftcoverBook extends NonFictionChineseSoftcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Short Book");
    }
}
