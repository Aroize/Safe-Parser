package expression;

import expression.exceptions.EvaluatingException;
import expression.exceptions.OverflowException;

public class CheckedMultiply extends BinaryExpression {
    public CheckedMultiply(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    @Override
    public int evaluate(int x, int y, int z) throws EvaluatingException {
        int first = firstExpr.evaluate(x, y, z);
        int second = secondExpr.evaluate(x, y, z);
        if(first > 0 && second > 0 && Integer.MAX_VALUE / first < second)
            throw new OverflowException("Caused by " + this.getClass().getName());
        if(first < 0 && second < 0 && Integer.MAX_VALUE / first > second)
            throw new OverflowException("Caused by " + this.getClass().getName());
        if(first > 0 && second < 0 && Integer.MIN_VALUE / first > second)
            throw new OverflowException("Caused by " + this.getClass().getName());
        if(first < 0 && second > 0 && Integer.MIN_VALUE / second > first)
            throw new OverflowException("Caused by " + this.getClass().getName());
        return first * second;
    }
}
