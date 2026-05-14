package Views;

public class GuestViewState implements ViewState {

    private static GuestViewState instance;

    private GuestViewState() {}

    public static GuestViewState getInstance() {
        if (instance == null) instance = new GuestViewState();
        return instance;
    }

    @Override
    public void enter() { System.out.println("Entering Guest View"); }

    @Override
    public void exit() { System.out.println("Leaving Guest View"); }

    @Override
    public void render() {
        System.out.println("=== Please Log In ===");
        System.out.println("Actions: LOGIN");
    }

    @Override
    public void handleAction(ViewContext ctx, String action, String... args) {
        if (action.equals("LOGIN")) {
            String username = args[0];
            String password = args[1];

            if (username.equals("student1") && password.equals("pass")) {
                ctx.setCurrentUser(username, "STUDENT");
                ctx.navigateTo(StudentDashboardViewState.getInstance());
            } else if (username.equals("faculty1") && password.equals("pass")) {
                ctx.setCurrentUser(username, "FACULTY");
                ctx.navigateTo(FacultyDashboardViewState.getInstance());
            } else {
                System.out.println("Invalid credentials.");
            }
        }
    }

    @Override
    public String getViewName() { return "GUEST"; }

    @Override
    public boolean requiresAuthentication() { return false; }
}