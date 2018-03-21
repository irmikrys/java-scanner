package tokens;

public class Token {
    private final String tokenValue;
    private final TokenType tokenType;
    private final Object literal;
    private final int line;

    public Token(String tokenValue, TokenType tokenType, Object literal, int line) {
        this.tokenValue = tokenValue;
        this.tokenType = tokenType;
        this.literal = literal;
        this.line = line;
    }

    @Override
    public String toString() {
        return "Type: " + tokenType + ", value: " + tokenValue +
                " literal: " + literal + ", line: " + line + "\n";
    }
}
