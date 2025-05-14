package thesis.circuit;

import org.jetbrains.annotations.NotNull;
import thesis.exceptions.InvalidInputSlotException;
import thesis.exceptions.InvalidNumberOfInputsException;
import thesis.exceptions.InvalidNumberOfOutputsExceptions;
import thesis.exceptions.InvalidOutputSlotException;
import thesis.wire.Pin;
import thesis.wire.Wire;

import java.util.List;
import java.util.stream.IntStream;

public abstract class Circuit {
    protected final @NotNull List<@NotNull Pin> inputPins;
    private final int outputSlots;

    public Circuit(int inputSlots, int outputSlots) {
        if (inputSlots < 0)
            throw new InvalidNumberOfInputsException(inputSlots);
        if (outputSlots < 0)
                throw new InvalidNumberOfOutputsExceptions(outputSlots);

        inputPins = IntStream.range(0, inputSlots).mapToObj(_ -> new Pin()).toList();
        this.outputSlots = outputSlots;
    }

    public final int inputSlots() {
        return inputPins.size();
    }

    public final int outputSlots() {
        return outputSlots;
    }

    public final void setInput(int inputSlot, @NotNull Wire input) {
        if (inputSlot < 0 || inputSlot >= inputSlots())
            throw new InvalidInputSlotException(this, inputSlot);

        inputPins.get(inputSlot).setWire(input);
    }

    public final void resetInput() {
        inputPins.forEach(p -> p.setWire(Wire.ZERO));
    }

    public final @NotNull Boolean evaluate(int outputSlot) {
        if (outputSlot < 0 || outputSlot >= outputSlots())
            throw new InvalidOutputSlotException(this, outputSlot);

        return evaluateSlot(outputSlot);
    }

    public abstract @NotNull Boolean evaluateSlot(int outputSlot);
}