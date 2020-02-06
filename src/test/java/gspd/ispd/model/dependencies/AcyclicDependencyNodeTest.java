package gspd.ispd.model.dependencies;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * AcyclicDependencyTest
 */
public class AcyclicDependencyNodeTest {

    @Test
    public void testAcyclic() {
        DependencyNode a = new AcyclicDependencyNode();
        DependencyNode b = new AcyclicDependencyNode();
        DependencyNode c = new AcyclicDependencyNode();
        assertTrue(a.addDependent(b));
        assertTrue(b.addDependent(c));
        // do not allow cyclic dependencies chaining
        assertFalse(c.addDependent(a));
    }
}