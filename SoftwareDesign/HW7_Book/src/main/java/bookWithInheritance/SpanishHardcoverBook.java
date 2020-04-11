package bookWithInheritance;

public abstract class SpanishHardcoverBook extends HardcoverBook{
    @Override
    public void read() {
        super.read();
        System.out.println("Reading in Spanish");
    }
}
