package thesis;

import thesis.circuit.AndCircuit;
import thesis.circuit.Circuit;
import thesis.circuit.IdentityCircuit;
import thesis.circuit.XorCircuit;
import thesis.circuit.karatsubamultiplier.KaratsubaMultiplier;
import thesis.wire.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        identityTest();
        andTest();
        xorTest();
        realKaratsubaTest();
        fourBitsTest();
        /*Circuit x = new KaratsubaMultiplier(2);

        Circuit adder = new NormalAdder(3);
        adder.setInput(0, InputWire.ZERO);
        adder.setInput(1, InputWire.ZERO);
        adder.setInput(2, InputWire.ZERO);
        adder.setInput(3, InputWire.ONE);
        adder.setInput(4, InputWire.ONE);
        adder.setInput(5, InputWire.ONE);
        System.out.println("Add 1: " + Arrays.toString(IntStream.range(0, 3).mapToObj(adder::evaluate).toArray()));

        adder.setInput(4, Wire.ZERO);
        System.out.println("Add 2: " + Arrays.toString(IntStream.range(0, 3).mapToObj(adder::evaluate).toArray()));
        */
        /*
        List<Wire> polyA = List.of(OneInput.instance(), OneInput.instance()); // 1 + x
        List<Wire> polyB = List.of(ZeroInput.instance(), OneInput.instance()); // x
        Circuit twoBitMultiplier = new TwoBitMultiplier(polyA, polyB);
        System.out.println("a * b = "); // x + x^2
        Wire out0 = twoBitMultiplier.getOutput(0);
        Wire out1 = twoBitMultiplier.getOutput(1);
        Wire out2 = twoBitMultiplier.getOutput(2);
        System.out.println(out0.evaluate());
        System.out.println(out1.evaluate());
        System.out.println(out2.evaluate());

        System.out.println("Switch b to 1 + x");
        twoBitMultiplier.setInput(OneInput.instance(), 2);
        System.out.println(out0.evaluate());
        System.out.println(out1.evaluate());
        System.out.println(out2.evaluate());

        setInput(twoBitMultiplier, 0, false );
        setInput(twoBitMultiplier, 1, true);
        setInput(twoBitMultiplier, 2, true);
        setInput(twoBitMultiplier, 3, true);
        System.out.println("Result");
        System.out.println(out0.evaluate());
        System.out.println(out1.evaluate());
        System.out.println(out2.evaluate());*/
    }

    public static void identityTest() {
        System.out.println("IDENTITY CIRCUIT TEST");
        Circuit identity = new IdentityCircuit(2);
        Wire a = Wire.ONE;
        Wire b = Wire.ZERO;
        identity.setInput(0, a);
        identity.setInput(1, b);
        System.out.println("InputSlot0: " + a.evaluate());
        System.out.println("InputSlot1: " + b.evaluate());
        System.out.println("OutputSlot0: " + identity.evaluate(0));
        System.out.println("OutputSlot1: " + identity.evaluate(1));
        System.out.println();

        a = Wire.ZERO;
        identity.setInput(0, a);
        System.out.println("InputSlot0: " + a.evaluate());
        System.out.println("InputSlot1: " + b.evaluate());
        System.out.println("OutputSlot0: " + identity.evaluate(0));
        System.out.println("OutputSlot1: " + identity.evaluate(1));
        System.out.println("------------------------------");
        System.out.println();
    }

    public static void andTest() {
        System.out.println("AND CIRCUIT TEST");
        Circuit and = new AndCircuit();
        Wire a = Wire.ONE;
        Wire b = Wire.ZERO;
        and.setInput(0, a);
        and.setInput(1, b);
        System.out.println("InputSlot0: " + a.evaluate());
        System.out.println("InputSlot1: " + b.evaluate());
        System.out.println("OutputSlot0: " + and.evaluate(0));
        System.out.println();

        b = Wire.ONE;
        and.setInput(1, b);
        System.out.println("InputSlot0: " + a.evaluate());
        System.out.println("InputSlot1: " + b.evaluate());
        System.out.println("OutputSlot0: " + and.evaluate(0));
        System.out.println("------------------------------");
        System.out.println();
    }

    public static void xorTest() {
        System.out.println("XOR CIRCUIT TEST");
        Circuit xor = new XorCircuit();
        Wire a = Wire.ONE;
        Wire b = Wire.ZERO;
        xor.setInput(0, a);
        xor.setInput(1, b);
        System.out.println("InputSlot0: " + a.evaluate());
        System.out.println("InputSlot1: " + b.evaluate());
        System.out.println("OutputSlot0: " + xor.evaluate(0));
        System.out.println();

        b = Wire.ONE;
        xor.setInput(1, b);
        System.out.println("InputSlot0: " + a.evaluate());
        System.out.println("InputSlot1: " + b.evaluate());
        System.out.println("OutputSlot0: " + xor.evaluate(0));
        System.out.println("------------------------------");
        System.out.println();
    }

    public static void realKaratsubaTest() {
        System.out.println("REAL KARATSUBA CIRCUIT TEST");
        Circuit mul = new KaratsubaMultiplier(2);
        List<Wire> a = List.of(Wire.ONE, Wire.ONE);
        List<Wire> b = List.of(Wire.ONE, Wire.ONE);

        mul.setInput(0, a.get(0));
        mul.setInput(1, a.get(1));
        mul.setInput(2, b.get(0));
        mul.setInput(3, b.get(1));
        System.out.println("InputA: " + (a.get(0).evaluate() ? 1 : 0) + " + " + (a.get(1).evaluate() ? 1 : 0) + "x");
        System.out.println("InputB: " + (b.get(0).evaluate() ? 1 : 0) + " + " + (b.get(1).evaluate() ? 1 : 0) + "x");
        System.out.println("OutputSlot0: " + mul.evaluate(0));
        System.out.println("OutputSlot1: " + mul.evaluate(1));
        System.out.println("OutputSlot2: " + mul.evaluate(2));
        System.out.println();

        a = List.of(Wire.ZERO, Wire.ZERO);
        mul.setInput(0, a.get(0));
        mul.setInput(1, a.get(1));
        System.out.println("InputA: " + (a.get(0).evaluate() ? 1 : 0) + " + " + (a.get(1).evaluate() ? 1 : 0) + "x");
        System.out.println("InputB: " + (b.get(0).evaluate() ? 1 : 0) + " + " + (b.get(1).evaluate() ? 1 : 0) + "x");
        System.out.println("OutputSlot0: " + mul.evaluate(0));
        System.out.println("OutputSlot1: " + mul.evaluate(1));
        System.out.println("OutputSlot2: " + mul.evaluate(2));
        System.out.println("------------------------------");
        System.out.println();
    }

    public static void fourBitsTest() {
        System.out.println("KARATSUBA 4 BIT TEST");
        Circuit mul = new KaratsubaMultiplier(4);
        List<Wire> a = List.of(Wire.ONE, Wire.ZERO, Wire.ONE, Wire.ZERO);
        List<Wire> b = List.of(Wire.ONE, Wire.ONE, Wire.ONE, Wire.ONE);

        mul.setInput(0, a.get(0));
        mul.setInput(1, a.get(1));
        mul.setInput(2, a.get(2));
        mul.setInput(3, a.get(3));
        mul.setInput(4, a.get(0));
        mul.setInput(5, a.get(1));
        mul.setInput(6, b.get(2));
        mul.setInput(7, b.get(3));
        System.out.print("InputA: " + (a.get(0).evaluate() ? 1 : 0) + " + " + (a.get(1).evaluate() ? 1 : 0) + "x + ");
        System.out.println((a.get(2).evaluate() ? 1 : 0) + "x^2 + " + (a.get(3).evaluate() ? 1 : 0) + "x^3");
        System.out.print("InputB: " + (b.get(0).evaluate() ? 1 : 0) + " + " + (b.get(1).evaluate() ? 1 : 0) + "x + ");
        System.out.println((b.get(2).evaluate() ? 1 : 0) + "x^2 + " + (b.get(3).evaluate() ? 1 : 0) + "x^3");
        System.out.println("OutputSlot0: " + mul.evaluate(0));
        System.out.println("OutputSlot1: " + mul.evaluate(1));
        System.out.println("OutputSlot2: " + mul.evaluate(2));
        System.out.println("OutputSlot3: " + mul.evaluate(3));
        System.out.println("OutputSlot4: " + mul.evaluate(4));
        System.out.println("OutputSlot5: " + mul.evaluate(5));
        System.out.println("OutputSlot6: " + mul.evaluate(6));
        System.out.println();

        a = List.of(Wire.ZERO, Wire.ZERO, Wire.ZERO, Wire.ZERO);
        mul.setInput(0, a.get(0));
        mul.setInput(1, a.get(1));
        mul.setInput(2, a.get(2));
        mul.setInput(3, a.get(3));
        System.out.print("InputA: " + (a.get(0).evaluate() ? 1 : 0) + " + " + (a.get(1).evaluate() ? 1 : 0) + "x + ");
        System.out.println((a.get(2).evaluate() ? 1 : 0) + "x^2 + " + (a.get(3).evaluate() ? 1 : 0) + "x^3");
        System.out.print("InputB: " + (b.get(0).evaluate() ? 1 : 0) + " + " + (b.get(1).evaluate() ? 1 : 0) + "x + ");
        System.out.println((b.get(2).evaluate() ? 1 : 0) + "x^2 + " + (b.get(3).evaluate() ? 1 : 0) + "x^3");
        System.out.println("OutputSlot0: " + mul.evaluate(0));
        System.out.println("OutputSlot1: " + mul.evaluate(1));
        System.out.println("OutputSlot2: " + mul.evaluate(2));
        System.out.println("OutputSlot3: " + mul.evaluate(3));
        System.out.println("OutputSlot4: " + mul.evaluate(4));
        System.out.println("OutputSlot5: " + mul.evaluate(5));
        System.out.println("OutputSlot6: " + mul.evaluate(6));
        System.out.println("------------------------------");
        System.out.println();
    }
}