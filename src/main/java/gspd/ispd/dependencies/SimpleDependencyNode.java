package gspd.ispd.dependencies;

/**
 * Subclass of dependency that not allow adding direct redundant dependency, or
 * self-dependencies. That is, it mantains the dependency graph simple.
 * 
 * @author luisbaldissera
 */
public class SimpleDependencyNode extends DependencyNode {

    /**
     * Simple dependency node constructor
     * @param reference the object the node refers to
     */
    public SimpleDependencyNode(Object reference) {
        super(reference);
    }

    /**
     * Acyclic dependency node constructor without object reference
     */
    public SimpleDependencyNode() {
        this(null);
    }

    /**
     * Adds a dependency that this depends on.
     * @param dependencyNode the dependency to add
     * @return true if could be added and false otherwise
     */
    @Override
    public boolean addDependency(DependencyNode dependencyNode) {
        if (validateNewDependency(dependencyNode)) {
            return super.addDependency(dependencyNode);
        } else {
            return false;
        }
    }

    /**
     * Adds a dependency that depends on this
     * @param dependent the dependent
     * @return true if could be added and false otherwise
     */
    @Override
    public boolean addDependent(DependencyNode dependent) {
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
     * @param dependencyNode the dependency to be added
     * @return true if the dependency can be safety added
     */
    protected boolean validateNewDependency(DependencyNode dependencyNode) {
        return ((SimpleDependencyNode) dependencyNode).validateNewDependent(this);
    }

    /**
     * Check if the dependent to be added as dependent of this will not break the
     * rules for the simple graph
     * 
     * @param dependent the dependency to be added
     * @return true if the dependency can be safety added
     */
    protected boolean validateNewDependent(DependencyNode dependent) {
        return !isDependencyOf(dependent) && dependent != this;
    }

}
