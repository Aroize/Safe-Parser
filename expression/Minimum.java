package expression;

import expression.exceptions.EvaluatingException;

public class Minimum extends BinaryExpression {

    public Minimum(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    @Override
    public int evaluate(int x, int y, int z) throws EvaluatingException {
        int first = firstExpr.evaluate(x, y, z);
        int second = secondExpr.evaluate(x, y, z);
        return first < second ? first : second;
    }
}
