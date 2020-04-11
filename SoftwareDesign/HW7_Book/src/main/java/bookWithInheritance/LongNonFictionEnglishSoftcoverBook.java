package bookWithInheritance;

public class LongNonFictionEnglishSoftcoverBook extends NonFictionEnglishSoftcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Long Book");
    }
}
