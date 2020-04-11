package bookWithInheritance;

public abstract class EnglishSoftcoverBook extends SoftcoverBook{
    @Override
    public void read() {
        super.read();
        System.out.println("Reading in English");
    }
}
