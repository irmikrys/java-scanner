package main.html;

import main.tokens.Token;
import main.tokens.TokenType;
import main.tokens.WhiteSpace;
import main.tokens.WhiteSpaceType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HTMLMaker {

    private List<Token> tokens;
    private List<WhiteSpace> whitespaces;
    private String filePath;

    public HTMLMaker(List<Token> tokens, List<WhiteSpace> whitespaces, String filePath) {
        this.tokens = tokens;
        this.whitespaces = whitespaces;
        this.filePath = filePath;
    }

    public void generateHTML() {

        int currentLine = 1;
        List<WhiteSpace> toRemove = new ArrayList<>();
        String html = "<html><body bgcolor=\"#3c3c3c\"><div>";
        html += "<h1 style=\"color: #9370db\"> This is a formatted output </h1>";

        for (Token token : tokens) {
            toRemove.clear();
            if (token.getLine() > currentLine) {
                currentLine = currentLine + 1;
            }
            for (WhiteSpace ws : whitespaces) {
                if (ws.getBeforeToken() == token.getLp()) {
                    html += whitespaceToCode(ws.getType());
                    toRemove.add(ws);
                }
            }
            whitespaces.removeAll(toRemove);
            html += crayonToken(token);
        }

        html += "</div></body></html>";
        writeToHTML(html);
    }

    private void writeToHTML(String html) {
        File file = new File(filePath);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(html);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String whitespaceToCode(WhiteSpaceType type) {
        switch(type) {
            case TAB:
                return "&nbsp;&nbsp;&nbsp;";
            case SPACE:
                return "&nbsp;";
            case ENTER:
                return "</br>";
        }
        return null;
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
            return "#9370db";
        } else if (stringType.equals("TK_IDENT") || stringType.equals("TK_NUMBER")) {
            return "#b0c4de";
        } else if (stringType.equals("TK_STRING") || stringType.equals("TK_CHAR")) {
            return "green";
        } else if (stringType.equals("TK_ANNOTATION")) {
            return "yellow";
        } else {
            return "red";
        }

    }

}