package main;

import main.scanner.Scanner;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JavaScan {

    public static void main(String[] args) {
        try {
            runFile("../java-scanner/resources/file/Input.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void error(int line, String message) {
        report(line, "", message);
    }

    private static void report(int line, String where, String message) {
        System.err.println(
                "[line " + line + "] Error" + where + ": " + message);
    }

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
    }

    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        scanner.scanTokens();
    }
}
