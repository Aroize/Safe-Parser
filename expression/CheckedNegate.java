package expression;

import expression.exceptions.EvaluatingException;
import expression.exceptions.OverflowException;

public class CheckedNegate implements TripleExpression {

    protected TripleExpression expression;

    public CheckedNegate(TripleExpression expression) {
        this.expression = expression;
    }

    @Override
    public int evaluate(int x, int y, int z) throws EvaluatingException {
        int result = expression.evaluate(x, y, z);
        if(result == Integer.MIN_VALUE)
            throw new OverflowException("Caused by CheckedNegate " + result);
        return -result;
    }
}
