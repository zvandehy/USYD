package bookWithInheritance;

public class FictionChineseSoftcoverBook extends ChineseSoftcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("Reading Fiction");
    }
}
