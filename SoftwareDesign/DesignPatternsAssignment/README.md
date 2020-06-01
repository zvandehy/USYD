1. Issue: System uses too much RAM from Reports (SOLVED)
- This issue was solved by implementing the Flyweight Design Pattern
- The pattern was applied by creating a ReportData class that stores all of the arrays (double[]) that a report contains.
- Then, the ReportImpl class contains a reference to a ReportData object instead of each array.
- A ReportDataFactory was used to check if the "new" Report had the same data as a previously saved report (check if the ReportData are exactly equal). If the data is exactly the same (the report data is duplicated), then instead of storing the duplicated arrays the report is given a reference to the existing ReportData object. Otherwise, a new ReportData object is saved and cached.
- The ReportData and ReportDataFactory classes were added to the reports package
- TL;DR: Flyweight Pattern. Changes:
    * ReportImpl
    * ReportData
    * ReportDataFactory

2. Issue: The Order class load needs to be reduced (SOLVED)
- This issue was solved by implementing the Bridge Design Pattern
- An abstraction OrderImpl class defines/implements the code that is shared between every type of Order.
- An OrderImpl delegates behavior that depends on the order's priority or worktype to its appropriate implementor(s).
- 2 Implementor (interfaces) types were created: PriorityType and WorkType
    * PriorityType handles the logic associated with an Order's Priority
    * 2 Concrete PriorityType Implementors were created: CriticalPriority and RegularPriority
    * WorkType handles the logic associated with an Order's work.
    * The provided domain outlined 2 types of work: Audit and Regular.
    * Thus, 2 Concrete WorkType implementors were Created: AuditWorkType and RegularWorkType
    * More WorkType implementors can easily be created by implementing the WorkType interface
- Every OrderImpl has the same constructor which include Order information as well as Implementor interfaces. A type of order changes depending on the initialized implementor.
- For example:
    * An order that is critical should be initialized with a CriticalPriority implementor.
    * An order that is an audit should be initialized with an AuditWorkType implementor.
    * Thus, a CriticalAuditOrder class is replaced with an OrderImpl that has references to a CriticalPriority implementor and an AuditWorkType implementor.
- A ScheduledOrderImpl class is a Refined Abstraction that extends the OrderImpl class. Like OrderImpl, it also takes PriorityType and WorkType implementors to delegate behavior. It also takes information specific to the scheduling of the record (number of quarters).
- The ScheduledOrderImpl introduces some of its own methods that are needed for the Scheduling behavior.
- The ScheduledOrderImpl also overrides some methods from the OrderImpl because the behavior changes when the order is scheduled.
- These changes are reflected in the FEAAFacade createOrder() method. There is a comment next to each new OrderImpl() that shows what class in the original version of the project has the same behavior as the Bridge implementation.
- TL;DR: Bridge Pattern. Changes:
    * OrderImpl
    * ScheduledOrderImpl
    * PriorityType: CriticalPriority, RegularPriority
    * WorkType: AuditWorkType, RegularWorkType
    * FEAAFacade createOrder()
    
3. Issue: Contact Handling needs to be streamlined (SOLVED)
- This issue was solved by implementing the Chain Of Responsibility Pattern
- The ContactMethod enum was changed to a Handler interface that defines the setNext() and sendInvoice() methods
- A Concrete ContactMethod handler class was created for each type of contact method. (I will note that: If desired, these classes could likely replace the classes (with some changes to the concrete handlers, obviously) in the contact package to reduce the class load of the system).
- A concrete handler will handle sending the invoice if the client has set the appropriate contact information (return true). Otherwise, it will pass the request on to the next handler. If there is no next handler (null), then sending the invoice fails (return false).
- The FEAAFacade finaliseOrder() method was changed so that the list of ContactMethod enums now contains a list of the respective Concrete ContactMethod handlers instead.
- Finally, the ContactHandler class was changed so that it could build the chain of handlers in the correct order defined by the priority list. Once the chain is built, sending the invoice to the client is attempted by calling sendInvoice() with the first handler in the chain.
- TL;DR: Chain Of Responsibility Pattern. Changes:
    * ContactMethod
    * Concrete handlers for each contact method
    * ContactHandler
    * FEAAFacade finaliseOrder()

4. Issue: Loading Clients from DB takes a long time (SOLVED)
- This issue was solved by implementing Lazy Loading on the ClientImpl class
- The pattern was implemented Lazy Loading by creating a map in ClientImpl that tracks if a field has been loaded (true/false).
- The constructor doesn't make any calls to the database. Instead, it sets the clientID to the given id and initializes the map so that each client field is set to false.
- The getter methods check the map if a field has been loaded.
- If the map returns true for that field, then the getter simply returns the field.
- If the map returns false for that field, the database is called for that field. The field is saved and the map is set to true for that field. The field is returned.
- A ClientLoadLagTest shows how the Lazy Loading improves performance.
- Recommendation: the CLI needs changes so that it doesn't load each field of a client if the provided id is not found in the database. Right now, even though the client id is not in the database, each field is still loaded (as null) from the database which defeats the purpose.
- TL;DR: Lazy Loading. Changes:
    * ClientImpl

5. Issue: Can't easily compare Equality of Reports without primary key (SOLVED)
- This issue was solved by implementing Value Object on the Report (and ReportData) class(es)
- The pattern was implemented by making the ReportImpl and ReportData classes immutable and creating equals() and hashCode() methods.
- The hashCode() method creates a hashCode in ReportImpl by getting a hash for the name, commissionPerEmployee, and the data of the report and multiplying them by 31.
- Similarly, the ReportData creates a hashCode by getting a hash for each array and multiplying it by 31.
- The ReportImpl equals() method works by first comparing the hash of the other report and returning false if they are not equal (because it is impossible for two Reports to be equal if they do not have the same hashcode). If their hashCodes are the same, they still need to check the equality of the other name, commission, and data because there is a small chance that different reports can have the same hashcode.
- ReportData objects are equal if they have the exact same arrays. Like reports, if two ReportData do not have the same hashChode then they cannot be equal. The ReportDataFactory uses this equals() method to check if a ReportData has already been cached (Issue 1.)
- ReportData objects are immutable because they clone the array arguments in the constructor before saving the array into its field AND in the getters before returning the array. It also marks its fields as final. This is necessary because if the array that is stored in the field of the ReportData could be accessed outside of the ReportData, then its contents could be modified (even when the array is marked final).
- It should be noted that the arrays could still be modified if changes were made inside of the ReportData class. This can be avoided if the arrays (double[]) were replaced with Immutable/Unmodifiable List<Double>. This was not done in my solution because it required changing the datatype from double to Double, which breaks out-of-scope code. (There are workarounds, but I decided that those workarounds were either too performance costly or unstable because they were marked @Beta).
- ReportImpl objects are immutable because all of its fields are marked final and there is no way to access the ReportData field.
- The ReportImpl delegates getters for its data to its ReportData object, which as I have already discussed ensures immutability.
- Using the hashCodes in the equals method improves performance for when different ReportImpl or ReportData objects are compared because the arrays do not have to be compared. It has no effect on ReportImpl or ReportData objects that are equal/duplicates because they would have had to make the array comparisons anyways.
- However, it does add the cost computing the hashCode() to the constructor.
- TL;DR: Value Object. Changes:
    * ReportImpl
    * (ReportData)
    
6. Issue: Order Creation process has lots of lag (NOT SOLVED)
- I suggest solving the issue with the Unit Of Work pattern

7. Issue: System is currently single-threaded and can't move slow processes to the background (NOT SOLVED)
- Perhaps after Unit of Work is implemented, the slow commit() processes could be performed in another thread?
