import java.util.ArrayList;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {


        //String + String
        long start = System.nanoTime();
        //This process needs to create a new string every time (allocate memory)
        String str = "";
        for(int i=0;i<1000; i++) {
            str+="string";
        }
        long end = System.nanoTime();
        long concatUsingPlusTime = end - start;

        //Create builder
        start = System.nanoTime();
        StringBuilder builder = new StringBuilder();
        end = System.nanoTime();
        long createBuilderTime = end - start;

        //append strings
        start = System.nanoTime();
        //builder already has a char array allocated to memory
        for(int i=0;i<1000; i++) {
            builder.append("str");
        }
        System.out.println(builder);
        end = System.nanoTime();
        long stringBuilderTime = end - start;

        //add() to List
        start = System.nanoTime();
        ArrayList<Integer> list = new ArrayList<>();
        for(int i=0;i<1000;i++) {
            list.add(i);
        }
        end = System.nanoTime();
        long addToList = end - start;

        //init to List
        start = System.nanoTime();
        ArrayList<Integer> list2 = new ArrayList<>(list);
        end = System.nanoTime();
        long initToList = end - start;

        LinkedList<Integer> longListOfStrings = new LinkedList<>();
        for(int i=0;i<100000; i++) {
            longListOfStrings.add(i);
        }

        //for enhanced
        start = System.nanoTime();
        int sum = 0;
        for(Integer i: longListOfStrings) {
            sum += i;
        }
        end = System.nanoTime();
        long enhancedFor = end - start;

        //for iterative
        sum = 0;
        start = System.nanoTime();
        for(int i=0;i<longListOfStrings.size();i++) {
            sum+=longListOfStrings.get(i);
        }
        end = System.nanoTime();
        long iterativeFor = end - start;

        //results
        System.out.println();
        System.out.println("String + String: " + concatUsingPlusTime);
        System.out.println("Create builder : " + createBuilderTime);
        System.out.println("Append Strings : " + stringBuilderTime);
        System.out.println();
        System.out.println("Add to List    : " + addToList);
        System.out.println("Init to List   : " + initToList);
        System.out.println();
        System.out.println("Enhanced For   : " + enhancedFor);
        System.out.println("Iterative For  : " + iterativeFor);





    }
}
