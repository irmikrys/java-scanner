package main.scanner;

import main.JavaScan;
import main.html.HTMLMaker;
import main.tokens.KeywordsMap;
import main.tokens.Token;
import main.tokens.TokenType;
import main.tokens.WhiteSpaceType;

import java.util.Map;

public class Scanner {

    private final String source;
    private int start = 0;
    private int current = 0;
    private int line = 1;
    private int tokenNr = 1;
    private String html;
    private HTMLMaker htmlMaker = new HTMLMaker("../output.html");
    private static final Map<String, TokenType> keywords = KeywordsMap.keywords;

    public Scanner(String source) {
        this.source = source;
        html = "<html><body bgcolor=\"#3c3c3c\"><div>";
        html += "<h1 style=\"color: #9370db\"> This is a formatted output </h1>";
    }

    public void scanTokens() {
        while (!atEnd()) {
            start = current;
            scanToken();
        }
        html += htmlMaker.crayonToken(new Token("", TokenType.EOF, null, line, tokenNr));
        html += "</div></body></html>";
        htmlMaker.writeToHTML(html);
    }

    private boolean atEnd() {
        return current >= source.length();
    }

    private void scanToken() {
        char c = getNextChar();
        switch (c) {
            case '(':
                addToken(TokenType.SYM_LEFT_PAREN);
                break;
            case ')':
                addToken(TokenType.SYM_RIGHT_PAREN);
                break;
            case '{':
                addToken(TokenType.SYM_LEFT_BRACE);
                break;
            case '}':
                addToken(TokenType.SYM_RIGHT_BRACE);
                break;
            case '[':
                addToken(TokenType.SYM_LEFT_SQUARE);
                break;
            case ']':
                addToken(TokenType.SYM_RIGHT_SQUARE);
                break;
            case ',':
                addToken(TokenType.SYM_COMMA);
                break;
            case '.':
                addToken(TokenType.SYM_DOT);
                break;
            case '-':
                addToken(TokenType.SYM_MINUS);
                break;
            case '+':
                addToken(TokenType.SYM_PLUS);
                break;
            case ';':
                addToken(TokenType.SYM_SEMICOLON);
                break;
            case '*':
                addToken(TokenType.SYM_STAR);
                break;
            case '!':
                addToken(checkNextSymbol('=') ? TokenType.SYM_BANG_EQUAL : TokenType.SYM_BANG);
                break;
            case '=':
                addToken(checkNextSymbol('=') ? TokenType.SYM_EQUAL_EQUAL : TokenType.SYM_EQUAL);
                break;
            case '<':
                addToken(checkNextSymbol('=') ? TokenType.SYM_LESS_EQUAL : TokenType.SYM_LESS);
                break;
            case '>':
                addToken(checkNextSymbol('=') ? TokenType.SYM_GREATER_EQUAL : TokenType.SYM_GREATER);
                break;
            case '|':
                addToken(checkNextSymbol('|') ? TokenType.SYM_OR_OR : TokenType.SYM_OR);
                break;
            case '&':
                addToken(checkNextSymbol('&') ? TokenType.SYM_AND_AND : TokenType.SYM_AND);
                break;
            case '/':
                if (checkNextSymbol('/')) {
                    while (checkCurrentChar() != '\n' && !atEnd()) {
                        getNextChar(); // TODO add comments as grey to html
                    }
                } else {
                    addToken(TokenType.SYM_SLASH);
                }
                break;
            case ' ':
                addWhiteSpace(WhiteSpaceType.SPACE, tokenNr);
                break;
            case '\t':
                addWhiteSpace(WhiteSpaceType.TAB, tokenNr);
                break;
            case '\r':
                break;
            case '\n':
                addWhiteSpace(WhiteSpaceType.ENTER, tokenNr);
                line++;
                break;
            case '"':
                handleString();
                break;
            case '\'':
                handleChar();
                break;
            case '@':
                handleAnnotation();
                break;
            default: //TODO add unexpected tokens with red color to HTML
                if (isDigit(c)) {
                    handleNumber();
                } else if (isAlpha(c)) {
                    handleIdentifier();
                } else {
                    JavaScan.error(line, "Unexpected character.");
                }
                break;
        }
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        html += htmlMaker.crayonToken(new Token(text, type, literal, line, tokenNr));
        tokenNr = tokenNr + 1;
    }

    private void addWhiteSpace(WhiteSpaceType type, int beforeToken) {
        html += htmlMaker.whitespaceToCode(type);
    }

    private void handleAnnotation() {
        while (isAlpha(checkCurrentChar())) {
            getNextChar();
        }
        String value = source.substring(start, current);
        addToken(TokenType.TK_ANNOTATION, value);
    }

    private void handleIdentifier() {
        while (isAlpha(checkCurrentChar()) || isDigit(checkCurrentChar())) {
            getNextChar();
        }
        String text = source.substring(start, current);
        TokenType type = keywords.get(text);
        if (type == null) {
            type = TokenType.TK_IDENT;
        }
        addToken(type);
    }

    private void handleString() {
        while (checkCurrentChar() != '"' && !atEnd()) {
            if (checkCurrentChar() == '\n') line++;
            getNextChar();
        }
        if (atEnd()) {
            JavaScan.error(line, "Unterminated string.");
            return;
        }
        getNextChar();
        String value = source.substring(start + 1, current - 1);
        addToken(TokenType.TK_STRING, value);
    }

    private void handleChar() {
        while (checkCurrentChar() != '\'' && !atEnd()) {
            if (checkCurrentChar() == '\n') {
                JavaScan.error(line, "Unclosed character literal");
                line++;
                return;
            }
            getNextChar();
        }
        if (atEnd()) {
            JavaScan.error(line, "Unterminated character.");
            return;
        }
        getNextChar();
        if ((current - 1) - (start + 1) > 3) {
            JavaScan.error(line, "Too may characters in one character literal");
            return;
        }
        String value = source.substring(start + 1, current - 1);
        addToken(TokenType.TK_CHAR, value);
    }


    private void handleNumber() {
        while (isDigit(checkCurrentChar())) {
            getNextChar();
        }
        if (checkCurrentChar() == '.' && isDigit(checkNextChar())) {
            getNextChar();
            while (isDigit(checkCurrentChar())) {
                getNextChar();
            }
        }
        addToken(TokenType.TK_NUMBER, Double.parseDouble(source.substring(start, current)));
    }

    private char getNextChar() {
        current++;
        return source.charAt(current - 1);
    }

    private char checkCurrentChar() {
        if (atEnd()) return '\0';
        return source.charAt(current);
    }

    private char checkNextChar() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private boolean checkNextSymbol(char expected) {
        if (atEnd()) {
            return false;
        }
        if (source.charAt(current) != expected) {
            return false;
        }
        current++;
        return true;
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                (c == '_');
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

}
