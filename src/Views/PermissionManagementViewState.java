package Views;

public class PermissionManagementViewState implements ViewState {

    private static PermissionManagementViewState instance;

    private PermissionManagementViewState() {}

    public static PermissionManagementViewState getInstance() {
        if (instance == null) instance = new PermissionManagementViewState();
        return instance;
    }

    @Override
    public void enter() { System.out.println("Entering Permission Management View"); }

    @Override
    public void exit() { System.out.println("Leaving Permission Management View"); }

    @Override
    public void render() {
        System.out.println("=== Permission Management ===");
        System.out.println("Actions: APPROVE, LOGOUT");
    }

    @Override
    public void handleAction(ViewContext ctx, String action, String... args) {
        if (action.equals("APPROVE")) {
            String permissionId = args[0];
            System.out.println("Approving permission: " + permissionId);
        } else if (action.equals("LOGOUT")) {
            ctx.logout();
        } else {
            System.out.println("Unknown action: " + action);
        }
    }

    @Override
    public String getViewName() { return "PERMISSION_MANAGEMENT"; }

    @Override
    public boolean requiresAuthentication() { return true; }
}