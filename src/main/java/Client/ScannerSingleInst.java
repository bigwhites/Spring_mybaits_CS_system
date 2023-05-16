package Client;

import java.util.Scanner;

public class ScannerSingleInst {
    private static Scanner sc = new Scanner(System.in);
    public static Scanner getInst(){
        return sc;
    }
}
