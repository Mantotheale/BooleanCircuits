package thesis.circuit.karatsubamultiplier;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.Circuit;
import thesis.circuit.XorCircuit;

import java.util.ArrayList;
import java.util.List;

public class NormalAdder extends Circuit {
    private final @NotNull List<@NotNull Circuit> circuits;

    public NormalAdder(int bits) {
        super(bits, bits);

        if (bits <= 0) {
            throw new IllegalArgumentException("An adder can only operate on a number of bits > 0");
        }

        circuits = new ArrayList<>();
        for (int i = 0; i < bits / 2; i++) {
            Circuit xor = new XorCircuit();
            xor.setInput(0, inputPins.get(i));
            xor.setInput(1, inputPins.get(bits / 2 + i));
            circuits.add(xor);
        }
    }

    @Override
    public @NotNull Boolean evaluateSlot(int outputSlot) {
        return circuits.get(outputSlot).evaluate(0);
    }
}
