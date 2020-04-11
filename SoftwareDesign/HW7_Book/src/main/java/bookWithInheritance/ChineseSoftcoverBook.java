package bookWithInheritance;

public abstract class ChineseSoftcoverBook extends SoftcoverBook{
    @Override
    public void read() {
        super.read();
        System.out.println("Reading in Chinese");
    }
}
