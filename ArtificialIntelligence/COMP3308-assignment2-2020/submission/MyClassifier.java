import java.io.*;
import java.util.*;

public class MyClassifier {

    public static void main(String[] args) {
        if(args.length == 3) {
            String trainingFilePath = args[0];
            String testingFilePath = args[1];
            String classifier = args[2];
            trainAndTest(trainingFilePath, testingFilePath, classifier);
        } else if (args.length == 2) {
            String filePath = args[0];
            String classifier = args[1];
            trainFolds(filePath, classifier);
        } else {
            System.out.println("Invalid Input");
            System.exit(0);
        }




    }

    private static void trainFolds(String filePath, String classifier) {
        try {
            int count = 1;
            ArrayList<ArrayList<String[]>> folds = new ArrayList<>();
            double total_accuracy = 0.0;

            ArrayList<String[]> fold = getFold(filePath, count);
            while(!fold.isEmpty()) {
                folds.add(fold);
                count++;
                fold = getFold(filePath, count);
            }

            count = 1;
            FileWriter test_writer = new FileWriter(new File("testing.csv"));
            FileWriter train_writer = new FileWriter(new File("training.csv"));

            ArrayList<String> expectedClass = new ArrayList<>();

            //for each set of folds
            for(int i=0;i<folds.size(); i++) { //size should be 10
                ArrayList<String[]> testingFold = folds.get(i);
                System.out.println("TEST: ");
                System.out.println("fold" + count);
                System.out.println("example: " + Arrays.toString(testingFold.get(0)).replaceAll("\\s", "")
                        .replace("[", "").replace("]", ""));
                //get examples for testing
                for (String[] test_example : testingFold) {
                    if (test_example.length > 1) {
                        expectedClass.add(test_example[test_example.length - 1]);
                        test_example = Arrays.copyOf(test_example, test_example.length - 1);
                        String exampleAsString = Arrays.toString(test_example).replaceAll("\\s", "")
                                .replace("[", "").replace("]", "");
                        test_writer.write(exampleAsString + "\n");
                        test_writer.flush();
                    }
                }
                System.out.println("\n" + "TRAIN: ");

                //get each training fold
                for(int k=0;k<folds.size(); k++) {

                    if(i!=k) {//if fold isn't the testing fold
                        ArrayList<String[]> trainingFold = folds.get(k);
                        System.out.println("fold" + (k+1));
                        System.out.println("example: " + Arrays.toString(trainingFold.get(0)).replaceAll("\\s", "")
                                .replace("[", "").replace("]", ""));
                        //get examples for each training fold
                        for (String[] train_example : trainingFold) {
                            String exampleAsString = Arrays.toString(train_example).replaceAll("\\s", "")
                                    .replace("[", "").replace("]", "");
                            if (!exampleAsString.equals("")) {
                                train_writer.write(exampleAsString + "\n");
                                train_writer.flush();
                            }
                        }
                    }
                }
                ArrayList<String> results = trainAndTest("training.csv", "testing.csv", classifier);
                int correct = 0;
                for(int j=0;j<results.size();j++) {
                    String result = results.get(j).replaceAll("\\s","");
                    String expected = expectedClass.get(j).replaceAll("\\s","");
                    if(result.equals(expected)) {
                        correct++;
                    }
                }
                total_accuracy += correct / (double) results.size();
                if(i!=9) {
                    test_writer = new FileWriter(new File("testing.csv"));
                    train_writer = new FileWriter(new File("training.csv"));
                    expectedClass = new ArrayList<>();
                }
                count++;
                System.out.println();
                System.out.println();
            }
            double average_accuracy = total_accuracy / (double) folds.size();
            System.out.println(average_accuracy);

        } catch(Exception e) {
            e.printStackTrace();
        }



        //for each set of folds
        //get examples for training
        //get examples for testing
        //train classifier with those examples and get results of testing data
        //compare results to actual -> get accuracy
        //adjust average accuracy



    }

