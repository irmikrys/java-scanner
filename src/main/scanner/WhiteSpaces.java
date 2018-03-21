package main.scanner;

public class WhiteSpaces {
    private WhiteSpaceType whiteSpaceType;
    private int line;
    private int followedTokenNumber;

    public enum WhiteSpaceType{
        SPACE, TAB, END_LINE
    }

    public WhiteSpaces(WhiteSpaceType whiteSpaceType, int line, int followedTokenNumber) {
        this.whiteSpaceType = whiteSpaceType;
        this.line = line;
        this.followedTokenNumber = followedTokenNumber;
    }

    public WhiteSpaceType getWhiteSpaceType() {
        return whiteSpaceType;
    }

    public int getLine() {
        return line;
    }

    public int getFollowedTokenNumber() {
        return followedTokenNumber;
    }

    @Override
    public String toString(){
        return "[type:"+whiteSpaceType+", line:"+line+", field:"+followedTokenNumber+"]";
    }
}
