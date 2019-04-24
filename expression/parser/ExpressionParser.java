package expression.parser;

import com.sun.istack.internal.Nullable;
import expression.*;
import expression.exceptions.LostClosingBranch;
import expression.tokenizer.*;
import jdk.nashorn.internal.runtime.ParserException;
import org.jetbrains.annotations.Contract;

public class ExpressionParser implements Parser {

    private Tokenizer tokenizer;
    private Token currentToken;

    @Contract(pure = true)
    private int fromTokenToInt(Token token) {
        switch (token) {
            case PLUS: return 0b1;
            case MINUS: return 0b11;
            case MULT: return 0b100;
            case DIV: return 0b1100;
        }
        return 0;
    }

    @Contract(pure = true)
    public ExpressionParser() {
        tokenizer = new Tokenizer();
    }

    @org.jetbrains.annotations.Nullable
    @Nullable
    private TripleExpression simpleToken() throws ParserException, LostClosingBranch{
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
        }
        throw new ParserException("Expected operand, found: " + tokenizer.getCurrentToken() + " at index " + (tokenizer.getIndex() - 1) + "\n" + tokenizer.getExpression());
    }

    private TripleExpression parseExpr(int predicate) throws LostClosingBranch {
        TripleExpression expression = simpleToken();
        while(currentToken != Token.END && (fromTokenToInt(currentToken) & predicate) != 0
        ) {
            expression = loopParse(expression);
        }
        return expression;
    }

    private TripleExpression parseBranches() throws ParserException, LostClosingBranch {
        TripleExpression expression = simpleToken();
        while (currentToken != Token.END && currentToken != Token.R_BRANCH) {
            expression = loopParse(expression);
        }
        if(currentToken != Token.R_BRANCH)
            throw new LostClosingBranch(currentToken.toString());
        currentToken = tokenizer.nextToken();
        return expression;
    }

    private TripleExpression loopParse(TripleExpression expression) throws LostClosingBranch{
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
            default:
                throw new ParserException("Expected operator, found: " + tokenizer.getCurrentToken() + " at index " + (tokenizer.getIndex() - 1) + "\n" + tokenizer.getExpression());
        }
        return expression;
    }

    @Override
    public TripleExpression parse(String expression) throws LostClosingBranch {
        tokenizer.setExpression(expression);
        currentToken = tokenizer.nextToken();
        TripleExpression currentExpression = simpleToken();
        while(currentToken != Token.END) {
            currentExpression = loopParse(currentExpression);
        }
        return currentExpression;
    }
}
