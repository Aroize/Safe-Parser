package expression.parser;

import expression.*;
import expression.exceptions.LostClosingBranch;
import expression.exceptions.ParserException;
import expression.tokenizer.*;

public class ExpressionParser implements Parser {

    private Tokenizer tokenizer;
    private Token currentToken;

    private int fromTokenToInt(Token token) {
        switch (token) {
            case MIN:
            case MAX: return 0b1;
            case PLUS:
            case MINUS: return 0b11;
            case MULT:
            case DIV: return 0b1100;
        }
        return 0;
    }

    public ExpressionParser() {
        tokenizer = new Tokenizer();
    }

    private TripleExpression simpleToken() throws ParserException {
        switch (currentToken) {
            case VAR: {
                TripleExpression Var = new Variable(tokenizer.getVariable());
                currentToken = tokenizer.nextToken();
                return Var;
            }
            case CONST: {
                TripleExpression Const = new Const(tokenizer.getNumber());
                currentToken = tokenizer.nextToken();
                return Const;
            }
            case UNARY_MINUS: {
                currentToken = tokenizer.nextToken();
                return new CheckedNegate(simpleToken());
            }
            case L_BRANCH: {
                currentToken = tokenizer.nextToken();
                return parseBranches();
            }
            case LOG: {
                currentToken = tokenizer.nextToken();
                return new CheckedLog(simpleToken());
            }
            case POW: {
                currentToken = tokenizer.nextToken();
                return new CheckedPow(simpleToken());
            }
            case ABS: {
                currentToken = tokenizer.nextToken();
                return new CheckedAbs(simpleToken());
            }
            case SQRT: {
                currentToken = tokenizer.nextToken();
                return new CheckedSqrt(simpleToken());
            }
        }
        throw new ParserException("Expected operand, found: " + tokenizer.getCurrentToken() + " at index " + (tokenizer.getIndex() - 1) + "\n" + tokenizer.getExpression());
    }

    private TripleExpression parseExpr(int predicate) throws ParserException {
        TripleExpression expression = simpleToken();
        while(currentToken != Token.END && (fromTokenToInt(currentToken) & predicate) != 0
        ) {
            expression = loopParse(expression);
        }
        return expression;
    }

    private TripleExpression parseBranches() throws ParserException {
        TripleExpression expression = simpleToken();
        while (currentToken != Token.END && currentToken != Token.R_BRANCH) {
            expression = loopParse(expression);
        }
        if(currentToken != Token.R_BRANCH)
            throw new LostClosingBranch(currentToken.toString());
        currentToken = tokenizer.nextToken();
        return expression;
    }

    private TripleExpression loopParse(TripleExpression expression) throws ParserException{
        currentToken = tokenizer.nextToken();
        switch (tokenizer.getPrevToken()) {
            case PLUS: {
                expression = new CheckedAdd(expression, parseExpr(0b1100));
                break;
            }
            case MINUS: {
                expression = new CheckedSubtract(expression, parseExpr(0b1100));
                break;
            }
            case MULT: {
                expression = new CheckedMultiply(expression, simpleToken());
                break;
            }
            case DIV: {
                expression = new CheckedDivide(expression, simpleToken());
                break;
            }
            case MIN: {
                expression = new Minimum(expression, parseExpr(0b10));
                break;
            }
            case MAX: {
                expression = new Maximum(expression, parseExpr(0b10));
                break;
            }
            default:
                throw new ParserException("Expected operator, found: " + tokenizer.getCurrentToken() + " at index " + (tokenizer.getIndex() - 1) + "\n" + tokenizer.getExpression());
        }
        return expression;
    }

    @Override
    public TripleExpression parse(String expression) throws ParserException {
        tokenizer.setExpression(expression);
        currentToken = tokenizer.nextToken();
        TripleExpression currentExpression = simpleToken();
        while(currentToken != Token.END) {
            currentExpression = loopParse(currentExpression);
        }
        return currentExpression;
    }
}
