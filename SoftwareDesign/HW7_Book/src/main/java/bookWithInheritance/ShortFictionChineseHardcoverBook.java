package bookWithInheritance;

public class ShortFictionChineseHardcoverBook extends FictionChineseHardcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Short Book");
    }
}
