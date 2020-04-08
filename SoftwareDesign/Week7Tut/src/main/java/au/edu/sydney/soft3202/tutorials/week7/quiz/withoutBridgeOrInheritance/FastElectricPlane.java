package au.edu.sydney.soft3202.tutorials.week7.quiz.withoutBridgeOrInheritance;

public class FastElectricPlane implements Vehicle {
    @Override
    public void drive() {
        System.out.println("Plane is flying.");
        System.out.println("Going really fast!");
        System.out.println("There is a quiet hum.");
    }
}
