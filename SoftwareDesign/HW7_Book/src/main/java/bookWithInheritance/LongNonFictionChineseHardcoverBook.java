package bookWithInheritance;

public class LongNonFictionChineseHardcoverBook extends NonFictionChineseHardcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Long Book");
    }
}