    /**
     * Main method for PASTA tests.
     * @param trainingFilePath
     * @param testingFilePath
     * @param classifier
     */
    private static ArrayList<String> trainAndTest(String trainingFilePath, String testingFilePath, String classifier) {
        MyClassifier myClassifier = new MyClassifier();
        if (classifier.substring(1).equals("NN")) {
            int k = Integer.parseInt(classifier.substring(0, 1));
            try {
                return myClassifier.kNN(k, trainingFilePath, testingFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (classifier.equals("NB")) {
            try {
                return myClassifier.NB(trainingFilePath, testingFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    /**
     * Use K-Nearest Neighbor to classify given data
     * @param k - number of nearest neighbors
     * @param trainingFile - File path to training data
     * @param testingFile - File path to testing data
     */
    public ArrayList<String> kNN(int k, String trainingFile, String testingFile) throws IOException {
        ArrayList<String[]> training = getData(trainingFile);
        ArrayList<String[]> testing = getData(testingFile);

        ArrayList<String> results = new ArrayList<>();
        for(String[] test : testing) {
            ArrayList<Data> distances = new ArrayList<>();
            for(String[] train : training) {
                double distance = euclidean(test, train);
                String classifier = train[train.length-1];
                distances.add(new Data(distance, classifier));
            }
            distances.sort(Data::compareTo);
            int yes = 0;
            int no = 0;
            for(int i=0;i<k;i++) {
                if(distances.get(i).classifier.contains("yes")) {
                    yes++;
                } else {
                    no++;
                }
            }
            if(yes >= no) {
                System.out.println("yes");
                results.add("yes");
            } else {
                System.out.println("no");
                results.add("no");
            }
        }
        return results;
    }

    /**
     * Holds distance and classifier information
     */
    private static class Data implements Comparable<Data>{
        public double distance;
        public String classifier;
        public Data(double distance, String classifier) {
            this.distance = distance;
            this.classifier = classifier;
        }
        public String toString() {
            return distance + " - " + classifier;
        }

        @Override
        public int compareTo(Data o) {
            if(this.distance > o.distance) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Given a testing example and training example, calculate the euclidian distance between their attributes
     * @param test - test example
     * @param train - train example
     * @return the euclidean distance between the two examples
     */
    private double euclidean(String[] test, String[] train) {
        float sum = 0;
        //i represents each attribute
        for(int i=0; i<test.length; i++) {
            float a = Float.parseFloat(train[i]);
            float b = Float.parseFloat(test[i]);
            float difference = (a - b);
            float square = difference * difference;
            sum += square;
        }
        return Math.sqrt(sum);
    }

    /**
     * Create a representation of the data in the provided file
     * @param file - the file with the data
     * @return a 2D array(list) of the data in the provided file
     * @throws IOException if file name results in an error
     */
    public ArrayList<String[]> getData(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
        ArrayList<String[]> data = new ArrayList<>();

        String line = reader.readLine();
        while(line != null && !line.equals("")) {
            String[] input = line.split(",");
            data.add(input);
            line = reader.readLine();
        }
        return data;
    }

    public static ArrayList<String[]> getFold(String file, int fold) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
        ArrayList<String[]> data = new ArrayList<>();

        String line = reader.readLine();
        boolean ready = false;
        while(line != null) {
            if(line.contains("fold" + fold)) {
                ready = true;
            } else if(line.contains("fold") && !line.contains("fold" + fold)) {
                ready = false;
            } else if(ready){
                String[] input = line.split(",");
                data.add(input);
            }
            line = reader.readLine();
        }
        return data;
    }

    /**
     * Use Naive Bayes to classify given data
     * @param trainingFile - File path to training data
     * @param testingFile - File path to testing data
     */
    public ArrayList<String> NB(String trainingFile, String testingFile) throws IOException {
        //P(yes|E) is probability class is "yes" given test data
        //P(yes|E) = f(E_1|yes) * f(E_2|yes) * f(E_i|yes) * P(yes)
        //P(E_1|yes) is normally distributed
        //E_1 is a value given by test
        //f(E_1|yes) = probability density function
        //pdf uses 3 things:
        //  stddev for E_1 given yes
        //  mean of E_1
        //  value of E_1
        //P(yes) = # yes / total (in training)
        //P(no|E) is probability class is "no" given test data
        //if P(yes) >= P(no) return yes

        ArrayList<String[]> training = getData(trainingFile);
        ArrayList<String[]> testing = getData(testingFile);

        ArrayList<String> results = new ArrayList<>();

        //for calculating P(E|yes) and P(E|no)
        float[] stddev_yes = new float[testing.get(0).length];
        float[] stddev_no = new float[testing.get(0).length];
        float[] mean_yes = new float[testing.get(0).length];
        float[] mean_no = new float[testing.get(0).length];


        int yesCount = 0;
        int noCount = 0;

        //for each attribute
        for(int i=0;i<testing.get(0).length;i++) {
            //get the list of numbers for each attribute
            ArrayList<Float> nums_yes = new ArrayList<>();
            ArrayList<Float> nums_no = new ArrayList<>();
            for(String[] train : training) {
                String attribute = train[i];
                String classifier = train[train.length-1];
                Float value = Float.parseFloat(attribute);
                if(classifier.contains("yes")) {
                    nums_yes.add(value);
                } else {
                    nums_no.add(value);
                }
            }
            yesCount = nums_yes.size();
            noCount = nums_no.size();
            //create mean_yes and mean_no for this attribute
            mean_yes[i] = mean(nums_yes);
            mean_no[i] = mean(nums_no);
            //create std dev for yes and no for this attribute
            stddev_yes[i] = stddev(nums_yes, mean_yes[i]);
            stddev_no[i] = stddev(nums_no, mean_no[i]);
        }

        float yes = yesCount / (float) training.size(); //P(yes)
        float no = noCount / (float) training.size(); //P(no)

        //for each example to be classified
        for(String[] test : testing) {

            float p_yes = 1; //P(yes|E)
            float p_no = 1; //P(no|E)

            //P(E|yes) and P(E|no)
            //for each attribute
            for(int i=0;i<test.length;i++) {
                //p_yes
                float stddev = stddev_yes[i];
                float mean = mean_yes[i];
                float x = Float.parseFloat(test[i]);
                p_yes *= prob(x, mean, stddev);
                //p_no
                stddev = stddev_no[i];
                mean = mean_no[i];
                p_no *= prob(x, mean, stddev);

            }
            p_yes *= yes;//multiply by P(yes)
            p_no *= no;//multiply by P(no)

            if(p_yes >= p_no) {
                System.out.println("yes");
                results.add("yes");
            } else {
                System.out.println("no");
                results.add("no");
            }
        }
        return results;
    }

    /**
     * Find the mean of a given set of numbers
     * @param numbers - the set of numbers to find the mean for
     * @return the average of the given numbers
     */
    private float mean(ArrayList<Float> numbers) {
        float sum = 0;
        for(Float f : numbers) {
            sum += f;
        }
        return sum / (float) (numbers.size());

    }

    /**
     * Find the standard deviation of a given set of numbers and their mean
     * @param numbers - the set of numbers to find the standard deviation for
     * @param mean - the mean of the set of numbers
     * @return the standard deviation of the given numbers
     */
    private float stddev(ArrayList<Float> numbers, Float mean) {
        float sum = 0;
        for(Float f : numbers) {
            float difference = f - mean;
            float squared = difference * difference;
            sum += squared;
        }
        float variance = sum / (float) (numbers.size()-1);
        return (float) Math.sqrt(variance);
    }

    /**
     * The probability density function given a value, a mean, and standard deviation
     * @param x
     * @param mean
     * @param stddev
     * @return the probability of the given values
     */
    private float prob(float x, float mean, float stddev) {
        float squared = (x - mean) * (x - mean);
        return (float) (1 / ( (stddev * Math.sqrt(2 * Math.PI))) * Math.exp(-((squared / (2 * (stddev * stddev) )))) );
    }
}
