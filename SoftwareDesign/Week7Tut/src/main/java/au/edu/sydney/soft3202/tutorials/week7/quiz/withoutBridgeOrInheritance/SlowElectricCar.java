package au.edu.sydney.soft3202.tutorials.week7.quiz.withoutBridgeOrInheritance;

public class SlowElectricCar implements Vehicle {
    @Override
    public void drive() {
        System.out.println("Car is driving.");
        System.out.println("Going kinda slow");
        System.out.println("There is a quiet hum.");
    }
}
