package au.edu.sydney.soft3202.tutorials.week7.flyweight;

public class GUIElement {
    private LargeImage largeImage;

    public GUIElement(int type) {
        this.largeImage = new LargeImage("Large image data: " + type);
    }

    public void display() {
        System.out.println("GUI Element");
        largeImage.display();
    }
}
