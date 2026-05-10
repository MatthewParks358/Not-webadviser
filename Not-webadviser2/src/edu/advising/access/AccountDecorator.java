package edu.advising.access;

import edu.advising.users.User;

/**
 * AccountDecorator - Abstract base class for account decorators.
 *
 * Implements the Decorator pattern by wrapping an Account and delegating
 * calls to it. Concrete decorators (holds) override hasAccess() to add
 * restrictions.
 *
 * Decorators can be stacked in any order to apply multiple holds.
 */
public abstract class AccountDecorator implements Account {

    protected final Account wrappedAccount;

    protected AccountDecorator(Account wrappedAccount) {
        this.wrappedAccount = wrappedAccount;
    }

    @Override
    public boolean hasAccess(Feature feature) {
        return wrappedAccount.hasAccess(feature);
    }

    @Override
    public User getUser() {
        return wrappedAccount.getUser();
    }

    @Override
    public String getAccountDescription() {
        return wrappedAccount.getAccountDescription();
    }

    /**
     * Get the wrapped account (for unwrapping/removing this decorator).
     *
     * @return the wrapped Account
     */
    public Account getWrappedAccount() {
        return wrappedAccount;
    }

    /**
     * Get the name of this hold type.
     *
     * @return hold type name
     */
    public abstract String getHoldType();

    /**
     * Print a denied message for the specified feature.
     *
     * @param feature the feature that was denied
     */
    protected void printDeniedMessage(Feature feature) {
        System.out.println("ACCESS DENIED: " + feature.getDisplayName() +
                " - " + getHoldType() + " hold on account for " +
                getUser().getUsername());
    }
}
