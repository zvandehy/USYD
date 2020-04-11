package bookWithInheritance;

public class LongNonFictionEnglishHardcoverBook extends NonFictionEnglishHardcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Long Book");
    }
}
