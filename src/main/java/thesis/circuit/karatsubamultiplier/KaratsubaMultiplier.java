package thesis.circuit.karatsubamultiplier;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.AndCircuit;
import thesis.circuit.Circuit;

import thesis.circuit.IdentityCircuit;
import thesis.wire.CompositeWire;
import thesis.wire.OutputWire;
import thesis.wire.Wire;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class KaratsubaMultiplier extends Circuit {
    private final @NotNull List<@NotNull Circuit> circuits;

    public KaratsubaMultiplier(int bits) {
        super(2 * bits, 2 * bits - 1);

        if (bits <= 0) {
            throw new IllegalArgumentException("A multiplier can only operate on a number of bits > 0");
        } else if (bits == 1) {
            circuits = new ArrayList<>();

            Circuit and = new AndCircuit();
            and.setInput(0, inputPins.get(0));
            and.setInput(1, inputPins.get(1));

            circuits.add(and);
        } else {
            int n = bits / 2;
            // A(x) = a0 + a1x^n
            // B(x) = b0 + b1x^n
            // A(x)B(x) = (1 + x^n)a0b0 + t^n(a0 + a1)(b0 + b1) + (t^n + t^2n)a1b1

            CompositeWire a0 = getWires(0, n);
            CompositeWire a1 = getWires(n, n);
            CompositeWire b0 = getWires(bits, n);
            CompositeWire b1 = getWires(bits + n, n);

            // Generate a0b0 circuit
            Circuit a0b0 = new KaratsubaMultiplier(n, a0, b0);
            // Generate a1b1 circuit
            Circuit a1b1 = new KaratsubaMultiplier(n, a1, b1);
            // Generate a0 + a1 circuit
            Circuit a0Plusa1 = new NormalAdder(n, a0, a1);
            // Generate b0 + b1 circuit
            Circuit b0Plusb1 = new NormalAdder(n, b0, b1);
            // Generate (a0 + a1)(b0 + b1) circuit
            Circuit a0Plusa1b0Plusb1 = new KaratsubaMultiplier(n,
                    new CompositeWire(a0Plusa1, 0, n),
                    new CompositeWire(b0Plusb1, 0, n)
            );
            // Generate (1 + t^n)a0b0 circuit
            Circuit onePlusTna0b0 = new ShiftAdder(2 * n - 1, n, new CompositeWire(a0b0, 0, 2 * n - 1));
            // Generate (1 + t^n)a1b1 circuit
            Circuit onePlusTna1b1 = new ShiftAdder(2 * n - 1, n, new CompositeWire(a1b1, 0, 2 * n - 1));
            // Generate middle part of (1 + t^n)a0b0 + t^n(a0 + a1)(b0 + b1) circuit
            Circuit firstAddMiddlePart = new NormalAdder(2 * n - 1,
                    new CompositeWire(onePlusTna0b0, n, 2 * n - 1),
                    new CompositeWire(a0Plusa1b0Plusb1, 0, 2 * n - 1)
            );

            // Generate middle part of (1 + t^n)a0b0 + t^n(a0 + a1)(b0 + b1) + (t^n + t^2n)a1b1 circuit
            Circuit secondAddMiddlePart = new NormalAdder(2 * n - 1,
                    new CompositeWire(firstAddMiddlePart, 0, 2 * n - 1),
                    new CompositeWire(onePlusTna1b1, 0, 2 * n - 1));

            circuits = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                Circuit id = new IdentityCircuit(1);
                id.setInput(0, new OutputWire(onePlusTna0b0, i));
                circuits.add(id);
            }
            for (int i = 0; i < 2 * n - 1; i++) {
                Circuit id = new IdentityCircuit(1);
                id.setInput(0, new OutputWire(secondAddMiddlePart, i));
                circuits.add(id);
            }
            for (int i = 0; i < n; i++) {
                Circuit id = new IdentityCircuit(1);
                id.setInput(0, new OutputWire(onePlusTna1b1, i + 2 * n - 1));
                circuits.add(id);
            }
        }
    }

    public KaratsubaMultiplier(int bits, @NotNull CompositeWire a, @NotNull CompositeWire b) {
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

    private @NotNull CompositeWire getWires(int offset, int count) {
        return new CompositeWire(
                IntStream.range(offset, offset + count).mapToObj(i -> (Wire) inputPins.get(i)).toList()
        );
    }
}
