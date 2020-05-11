import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class StratifiedFolds {
    public static void main(String[] args) throws IOException {
        String filepath = args[0];
        BufferedReader reader = new BufferedReader(new FileReader(new File(filepath)));
        ArrayList<String[]> data_yes = new ArrayList<>();
        ArrayList<String[]> data_no = new ArrayList<>();
        int total = 0;

        String line = reader.readLine();
        while (line != null && !line.equals("")) {
            String[] input = line.split(",");

            String classifier = input[input.length - 1].replaceAll("\\s", "");
            if (classifier.equals("yes")) {
                data_yes.add(input);
            } else {
                data_no.add(input);
            }
            total++;
            line = reader.readLine();
        }
        reader.close();

        //instantiate folds
        ArrayList<ArrayList<String[]>> folds = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ArrayList<String[]> fold = new ArrayList<>();
            folds.add(fold);
        }

        //add yes
        int fold = 0;
        for (int i = 0; i < data_yes.size(); i++) {
            folds.get(fold).add(data_yes.get(i));
            fold++;
            if (fold >= 10) {
                fold = 0;
            }
        }

        //add no
        fold = 0;
        for (int i = 0; i < data_no.size(); i++) {
            folds.get(fold).add(data_no.get(i));
            fold++;
            if (fold >= 10) {
                fold = 0;
            }
        }


        FileWriter writer = new FileWriter(new File("pima-folds.csv"));

        //write to pima-folds
        for (int i = 0; i < folds.size(); i++) {
            writer.write(("fold" + (i + 1)) + "\n");
            writer.flush();
            for (int j = 0; j < folds.get(i).size(); j++) {
                writer.write(Arrays.toString(folds.get(i).get(j)).replaceAll("\\s", "")
                        .replace("[", "").replace("]", "") + "\n");
                writer.flush();
            }
            writer.write("\n");
            writer.flush();
        }

        writer.close();

        for (int i = 0; i < folds.size(); i++) {
            System.out.println(folds.get(i).size());
        }

    }

}
