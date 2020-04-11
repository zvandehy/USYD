package bookWithInheritance;

public class ShortFictionChineseSoftcoverBook extends FictionChineseSoftcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Short Book");
    }
}
