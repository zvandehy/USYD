package bookWithInheritance;

public class ShortFictionEnglishEBook extends FictionEnglishEBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Short Book");
    }
}
