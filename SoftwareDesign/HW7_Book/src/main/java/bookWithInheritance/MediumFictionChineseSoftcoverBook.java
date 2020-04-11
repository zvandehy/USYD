package bookWithInheritance;

public class MediumFictionChineseSoftcoverBook extends FictionChineseSoftcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Medium Book");
    }
}
