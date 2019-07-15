package gspd.ispd.dependencies;

/**
 * Subclass of simple dependency. This class, besides garantees
 * that the dependency graph is simples, it also always check
 * for acyclicness to validate additions
 * 
 * @author luisbaldissera
 */
public class AcyclicDependency extends SimpleDependency {

    /**
     * Used in the algorithm for identifying cycles
     */
    private boolean flag;
    /**
     * Used in the algorithm for identifying cycles, optmizing redundant
     * verifications. This counter increases at each redundant verification, so does
     * not need re-verifying.
     */
    private int counter;

    /**
     * Acyclic dependency node constructor
     * @param reference the object the node refers to
     */
    public AcyclicDependency(Object reference) {
        super(reference);
        flag = false;
        counter = 0;
    }

    /**
     * Acyclic dependency node constructor without object reference
     */
    public AcyclicDependency() {
        this(null);
    }

    @Override
    protected boolean validateNewDependency(Dependency dependency) {
        return ((AcyclicDependency) dependency).validateNewDependent(this);
    }

    @Override
    protected boolean validateNewDependent(Dependency dependent) {
        boolean valid = true;
        // chacks first if it is a simple dependency
        if (super.validateNewDependent(dependent)) {
            // then if it is, check is it has cycles
            flagUp();
            ((AcyclicDependency) dependent).inCycle();
            if (isFlagDown()) {
                valid = false;
            }
            flagDown();
        } else {
            // if the graph is not simple, invalidate it
            valid = false;
        }
        return valid;
    }

    private boolean inCycle() {
        boolean cycleDetected = false;
        if (isFlagUp()) {
            // *** CYCLE FOUND ***
            // found a cycle. Lies telling it does
            // not found it and flag down to the lie be
            // indentified later
            flagDown();
            // ATTEINTION: it's a lie
            cycleDetected = false;
        } else if (alreadyChecked()) {
            // *** REDUNDANT VERIFICATION ***
            // this node was already checked, and so it is
            // not in a cycle.
            incrementCounter();
            cycleDetected = false;
        } else if (isTheLast()) {
            // *** LAST NODE ***
            // this node is the last, then can't be part of
            // a cycle
            incrementCounter();
            cycleDetected = false;
        } else {
            // *** MAY HAVE A CYCLE ***
            // verification of the childs are needded.
            // Flag up to denote it may be in a cycle
            flagUp();
            for (Dependency dependent : dependents) {
                AcyclicDependency aDependent = (AcyclicDependency) dependent;
                if (aDependent.inCycle()) {
                    // *** CYCLE DETECTED IN DEPENDENT ***
                    // if the dependent is in a cycle, then the
                    // cycle was already detected
                    cycleDetected = true;
                    break;
                }
            }
            if (isFlagDown()) {
                // *** LIE DETECTED ***
                // lie detected, there was a cycle here
                cycleDetected = true;
            } else {
                incrementCounter();
            }
            flagDown();
        }
        return cycleDetected;
    }

    private void flagUp() {
        flag = true;
    }

    private void flagDown() {
        flag = false;
    }

    private boolean isFlagUp() {
        return flag;
    }

    private boolean isFlagDown() {
        return !flag;
    }

    private void incrementCounter() {
        if (dependenciesCount != 0)
            counter = (counter + 1) % dependenciesCount;
    }

    private boolean alreadyChecked() {
        return counter > 0;
    }

    private boolean isTheLast() {
        return dependents.size() == 0;
    }

    private void resetCounter() {
        counter = 0;
    }
}
