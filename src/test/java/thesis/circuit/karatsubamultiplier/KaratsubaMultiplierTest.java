package thesis.circuit.karatsubamultiplier;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import thesis.IntToBinaryList;
import thesis.circuit.Circuit;
import thesis.exceptions.InvalidInputSlotException;
import thesis.exceptions.InvalidOutputSlotException;
import thesis.poly.Polynomial;
import thesis.wire.CompositeWire;
import thesis.wire.Wire;

import java.util.List;
import java.util.stream.IntStream;

public class KaratsubaMultiplierTest {
    @Test
    public void karatsubaThrowsExceptionForNonPositiveNumberOfBits() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new KaratsubaMultiplier(-5));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new KaratsubaMultiplier(0));
    }

    @Test
    public void karatsubaNBitsHas2NMinus1BitOutputs() {
        Assertions.assertThrows(InvalidOutputSlotException.class, () -> new KaratsubaMultiplier(4).evaluate(7));
        Assertions.assertThrows(InvalidOutputSlotException.class, () -> new KaratsubaMultiplier(8).evaluate(40));
    }

    @Test
    public void karatsubaNBitsHas2NBitInputs() {
        Assertions.assertThrows(InvalidInputSlotException.class, () -> new KaratsubaMultiplier(4).setInput(8, Wire.ZERO));
        Assertions.assertThrows(InvalidInputSlotException.class, () -> new FinalAdder(8).setInput(40, Wire.ZERO));
    }

    @Test
    public void twoBitMulTest() {
        Circuit circuit = new KaratsubaMultiplier(2);

        setUpInput(circuit, List.of(Wire.ZERO, Wire.ZERO), List.of(Wire.ZERO, Wire.ZERO));
        checkEvaluation(circuit, List.of(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE));
        setUpInput(circuit, List.of(Wire.ZERO, Wire.ONE), List.of(Wire.ZERO, Wire.ZERO));
        checkEvaluation(circuit, List.of(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE));
        setUpInput(circuit, List.of(Wire.ONE, Wire.ZERO), List.of(Wire.ZERO, Wire.ZERO));
        checkEvaluation(circuit, List.of(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE));
        setUpInput(circuit, List.of(Wire.ONE, Wire.ONE), List.of(Wire.ZERO, Wire.ZERO));
        checkEvaluation(circuit, List.of(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE));

        setUpInput(circuit, List.of(Wire.ZERO, Wire.ZERO), List.of(Wire.ZERO, Wire.ONE));
        checkEvaluation(circuit, List.of(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE));
        setUpInput(circuit, List.of(Wire.ZERO, Wire.ONE), List.of(Wire.ZERO, Wire.ONE));
        checkEvaluation(circuit, List.of(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE));
        setUpInput(circuit, List.of(Wire.ONE, Wire.ZERO), List.of(Wire.ZERO, Wire.ONE));
        checkEvaluation(circuit, List.of(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE));
        setUpInput(circuit, List.of(Wire.ONE, Wire.ONE), List.of(Wire.ZERO, Wire.ONE));
        checkEvaluation(circuit, List.of(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE));

        setUpInput(circuit, List.of(Wire.ZERO, Wire.ZERO), List.of(Wire.ONE, Wire.ZERO));
        checkEvaluation(circuit, List.of(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE));
        setUpInput(circuit, List.of(Wire.ZERO, Wire.ONE), List.of(Wire.ONE, Wire.ZERO));
        checkEvaluation(circuit, List.of(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE));
        setUpInput(circuit, List.of(Wire.ONE, Wire.ZERO), List.of(Wire.ONE, Wire.ZERO));
        checkEvaluation(circuit, List.of(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE));
        setUpInput(circuit, List.of(Wire.ONE, Wire.ONE), List.of(Wire.ONE, Wire.ZERO));
        checkEvaluation(circuit, List.of(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE));

        setUpInput(circuit, List.of(Wire.ZERO, Wire.ZERO), List.of(Wire.ONE, Wire.ONE));
        checkEvaluation(circuit, List.of(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE));
        setUpInput(circuit, List.of(Wire.ZERO, Wire.ONE), List.of(Wire.ONE, Wire.ONE));
        checkEvaluation(circuit, List.of(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE));
        setUpInput(circuit, List.of(Wire.ONE, Wire.ZERO), List.of(Wire.ONE, Wire.ONE));
        checkEvaluation(circuit, List.of(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE));
        setUpInput(circuit, List.of(Wire.ONE, Wire.ONE), List.of(Wire.ONE, Wire.ONE));
        checkEvaluation(circuit, List.of(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE));
    }

    @Test
    public void fourBitAddTest() {
        Circuit circuit = new KaratsubaMultiplier(4);

        setUpInput(circuit,
                List.of(Wire.ZERO, Wire.ONE, Wire.ZERO, Wire.ONE),
                List.of(Wire.ZERO, Wire.ONE, Wire.ZERO, Wire.ONE));
        checkEvaluation(circuit,
                List.of(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE));

        setUpInput(circuit,
                List.of(Wire.ONE, Wire.ZERO, Wire.ZERO, Wire.ONE),
                List.of(Wire.ZERO, Wire.ONE, Wire.ONE, Wire.ZERO));
        checkEvaluation(circuit,
                List.of(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE));

        setUpInput(circuit,
                List.of(Wire.ONE, Wire.ZERO, Wire.ONE, Wire.ZERO),
                List.of(Wire.ONE, Wire.ONE, Wire.ONE, Wire.ONE));
        checkEvaluation(circuit,
                List.of(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE));

        setUpInput(circuit,
                List.of(Wire.ONE, Wire.ONE, Wire.ONE, Wire.ONE),
                List.of(Wire.ONE, Wire.ONE, Wire.ONE, Wire.ONE));
        checkEvaluation(circuit,
                List.of(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE));

    }

    @Test
    public void twoPowerBitMulTest() {
        for (int bits = 1; bits <= 16; bits *= 2) {
            Circuit circuit = new KaratsubaMultiplier(bits);

            for (int i = 0; i < Math.pow(2, bits); i++) {
                for (int j = 0; j < Math.pow(2, bits); j++) {
                    List<Boolean> a = padList(IntToBinaryList.convert(i), bits);
                    List<Boolean> b = padList(IntToBinaryList.convert(j), bits);

                    circuit.resetInput();
                    setUpInputBools(circuit, a, b);
                    List<Boolean> res = IntStream.range(0, 2 * bits - 1).mapToObj(circuit::evaluate).toList();

                    Assertions.assertEquals(new Polynomial(a).mul(new Polynomial(b)), new Polynomial(res));
                }
            }
        }
    }

    private static void setUpInput(@NotNull Circuit circuit, @NotNull List<@NotNull Wire> a, @NotNull List<@NotNull Wire> b) {
        new CompositeWire(a).wireToCircuit(circuit, 0);
        new CompositeWire(b).wireToCircuit(circuit, a.size());
    }

    private static void setUpInputBools(@NotNull Circuit circuit, @NotNull List<@NotNull Boolean> a, @NotNull List<@NotNull Boolean> b) {
        new CompositeWire(a.stream().map(x -> x ? Wire.ONE : Wire.ZERO).toList()).wireToCircuit(circuit, 0);
        new CompositeWire(b.stream().map(x -> x ? Wire.ONE : Wire.ZERO).toList()).wireToCircuit(circuit, a.size());
    }

    private static @NotNull List<@NotNull Boolean> padList(@NotNull List<@NotNull Boolean> l, int len) {
        for (int i = l.size(); i < len; i++) {
            l.add(Boolean.FALSE);
        }

        return l;
    }

    private static void checkEvaluation(@NotNull Circuit circuit, @NotNull List<@NotNull Boolean> expected) {
        for (int i = 0; i < expected.size(); i++) {
            Assertions.assertEquals(expected.get(i), circuit.evaluate(i));
        }
    }
}
