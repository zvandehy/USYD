package bookWithInheritance;

public class FictionChineseEBook extends ChineseEBook {
    @Override
    public void read() {
        super.read();
        System.out.println("Reading Fiction");
    }
}
