package main.scanner;

import main.JavaScan;
import main.tokens.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Scanner {

    private final String source;
    private final List<Token> tokenList = new ArrayList<>();
    private final List<WhiteSpace> whitespaces = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;
    private int tokenNr = 1;
    private static final Map<String, TokenType> keywords = KeywordsMap.keywords;

    public Scanner(String source) {
        this.source = source;
    }

    public List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }
        tokenList.add(new Token("", TokenType.EOF, null, line, tokenNr));
        return tokenList;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private void scanToken() {
        char c = advance();
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
                addToken(match('=') ? TokenType.SYM_BANG_EQUAL : TokenType.SYM_BANG);
                break;
            case '=':
                addToken(match('=') ? TokenType.SYM_EQUAL_EQUAL : TokenType.SYM_EQUAL);
                break;
            case '<':
                addToken(match('=') ? TokenType.SYM_LESS_EQUAL : TokenType.SYM_LESS);
                break;
            case '>':
                addToken(match('=') ? TokenType.SYM_GREATER_EQUAL : TokenType.SYM_GREATER);
                break;
            case '/':
                if (match('/')) {
                    while (peek() != '\n' && !isAtEnd()) {
                        advance();
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
                line++;
                break;
            case '"':
                string();
                break;
            default:
                if (isDigit(c)) {
                    number();
                } else if (isAlpha(c)) {
                    identifier();
                } else {
                    JavaScan.error(line, "Unexpected character.");
                }
                break;
        }
    }

    private void identifier() {
        while (isAlphaNumeric(peek())) {
            advance();
        }
        String text = source.substring(start, current);
        TokenType type = keywords.get(text);
        if (type == null) type = TokenType.TK_IDENT;
        addToken(type);
    }

    private void string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance();
        }
        if (isAtEnd()) {
            JavaScan.error(line, "Unterminated string.");
            return;
        }
        advance();
        String value = source.substring(start + 1, current - 1);
        addToken(TokenType.TK_STRING, value);
    }

    private void number() {
        while (isDigit(peek())) {
            advance();
        }
        if (peek() == '.' && isDigit(peekNext())) {
            advance();
            while (isDigit(peek())) {
                advance();
            }
        }
        addToken(TokenType.TK_NUMBER, Double.parseDouble(source.substring(start, current)));
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private char advance() {
        current++;
        return source.charAt(current - 1);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokenList.add(new Token(text, type, literal, line, tokenNr));
        tokenNr = tokenNr + 1;
    }

    private void addWhiteSpace(WhiteSpaceType type, int beforeToken) {
        whitespaces.add(new WhiteSpace(type, beforeToken));
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    public List<WhiteSpace> getWhitespaces() {
        return whitespaces;
    }

}
