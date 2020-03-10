package Tut2;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CollisionTestSuiteParameterized {
    //parameters of the checkCollision method
    @Parameter()
    public double boundX;
    @Parameter(1)
    public double boundY;
    @Parameter(2)
    public double c1X;
    @Parameter(3)
    public double c1Y;
    @Parameter(4)
    public double c1R;
    @Parameter(5)
    public Double c2X;
    @Parameter(6)
    public Double c2Y;
    @Parameter(7)
    public Double c2R;
    @Parameter(8)
    public boolean result;

    //create the array of test data
    @Parameters
    public static Collection<Object[]> data(){
        Object[][] data = new Object[][]{
                {2,5,1,6,2,null,null,null,true}, //3 null & x out of bounds
                {5,2,6,1,2,null,null,null,true}, //3 null & y out of bounds
                //would continue to add examples as they relate the the
                //tests shown in Tut2.CollisionTestSuite
        };
        return Arrays.asList(data);
    }

    @Test
    public void testCheckCollision() {
        assertEquals(result, checkCollision(boundX,boundY,c1X,c1Y,c1R,c2X,c2Y,c2R));
    }

    //created this just so there aren't compile errors
    private boolean checkCollision(double boundX, double boundY, double c1X, double c1Y, double c1R, Double c2X, Double c2Y, Double c2R) {
        return true;
    }
}
