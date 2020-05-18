package abstractfactory;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Select 'mac' or 'windows'");
        AbstractOS os;
        AbstractIDE ide;
        AbstractMalware malware;
//        switch(args[0]) {
//            case "windows":
//                os = new WindowsOS();
//                break;
//            default:
//                os = new MacOS();
//        }
        os = new WindowsOS();
        ide = os.makeIDE();
        malware = os.makeMalware();
        System.out.println(ide.getCost());
        System.out.println(malware.getFrequency());
    }
}
