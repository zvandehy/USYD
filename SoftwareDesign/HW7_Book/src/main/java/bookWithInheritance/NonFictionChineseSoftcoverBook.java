package bookWithInheritance;

public class NonFictionChineseSoftcoverBook extends ChineseSoftcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("Reading Non-Fiction");
    }
}
