package bookWithInheritance;

public class ShortNonFictionChineseEBook extends NonFictionChineseEBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Short Book");
    }
}
