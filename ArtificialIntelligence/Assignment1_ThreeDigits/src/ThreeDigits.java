import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ThreeDigits {

    public static void main(String[] args) {
        String algorithm = args[0];
        String gameFile = args[1];
        Game game = new AlgorithmFactory().createAlgorithm(algorithm, gameFile);
//        System.out.println(game);
        game.processGame();
//        System.out.println("Thanks!");
    }
}
