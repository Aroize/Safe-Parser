package expression;

import expression.exceptions.EvaluatingException;
import expression.exceptions.OverflowException;

public class CheckedSubtract extends BinaryExpression{
    public CheckedSubtract(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    @Override
    public int evaluate(int x, int y, int z) throws EvaluatingException {
        int first = firstExpr.evaluate(x, y, z);
        int second = secondExpr.evaluate(x, y, z);
        if(first >= 0 && second < 0 && first - Integer.MAX_VALUE > second)
            throw new OverflowException("Caused by CheckedSubtract " + first + " " + second);
        if(first <= 0 && second > 0 && Integer.MIN_VALUE - first > -second)
            throw new OverflowException("Caused by CheckedSubtract " + first + " " + second);
        return first - second;
    }
}
