package bookWithInheritance;

public class MediumNonFictionChineseHardCoverBook extends NonFictionChineseHardcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Medium Book");
    }
}
