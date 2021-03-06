package main.html;

import main.tokens.Token;
import main.tokens.TokenType;
import main.tokens.WhiteSpace;
import main.tokens.WhiteSpaceType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HTMLMaker {

    private List<Token> tokens;
    private List<WhiteSpace> whitespaces;
    private String filePath;

    public HTMLMaker(String filePath) {
        this.filePath = filePath;
    }

    public void writeToHTML(String html) {
        File file = new File(filePath);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(html);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String whitespaceToCode(WhiteSpaceType type) {
        switch (type) {
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
    public String crayonToken(Token token) {
        return "<font color=\"" + getColor(token.getTokenType()) + "\">" +
                token.getTokenValue() + "</font>";
    }

    /* return what color should token have */
    private String getColor(TokenType type) {
        String stringType = type.toString();
        if (stringType.contains("KW_")) {
            return "orange";
        } else if (stringType.contains("SYM_")) {
            return "#f7d358";
        } else if (stringType.equals("TK_IDENT")) {
            return "#b0c4de";
        } else if (stringType.equals("TK_NUMBER")) {
            return "pink";
        } else if (stringType.equals("TK_STRING") || stringType.equals("TK_CHAR")) {
            return "green";
        } else if (stringType.equals("TK_ANNOTATION")) {
            return "yellow";
        } else if (stringType.equals("COMMENT")) {
            return "grey";
        } else {
            return "red";
        }

    }

}