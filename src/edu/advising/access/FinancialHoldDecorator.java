package edu.advising.access;

import java.util.EnumSet;
import java.util.Set;

/**
 * FinancialHoldDecorator - Blocks access to financial and registration features.
 *
 * Applied when a student has unpaid balances or financial aid issues.
 * Blocks: REGISTRATION, FINANCIAL_AID, MAKE_PAYMENT, VIEW_GRADES (transcript hold)
 */
public class FinancialHoldDecorator extends AccountDecorator {

    private static final Set<Feature> BLOCKED_FEATURES = EnumSet.of(
            Feature.REGISTRATION,
            Feature.FINANCIAL_AID,
            Feature.MAKE_PAYMENT,
            Feature.VIEW_GRADES
    );

    public FinancialHoldDecorator(Account wrappedAccount) {
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
        return "Financial";
    }
}
