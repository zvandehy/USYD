package bookWithInheritance;

public class FictionEnglishEBook extends EnglishEBook {
    @Override
    public void read() {
        super.read();
        System.out.println("Reading Fiction");
    }
}
