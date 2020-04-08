package au.edu.sydney.soft3202.tutorials.week7.quiz.withInheritance;

public class FastPetrolPlane extends FastPetrolVehicle {
    @Override
    public void drive() {
        System.out.println("Plane is flying.");
        System.out.println(super.getSpeedOutput());
        System.out.println(super.getEngineOutput());
    }
}
