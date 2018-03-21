package scanner;

import chars.CharType;
import tokens.Token;
import tokens.TokenType;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private final File source;
    private final List<Token> tokenList = new ArrayList<>();
    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("package", TokenType.KW_PACKAGE);
        keywords.put("import", TokenType.KW_IMPORT);
        keywords.put("private", TokenType.KW_PRIVATE);
        keywords.put("public", TokenType.KW_PUBLIC);
        keywords.put("protected", TokenType.KW_PROTECTED);
        keywords.put("abstract", TokenType.KW_ABSTRACT);
        keywords.put("enum", TokenType.KW_ENUM);
        keywords.put("class", TokenType.KW_CLASS);
        keywords.put("interface", TokenType.KW_INTERFACE);
        keywords.put("implements", TokenType.KW_IMPLEMENTS);
        keywords.put("synchronized", TokenType.KW_SYNCHRONIZED);
        keywords.put("extends", TokenType.KW_EXTENDS);
        keywords.put("assert", TokenType.KW_ASSERT);
        keywords.put("new", TokenType.KW_NEW);
        keywords.put("super", TokenType.KW_SUPER);
        keywords.put("this", TokenType.KW_THIS);
        keywords.put("instanceof", TokenType.KW_INSTANCEOF);
        keywords.put("final", TokenType.KW_FINAL);
        keywords.put("static", TokenType.KW_STATIC);
        keywords.put("transient", TokenType.KW_TRANSIENT);
        keywords.put("const", TokenType.KW_CONST);
        keywords.put("volatile", TokenType.KW_VOLATILE);
        keywords.put("native", TokenType.KW_NATIVE);
        keywords.put("throws", TokenType.KW_THROWS);
        keywords.put("try", TokenType.KW_TRY);
        keywords.put("catch", TokenType.KW_CATCH);
        keywords.put("throw", TokenType.KW_THROW);
        keywords.put("finally", TokenType.KW_FINALLY);
        keywords.put("return", TokenType.KW_RETURN);
        keywords.put("strictfp", TokenType.KW_STRICTFP);
        keywords.put("boolean", TokenType.KW_BOOLEAN);
        keywords.put("long", TokenType.KW_LONG);
        keywords.put("char", TokenType.KW_CHAR);
        keywords.put("double", TokenType.KW_DOUBLE);
        keywords.put("short", TokenType.KW_SHORT);
        keywords.put("float", TokenType.KW_FLOAT);
        keywords.put("byte", TokenType.KW_BYTE);
        keywords.put("if", TokenType.KW_IF);
        keywords.put("else", TokenType.KW_ELSE);
        keywords.put("for", TokenType.KW_FOR);
        keywords.put("switch", TokenType.KW_SWITCH);
        keywords.put("case", TokenType.KW_CASE);
        keywords.put("default", TokenType.KW_DEFAULT);
        keywords.put("do", TokenType.KW_DO);
        keywords.put("while", TokenType.KW_WHILE);
        keywords.put("goto", TokenType.KW_GOTO);
        keywords.put("continue", TokenType.KW_CONTINUE);
        keywords.put("break", TokenType.KW_BREAK);

    }

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

    private List<Token> tokenize(Reader reader) throws IOException {
        String tokenValue = "";
        CharType charType;
        TokenType tokenType = TokenType.NONE;
        TokenType nextTokenType = tokenType;
        int r;
        while ((r = reader.read()) != -1) {
            char ch = (char) r;
            charType = getCharType(ch);
            nextTokenType = getTokenType(tokenValue, charType, tokenType, nextTokenType, ch);
            tokenValue = checkToken(tokenValue, charType, tokenType, nextTokenType, ch);
            tokenType = nextTokenType;
        }
        if(tokenValue != "") {
            tokenList.add(new Token(tokenValue, tokenType));
        }
        tokenList.add(new Token("EOF", TokenType.EOF));
        return tokenList;
    }

    private String checkToken(String tokenValue, CharType charType, TokenType tokenType, TokenType nextTokenType, char ch) {
        if (nextTokenType != TokenType.NONE) {
            tokenValue += ch;
        } else {
            if(!tokenValue.equals("")) {
                tokenList.add(new Token(tokenValue, tokenType));
            }
            if (charType != CharType.WHITE_SPACE) {
                tokenValue = "" + ch;
            } else {
                tokenValue = "";
            }
        }
        return tokenValue;
    }

    private TokenType getTokenType(String tokenValue, CharType charType, TokenType tokenType, TokenType nextTokenType, char ch) {
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
        return nextTokenType;
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
