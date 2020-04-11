package bookWithInheritance;

public class NonFictionChineseHardcoverBook extends ChineseHardcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("Reading Non-Fiction");
    }
}
