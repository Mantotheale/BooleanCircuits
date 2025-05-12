package thesis.exceptions;

public class InvalidNumberOfOutputsExceptions extends IllegalArgumentException {
    public InvalidNumberOfOutputsExceptions(int inputSlots) {
        super("The number of outputs of a circuit must be greater than or equal to 0, it was " + inputSlots);

    }
}