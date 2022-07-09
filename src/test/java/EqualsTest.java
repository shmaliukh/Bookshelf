import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EqualsTest {

    @Test
    public void test1() {
        Integer a = 9;
        Integer b = 9;
        Assertions.assertTrue(a == b);
    }

    @Test
    public void test2() {
        Long a = (long) 9;
        Long b = (long) 9;
        Assertions.assertFalse(a == b);
    }

    @Test
    public void test3() {
        Assertions.assertTrue("" + 5 == "5");

    }
}
