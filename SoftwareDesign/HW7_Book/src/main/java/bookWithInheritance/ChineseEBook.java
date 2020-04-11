package bookWithInheritance;

public abstract class ChineseEBook extends EBook{
    @Override
    public void read() {
        super.read();
        System.out.println("Reading in Chinese");
    }
}
