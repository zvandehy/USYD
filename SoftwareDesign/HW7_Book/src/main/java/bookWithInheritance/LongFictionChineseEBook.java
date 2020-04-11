package bookWithInheritance;

public class LongFictionChineseEBook extends FictionChineseEBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Long Book");
    }
}
