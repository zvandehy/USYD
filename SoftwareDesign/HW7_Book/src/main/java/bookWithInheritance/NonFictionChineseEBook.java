package bookWithInheritance;

public class NonFictionChineseEBook extends ChineseEBook {
    @Override
    public void read() {
        super.read();
        System.out.println("Reading Non-Fiction");
    }
}
