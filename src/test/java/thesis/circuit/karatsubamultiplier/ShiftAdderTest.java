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

public class ShiftAdderTest {
    @Test
    public void shiftAdderThrowsExceptionForNonPositiveNumberOfBits() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ShiftAdder(-5));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ShiftAdder(0));
    }

    @Test
    public void shiftAdderNBitsHas2NMinus1BitOutputs() {
        Assertions.assertThrows(InvalidOutputSlotException.class, () -> new ShiftAdder(4).evaluate(7));
        Assertions.assertThrows(InvalidOutputSlotException.class, () -> new ShiftAdder(8).evaluate(40));
    }

    @Test
    public void shifterNBitsHasNMinus1BitInputs() {
        Assertions.assertThrows(InvalidInputSlotException.class, () -> new ShiftAdder(4).setInput(3, Wire.ZERO));
        Assertions.assertThrows(InvalidInputSlotException.class, () -> new ShiftAdder(8).setInput(15, Wire.ZERO));
    }

    @Test
    public void twoBitShiftAdderTest() {
        Circuit circuit = new ShiftAdder(2);

        setUpInput(circuit, List.of(Wire.ZERO));
        checkEvaluation(circuit, List.of(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE));

        setUpInput(circuit, List.of(Wire.ONE));
        checkEvaluation(circuit, List.of(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE));
    }

    @Test
    public void fourShifterTest() {
        Circuit circuit = new ShiftAdder(4);

        setUpInput(circuit, List.of(Wire.ZERO, Wire.ONE, Wire.ONE));
        checkEvaluation(circuit, List.of(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE));

        setUpInput(circuit, List.of(Wire.ONE, Wire.ZERO, Wire.ZERO));
        checkEvaluation(circuit, List.of(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE));
    }

    private static void setUpInput(@NotNull Circuit circuit, @NotNull List<@NotNull Wire> input) {
        new CompositeWire(input).wireToCircuit(circuit, 0);
    }

    private static void checkEvaluation(@NotNull Circuit circuit, @NotNull List<@NotNull Boolean> expected) {
        for (int i = 0; i < expected.size(); i++) {
            Assertions.assertEquals(expected.get(i), circuit.evaluate(i));
        }
    }
}