package au.edu.sydney.soft3202.tutorials.week8.quiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is an abstraction of a data source. It might be a network interface, it might be a sql database, it might be
 * a computational platform generating some data - this is not relevant.
 *
 * The important point is, getting information out of this data source takes a LONG TIME. In this instance it takes a
 * long time because of calls to Thread.sleep() - obviously this is just pretending to be something more complex.
 */
public class AnimalDataSource {
    private static List<Map<Field, String>> animals;

    static {
        animals = new ArrayList<>();

        Map<Field, String> bob = new HashMap<>();
        Map<Field, String> lucy = new HashMap<>();
        Map<Field, String> fred = new HashMap<>();

        bob.put(Field.NAME, "Bob");
        bob.put(Field.AGE, "7");
        bob.put(Field.ANIMAL_CLASS, "Mammal");
        bob.put(Field.COLOUR, "Brown");
        bob.put(Field.SPECIES, "Horse");
        bob.put(Field.NUM_LEGS, "4");

        lucy.put(Field.NAME, "Lucy");
        lucy.put(Field.AGE, "2");
        lucy.put(Field.ANIMAL_CLASS, "Reptile");
        lucy.put(Field.COLOUR, "Yellow");
        lucy.put(Field.SPECIES, "Python");
        lucy.put(Field.NUM_LEGS, "0");

        fred.put(Field.NAME, "Fred");
        fred.put(Field.AGE, "90");
        fred.put(Field.ANIMAL_CLASS, "Reptile");
        fred.put(Field.COLOUR, "Green");
        fred.put(Field.SPECIES, "Galapagos Turtle");
        fred.put(Field.NUM_LEGS, "4");

        animals.add(bob);
        animals.add(lucy);
        animals.add(fred);
    }

    static String getData(int animalKey, Field animalField) {
        Map<Field, String> animal = animals.get(animalKey);

        if (null == animal) {
            throw new IllegalArgumentException();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignored) {}

        return animal.get(animalField);
    }

    public enum Field {
        SPECIES, NUM_LEGS, COLOUR, NAME, AGE, ANIMAL_CLASS
    }
}
