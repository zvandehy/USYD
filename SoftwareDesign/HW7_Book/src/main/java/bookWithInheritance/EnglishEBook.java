package bookWithInheritance;

public abstract class EnglishEBook extends EBook{
    @Override
    public void read() {
        super.read();
        System.out.println("Reading in English");
    }
}
