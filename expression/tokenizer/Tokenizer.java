package expression.tokenizer;

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

    private String getConst() throws NumberFormatException{
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

    public Token nextToken() {
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
