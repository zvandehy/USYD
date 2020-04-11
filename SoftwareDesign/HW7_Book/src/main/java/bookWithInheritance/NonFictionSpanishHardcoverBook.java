package bookWithInheritance;

public class NonFictionSpanishHardcoverBook extends SpanishHardcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("Reading Non-Fiction");
    }
}
