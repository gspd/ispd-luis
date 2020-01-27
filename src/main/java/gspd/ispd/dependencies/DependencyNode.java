package gspd.ispd.dependencies;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> Dependency class manage dependencies as a distributed direct graph.
 * Each node - a dependency - has a list of the nodes those depends on it
 * and the quantity of nodes that it depends on. </p>
 *
 * @author luisbaldissera
 */
public class DependencyNode {
    /**
     * The list of dependencies those depends on this
     */
    protected List<DependencyNode> dependents;
    /**
     * The number of dependencies this depends on
     */
    protected int dependenciesCount;
    /**
     * The objects that this dependency refers to
     */
    private Object reference;

    /**
     * Dependency constructor
     * @param reference the object this dependency node refer to
     */
    public DependencyNode(Object reference) {
        dependents = new ArrayList<>();
        dependenciesCount = 0;
        this.reference = reference;
    }

    /**
     * Dependency contructor, with no reference
     * object
     */
    public DependencyNode() {
        this(null);
    }

    /**
     * @return {@code true} whether the dependencies os resolved
     */
    public boolean isFree() {
        return dependenciesCount == 0;
    }

    /**
     * Adds a dependency that this depends on.
     * @param dependencyNode the dependency to add
     * @return always true
     */
    public boolean addDependency(DependencyNode dependencyNode) {
        dependencyNode.addDependent(this);
        return true;
    }

    /**
     * Adds a dependency that depends on this
     * @param dependent the dependent
     * @return always true
     */
    public boolean addDependent(DependencyNode dependent) {
        dependents.add(dependent);
        dependent.dependenciesCount++;
        return true;
    }

    /**
     * Removes the dependency of this, if this has it as dependency
     * @param dependencyNode the dependency to remove
     */
    public void removeDependency(DependencyNode dependencyNode) {
        dependencyNode.removeDependent(this);
    }

    /**
     * Removes the dependent if it is dependent of this
     * @param dependent the dependent to remove
     */
    public void removeDependent(DependencyNode dependent) {
        if (dependents.remove(dependent)) {
            dependent.dependenciesCount--;
        }

    }

    /**
     * Removes the dependent by index in the dependents list
     * @param index the index of the dependency to remove
     */
    private void removeDependent(int index) {
        DependencyNode removed = dependents.remove(index);
        if (removed != null) {
            removed.dependenciesCount--;
        }
    }

    /**
     * Unlock all dependencies those depends on this
     */
    public void disconectDependents() {
        for (int i = 0; i < dependents.size(); i++) {
            removeDependent(i);
        }
    }

    /**
     * Checks if this dependency depends on the given dependency
     * @param dependencyNode the dependency
     * @return true if {@code dependency} is a dependency of this
     */
    public boolean dependsOn(DependencyNode dependencyNode) {
        return dependencyNode.isDependencyOf(this);
    }

    /**
     * Checks whether this dependency is a dependency of given dependency
     * @param dependencyNode the dependency
     * @return true if this dependency is a dependency of {@code dependency}
     */
    public boolean isDependencyOf(DependencyNode dependencyNode) {
        return dependents.contains(dependencyNode);
    }

    /**
     * @return the reference object of the dependency node
     */
    public Object getReference() {
        return reference;
    }
}
