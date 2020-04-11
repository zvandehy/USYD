package bookWithInheritance;

public abstract class EnglishHardcoverBook extends HardcoverBook{
    @Override
    public void read() {
        super.read();
        System.out.println("Reading in English");
    }
}
