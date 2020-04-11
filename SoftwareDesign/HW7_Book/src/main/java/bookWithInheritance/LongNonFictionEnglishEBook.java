package bookWithInheritance;

public class LongNonFictionEnglishEBook extends NonFictionEnglishEBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Long Book");
    }
}
