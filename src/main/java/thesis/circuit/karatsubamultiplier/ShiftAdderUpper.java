package thesis.circuit.karatsubamultiplier;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.Circuit;
import thesis.circuit.XorCircuit;

import java.util.ArrayList;
import java.util.List;

public class ShiftAdderUpper extends Circuit {
    private final int n;
    private final @NotNull List<@NotNull Circuit> circuits;

    public ShiftAdderUpper(int bits) {
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
        if (outputSlot < n) {
            return Boolean.FALSE;
        }

        if (outputSlot < 2 * n) {
            return inputPins.get(outputSlot - n).evaluate();
        }

        if (outputSlot >= 3 * n - 1) {
            return inputPins.get(outputSlot - 2 * n).evaluate();
        }

        return circuits.get(outputSlot - 2 * n).evaluate(0);
    }
}