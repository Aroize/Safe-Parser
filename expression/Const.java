package expression;

public class Const implements Expression, DoubleExpression, TripleExpression {
    private int value;
    private double dValue;

    public Const(int x) {
        this.value = x;
        dValue = x;
    }

    public Const(double x) {
        this.dValue = x;
        value = (int)x;
    }

    @Override
    public int evaluate(int x) {
        return value;
    }

    @Override
    public double evaluate(double x) {
        return dValue;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value;
    }
}
