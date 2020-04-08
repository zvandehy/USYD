/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package mypackage;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class MyClassTest {
    @Test
    public void testDoSomething() {
        //Mockito object that captures String arguments
        ArgumentCaptor<String> myDependencyStringCaptor = ArgumentCaptor.forClass(String.class);
        //Mockito object that captures Integer arguments
        ArgumentCaptor<Integer> myDependencyIntegerCaptor = ArgumentCaptor.forClass(Integer.class);
        //MyDependency mock
        MyDependency mock = Mockito.mock(MyDependency.class);
        //MyClass test
        MyClass myClass = new MyClass(mock);
        //doSomething calls getSomeResult(String param, int paramInt) some unknown number of times
        myClass.doSomething();
        //verify that doSomething() calls getSomeResult() at least 0 times
        //save the String and int arguments in the respective ArgumentCaptors
        verify(mock, atLeast(0)).getSomeResult(myDependencyStringCaptor.capture(), myDependencyIntegerCaptor.capture());
        //get the arguments that were passed from doSomething() to getSomeResult()
        List<String> capturedStrings = myDependencyStringCaptor.getAllValues();
        List<Integer> capturedInts = myDependencyIntegerCaptor.getAllValues();
        String string1 = "";
        String string2 = "";
        String int1 = "";
        String int2 = "";

        if(capturedStrings.size() >= 1) {
            string1 = capturedStrings.get(0);
            int1 = "" + capturedInts.get(0);
        }
        if(capturedStrings.size() >= 2) {
            string2 = capturedStrings.get(1);
            int2 = "" + capturedInts.get(1);
        }

        System.out.println("******************************");
        System.out.println("MyClass.doSomething Test Results:");
        System.out.println("Number of times MyDependency.getSomeResult was called: " + capturedStrings.size());
        System.out.println("String parameter given to first MyDependency.getSomeResult call (if any): " + string1);
        System.out.println("String parameter given to second MyDependency.getSomeResult call (if any): " + string2);
        System.out.println(" int parameter given to first MyDependency.getSomeResult call (if any): " + int1);
        System.out.println(" int parameter given to second MyDependency.getSomeResult call (if any): " + int2);
        System.out.println("******************************\n");
    }
}