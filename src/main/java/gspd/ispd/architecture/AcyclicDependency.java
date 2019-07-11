package gspd.ispd.architecture;

public class AcyclicDependency extends SimpleDependency {

    private boolean passedHere;
    private boolean permanent;

    public AcyclicDependency() {
        super();
        passedHere = false;
        permanent = false;
    }

    @Override
    protected boolean validateDependency(Dependency dependency) {
        if (super.validateDependency(dependency)) {
            return !hasCycle();
        } else {
            return false;
        }
    }

    private boolean hasCycle() {

        // CYCLE DETECTED: FLAG WAS UP
        // ---------------------------
        // if already passed here, then
        // it found a cycle. It will lie
        // telling that it did not find
        // cycle and flag down the
        // passedHere variable, to the
        // "lie" be detected later.
        // Note that it is also not
        // permanent
        if (passedHere) {
            // not permanent
            permanent = false;
            // flag down
            passedHere = false;
            // lie: tell that there is no cycle
            return false;
        }
        // NO CYCLE: ALREADY CHECKED
        // -------------------------
        // if this already was checked,
        // it does not contains a cycle
        if (isPermanent()) {
            return false;
        }
        // NO CYCLE: NO TRIGGERS
        // ---------------------
        // if there are no triggers,
        // then it does not close a cycle
        if (dependents.size() == 0) {
            // it permanently does not have cycle
            permanent = true;
            return false;
        }
        // if the code arrives here, it is not sure
        // whether there is a cycle or not. It needed
        // to check subsequent dependencies for cycles

        // TODO: Continue (Probably redo) from here

        return true;
    }

    private boolean isPermanent() {
        return permanent;
    }
}
