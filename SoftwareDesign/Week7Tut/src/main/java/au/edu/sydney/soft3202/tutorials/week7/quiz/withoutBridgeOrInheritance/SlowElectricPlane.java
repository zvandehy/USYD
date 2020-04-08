package au.edu.sydney.soft3202.tutorials.week7.quiz.withoutBridgeOrInheritance;

public class SlowElectricPlane implements Vehicle {
    @Override
    public void drive() {
        System.out.println("Plane is flying.");
        System.out.println("Going kinda slow");
        System.out.println("There is a quiet hum.");
    }
}
