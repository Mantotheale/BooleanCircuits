package thesis;

import org.jetbrains.annotations.NotNull;
import thesis.circuit.*;
import thesis.poly.Polynomial;
import thesis.signal.Bus;
import thesis.signal.ConstantSignal;
import thesis.signal.Signal;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main() {
        Adder adder = new Adder(
                new Bus(ConstantSignal.TRUE, ConstantSignal.FALSE, ConstantSignal.FALSE), // 1
                new Bus(ConstantSignal.TRUE, ConstantSignal.TRUE, ConstantSignal.FALSE) // 1 + x
        );
        System.out.println(adder.outputBus().stream().map(Signal::evaluate).toList()); // x
        System.out.println("Gate count: " + adder.gateCount());

        System.out.println("SHIFTADDER");
        ShiftAdder shiftAdder = new ShiftAdder(
                new Bus(ConstantSignal.TRUE, ConstantSignal.FALSE, ConstantSignal.TRUE),
                2
        ); // 1 + x^2, shift: 2
        System.out.println(shiftAdder.outputBus().stream().map(Signal::evaluate).toList()); // 1 + x^4
        System.out.println("Gate count: " + shiftAdder.gateCount());

        System.out.println("KARATSUBA 1bit");
        SchoolBook karatsuba1bit = new SchoolBook(
                new Bus(ConstantSignal.TRUE), // 1
                new Bus(ConstantSignal.FALSE) // 0
        );
        System.out.println(karatsuba1bit.outputBus().stream().map(Signal::evaluate).toList()); // 0
        System.out.println("Gate count: " + karatsuba1bit.gateCount());

        System.out.println("KARATSUBA 2bit");
        SchoolBook karatsuba2bit = new SchoolBook(
                new Bus(ConstantSignal.TRUE, ConstantSignal.FALSE), // 1
                new Bus(ConstantSignal.FALSE, ConstantSignal.TRUE) // x
        );
        System.out.println(karatsuba2bit.outputBus().stream().map(Signal::evaluate).toList()); // x
        System.out.println("Gate count: " + karatsuba2bit.gateCount());

        System.out.println("KARATSUBA3bit");
        SchoolBook karatsuba3bit = new SchoolBook(
                new Bus(ConstantSignal.TRUE, ConstantSignal.FALSE, ConstantSignal.TRUE), // 1 + x^2
                new Bus(ConstantSignal.FALSE, ConstantSignal.FALSE, ConstantSignal.TRUE) // x^2
        );
        System.out.println(karatsuba3bit.outputBus().stream().map(Signal::evaluate).toList()); // x^2 + x^4
        System.out.println("Gate count: " + karatsuba3bit.gateCount());

        System.out.println("KARATSUBA 4bit");
        SchoolBook karatsuba4bit = new SchoolBook(
                new Bus(ConstantSignal.TRUE, ConstantSignal.FALSE, ConstantSignal.TRUE, ConstantSignal.FALSE), // 1 + x^2
                new Bus(ConstantSignal.FALSE, ConstantSignal.FALSE, ConstantSignal.TRUE, ConstantSignal.TRUE) // x^2 + x^3
        );
        System.out.println(karatsuba4bit.outputBus().stream().map(Signal::evaluate).toList()); // x^2 + x^3 + x^4 + x^5
        System.out.println("Gate count: " + karatsuba4bit.gateCount());

        Random r = new Random();
        List<Boolean> a = padList(IntToBinaryList.convert(new BigInteger(100, r)), 100);
        List<Boolean> b = padList(IntToBinaryList.convert(new BigInteger(100, r)), 100);
        // Schoolbook = 19801
        // Karatsuba = 13575

        SchoolBook circuit = new SchoolBook(
                new Bus(a.stream().map(x -> x ? ConstantSignal.TRUE : ConstantSignal.FALSE)),
                new Bus(b.stream().map(x -> x ? ConstantSignal.TRUE : ConstantSignal.FALSE))
        );

        long start = System.currentTimeMillis();
        List<Boolean> res = circuit.outputBus().stream().map(Signal::evaluate).toList();
        Polynomial circRes = new Polynomial(res);
        Polynomial exp = new Polynomial(a).mul(new Polynomial(b));
        if (!circRes.equals(exp)) {
            throw new RuntimeException("ERRORE circuito");
        }

        long middle = System.currentTimeMillis();

        res = circuit.outputBus().stream().map(Signal::evaluate).toList();
        circRes = new Polynomial(res);
        if (!circRes.equals(exp)) {
            throw new RuntimeException("ERRORE circuito");
        }

        long end = System.currentTimeMillis();

        System.out.println("Gates: " + circuit.gateCount());
        System.out.println("Tempo 1: " + (middle - start));
        System.out.println("Tempo 2: " + (end - middle));
        System.out.println("Tot: " + (end - start));
        // Normale: 13905
    }

    private static @NotNull List<@NotNull Boolean> padList(@NotNull List<@NotNull Boolean> l, int len) {
        for (int i = l.size(); i < len; i++) {
            l.add(Boolean.FALSE);
        }

        return l;
    }
}