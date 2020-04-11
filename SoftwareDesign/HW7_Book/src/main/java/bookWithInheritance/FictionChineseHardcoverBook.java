package bookWithInheritance;

public class FictionChineseHardcoverBook extends ChineseHardcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("Reading Fiction");
    }
}
