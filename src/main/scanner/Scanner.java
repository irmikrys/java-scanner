package main.scanner;

import main.chars.CharType;
import main.tokens.KeywordsMap;
import main.tokens.Token;
import main.tokens.TokenType;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Scanner {

    private final File source;
    private final List<Token> tokenList = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;
    private static final Map<String, TokenType> keywords = KeywordsMap.keywords;

    public Scanner(File source) {
        this.source = source;
    }

    public void scanFile(Charset encoding) throws IOException {
        try (
                InputStream in = new FileInputStream(this.source);
                Reader reader = new InputStreamReader(in, encoding);
                Reader buffer = new BufferedReader(reader)
        ) {
            System.out.println(tokenize(buffer));
        }
    }

    public List<Token> tokenize(Reader reader) throws IOException {

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
                    } else if (charType == CharType.WHITE_SPACE) {
                        nextTokenType = TokenType.NONE;
                    }
                    break;
                case TK_IDENT:
                    if (charType == CharType.CHAR_LETTER) {
                        nextTokenType = getKeyword(tokenValue + ch);
                    } else if (charType == CharType.WHITE_SPACE) {
                        nextTokenType = TokenType.NONE;
                    }
                    break;
                case TK_NUMBER:
                    if (charType != CharType.CHAR_DIGIT) {
                        nextTokenType = TokenType.NONE;
                    }
                    break;
                case KW_ELSE:
                    if (charType == CharType.WHITE_SPACE) {
                        nextTokenType = TokenType.NONE;
                    }
                    break;
                case KW_IF:
                    if (charType == CharType.WHITE_SPACE) {
                        nextTokenType = TokenType.NONE;
                    }
                    break;
            }

            if (nextTokenType != TokenType.NONE) {
                tokenValue += ch;
            } else {
                if (!tokenValue.equals("")) {
                    tokenList.add(new Token(tokenValue, tokenType, null, 0));
                }
                if (charType != CharType.WHITE_SPACE) {
                    tokenValue = "" + ch;
                } else {
                    tokenValue = "";
                }
            }
            tokenType = nextTokenType;
        }
        if (!tokenValue.equals("")) {
            tokenList.add(new Token(tokenValue, tokenType, null, 0));
        }
        tokenList.add(new Token("EOF", TokenType.EOF, null, 0));

        return tokenList;
    }

    private CharType getCharType(int c) {
        if ((c >= 97 && c <= 122) || (c >= 65 && c <= 90)) {
            return CharType.CHAR_LETTER;
        } else if (c >= 48 && c <= 57) {
            return CharType.CHAR_DIGIT;
        } else if (c == 43) {
            return CharType.CHAR_PLUS;
        } else if (c == 45) {
            return CharType.CHAR_MINUS;
        } else if (c == 32 || c == 9 || c == 10 || c == 13) { //toDo modify to differentiate white spaces
            return CharType.WHITE_SPACE;
        } else return CharType.NOT_SUPPORTED;
    }

    private TokenType getKeyword(String word) {
        return keywords.get(word) == null ? TokenType.TK_IDENT : keywords.get(word);
    }

}
