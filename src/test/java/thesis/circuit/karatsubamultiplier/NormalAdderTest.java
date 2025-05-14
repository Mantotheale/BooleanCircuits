package thesis.circuit.karatsubamultiplier;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import thesis.circuit.Circuit;
import thesis.exceptions.InvalidOutputSlotException;
import thesis.wire.CompositeWire;
import thesis.wire.Wire;

import java.util.List;

public class NormalAdderTest {
    @Test
    public void adderThrowsExceptionForNonPositiveNumberOfBits() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new NormalAdder(-5));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new NormalAdder(0));
    }

    @Test
    public void adderNBitsHasNBitOutputs() {
        Assertions.assertThrows(InvalidOutputSlotException.class, () -> new NormalAdder(4).evaluate(4));
        Assertions.assertThrows(InvalidOutputSlotException.class, () -> new NormalAdder(8).evaluate(10));
    }

    @Test
    public void twoBitAddTest() {
        Circuit circuit = new NormalAdder(2);

        setUpInput(circuit, List.of(Wire.ZERO), List.of(Wire.ZERO));
        checkEvaluation(circuit, List.of(Boolean.FALSE));

        setUpInput(circuit, List.of(Wire.ZERO), List.of(Wire.ONE));
        checkEvaluation(circuit, List.of(Boolean.TRUE));

        setUpInput(circuit, List.of(Wire.ONE), List.of(Wire.ZERO));
        checkEvaluation(circuit, List.of(Boolean.TRUE));

        setUpInput(circuit, List.of(Wire.ONE), List.of(Wire.ONE));
        checkEvaluation(circuit, List.of(Boolean.FALSE));
    }

    @Test
    public void fourBitAddTest() {
        Circuit circuit = new NormalAdder(4);

        setUpInput(circuit, List.of(Wire.ZERO, Wire.ONE), List.of(Wire.ZERO, Wire.ONE));
        checkEvaluation(circuit, List.of(Boolean.FALSE, Boolean.FALSE));

        setUpInput(circuit, List.of(Wire.ONE, Wire.ZERO), List.of(Wire.ZERO, Wire.ONE));
        checkEvaluation(circuit, List.of(Boolean.TRUE, Boolean.TRUE));

        setUpInput(circuit, List.of(Wire.ZERO, Wire.ZERO), List.of(Wire.ZERO, Wire.ONE));
        checkEvaluation(circuit, List.of(Boolean.FALSE, Boolean.TRUE));

        setUpInput(circuit, List.of(Wire.ONE, Wire.ONE), List.of(Wire.ZERO, Wire.ONE));
        checkEvaluation(circuit, List.of(Boolean.TRUE, Boolean.FALSE));
    }

    private static void setUpInput(@NotNull Circuit circuit, @NotNull List<@NotNull Wire> a, @NotNull List<@NotNull Wire> b) {
        new CompositeWire(a).wireToCircuit(circuit, 0);
        new CompositeWire(b).wireToCircuit(circuit, a.size());
    }

    private static void checkEvaluation(@NotNull Circuit circuit, @NotNull List<@NotNull Boolean> expected) {
        for (int i = 0; i < expected.size(); i++) {
            Assertions.assertEquals(expected.get(i), circuit.evaluate(i));
        }
    }
}
