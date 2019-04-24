package expression;

abstract class BinaryExpression implements TripleExpression {
    protected TripleExpression firstExpr;
    protected TripleExpression secondExpr;

    public BinaryExpression(TripleExpression first, TripleExpression second) {
        this.firstExpr = first;
        this.secondExpr = second;
    }

    /*public BinaryExpression(DoubleExpression first, DoubleExpression second) {
        this.firstDExpr = first;
        this.secondDExpr = second;
    }*/
}
