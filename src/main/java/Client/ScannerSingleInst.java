package Client;

import java.util.Scanner;

public class ScannerSingleInst {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static Scanner getInst(){
        return SCANNER;
    }
}
