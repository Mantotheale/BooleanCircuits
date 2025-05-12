package thesis.circuit.karatsubamultiplier;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.Circuit;

public class Shifter extends Circuit {
    private final int n;

    public Shifter(int bits) {
        super(bits - 1, 2 * bits - 1);
        this.n = bits / 2;
    }

    @Override
    public @NotNull Boolean evaluateSlot(int outputSlot) {
        if (outputSlot < n) {
            return Boolean.FALSE;
        }

        if (outputSlot >= 3 * n - 1) {
            return Boolean.FALSE;
        }

        return inputPins.get(outputSlot - n).evaluate();
    }
}