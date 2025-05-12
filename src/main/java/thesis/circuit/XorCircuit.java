package thesis.circuit;

import org.jetbrains.annotations.NotNull;

public class XorCircuit extends Circuit {
    public XorCircuit() {
        super(2, 1);
    }

    @Override
    public @NotNull Boolean evaluateSlot(int outputSlot) {
        return inputPins.get(0).evaluate() ^ inputPins.get(1).evaluate();
    }
}