package thesis.exceptions;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.Circuit;

public class InvalidInputSlotException extends IllegalArgumentException {
    public InvalidInputSlotException(@NotNull Circuit circuit, int inputSlot) {
        super("The input slot " + inputSlot + " doesn't exist for circuit " + circuit + " only inputs 0 - " + (circuit.inputSlots() - 1) + " exist for the circuit");
    }
}