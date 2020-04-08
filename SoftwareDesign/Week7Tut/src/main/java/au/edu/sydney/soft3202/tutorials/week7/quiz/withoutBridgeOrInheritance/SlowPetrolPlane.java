package au.edu.sydney.soft3202.tutorials.week7.quiz.withoutBridgeOrInheritance;

public class SlowPetrolPlane implements Vehicle {
    @Override
    public void drive() {
        System.out.println("Plane is flying.");
        System.out.println("Going kinda slow");
        System.out.println("There are petrol fumes spewing out the back.");
    }
}
