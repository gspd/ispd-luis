package gspd.ispd.architecture;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> Dependency class manage dependencies as a distributed direct graph.
 * Each node - a dependency - has a list of the nodes those depends on it
 * and the quantity of nodes that it depends on. </p>
 *
 * @author luisbaldissera
 */
public class Dependency {
    /**
     * The list of dependencies those depends on this
     */
    protected List<Dependency> dependents;
    /**
     * The number of dependencies this depends on
     */
    protected int dependenciesCount;

    /**
     * Dependency constructor
     */
    public Dependency() {
        dependents = new ArrayList<>();
        dependenciesCount = 0;
    }

    /**
     * @return {@code true} whether the dependencies os resolved
     */
    public boolean isFree() {
        return dependenciesCount == 0;
    }

    /**
     * Adds a dependency that this depends on
     * @param dependency the dependency to add
     */
    public boolean addDependency(Dependency dependency) {
        dependency.addDependent(this);
        return true;
    }

    /**
     * Adds a dependency that depends on this
     * @param dependency the dependent
     */
    public void addDependent(Dependency dependent) {
        dependents.add(dependent);
        dependent.dependenciesCount++;
    }

    /**
     * Removes the dependency of this, if this has it as dependency
     * @param dependency the dependency to remove
     */
    public void removeDependency(Dependency dependency) {
        dependency.removeDependent(this);
    }

    /**
     * Removes the dependent if it is dependent of this
     * @param dependent the dependent to remove
     */
    public void removeDependent(Dependency dependent) {
        if (dependents.remove(dependent)) {
            dependent.dependenciesCount--;
        }

    }

    /**
     * Removes the dependent by index in the dependents list
     * @param index the index of the dependency to remove
     */
    private void removeDependent(int index) {
        Dependency removed = dependents.remove(index);
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
     * @param dependency the dependency
     * @return true if {@code dependency} is a dependency of this
     */
    public boolean dependsOn(Dependency dependency) {
        return dependency.isDependencyOf(this);
    }

    /**
     * Checks whether this dependency is a dependency of given dependency
     * @param dependency the dependency
     * @return true if this dependency is a dependency of {@code dependency}
     */
    public boolean isDependencyOf(Dependency dependency) {
        return dependents.contains(dependency);
    }
}
