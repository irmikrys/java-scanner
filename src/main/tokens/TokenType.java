package main.tokens;

public enum TokenType {

    /* keywords */
    KW_PACKAGE, KW_IMPORT, KW_PRIVATE, KW_PUBLIC, KW_PROTECTED,
    KW_ABSTRACT, KW_ENUM, KW_CLASS, KW_INTERFACE,
    KW_IMPLEMENTS, KW_SYNCHRONIZED, KW_EXTENDS,
    KW_ASSERT, KW_NEW, KW_SUPER, KW_THIS, KW_INSTANCEOF,
    KW_FINAL, KW_STATIC, KW_TRANSIENT, KW_CONST, KW_VOLATILE,
    KW_NATIVE, KW_THROWS, KW_TRY, KW_CATCH, KW_THROW, KW_FINALLY,
    KW_RETURN, KW_STRICTFP,
    KW_BOOLEAN, KW_LONG, KW_CHAR, KW_DOUBLE, KW_INT, KW_SHORT,
    KW_FLOAT, KW_BYTE,
    KW_IF, KW_ELSE, KW_FOR, KW_SWITCH, KW_CASE, KW_DEFAULT,
    KW_DO, KW_WHILE, KW_GOTO, KW_CONTINUE, KW_BREAK,

    /* literals */
    TK_IDENT, TK_NUMBER, TK_STRING, TK_CHAR, TK_ANNOTATION,

    /* single-character symbols */
    SYM_LEFT_PAREN, SYM_RIGHT_PAREN, SYM_LEFT_BRACE,
    SYM_LEFT_SQUARE, SYM_RIGHT_SQUARE,
    SYM_RIGHT_BRACE, SYM_COMMA, SYM_DOT, SYM_SEMICOLON,
    SYM_SLASH, SYM_PLUS, SYM_MINUS, SYM_STAR,

    /* one or two character symbols */
    SYM_BANG, SYM_BANG_EQUAL,
    SYM_EQUAL, SYM_EQUAL_EQUAL,
    SYM_GREATER, SYM_GREATER_EQUAL,
    SYM_LESS, SYM_LESS_EQUAL,
    SYM_OR, SYM_OR_OR, SYM_AND, SYM_AND_AND,

    /* end of file */
    EOF,

    /* other */
    ERROR, COMMENT
}
