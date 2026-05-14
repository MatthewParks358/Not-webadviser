package Views;

public class FacultyDashboardViewState implements ViewState {

    private static FacultyDashboardViewState instance;

    private FacultyDashboardViewState() {}

    public static FacultyDashboardViewState getInstance() {
        if (instance == null) instance = new FacultyDashboardViewState();
        return instance;
    }

    @Override
    public void enter() { System.out.println("Entering Faculty Dashboard"); }

    @Override
    public void exit() { System.out.println("Leaving Faculty Dashboard"); }

    @Override
    public void render() {
        System.out.println("=== Faculty Dashboard ===");
        System.out.println("Actions: NAVIGATE PERMISSIONS, LOGOUT");
    }

    @Override
    public void handleAction(ViewContext ctx, String action, String... args) {
        if (action.equals("NAVIGATE") && args[0].equals("PERMISSIONS")) {
            ctx.navigateTo(PermissionManagementViewState.getInstance());
        } else if (action.equals("LOGOUT")) {
            ctx.logout();
        }
    }

    @Override
    public String getViewName() { return "FACULTY_DASHBOARD"; }

    @Override
    public boolean requiresAuthentication() { return true; }
}