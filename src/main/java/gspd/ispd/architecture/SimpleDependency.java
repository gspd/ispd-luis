package gspd.ispd.architecture;

/**
 * Subclass of dependency that not allow adding direct redundant dependency,
 * or self-dependencies. That is, it mantains the dependency graph simple
 */
public class SimpleDependency extends Dependency {

    @Override
    public boolean addDependency(Dependency dependency) {
        if (validateDependency(dependency)) {
            return super.addDependency(dependency);
        } else {
            return false;
        }
    }

    /**
     * Check if the dependency to be added as dependency of this will
     * not break the rules for the simple graph
     * @param dependency the dependency to be added
     * @return true if the dependency can be safety added
     */
    protected boolean validateDependency(Dependency dependency) {
        return !dependsOn(dependency) && dependency != this;
    }
}
