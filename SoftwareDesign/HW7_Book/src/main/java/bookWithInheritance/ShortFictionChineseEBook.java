package bookWithInheritance;

public class ShortFictionChineseEBook extends FictionChineseEBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Short Book");
    }
}
