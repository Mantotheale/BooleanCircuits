package thesis.circuit;

import org.jetbrains.annotations.NotNull;

public class AndCircuit extends Circuit {
    public AndCircuit() {
        super(2, 1);
    }

    @Override
    public @NotNull Boolean evaluateSlot(int outputSlot) {
        return inputPins.get(0).evaluate() & inputPins.get(1).evaluate();
    }
}