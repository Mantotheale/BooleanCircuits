package thesis.wire;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.Circuit;

import java.util.*;

public class CompositeWire {
    private final @NotNull List<@NotNull Wire> wires;

    public CompositeWire(@NotNull List<@NotNull Wire> wires) {
        this.wires = new ArrayList<>();
        for (Wire w: wires) {
            this.wires.add(Objects.requireNonNull(w));
        }
    }

    public CompositeWire(@NotNull Circuit circuit, int offset, int bits) {
        this.wires = new ArrayList<>();
        for (int i = 0; i < bits; i++) {
            this.wires.add(new OutputWire(circuit, offset + i));
        }
    }

    public void wireToCircuit(@NotNull Circuit circuit, int offset) {
        for (int i = 0; i < wires.size(); i++) {
            circuit.setInput(offset + i, wires.get(i));
        }
    }

    public @NotNull List<@NotNull Boolean> evaluate() {
        return wires.stream().map(Wire::evaluate).toList();
    }

    public @NotNull Wire getWire(int i) {
        return wires.get(i);
    }

    public int len() {
        return wires.size();
    }
}
