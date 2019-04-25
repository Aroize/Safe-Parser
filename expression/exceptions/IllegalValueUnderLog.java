package expression.exceptions;

public class IllegalValueUnderLog extends EvaluatingException {
    public IllegalValueUnderLog(int value) {
        super("Expected value > 0, found: " + value);
    }
}
