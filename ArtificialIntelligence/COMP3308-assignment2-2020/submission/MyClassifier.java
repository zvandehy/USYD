import java.io.*;
import java.util.*;

public class MyClassifier {

    public static void main(String[] args) {
        if(args.length != 3) {
            System.out.println("Invalid Input");
            System.exit(0);
        }
        String trainingFilePath = args[0];
        String testingFilePath = args[1];
        String classifier = args[2];

        MyClassifier myClassifier = new MyClassifier();
        if(classifier.substring(1).equals("NN")) {
            int k = Integer.parseInt(classifier.substring(0,1));
            try {
                myClassifier.kNN(k, trainingFilePath, testingFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(classifier.equals("NB")) {
            try {
                myClassifier.NB(trainingFilePath, testingFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Use K-Nearest Neighbor to classify given data
     * @param k - number of nearest neighbors
     * @param trainingFile - File path to training data
     * @param testingFile - File path to testing data
     */
    public void kNN(int k, String trainingFile, String testingFile) throws IOException {
        ArrayList<String[]> training = getData(trainingFile);
        ArrayList<String[]> testing = getData(testingFile);

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
            } else {
                System.out.println("no");
            }
        }
    }

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


    /**
     * Use Naive Bayes to classify given data
     * @param trainingFile - File path to training data
     * @param testingFile - File path to testing data
     */
    public void NB(String trainingFile, String testingFile) throws IOException {
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

        for(String[] test : testing) {
            float p_yes = 1;
            float p_no = 1;
            for(int i=0;i<test.length;i++) {
                float stddev = stddev_yes[i];
                float mean = mean_yes[i];
                float x = Float.parseFloat(test[i]);
                p_yes *= prob(x, mean, stddev);

//                System.out.println("-----YES-----");
//                System.out.println("STD DEV: " +stddev);
//                System.out.println("MEAN   : " + mean);
//                System.out.println("X      : " + x);
//                System.out.println("left   : " + (stddev * Math.sqrt(2 * Math.PI)));
//                System.out.println("stddev2: " + (2 * (stddev * stddev)));
//                System.out.println("PROB   : " + p_yes);
//                System.out.println("-------------\n");




                stddev = stddev_no[i];
                mean = mean_no[i];
                p_no *= prob(x, mean, stddev);

//                System.out.println("-----NO-----");
//                System.out.println("STD DEV: " +stddev);
//                System.out.println("MEAN   : " + mean);
//                System.out.println("X      : " + x);
//                System.out.println("left   : " + (stddev * Math.sqrt(2 * Math.PI)));
//                System.out.println("stddev2: " + (2 * (stddev * stddev)));
//                System.out.println("PROB   : " + p_no);
//                System.out.println("-------------\n");

            }
            p_yes *= yes;
            p_no *= no;

            if(p_yes >= p_no) {
                System.out.println("yes");
            } else {
                System.out.println("no");
            }
        }
    }

    private float mean(ArrayList<Float> numbers) {
        float sum = 0;
        for(Float f : numbers) {
            sum += f;
        }
        return sum / (float) (numbers.size());

    }

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

    private float prob(float x, float mean, float stddev) {
        float squared = (x - mean) * (x - mean);
        return (float) (1 / ( (stddev * Math.sqrt(2 * Math.PI))) * Math.exp(-((squared / (2 * (stddev * stddev) )))) );
    }

}