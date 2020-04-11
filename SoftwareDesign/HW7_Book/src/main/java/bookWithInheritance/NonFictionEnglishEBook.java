package bookWithInheritance;

public class NonFictionEnglishEBook extends EnglishEBook {
    @Override
    public void read() {
        super.read();
        System.out.println("Reading Non-Fiction");
    }
}
