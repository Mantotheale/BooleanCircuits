package thesis.circuit.karatsubamultiplier;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.AndCircuit;
import thesis.circuit.Circuit;
import thesis.wire.OutputWire;

public class KaratsubaMultiplier extends Circuit {
    private final @NotNull Circuit circuit;

    public KaratsubaMultiplier(int bits) {
        super(2 * bits, 2 * bits - 1);

        if (bits <= 0) {
            throw new IllegalArgumentException("A multiplier can only operate on a number of bits > 0");
        } else if (bits == 1) {
            circuit = new AndCircuit();
            circuit.setInput(0, inputPins.get(0));
            circuit.setInput(1, inputPins.get(1));
        } else {
            int n = bits / 2;
            // A(x) = a0 + a1x^n
            // B(x) = b0 + b1x^n
            // A(x)B(x) = (1 + x^n)a0b0 + t^n(a0 + a1)(b0 + b1) + (t^n + t^2n)a1b1

            // Generate a0b0 circuit
            Circuit a0b0 = new KaratsubaMultiplier(n);
            for (int i = 0; i < n; i++) {
                a0b0.setInput(i, inputPins.get(i));
                a0b0.setInput(n + i, inputPins.get(bits + i));

                //a0b0.setInput(i, new NamedWire(inputPins.get(i), "a[" + i + "]"));
                //a0b0.setInput(n + i, new NamedWire(inputPins.get(bits + i), "b[" + i + "]"));
            }

            // Generate a1b1 circuit
            Circuit a1b1 = new KaratsubaMultiplier(n);
            for (int i = 0; i < n; i++) {
                a1b1.setInput(i, inputPins.get(n + i));
                a1b1.setInput(n + i, inputPins.get(bits + n + i));
            }

            // Generate a0 + a1 circuit
            Circuit a0Plusa1 = new NormalAdder(bits);
            for (int i = 0; i < bits; i++) {
                a0Plusa1.setInput(i, inputPins.get(i));
            }

            // Generate b0 + b1 circuit
            Circuit b0Plusb1 = new NormalAdder(bits);
            for (int i = 0; i < bits; i++) {
                b0Plusb1.setInput(i, inputPins.get(bits + i));
            }

            // Generate (a0 + a1)(b0 + b1) circuit
            Circuit a0Plusa1b0Plusb1 = new KaratsubaMultiplier(n);
            for (int i = 0; i < n; i++) {
                a0Plusa1b0Plusb1.setInput(i, new OutputWire(a0Plusa1, i));
                a0Plusa1b0Plusb1.setInput(n + i, new OutputWire(b0Plusb1, i));
            }

            // Generate (1 + t^n)a0b0 circuit
            Circuit onePlusTna0b0 = new ShiftAdder(bits);
            for (int i = 0; i < 2 * n - 1; i++) {
                onePlusTna0b0.setInput(i, new OutputWire(a0b0, i));
            }

            // Generate (1 + t^n)a1b1 circuit
            Circuit onePlusTna1b1 = new ShiftAdder(bits);
            for (int i = 0; i < 2 * n - 1; i++) {
                onePlusTna1b1.setInput(i, new OutputWire(a1b1, i));
            }

            // Generate (t^n + t^2n)a1b1 circuit
            Circuit tnPlusT2na1b1 = new Shifter(bits);
            for (int i = 0; i < 2 * bits - 1; i++) {
                tnPlusT2na1b1.setInput(i, new OutputWire(onePlusTna1b1, i));
            }

            // Generate t^n(a0 + a1)(b0 + b1)
            Circuit tna0Plusa1b0Plusb1 = new Shifter(bits);
            for (int i = 0; i < 2 * n - 1; i++) {
                tna0Plusa1b0Plusb1.setInput(i, new OutputWire(a0Plusa1b0Plusb1, i));
            }

            // Generate (1 + t^n)a0b0 + t^n(a0 + a1)(b0 + b1) circuit
            Circuit firstAdd = new FinalAdder(bits);
            for (int i = 0; i < 2 * bits - 1; i++) {
                firstAdd.setInput(i, new OutputWire(onePlusTna0b0, i));
                firstAdd.setInput(2 * bits - 1 + i, new OutputWire(tna0Plusa1b0Plusb1, i));
            }

            // Generate (1 + t^n)a0b0 + t^n(a0 + a1)(b0 + b1) + (t^n + t^2n)a1b1 circuit
            Circuit secondAdd = new FinalAdder(bits);
            for (int i = 0; i < 2 * bits - 1; i++) {
                secondAdd.setInput(i, new OutputWire(firstAdd, i));
                secondAdd.setInput(2 * bits - 1 + i, new OutputWire(tnPlusT2na1b1, i));
            }

            circuit = secondAdd;
        }
    }

    @Override
    public @NotNull Boolean evaluateSlot(int outputSlot) {
        return circuit.evaluate(outputSlot);
    }
}
