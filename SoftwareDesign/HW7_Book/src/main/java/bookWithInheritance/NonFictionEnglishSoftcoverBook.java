package bookWithInheritance;

public class NonFictionEnglishSoftcoverBook extends EnglishSoftcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("Reading Non-Fiction");
    }
}
