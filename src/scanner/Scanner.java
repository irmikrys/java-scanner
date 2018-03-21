package scanner;

import chars.CharType;
import tokens.Token;
import tokens.TokenType;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Scanner {

    private final File source;

    public Scanner(File source) {
        this.source = source;
    }

    public void scanFile(Charset encoding) throws IOException {
        try (
                InputStream in = new FileInputStream(this.source);
                Reader reader = new InputStreamReader(in, encoding);
                Reader buffer = new BufferedReader(reader)
        ) {
            List<Token> tokens = tokenize(buffer);
            System.out.println(tokens);
        }
    }

    private static List<Token> tokenize(Reader reader) throws IOException {

        List<Token> tokenList = new ArrayList<>();
        String tokenValue = "";
        CharType charType;
        TokenType tokenType = TokenType.NONE;
        TokenType nextTokenType = tokenType;
        int r;

        while ((r = reader.read()) != -1) {
            char ch = (char) r;
            charType = getCharType(ch);
            switch (tokenType) {
                case NONE:
                    if (charType == CharType.CHAR_LETTER) {
                        nextTokenType = TokenType.TK_IDENT;
                    } else if (charType == CharType.CHAR_DIGIT) {
                        nextTokenType = TokenType.TK_NUMBER;
                    } else if (charType == CharType.CHAR_PLUS) {
                        nextTokenType = TokenType.SYM_PLUS;
                    } else if (charType == CharType.CHAR_MINUS) {
                        nextTokenType = TokenType.SYM_MINUS;
                    }
                    break;
                case TK_IDENT:
                    if (charType == CharType.CHAR_LETTER) {
                        nextTokenType = getKeyword(tokenValue + ch);
                    } else if (charType == CharType.SPACE) {
                        nextTokenType = TokenType.NONE;
                    }
                    break;
                case TK_NUMBER:
                    if (charType != CharType.CHAR_DIGIT) {
                        nextTokenType = TokenType.NONE;
                    }
                    break;
                case KW_ELSE:
                    if (charType == CharType.SPACE) {
                        nextTokenType = TokenType.NONE;
                    }
            }

            System.out.println(nextTokenType);
            tokenType = nextTokenType;
            if (tokenType != TokenType.NONE) {
                tokenValue += ch;
            } else {
                tokenList.add(new Token(tokenValue, tokenType));
                if (charType != CharType.SPACE) {
                    tokenValue = "" + ch;
                } else {
                    tokenValue = "";
                }
            }
//            System.out.println(tokenList);
        }
        return tokenList;
    }

    private static CharType getCharType(int c) {
        System.out.println(c);

        if ((c >= 97 && c <= 122) || (c >= 65 && c <= 90)) {
            return CharType.CHAR_LETTER;
        } else if (c >= 48 && c <= 57) {
            return CharType.CHAR_DIGIT;
        } else if (c == 43) {
            return CharType.CHAR_PLUS;
        } else if (c == 45) {
            return CharType.CHAR_MINUS;
        } else if (c == 32) {
            return CharType.SPACE;
        } else return CharType.NOT_SUPPORTED;
    }

    private static TokenType searchKeyword(String keyword) {
        switch (keyword) {
            case "+":
                return TokenType.SYM_PLUS;
            case "-":
                return TokenType.SYM_MINUS;
            case "/":
                return TokenType.SYM_DIV;
            case "*":
                return TokenType.SYM_STAR;
            default:
                return TokenType.NONE;
        }
    }

    private static TokenType getKeyword(String word) {
        switch (word) {
            case "if":
                return TokenType.KW_IF;
            case "else":
                return TokenType.KW_ELSE;
            default:
                return TokenType.TK_IDENT;
        }
    }

}
