package Tut2;/*
 * This Java source file was generated by the Gradle 'init' task.
 */

import org.junit.Test;
import static org.junit.Assert.*;

public class CollisionTestSuite {


    /*boolean checkCollision(double boundsX, double boundsY,
                           double circleOneX, double circleOneY, double circleOneRadius,
                           Double circleTwoX, Double circleTwoY, Double circleTwoRadius)
    */

    //all 3 null
    @Test public void All3NullXOutOfBounds() {
        fail();
    }
    @Test public void All3NullYOutOfBounds() {
        fail();
    }
    @Test public void All3NullXAndYOutOfBounds() {
        fail();
    }
    @Test public void All3NullInBounds() {
        fail();
    }

    //all 3 not null
    @Test public void All3NotNullXCollision() {
        fail();
    }
    @Test public void All3NotNullYCollision() {
        fail();
    }
    @Test public void All3NotNullXAndYCollision() {
        fail();
    }
    @Test public void All3NotNullNoCollision() {
        fail();
    }
    @Test public void All3NotNullCircle1OutOfBounds() {
        fail();
    }
    @Test public void Al3NotNullCircle2OutofBounds() {
        fail();
    }

    //radius null
    @Test public void RadiusNullXCollision() {
        fail();
    }
    @Test public void RadiusNullYCollision() {
        fail();
    }
    @Test public void RadiusNullXAndYCollision() {
        fail();
    }
    @Test public void RadiusNullNoCollision() {
        fail();
    }
    //null values
    @Test public void BoundXNull() {
        fail();
    }
    @Test public void BoundYNull() {
        fail();
    }
    @Test public void Circle1XNull() {
        fail();
    }
    @Test public void Circle1YNull() { fail();}
    @Test public void Circle1XAndYNull() {
        fail();
    }
    @Test public void Circle1RadiusNull() {
        fail();
    }
    @Test public void Circle2Only1OfXOrYNull() {
        fail();
    }

    //negative
    @Test public void testCheckCollisionAnyNegative() {
        fail();
    }

}
