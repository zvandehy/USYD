package bookWithInheritance;

public class ShortNonFictionSpanishEBook extends NonFictionSpanishEBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Short Book");
    }
}
