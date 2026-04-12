package edu.advising.access;

import edu.advising.users.User;

/**
 * Account - Interface for the Decorator pattern.
 *
 * Defines the contract for checking feature access. Both concrete account
 * types and decorators implement this interface.
 */
public interface Account {

    /**
     * Check if this account has access to the specified feature.
     *
     * @param feature the feature to check access for
     * @return true if access is granted, false otherwise
     */
    boolean hasAccess(Feature feature);

    /**
     * Get the underlying user associated with this account.
     *
     * @return the User object
     */
    User getUser();

    /**
     * Get a description of this account for display purposes.
     *
     * @return account description
     */
    String getAccountDescription();
}
