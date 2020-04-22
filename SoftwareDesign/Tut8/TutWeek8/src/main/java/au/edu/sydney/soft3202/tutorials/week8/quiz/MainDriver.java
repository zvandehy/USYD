package au.edu.sydney.soft3202.tutorials.week8.quiz;

public class MainDriver {
    public static void main(String[] args) {
        Animal first = new Animal(0);
        Animal second = new Animal(1);
        Animal third = new Animal(2);

        System.out.println("The first animal has " + first.getNumLegs() + " legs.");
        System.out.println("The second animal is a " + second.getAnimalClass() + ".");
        System.out.println("The third animal is " + third.getAge() + " years old.");
    }
}
