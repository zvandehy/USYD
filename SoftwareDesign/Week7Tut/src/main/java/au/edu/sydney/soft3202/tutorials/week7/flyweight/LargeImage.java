package au.edu.sydney.soft3202.tutorials.week7.flyweight;

public class LargeImage {
    private final String imageData;

    public LargeImage(String imageData) {
        this.imageData = imageData;

        LargeImage.count++;
    }

    public void display() {
        System.out.println(imageData);
    }


    public static int count = 0;
}
