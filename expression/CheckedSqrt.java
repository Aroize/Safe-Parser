package expression;

import expression.exceptions.EvaluatingException;
import expression.exceptions.IllegalValueUnderSqrt;

public class CheckedSqrt implements TripleExpression {

    private TripleExpression expression;

    public CheckedSqrt(TripleExpression expression) {
        this.expression = expression;
    }

    private int fastSqrt(int value) {
        int i;
        for (i = 0; i*i < value; ++i){}
        return i*i == value ? i : i - 1;
    }

    @Override
    public int evaluate(int x, int y, int z) throws EvaluatingException {
        int value = expression.evaluate(x, y, z);
        if(value < 0)
            throw new IllegalValueUnderSqrt(value);
        return fastSqrt(value);
    }
}
