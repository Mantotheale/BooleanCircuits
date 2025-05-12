package thesis.wire;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.Circuit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompositeWire {
    private final @NotNull List<@NotNull Wire> wires;

    public CompositeWire(@NotNull List<@NotNull Wire> wires) {
        this.wires = new ArrayList<>();
        for (Wire w: wires) {
            this.wires.add(Objects.requireNonNull(w));
        }
    }

    public CompositeWire(@NotNull Circuit circuit, int bits, int offset) {
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
}
