package abstractfactory;

// Product (abstract)
abstract class AbstractIDE {
    // abstract method
    public abstract int getCost();
};

// Concrete Product
class WindowsIDE extends AbstractIDE {
    public int getCost() {
        return 4500;
    }
};

// Concrete Product
class MacIDE extends AbstractIDE {
    public int getCost() {
        return 99;
    }
};

// Product (abstract)
abstract class AbstractMalware {
    public abstract int getFrequency();
};

// Concrete Product
class WindowsMalware extends AbstractMalware {
    public int getFrequency()  {
        return 9999;
    }
};

// Concrete Product
class MacMalware extends AbstractMalware {
    public int getFrequency() {
        return 1;
    }
};

// Factory (abstract)
abstract class AbstractOS {
    public abstract AbstractIDE makeIDE();
    public abstract AbstractMalware makeMalware();
};

// Concrete Factory
class MacOS extends AbstractOS {

    @Override
    public AbstractIDE makeIDE() {
        return new MacIDE();
    }

    @Override
    public AbstractMalware makeMalware() {
        return new MacMalware();
    }
}

class WindowsOS extends AbstractOS {

    @Override
    public AbstractIDE makeIDE() {
        return new WindowsIDE();
    }

    @Override
    public AbstractMalware makeMalware() {
        return new WindowsMalware();
    }
}
