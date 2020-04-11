package bookWithInheritance;

public class FictionEnglishSoftcoverBook extends EnglishSoftcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("Reading Fiction");
    }
}
