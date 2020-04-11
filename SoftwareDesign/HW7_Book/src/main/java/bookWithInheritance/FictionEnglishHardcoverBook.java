package bookWithInheritance;

public class FictionEnglishHardcoverBook extends EnglishHardcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("Reading Fiction");
    }
}
