package au.edu.sydney.pac.erp.client;

public class ClientFactory {
    /* NOTES ON WHAT A FACTORY IS
       - Objects are created in the factory so that the objects can focus on other behavior
       - Concrete instantiation is when you actually instantiate a class ("new")
       - We create a concrete instantiation of a certain type depending on the input (usually String)
       of some method that is called to create the Object
       - Other classes will use the factory to delegate concrete instantiation to the factory
       - This makes it easier to make changes to your instantiation code later, instead of changing it in all
       of the classes that would use the factory (those classes are referred to as "client")
       - The above describes a factory object, below we will investigate factory method pattern
       - The factory method pattern works slightly differently. We create an abstract factory class that will
       implement the methods that all types of factories will need, but not the factory (creation) method. Instead,
       subclasses of the abstract factory will implement the factory method so that they can create the concrete
       instantiation of the object that they want. Ex: BudgetKnifeFactory creates different types of Budget knives,
       where the QualityKnifeFactory creates different types of Quality knives. In this example, the two factories
       have different creation/factory methods, but the abstract class implementation of other knife factory methods
       would be sufficient for both.
     */

}
