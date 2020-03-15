import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AlgorithmFactory {

    public Game createAlgorithm(String algorithm, String gameFile) throws IllegalArgumentException{
        //get game specs from gameFile
        File file = new File("./"+gameFile);
        BufferedReader reader = null;
        String start = "start";
        String goal = "goal";
        String forbidden = "forbidden";
        try {
            reader = new BufferedReader(new FileReader(file));
            start = reader.readLine();
            goal = reader.readLine();
            forbidden = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> forbiddenList;
        try {
            forbiddenList = Stream.of(forbidden.split(",")).collect(Collectors.toList());
        } catch(Exception e) {
            forbiddenList = new ArrayList<String>();
        }

        //print game specs
//        System.out.println("### SETUP GAME ####");
//        System.out.println("Start    : " + start);
//        System.out.println("Goal     : " + goal);
//        System.out.println("Forbidden: " + forbiddenList + "\n");

        switch(algorithm) {
            case "A":
                return new AStar(start, goal, forbiddenList);
            case "B":
                return new BreadthFirstSearch(start, goal, forbiddenList);
            case "D":
                return new DepthFirstSearch(start, goal, forbiddenList);
            case "I":
                return new IterativeDeepeningSearch(start, goal, forbiddenList);
            case "G":
                return new Greedy(start, goal, forbiddenList);
            case "H":
                return new HillClimbing(start, goal, forbiddenList);
            default:
                throw new IllegalArgumentException("Invalid algorithm");
        }
    }

}
