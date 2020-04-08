package au.edu.sydney.soft3202.tutorials.week7.quiz.withoutBridgeOrInheritance;

public class SlowPetrolCar implements Vehicle {
    @Override
    public void drive() {
        System.out.println("Car is driving.");
        System.out.println("Going kinda slow");
        System.out.println("There are petrol fumes spewing out the back.");
    }
}
