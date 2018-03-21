import scanner.Scanner;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class Main {

    public static void main(String[] args) {
        Charset encoding = Charset.defaultCharset();
        File file = new File("../Input.txt");
        Scanner scanner = new Scanner(file);
        try {
            scanner.scanFile(encoding);
        } catch (IOException e) {
            System.out.println("An IOException caught while handling a file...");
        }
    }
}
