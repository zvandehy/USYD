package bookWithInheritance;

public class NonFictionSpanishEBook extends SpanishEBook {
    @Override
    public void read() {
        super.read();
        System.out.println("Reading Non-Fiction");
    }
}
