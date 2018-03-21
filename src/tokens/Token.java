package tokens;

public class Token {
    private String tokenValue;
    private TokenType tokenType;

    public Token(String tokenValue, TokenType tokenType){
        this.tokenValue = tokenValue;
        this.tokenType = tokenType;
    }

    @Override
    public String toString(){
        return "Type: "+tokenType+", token value: "+tokenValue+"\n";
    }
}
