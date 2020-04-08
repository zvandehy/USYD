package au.edu.sydney.soft3202.tutorials.week7.quiz.withoutBridgeOrInheritance;

public class ExampleMainClass {
    public static void main(String[] args) {
        Vehicle vehicle = ExampleMainClass.getAVehicleFromSomewhere();

        vehicle.drive();
    }

    private static Vehicle getAVehicleFromSomewhere() {
        return new FastPetrolCar();
    }
}
