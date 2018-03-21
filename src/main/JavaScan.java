package main;

import main.html.HTMLMaker;
import main.scanner.Scanner;
import main.tokens.Token;
import main.tokens.WhiteSpace;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JavaScan {

    static List<WhiteSpace> whitespaces = new ArrayList<>();

    public static void main(String[] args) {

        List<Token> tokens = new ArrayList<>();
        try {
            tokens = runFile("../Input.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        HTMLMaker htmlMaker = new HTMLMaker(tokens, whitespaces, "../output.html");
        htmlMaker.generateHTML();
    }

    public static void error(int line, String message) {
        report(line, "", message);
    }

    private static void report(int line, String where, String message) {
        System.err.println(
                "[line " + line + "] Error" + where + ": " + message);
    }

    private static List<Token> runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        return run(new String(bytes, Charset.defaultCharset()));
    }

    private static List<Token> run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        whitespaces = scanner.getWhitespaces();

        for (Token token : tokens) {
            System.out.println(token);
        }

        for (WhiteSpace ws : whitespaces) {
            System.out.println(ws);
        }

        return tokens;
    }
}
