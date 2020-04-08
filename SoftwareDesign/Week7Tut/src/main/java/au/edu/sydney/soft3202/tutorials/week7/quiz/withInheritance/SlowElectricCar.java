package au.edu.sydney.soft3202.tutorials.week7.quiz.withInheritance;

public class SlowElectricCar extends SlowElectricVehicle {
    @Override
    public void drive() {
        System.out.println("Car is driving.");
        System.out.println(super.getSpeedOutput());
        System.out.println(super.getEngineOutput());
    }
}
