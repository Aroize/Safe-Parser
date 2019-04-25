package expression.tokenizer;

import expression.exceptions.ParserException;
import expression.parser.Parser;

public class Tokenizer {
    private Token currentToken;
    private Token prevToken;
    private String expression;
    private int index;
    private int number;

    public String getExpression() {
        return expression;
    }

    private void skipNull() {
        while(index < expression.length() && Character.isWhitespace(expression.charAt(index)))
            index++;
    }
    public void setExpression(String Expression) {
        expression = Expression;
        index = 0;
        currentToken = Token.START;
    }

    private void checkPattern(String pattern) throws ParserException {
        if(index + pattern.length() - 1 >= expression.length())
            throw new ParserException("Exprected " + pattern + ", found end of expression");
        int start = index - 1;
        while (index < expression.length() && Character.isLetterOrDigit(expression.charAt(index)))
            index++;
        String command = expression.substring(start, index);
        if(!command.equals(pattern))
            throw new ParserException("Expected " + pattern + ", found " + command + " at index " + (index - 1));
    }


    private String getConst() {
        int start = index - 1;
        while(index < expression.length() && Character.isDigit(expression.charAt(index)))
            index++;
        return expression.substring(start, index);
    }

    public int getNumber() {
        return number;
    }

    public int getIndex() {
        return index;
    }

    public String getVariable() {
        return Character.toString(expression.charAt(index - 1));
    }

    public Token nextToken() throws ParserException{
        prevToken = currentToken;
        skipNull();
        if(index >= expression.length())
            return Token.END;
        index++;
        switch (expression.charAt(index - 1)) {
            case 'x':
            case 'y':
            case 'z': {
                currentToken = Token.VAR;
                return currentToken;
            }
            case '+': {
                currentToken = Token.PLUS;
                return currentToken;
            }
            case '-': {
                if(currentToken == Token.VAR || currentToken == Token.R_BRANCH || currentToken == Token.CONST) {
                    currentToken = Token.MINUS;
                    return currentToken;
                }
                if(Character.isDigit(expression.charAt(index))) {
                    number = Integer.parseInt(getConst());
                    return currentToken = Token.CONST;
                }
                currentToken = Token.UNARY_MINUS;
                return currentToken;
            }
            case '*': {
                currentToken = Token.MULT;
                return currentToken;
            }
            case '/': {
                currentToken = Token.DIV;
                return currentToken;
            }
            case '(': {
                return currentToken = Token.L_BRANCH;
            }
            case ')': {
                return currentToken = Token.R_BRANCH;
            }
            case 'a': {
                checkPattern("abs");
                return currentToken = Token.ABS;
            }
            case 's': {
                checkPattern("sqrt");
                return currentToken = Token.SQRT;
            }
            case 'l': {
                checkPattern("log2");
                return currentToken = Token.LOG;
            }
            case 'p': {
                checkPattern("pow2");
                return currentToken = Token.POW;
            }
            case 'm': {
                switch (expression.charAt(index)) {
                    case 'i': {
                        checkPattern("min");
                        return currentToken = Token.MIN;
                    }
                    case 'a': {
                        checkPattern("max");
                        return currentToken = Token.MAX;
                    }
                    default: {
                        throw new ParserException("Expected 'min' or 'max', found " + expression.substring(index - 1, index + 1));
                    }
                }
            }
            default: {
                if(Character.isDigit(expression.charAt(index - 1))) {
                    number = Integer.parseInt(getConst());
                    return currentToken = Token.CONST;
                }
            }
        }
        return Token.ERROR;
    }

    public Token getPrevToken() {
        return prevToken;
    }
    public Token getCurrentToken() {
        return currentToken;
    }
}
