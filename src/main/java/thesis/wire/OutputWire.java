package thesis.wire;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.Circuit;
import thesis.exceptions.InvalidOutputSlotException;

import java.util.Objects;

public record OutputWire(@NotNull Circuit circuit, int outputSlot) implements Wire {
    public OutputWire(@NotNull Circuit circuit, int outputSlot) {
        this.circuit = Objects.requireNonNull(circuit);

        if (outputSlot < 0 || outputSlot >= circuit.outputSlots())
            throw new InvalidOutputSlotException(circuit, outputSlot);

        this.outputSlot = outputSlot;
    }

    @Override
    public @NotNull Boolean evaluate() {
        return circuit.evaluate(outputSlot);
    }
}