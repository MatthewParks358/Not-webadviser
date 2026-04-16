package edu.advising.access;

import edu.advising.users.User;

import java.util.EnumSet;

/**
 * AdminAccount - Concrete account for administrators.
 *
 * Provides full access to ALL system features by default.
 */
public class AdminAccount extends BaseAccount {

    private static final EnumSet<Feature> ALL_FEATURES = EnumSet.allOf(Feature.class);

    public AdminAccount(User user) {
        super(user, ALL_FEATURES);
    }

    @Override
    protected String getAccountType() {
        return "Admin";
    }
}
