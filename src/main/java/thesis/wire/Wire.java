package thesis.wire;

import org.jetbrains.annotations.NotNull;

public interface Wire {
    @NotNull Boolean evaluate();

    Wire ZERO = () -> Boolean.FALSE;
    Wire ONE = () -> Boolean.TRUE;
}
