package threedigits;

public class Main {

    public static void main(String[] args) {
        String algorithm = args[1];
        String gameFile = args[2];
        switch(algorithm) {
            case "A":
                System.out.println("Use A* on: " + gameFile);
                break;
            case "B":
                System.out.println("Use BFS on: " + gameFile);
                break;
            case "D":
                System.out.println("Use DFS on: " + gameFile);
                break;
            case "I":
                System.out.println("Use IDS on: " + gameFile);
                break;
            case "G":
                System.out.println("Use Greedy on: " + gameFile);
                break;
            case "H":
                System.out.println("Use Hill-Climbing on: " + gameFile);
                break;
        }
        System.out.println("Thanks!");
    }
}
