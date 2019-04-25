package expression.exceptions;

public class IllegalValueUnderSqrt extends EvaluatingException {
    public IllegalValueUnderSqrt(int value) {
        super("Expected value >= 0, found " + value);
    }
}
