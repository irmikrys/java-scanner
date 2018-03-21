package main;

import main.html.HTMLMaker;
import main.scanner.Scanner;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        File file = main.getFile();
        Charset encoding = Charset.defaultCharset();
        Scanner scanner = new Scanner(file);
        try {
            scanner.scanFile(encoding);
        } catch (IOException e) {
            System.out.println("An IOException caught while handling a file...");
        }
        HTMLMaker htmlMaker = new HTMLMaker(scanner.getTokenList(), "../test.html");
        htmlMaker.generateHTML();

    }

    private File getFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(Objects.requireNonNull(
                classLoader.getResource("file/Input.txt")).getFile()
        );
    }
}
