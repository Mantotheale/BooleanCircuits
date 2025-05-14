package thesis.poly;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

public class PolynomialTest {
    @Test
    public void emptyPolyTest() {
        Polynomial poly = new Polynomial();

        for (int i = 0; i < 10; i++) {
            Assertions.assertFalse(poly.coeff(i));
        }
    }

    @Test
    public void listPolyTest() {
        List<Boolean> coeffs = List.of(true, false, true, false, true);
        Polynomial poly = new Polynomial(coeffs);
        Assertions.assertEquals(coeffs, IntStream.range(0, coeffs.size()).mapToObj(poly::coeff).toList());
        Assertions.assertFalse(poly.coeff(coeffs.size()));
    }

    @Test
    public void arrayPolyTest() {
        boolean[] coeffs = new boolean[] {true, false, true, false, true};
        Polynomial poly = new Polynomial(coeffs);

        for (int i = 0; i < coeffs.length; i++) {
            Assertions.assertEquals(coeffs[i], poly.coeff(i));
        }
        Assertions.assertFalse(poly.coeff(coeffs.length));
    }

    @Test
    public void polyMulTest() {
        Polynomial a = new Polynomial(List.of(true, false, true));
        Polynomial b = new Polynomial(List.of(false, true, false));
        Polynomial prod = new Polynomial(List.of(false, true, false, true));
        Assertions.assertEquals(prod, a.mul(b));

        a = new Polynomial(List.of(true, true, true, true, true));
        b = new Polynomial(List.of(true, true, true, true, true));
        prod = new Polynomial(List.of(true, false, true, false, true, false, true, false, true));
        Assertions.assertEquals(prod, a.mul(b));

        a = new Polynomial(List.of(true, true, true, true, true));
        b = new Polynomial(List.of(false, false, false));
        prod = new Polynomial(List.of(false));
        Assertions.assertEquals(prod, a.mul(b));

        a = new Polynomial(List.of(true, false, true, true, false, true, true));
        b = new Polynomial(List.of(false, true, false, true, true, false, true));
        prod = new Polynomial(List.of(false, true, false, false, false, true, false, false, false, true, true, true, true));
        Assertions.assertEquals(prod, a.mul(b));
    }
}
