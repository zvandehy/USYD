package bookWithInheritance;

public class NonFictionSpanishSoftcoverBook extends SpanishSoftcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("Reading Non-Fiction");
    }
}
