package gspd.ispd.dependencies;

/**
 * Subclass of dependency that not allow adding direct redundant dependency, or
 * self-dependencies. That is, it mantains the dependency graph simple
 */
public class SimpleDependency extends Dependency {

    /**
     * Adds a dependency that this depends on.
     * @param dependency the dependency to add
     * @return true if could be added and false otherwise
     */
    @Override
    public boolean addDependency(Dependency dependency) {
        if (validateNewDependency(dependency)) {
            return super.addDependency(dependency);
        } else {
            return false;
        }
    }

    /**
     * Adds a dependency that depends on this
     * @param dependency the dependent
     * @return true if could be added and false otherwise
     */
    @Override
    public boolean addDependent(Dependency dependent) {
        if (validateNewDependent(dependent)) {
            return super.addDependent(dependent);
        } else {
            return false;
        }
    }

    /**
     * Check if the dependency to be added as dependency of this will not break the
     * rules for the simple graph.
     * 
     * @param dependency the dependency to be added
     * @return true if the dependency can be safety added
     */
    protected boolean validateNewDependency(Dependency dependency) {
        return ((SimpleDependency) dependency).validateNewDependent(this);
    }

    /**
     * Check if the dependent to be added as dependent of this will not break the
     * rules for the simple graph
     * 
     * @param dependency the dependency to be added
     * @return true if the dependency can be safety added
     */
    protected boolean validateNewDependent(Dependency dependent) {
        return !isDependencyOf(dependent) && dependent != this;
    }

}
