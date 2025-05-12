package thesis.exceptions;

public class InvalidNumberOfInputsException extends IllegalArgumentException {
    public InvalidNumberOfInputsException(int inputSlots) {
        super("The number of inputs of a circuit must be greater than or equal to 0, it was " + inputSlots);

    }
}
