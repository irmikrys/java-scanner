package main.html;

import main.tokens.Token;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HTMLMaker {

    private List<Token> tokens = new ArrayList<>();
    private String filePath;

    public HTMLMaker(List<Token> tokens, String filePath) {
        this.tokens = tokens;
        this.filePath = filePath;
    }

    public void generateHTML() {

        String html = "<html><body><div>";
        html += "<h1> This is a formatted output </h1>";

        for(Token token : tokens) {
            html += token.getTokenValue() + " ";
        }

        html += "</div></body></html>";

        File file = new File(filePath);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(html);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}