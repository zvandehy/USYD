package bookWithInheritance;

public class ShortNonFictionEnglishEBook extends NonFictionEnglishEBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Short Book");
    }
}
