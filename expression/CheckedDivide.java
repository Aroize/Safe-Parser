package expression;

import expression.exceptions.DivisionByZero;
import expression.exceptions.EvaluatingException;
import expression.exceptions.OverflowException;

public class CheckedDivide extends BinaryExpression {
    public CheckedDivide(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    @Override
    public int evaluate(int x, int y, int z) throws EvaluatingException {
        int first = firstExpr.evaluate(x, y, z);
        int second = secondExpr.evaluate(x, y, z);
        if(second == 0)
            throw new DivisionByZero();
        if(first == Integer.MIN_VALUE && second == -1)
            throw new OverflowException("Caused by CheckedDivision");
        return first / second;
    }
}
