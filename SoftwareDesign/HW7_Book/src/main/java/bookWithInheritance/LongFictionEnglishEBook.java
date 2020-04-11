package bookWithInheritance;

public class LongFictionEnglishEBook extends FictionEnglishEBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Long Book");
    }
}
