package bookWithInheritance;

public class MediumNonFictionEnglishEBook extends NonFictionEnglishEBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Medium Book");
    }
}
