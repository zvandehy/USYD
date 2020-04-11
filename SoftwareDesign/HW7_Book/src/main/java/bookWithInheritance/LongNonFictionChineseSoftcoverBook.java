package bookWithInheritance;

public class LongNonFictionChineseSoftcoverBook extends NonFictionChineseSoftcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Long Book");
    }
}
