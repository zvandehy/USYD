package bookWithInheritance;

public class MediumFictionChineseEBook extends FictionChineseEBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Medium Book");
    }
}
