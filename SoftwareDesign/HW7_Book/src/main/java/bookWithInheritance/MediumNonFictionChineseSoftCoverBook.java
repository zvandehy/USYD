package bookWithInheritance;

public class MediumNonFictionChineseSoftCoverBook extends NonFictionChineseHardcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Medium Book");
    }
}
