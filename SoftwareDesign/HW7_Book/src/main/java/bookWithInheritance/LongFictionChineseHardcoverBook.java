package bookWithInheritance;

public class LongFictionChineseHardcoverBook extends FictionChineseHardcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Long Book");
    }
}
