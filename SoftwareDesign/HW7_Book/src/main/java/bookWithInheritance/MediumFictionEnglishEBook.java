package bookWithInheritance;

public class MediumFictionEnglishEBook extends FictionEnglishEBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Medium Book");
    }
}
