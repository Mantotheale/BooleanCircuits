package thesis.circuit.karatsubamultiplier;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import thesis.circuit.Circuit;
import thesis.exceptions.InvalidInputSlotException;
import thesis.exceptions.InvalidOutputSlotException;
import thesis.wire.CompositeWire;
import thesis.wire.Wire;

import java.util.List;

public class FinalAdderTest {
    @Test
    public void finalAdderThrowsExceptionForNonPositiveNumberOfBits() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new FinalAdder(-5));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new FinalAdder(0));
    }

    @Test
    public void finalAdderNBitsHas2NMinus1BitOutputs() {
        Assertions.assertThrows(InvalidOutputSlotException.class, () -> new FinalAdder(4).evaluate(7));
        Assertions.assertThrows(InvalidOutputSlotException.class, () -> new FinalAdder(8).evaluate(40));
    }

    @Test
    public void finalAdderNBitsHas4NMinus2BitInputs() {
        Assertions.assertThrows(InvalidInputSlotException.class, () -> new FinalAdder(4).setInput(14, Wire.ZERO));
        Assertions.assertThrows(InvalidInputSlotException.class, () -> new FinalAdder(8).setInput(40, Wire.ZERO));
    }

    @Test
    public void twoBitAddTest() {
        Circuit circuit = new FinalAdder(2);

        setUpInput(circuit, List.of(Wire.ZERO, Wire.ONE, Wire.ZERO), List.of(Wire.ONE, Wire.ONE, Wire.ONE));
        checkEvaluation(circuit, List.of(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE));

        setUpInput(circuit, List.of(Wire.ONE, Wire.ONE, Wire.ONE), List.of(Wire.ONE, Wire.ONE, Wire.ONE));
        checkEvaluation(circuit, List.of(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE));

        setUpInput(circuit, List.of(Wire.ONE, Wire.ZERO, Wire.ONE), List.of(Wire.ZERO, Wire.ONE, Wire.ZERO));
        checkEvaluation(circuit, List.of(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE));

        setUpInput(circuit, List.of(Wire.ZERO, Wire.ONE, Wire.ZERO), List.of(Wire.ONE, Wire.ZERO, Wire.ONE));
        checkEvaluation(circuit, List.of(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE));
    }

    @Test
    public void fourBitAddTest() {
        Circuit circuit = new FinalAdder(4);

        setUpInput(circuit,
                List.of(Wire.ZERO, Wire.ONE, Wire.ZERO, Wire.ONE, Wire.ZERO, Wire.ONE, Wire.ZERO),
                List.of(Wire.ZERO, Wire.ONE, Wire.ZERO, Wire.ONE, Wire.ZERO, Wire.ONE, Wire.ZERO));
        checkEvaluation(circuit,
                List.of(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE));

        setUpInput(circuit,
                List.of(Wire.ONE, Wire.ONE, Wire.ONE, Wire.ONE, Wire.ONE, Wire.ONE, Wire.ONE),
                List.of(Wire.ZERO, Wire.ZERO, Wire.ZERO, Wire.ZERO, Wire.ZERO, Wire.ZERO, Wire.ZERO));
        checkEvaluation(circuit,
                List.of(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE));

        setUpInput(circuit,
                List.of(Wire.ONE, Wire.ONE, Wire.ONE, Wire.ONE, Wire.ONE, Wire.ONE, Wire.ONE),
                List.of(Wire.ZERO, Wire.ZERO, Wire.ZERO, Wire.ZERO, Wire.ZERO, Wire.ZERO, Wire.ZERO));
        checkEvaluation(circuit,
                List.of(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE));

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
