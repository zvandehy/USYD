package au.edu.sydney.soft3202.tutorials.week7.quiz.withInheritance;

public class SlowElectricPlane extends SlowElectricVehicle {
    @Override
    public void drive() {
        System.out.println("Plane is flying.");
        System.out.println(super.getSpeedOutput());
        System.out.println(super.getEngineOutput());
    }
}
