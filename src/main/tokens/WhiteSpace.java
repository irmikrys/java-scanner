package main.tokens;

public class WhiteSpace {
    private final WhiteSpaceType type;
    private final int beforeToken;

    public WhiteSpace(WhiteSpaceType type, int beforeToken) {
        this.type = type;
        this.beforeToken = beforeToken;
    }

    public WhiteSpaceType getType() {
        return type;
    }

    public int getBeforeToken() {
        return beforeToken;
    }

    @Override
    public String toString() {
        return "Type: " + type + "\t before: " + beforeToken;
    }
}
