package bookWithInheritance;

public class NonFictionEnglishHardcoverBook extends EnglishHardcoverBook{
    @Override
    public void read() {
        super.read();
        System.out.println("Reading Non-Fiction");
    }
}
