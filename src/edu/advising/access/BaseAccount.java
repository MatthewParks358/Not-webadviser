package edu.advising.access;

import edu.advising.users.User;

import java.util.EnumSet;
import java.util.Set;

/**
 * BaseAccount - Abstract base class for concrete account types.
 *
 * Wraps a User and provides default access based on the account type.
 * Subclasses define which features are accessible by default.
 */
public abstract class BaseAccount implements Account {

    protected final User user;
    protected final Set<Feature> defaultFeatures;

    protected BaseAccount(User user, Set<Feature> defaultFeatures) {
        this.user = user;
        this.defaultFeatures = EnumSet.copyOf(defaultFeatures);
    }

    @Override
    public boolean hasAccess(Feature feature) {
        return defaultFeatures.contains(feature);
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public String getAccountDescription() {
        return user.getUsername() + " (" + getAccountType() + ")";
    }

    /**
     * Get the type name for this account.
     *
     * @return account type name (e.g., "Student", "Faculty", "Admin")
     */
    protected abstract String getAccountType();
}
