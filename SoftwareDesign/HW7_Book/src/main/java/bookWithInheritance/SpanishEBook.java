package bookWithInheritance;

public abstract class SpanishEBook extends EBook{
    @Override
    public void read() {
        super.read();
        System.out.println("Reading in Spanish");
    }
}
