package thesis.circuit;

import org.jetbrains.annotations.NotNull;

public class IdentityCircuit extends Circuit {
    public IdentityCircuit(int slots) {
        super(slots, slots);
    }

    @Override
    public @NotNull Boolean evaluateSlot(int outputSlot) {
        return inputPins.get(outputSlot).evaluate();
    }
}
