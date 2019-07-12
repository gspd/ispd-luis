package gspd.ispd.dependencies;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * AcyclicDependencyTest
 */
public class AcyclicDependencyTest {

    @Test
    public void testAcyclic() {
        Dependency a = new AcyclicDependency();
        Dependency b = new AcyclicDependency();
        Dependency c = new AcyclicDependency();
        assertTrue(a.addDependent(b));
        assertTrue(b.addDependent(c));
        // do not allow cyclic dependencies chaining
        assertFalse(c.addDependent(a));
    }
}