package thesis.exceptions;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.Circuit;

public class InvalidOutputSlotException extends IllegalArgumentException {
    public InvalidOutputSlotException(@NotNull Circuit circuit, int outputSlot) {
        super("The output slot " + outputSlot + " doesn't exist for circuit " + circuit + " only outputs 0 - " + (circuit.outputSlots() - 1) + " exist for the circuit");
    }
}
