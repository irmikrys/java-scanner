package main.tokens;

public class Token {
    private final String tokenValue;
    private final TokenType tokenType;
    private final Object literal;
    private final int line;
    private final int lp;

    public Token(String tokenValue, TokenType tokenType, Object literal, int line, int lp) {
        this.tokenValue = tokenValue;
        this.tokenType = tokenType;
        this.literal = literal;
        this.line = line;
        this.lp = lp;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public int getLine() {
        return line;
    }

    public int getLp() {
        return lp;
    }

    @Override
    public String toString() {
        return "Type: " + tokenType + ", value: " + tokenValue +
                " literal: " + literal + ", line: " + line + ", number: " + lp;
    }
}
