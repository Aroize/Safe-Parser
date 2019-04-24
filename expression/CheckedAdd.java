package expression;

import expression.exceptions.EvaluatingException;
import expression.exceptions.OverflowException;

public class CheckedAdd extends BinaryExpression {
    public CheckedAdd(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    @Override
    public int evaluate(int x, int y, int z) throws EvaluatingException {
        int first;
        int second;
        first = firstExpr.evaluate(x, y, z);
        second = secondExpr.evaluate(x, y, z);
        if(first > 0 && Integer.MAX_VALUE - first < second)
            throw new OverflowException("Caused by CheckedAdd");
        if(first < 0 && Integer.MIN_VALUE - first > second)
            throw new OverflowException("Caused by CheckedAdd");
        return first + second;
    }
}
