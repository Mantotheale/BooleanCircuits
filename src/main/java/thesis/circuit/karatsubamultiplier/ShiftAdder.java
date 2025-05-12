package thesis.circuit.karatsubamultiplier;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.Circuit;
import thesis.circuit.XorCircuit;

import java.util.ArrayList;
import java.util.List;

public class ShiftAdder extends Circuit {
    private final int n;
    private final @NotNull List<@NotNull Circuit> circuits;

    public ShiftAdder(int bits) {
        super(bits - 1, 2 * bits - 1);
        this.n = bits / 2;

        circuits = new ArrayList<>();
        for (int i = 0; i < n - 1; i++) {
            Circuit xor = new XorCircuit();
            xor.setInput(0, inputPins.get(n + i));
            xor.setInput(1, inputPins.get(i));
            circuits.add(xor);
        }
    }

    @Override
    public @NotNull Boolean evaluateSlot(int outputSlot) {
        if (outputSlot >= 3 * n - 1) {
            return Boolean.FALSE;
        }

        if (outputSlot < n) {
            return inputPins.get(outputSlot).evaluate();
        }

        if (outputSlot >= 2 * n - 1) {
            return inputPins.get(outputSlot - n).evaluate();
        }

        return circuits.get(outputSlot - n).evaluate(0);
    }
}