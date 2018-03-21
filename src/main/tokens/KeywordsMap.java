package main.tokens;

import java.util.HashMap;
import java.util.Map;

public class KeywordsMap {

    public static final Map<String, TokenType> keywords;

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
        keywords.put("int", TokenType.KW_INT);
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
}
