package bookWithInheritance;

public class MediumNonFictionSpanishEBook extends NonFictionSpanishEBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Medium Book");
    }
}
