package gspd.ispd.dependencies;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * SimpleDependencyTest
 */
public class SimpleDependencyNodeTest {

    @Test
    public void testSimpleDependency() {
        DependencyNode a = new SimpleDependencyNode();
        DependencyNode b = new SimpleDependencyNode();
        // b depends on a (a -> b)
        assertTrue(b.addDependency(a));
        // do not add loops
        assertFalse(a.addDependency(a));
        assertFalse(a.isDependencyOf(a));
        // do not add parallel dependencies
        // NOTE: a is already dependency of b
        assertFalse(b.addDependency(a));
        // do not add loops
        assertFalse(a.addDependent(a));
        // do not add parallel dependencies
        assertFalse(a.addDependent(b));
    }
}