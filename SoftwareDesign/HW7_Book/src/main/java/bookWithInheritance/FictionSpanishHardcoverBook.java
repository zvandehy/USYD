package bookWithInheritance;

public class FictionSpanishHardcoverBook extends SpanishHardcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("Reading Fiction");
    }
}
