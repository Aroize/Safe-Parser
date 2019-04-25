package expression;

import expression.exceptions.EvaluatingException;
import expression.exceptions.OverflowException;

public class CheckedAbs implements TripleExpression {

    TripleExpression expression;

    public CheckedAbs(TripleExpression expression) {
        this.expression = expression;
    }

    @Override
    public int evaluate(int x, int y, int z) throws EvaluatingException {
        int result = expression.evaluate(x, y, z);
        if(result == Integer.MIN_VALUE)
            throw new OverflowException("Caused by Abs of " + result);
        if(result < 0)
            return -result;
        return result;
    }
}
