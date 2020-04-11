package bookWithInheritance;

public class LongFictionChineseSoftcoverBook extends FictionChineseSoftcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Long Book");
    }
}
