package edu.advising.access;

/**
 * AccessChecker - Utility for checking feature access before operations.
 *
 * Provides a centralized way to verify access and print appropriate messages.
 * Use this before each feature access attempt.
 */
public class AccessChecker {

    /**
     * Check if the account has access to the feature.
     * Prints a success or denied message.
     *
     * @param account the account to check
     * @param feature the feature to access
     * @return true if access is granted, false otherwise
     */
    public static boolean checkAccess(Account account, Feature feature) {
        boolean hasAccess = account.hasAccess(feature);
        if (hasAccess) {
            System.out.println("Access granted: " + feature.getDisplayName() +
                    " for " + account.getUser().getUsername());
        }
        // Denied message is printed by the decorator that blocks access
        return hasAccess;
    }

    /**
     * Remove a specific hold type from the account chain.
     * Returns the account with that hold layer removed.
     *
     * @param account the decorated account
     * @param holdClass the hold decorator class to remove
     * @return the account with the hold removed, or original if hold not found
     */
    public static Account removeHold(Account account, Class<? extends AccountDecorator> holdClass) {
        if (account == null) {
            return null;
        }

        // If this is the hold we want to remove, return the wrapped account
        if (holdClass.isInstance(account)) {
            return ((AccountDecorator) account).getWrappedAccount();
        }

        // If this is a decorator, recurse into the wrapped account
        if (account instanceof AccountDecorator) {
            AccountDecorator decorator = (AccountDecorator) account;
            Account inner = removeHold(decorator.getWrappedAccount(), holdClass);
            // Rebuild this layer with the modified inner account
            return rebuildDecorator(decorator, inner);
        }

        // Base account, nothing to remove
        return account;
    }

    /**
     * Rebuild a decorator around a new inner account.
     */
    private static Account rebuildDecorator(AccountDecorator original, Account newInner) {
        if (original instanceof FinancialHoldDecorator) {
            return new FinancialHoldDecorator(newInner);
        } else if (original instanceof AcademicHoldDecorator) {
            return new AcademicHoldDecorator(newInner);
        } else if (original instanceof LibraryHoldDecorator) {
            return new LibraryHoldDecorator(newInner);
        }
        // Unknown decorator type - return inner as fallback
        return newInner;
    }
}
