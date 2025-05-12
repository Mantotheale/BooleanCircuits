package thesis.wire;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class NamedWire implements Wire {
    private final @NotNull Wire wire;
    private final @NotNull String name;

    public NamedWire(@NotNull Wire wire, @NotNull String name) {
        this.wire = Objects.requireNonNull(wire);
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public @NotNull Boolean evaluate() {
        Boolean value = wire.evaluate();
        System.out.println(name + ": " + value);
        return value;
    }
}
