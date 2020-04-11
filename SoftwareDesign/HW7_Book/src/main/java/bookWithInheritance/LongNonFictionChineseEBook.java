package bookWithInheritance;

public class LongNonFictionChineseEBook extends NonFictionChineseEBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Long Book");
    }
}
