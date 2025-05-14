package thesis.circuit.karatsubamultiplier;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.Circuit;
import thesis.circuit.IdentityCircuit;
import thesis.circuit.XorCircuit;
import thesis.wire.CompositeWire;
import thesis.wire.Wire;

import java.util.ArrayList;
import java.util.List;

public class ShiftAdder extends Circuit {
    private final @NotNull List<@NotNull Circuit> circuits;

    public ShiftAdder(int bits, int shift) {
        super(bits, bits + shift);

        circuits = new ArrayList<>();
        if (shift > bits) {
            for (int i = 0; i < bits; i++) {
                Circuit id = new IdentityCircuit(1);
                id.setInput(0, inputPins.get(i));
                circuits.add(id);
            }

            for (int i = bits; i < shift; i++) {
                Circuit id = new IdentityCircuit(1);
                id.setInput(0, Wire.ZERO);
                circuits.add(id);
            }

            for (int i = shift; i < bits + shift; i++) {
                Circuit id = new IdentityCircuit(1);
                id.setInput(0, inputPins.get(i - shift));
                circuits.add(id);
            }
        } else {
            for (int i = 0; i < shift; i++) {
                Circuit id = new IdentityCircuit(1);
                id.setInput(0, inputPins.get(i));
                circuits.add(id);
            }

            for (int i = 0; i < bits - shift; i++) {
                Circuit xor = new XorCircuit();
                xor.setInput(0, inputPins.get(i));
                xor.setInput(1, inputPins.get(shift + i));
                circuits.add(xor);
            }

            for (int i = bits; i < bits + shift; i++) {
                Circuit id = new IdentityCircuit(1);
                id.setInput(0, inputPins.get(i - shift));
                circuits.add(id);
            }
        }
    }

    public ShiftAdder(int bits, int shift, @NotNull CompositeWire wire) {
        this(bits, shift);

        for (int i = 0; i < wire.len(); i++) {
            setInput(i, wire.getWire(i));
        }
    }

    @Override
    public @NotNull Boolean evaluateSlot(int outputSlot) {
        return circuits.get(outputSlot).evaluate(0);
    }
}