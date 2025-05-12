package thesis.wire;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Pin implements Wire {
    private @NotNull Wire wire = Wire.ZERO;

    public void setWire(@NotNull Wire wire) {
        this.wire = Objects.requireNonNull(wire);
    }

    public @NotNull Boolean evaluate() {
        return wire.evaluate();
    }
}
