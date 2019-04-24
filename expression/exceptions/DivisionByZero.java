package expression.exceptions;

public class DivisionByZero extends EvaluatingException{
    public DivisionByZero() {
        super("Division by zero");
    }
}
