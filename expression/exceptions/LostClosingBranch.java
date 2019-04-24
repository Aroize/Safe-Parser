package expression.exceptions;

public class LostClosingBranch extends ParserException {
    public LostClosingBranch(String found) {
        super("Expected ')', found: " + found);
    }
}
