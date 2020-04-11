package bookWithInheritance;

public class MediumNonFictionEnglishSoftCoverBook extends NonFictionEnglishHardcoverBook {
    @Override
    public void read() {
        super.read();
        System.out.println("The book is a Medium Book");
    }
}
