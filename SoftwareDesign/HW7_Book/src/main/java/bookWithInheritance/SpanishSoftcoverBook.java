package bookWithInheritance;

import bookWithBridge.SoftCover;

public abstract class SpanishSoftcoverBook extends SoftcoverBook{
    @Override
    public void read() {
        super.read();
        System.out.println("Reading in Spanish");
    }
}
