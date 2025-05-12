package thesis.wire;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class InputWire implements Wire {
    private @NotNull Boolean input;

    public InputWire(@NotNull Boolean input) {
        this.input = Objects.requireNonNull(input);
    }

    @Override
    public @NotNull Boolean evaluate() {
        return input;
    }

    public void set(@NotNull Boolean value) {
        input = Objects.requireNonNull(value);
    }
}
