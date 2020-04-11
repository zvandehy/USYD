package bookWithInheritance;

public class FictionSpanishSoftcoverBook extends SpanishSoftcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("Reading Fiction");
    }
}
