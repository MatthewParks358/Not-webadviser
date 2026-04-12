package edu.advising.access;

import java.util.EnumSet;
import java.util.Set;

/**
 * LibraryHoldDecorator - Blocks access to library features.
 *
 * Applied when a user has overdue library materials or unpaid fines.
 * Blocks: LIBRARY
 */
public class LibraryHoldDecorator extends AccountDecorator {

    private static final Set<Feature> BLOCKED_FEATURES = EnumSet.of(
            Feature.LIBRARY
    );

    public LibraryHoldDecorator(Account wrappedAccount) {
        super(wrappedAccount);
    }

    @Override
    public boolean hasAccess(Feature feature) {
        if (BLOCKED_FEATURES.contains(feature)) {
            printDeniedMessage(feature);
            return false;
        }
        return wrappedAccount.hasAccess(feature);
    }

    @Override
    public String getHoldType() {
        return "Library";
    }
}
