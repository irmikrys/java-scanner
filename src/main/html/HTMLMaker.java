package main.html;

import main.scanner.WhiteSpaces;
import main.tokens.Token;
import main.tokens.TokenType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HTMLMaker {

    private List<Token> tokens = new ArrayList<>();
    private String filePath;
    private List<WhiteSpaces> whiteSpaces = new ArrayList<>();

    public HTMLMaker(List<Token> tokens, String filePath, List<WhiteSpaces> whiteSpaces) {
        this.tokens = tokens;
        this.filePath = filePath;
        this.whiteSpaces = whiteSpaces;
    }

    public void generateHTML() {

        int currentLine = 1;
        int fieldInLine = 0;
        String html = "<html><body bgcolor=\"#3c3c3c\"><div>";
        html += "<h1 style=\"color: #9370db\"> This is a formatted output </h1>";

        for (Token token : tokens) {
            if(token.getLine() > currentLine) {
                html += "</br>";
                currentLine = currentLine + 1;
                fieldInLine = 0;
            }
            while (!whiteSpaces.isEmpty() && whiteSpaces.get(0).getFollowedTokenNumber() == fieldInLine && whiteSpaces.get(0).getLine() == currentLine) {
                if (whiteSpaces.get(0).getWhiteSpaceType() == WhiteSpaces.WhiteSpaceType.SPACE) {
                    html += "&nbsp";
                } else if (whiteSpaces.get(0).getWhiteSpaceType() == WhiteSpaces.WhiteSpaceType.TAB) {
                    html += "&nbsp&nbsp&nbsp&nbsp";
                }
                whiteSpaces.remove(0);
            }
            html += crayonToken(token) + " ";
            fieldInLine++;
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

    /* return crayoned token */
    private String crayonToken(Token token) {
        return "<font color=\"" + getColor(token.getTokenType()) + "\">" +
                token.getTokenValue() + "</font>";
    }

    /* return what color should token have */
    private String getColor(TokenType type) {
        String stringType = type.toString();
        if (stringType.contains("KW_")) {
            return "orange";
        } else if (stringType.contains("SYM_")) {
            return "yellow";
        } else if (stringType.contains("TK_")) {
            return "#b0c4de";
        } else {
            return "red";
        }

    }

}