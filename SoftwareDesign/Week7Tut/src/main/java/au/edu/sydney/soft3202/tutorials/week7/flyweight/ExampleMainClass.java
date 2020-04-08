package au.edu.sydney.soft3202.tutorials.week7.flyweight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExampleMainClass {
    public static void main(String[] args) {
        List<GUIElement> elements = new ArrayList<>();

        Random rand = new Random();

        for (int i = 0; i < 100; i++) {
            elements.add(new GUIElement(rand.nextInt(3)));
        }

        for (GUIElement element: elements) {
            element.display();
        }

        System.out.println("Total LargeImages created: " + LargeImage.count);
    }
}
