
package Views;

public class StudentDashboardViewState implements ViewState {

    private static StudentDashboardViewState instance;

    private StudentDashboardViewState() {}

    public static StudentDashboardViewState getInstance() {
        if (instance == null) instance = new StudentDashboardViewState();
        return instance;
    }

    @Override
    public void enter() { System.out.println("Entering Student Dashboard"); }

    @Override
    public void exit() { System.out.println("Leaving Student Dashboard"); }

    @Override
    public void render() {
        System.out.println("=== Student Dashboard ===");
        System.out.println("Actions: NAVIGATE REGISTRATION, NAVIGATE TRANSCRIPT, LOGOUT");
    }

    @Override
    public void handleAction(ViewContext ctx, String action, String... args) {
        if (action.equals("NAVIGATE")) {
            if (args[0].equals("REGISTRATION")) {
                ctx.navigateTo(RegistrationViewState.getInstance());
            } else if (args[0].equals("TRANSCRIPT")) {
                ctx.navigateTo(TranscriptViewState.getInstance());
            }
        } else if (action.equals("LOGOUT")) {
            ctx.logout();
        }
    }

    @Override
    public String getViewName() { return "STUDENT_DASHBOARD"; }

    @Override
    public boolean requiresAuthentication() { return true; }
}