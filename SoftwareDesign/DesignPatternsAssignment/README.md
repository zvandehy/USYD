1'. Problem: Too much RAM from Reports
- Flyweight
- There is at least one copy of each report in the system
- Report could be the intrinsic state of an order

2'. So many types of orders
- Bridge...
- We abstract: (if ignore scheduled)
    - Depends on orderType (abstract it)
    - Depends on critical (abstract it)
    - Need to be creative to abstract scheduled
        - Without scheduled it looks like the regular bridge pattern
        - With scheduled, you need to be more creative
        
3'. Contact Handling
- Chain Of Responsability
- Need to Review this more

4'. Loading Clients from DB takes a long time (DONE)
- Lazy Loading
- I implemented Lazy Loading on the ClientImpl class by creating a map that tracks if a field has been loaded (true/false).
The constructor doesn't make any calls to the database. The database is called if a field is requested and it has not been loaded yet.
One concern: I am storing the AuthToken in the Client class. This doesn't break authentication because of how the tokens are handled (when the token is used, the system checks if it is still a valid token).

5'. Compare Equality of Reports without primary key (DONE)
- Value Object
- When you have an array, you want an immutable list
- Could use Hashcode as a field, and generate the hash when the value object is initialized
- Then in the equals(), we can check the hashcode, and if they are equal then check the equality. Otherwise, we know that they are different and we don’t have to go through the pain of checking every value in each Array.

6'. Order Creation process has lots of lag
- Unit Of Work

7'. Multithreading

