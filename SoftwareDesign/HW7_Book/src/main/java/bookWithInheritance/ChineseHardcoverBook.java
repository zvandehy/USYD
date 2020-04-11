package bookWithInheritance;

public abstract class ChineseHardcoverBook extends HardcoverBook{
    @Override
    public void read() {
        super.read();
        System.out.println("Reading in Chinese");
    }
}
