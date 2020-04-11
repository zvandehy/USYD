package bookWithInheritance;

public class MediumFictionChineseHardcoverBook extends FictionChineseHardcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Medium Book");
    }
}
