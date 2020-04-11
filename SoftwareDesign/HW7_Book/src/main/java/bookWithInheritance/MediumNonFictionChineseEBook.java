package bookWithInheritance;

public class MediumNonFictionChineseEBook extends NonFictionChineseEBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Medium Book");
    }
}
