package bookWithInheritance;

public class FictionSpanishEBook extends SpanishEBook {
    @Override
    public void read() {
        super.read();
        System.out.println("Reading Fiction");
    }
}
