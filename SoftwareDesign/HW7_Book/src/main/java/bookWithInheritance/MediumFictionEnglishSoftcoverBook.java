package bookWithInheritance;

public class MediumFictionEnglishSoftcoverBook extends FictionEnglishSoftcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Medium Book");
    }
}
