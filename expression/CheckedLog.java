package expression;

import expression.exceptions.EvaluatingException;
import expression.exceptions.IllegalValueUnderLog;

public class CheckedLog implements TripleExpression {

    private TripleExpression expression;

    public CheckedLog(TripleExpression expression) {
        this.expression = expression;
    }

    private int evaluateLog(int x) {
        int count = 0;
        while (x > 1) {
            x /= 2;
            count++;
        }
        return count;
    }

    @Override
    public int evaluate(int x, int y, int z) throws EvaluatingException {
        int result = expression.evaluate(x, y, z);
        if(result <= 0)
            throw new IllegalValueUnderLog(result);
        return evaluateLog(result);
    }
}
