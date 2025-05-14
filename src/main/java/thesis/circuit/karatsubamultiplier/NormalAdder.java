package thesis.circuit.karatsubamultiplier;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.Circuit;
import thesis.circuit.XorCircuit;
import thesis.wire.CompositeWire;

import java.util.ArrayList;
import java.util.List;

public class NormalAdder extends Circuit {
    private final @NotNull List<@NotNull Circuit> circuits;

    public NormalAdder(int bits) {
        super(2 * bits, bits);

        if (bits <= 0) {
            throw new IllegalArgumentException("An adder can only operate on a number of bits > 0");
        }

        circuits = new ArrayList<>();
        for (int i = 0; i < bits; i++) {
            Circuit xor = new XorCircuit();
            xor.setInput(0, inputPins.get(i));
            xor.setInput(1, inputPins.get(bits + i));
            circuits.add(xor);
        }
    }

    public NormalAdder(int bits, @NotNull CompositeWire a, @NotNull CompositeWire b) {
        this(bits);

        for (int i = 0; i < a.len(); i++) {
            setInput(i, a.getWire(i));
        }

        for (int i = 0; i < b.len(); i++) {
            setInput(bits + i, b.getWire(i));
        }
    }

    @Override
    public @NotNull Boolean evaluateSlot(int outputSlot) {
        return circuits.get(outputSlot).evaluate(0);
    }
}
