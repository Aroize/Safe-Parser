package expression;

import expression.exceptions.EvaluatingException;
import expression.exceptions.OverflowException;

public class CheckedPow implements TripleExpression {

    private TripleExpression expression;

    public CheckedPow(TripleExpression expression) {
        this.expression = expression;
    }

    private int fastPow(int x) {
        if(x == 0)
            return 1;
        if((x & 1) == 1)
            return 2 * fastPow(x - 1);
        int b = fastPow(x / 2);
        return b*b;
    }

    @Override
    public int evaluate(int x, int y, int z) throws EvaluatingException {
        int result = expression.evaluate(x, y, z);
        if(result < 0 || result > 31)
            throw new OverflowException("Caused by " + this.getClass().getName());
        return fastPow(result);
    }
}
