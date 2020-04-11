package bookWithInheritance;

public class MediumFictionSpanishEBook extends FictionSpanishEBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Medium Book");
    }
}
