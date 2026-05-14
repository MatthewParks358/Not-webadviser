import edu.advising.gui.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// ============================================================================
// WEEK 6: GUI SYSTEM — Composite, Command, Iterator Patterns
// ============================================================================
//
// PURPOSE:
//   Exercises the GUI structure system implemented in Week 6.
//   Demonstrates three design patterns:
//     - COMPOSITE: GUI elements can contain other GUI elements
//     - COMMAND:   Buttons execute commands (e.g., screen changes)
//     - ITERATOR:  GUI elements can be traversed
//
// TESTS COVERED:
//   GROUP 1  — GuiElement basics (position, size, visibility)
//   GROUP 2  — GuiContainer (add/remove children, composite structure)
//   GROUP 3  — GuiScreen (screen lifecycle, activation)
//   GROUP 4  — GuiButton + GuiCommand (command execution)
//   GROUP 5  — ChangeScreenCommand (screen navigation)
//   GROUP 6  — ScreenManager (history, navigation)
//   GROUP 7  — Iterator pattern (shallow and deep iteration)
//   GROUP 8  — SpriteRegion (sprite atlas metadata)
//   GROUP 9  — Complex nested structure (real-world scenario)
//
// ============================================================================

public class Week6GuiTest {

    // ── Counters ─────────────────────────────────────────────────────────────
    private static int passed = 0;
    private static int failed = 0;

    // =========================================================================
    // ENTRY POINT
    // =========================================================================

    public static void main(String[] args) {
        banner("WEEK 6 — GUI SYSTEM  |  Composite + Command + Iterator");

        testGuiElementBasics();
        testGuiContainer();
        testGuiScreen();
        testGuiButtonAndCommand();
        testChangeScreenCommand();
        testScreenManager();
        testIteratorPattern();
        testSpriteRegion();
        testComplexNestedStructure();

        // ── Final report ──────────────────────────────────────────────────────
        banner("RESULTS");
        System.out.printf("  Total  : %d%n", passed + failed);
        System.out.printf("  Passed : %d%n", passed);
        System.out.printf("  Failed : %d%n", failed);
        System.out.println(failed == 0 ? "\n  ALL TESTS PASSED\n" : "\n  SOME TESTS FAILED\n");
    }

    // =========================================================================
    // GROUP 1 — GuiElement Basics
    // =========================================================================

    private static void testGuiElementBasics() {
        header("GROUP 1 — GuiElement Basics");

        GuiLabel label = new GuiLabel("title", "Welcome", 10, 20);
        label.setSize(100, 30);

        check("1.1  getId() returns correct id",         "title".equals(label.getId()));
        check("1.2  getX() returns 10",                  label.getX() == 10);
        check("1.3  getY() returns 20",                  label.getY() == 20);
        check("1.4  getWidth() returns 100",             label.getWidth() == 100);
        check("1.5  getHeight() returns 30",             label.getHeight() == 30);
        check("1.6  isVisible() default true",           label.isVisible());

        label.setVisible(false);
        check("1.7  setVisible(false) works",            !label.isVisible());

        label.setPosition(50, 60);
        check("1.8  setPosition() updates x",            label.getX() == 50);
        check("1.9  setPosition() updates y",            label.getY() == 60);

        check("1.10 containsPoint(55, 65) inside",       label.containsPoint(55, 65));
        check("1.11 containsPoint(200, 200) outside",    !label.containsPoint(200, 200));

        check("1.12 describe() contains id",             label.describe().contains("title"));
    }

    // =========================================================================
    // GROUP 2 — GuiContainer (COMPOSITE PATTERN)
    // =========================================================================

    private static void testGuiContainer() {
        header("GROUP 2 — GuiContainer (COMPOSITE)");

        GuiContainer container = new GuiContainer("panel", 0, 0, 200, 200);
        GuiLabel labelA = new GuiLabel("labelA", "First");
        GuiLabel labelB = new GuiLabel("labelB", "Second");

        check("2.1  initial child count = 0",            container.getChildCount() == 0);

        container.addChild(labelA);
        check("2.2  after addChild, count = 1",          container.getChildCount() == 1);

        container.addChild(labelB);
        check("2.3  after second addChild, count = 2",   container.getChildCount() == 2);

        check("2.4  getChild(0) is labelA",              container.getChild(0) == labelA);
        check("2.5  getChild(1) is labelB",              container.getChild(1) == labelB);

        check("2.6  findChild('labelA') finds it",       container.findChild("labelA") == labelA);
        check("2.7  findChild('missing') returns null",  container.findChild("missing") == null);

        container.removeChild(labelA);
        check("2.8  removeChild reduces count",          container.getChildCount() == 1);
        check("2.9  removed element not found",          container.findChild("labelA") == null);

        container.removeChild("labelB");
        check("2.10 removeChild by id works",            container.getChildCount() == 0);
    }

    // =========================================================================
    // GROUP 3 — GuiScreen
    // =========================================================================

    private static void testGuiScreen() {
        header("GROUP 3 — GuiScreen");

        GuiScreen screen = new GuiScreen("main", "Main Menu", 800, 600);

        check("3.1  getId() returns 'main'",             "main".equals(screen.getId()));
        check("3.2  getTitle() returns 'Main Menu'",     "Main Menu".equals(screen.getTitle()));
        check("3.3  getWidth() returns 800",             screen.getWidth() == 800);
        check("3.4  isActive() default false",           !screen.isActive());

        screen.onActivate();
        check("3.5  onActivate() sets active true",      screen.isActive());

        screen.onDeactivate();
        check("3.6  onDeactivate() sets active false",   !screen.isActive());

        // Screen is a container - add elements
        GuiLabel title = new GuiLabel("screenTitle", "Welcome");
        screen.addChild(title);
        check("3.7  screen can contain elements",        screen.getChildCount() == 1);
    }

    // =========================================================================
    // GROUP 4 — GuiButton + GuiCommand (COMMAND PATTERN)
    // =========================================================================

    private static void testGuiButtonAndCommand() {
        header("GROUP 4 — GuiButton + GuiCommand (COMMAND)");

        GuiButton button = new GuiButton("btn", "Click Me", 10, 10, 80, 30);

        check("4.1  getLabel() returns 'Click Me'",      "Click Me".equals(button.getLabel()));
        check("4.2  isEnabled() default true",           button.isEnabled());
        check("4.3  getCommand() default null",          button.getCommand() == null);

        // Create a simple test command
        final int[] counter = {0};
        GuiCommand incrementCommand = new GuiCommand() {
            @Override
            public void execute() {
                counter[0]++;
            }

            @Override
            public String getDescription() {
                return "Increment counter";
            }
        };

        button.setCommand(incrementCommand);
        check("4.4  setCommand() attaches command",      button.getCommand() == incrementCommand);

        button.click();
        check("4.5  click() executes command",           counter[0] == 1);

        button.click();
        check("4.6  second click() increments again",    counter[0] == 2);

        button.setEnabled(false);
        button.click();
        check("4.7  disabled button doesn't execute",    counter[0] == 2);

        button.setEnabled(true);
        button.setCommand(null);
        button.click();
        check("4.8  null command doesn't throw",         counter[0] == 2);

        check("4.9  describe() includes command info",   button.describe().contains("Increment counter") || button.describe().contains("none"));
    }

    // =========================================================================
    // GROUP 5 — ChangeScreenCommand
    // =========================================================================

    private static void testChangeScreenCommand() {
        header("GROUP 5 — ChangeScreenCommand");

        ScreenManager manager = new ScreenManager();
        GuiScreen mainScreen = new GuiScreen("main", "Main Menu");
        GuiScreen settingsScreen = new GuiScreen("settings", "Settings");

        manager.registerScreen(mainScreen);
        manager.registerScreen(settingsScreen);
        manager.changeScreen("main", false);

        ChangeScreenCommand toSettings = new ChangeScreenCommand(manager, "settings");

        check("5.1  getDescription() contains target",   toSettings.getDescription().contains("settings"));
        check("5.2  canUndo() false before execute",     !toSettings.canUndo());

        toSettings.execute();
        check("5.3  execute() changes screen",           manager.getCurrentScreen() == settingsScreen);
        check("5.4  canUndo() true after execute",       toSettings.canUndo());

        toSettings.undo();
        check("5.5  undo() returns to previous",         manager.getCurrentScreen() == mainScreen);
    }

    // =========================================================================
    // GROUP 6 — ScreenManager
    // =========================================================================

    private static void testScreenManager() {
        header("GROUP 6 — ScreenManager");

        ScreenManager manager = new ScreenManager();

        check("6.1  initial screen count = 0",           manager.getScreenCount() == 0);
        check("6.2  getCurrentScreen() initial null",    manager.getCurrentScreen() == null);

        GuiScreen home = new GuiScreen("home", "Home");
        GuiScreen profile = new GuiScreen("profile", "Profile");
        GuiScreen settings = new GuiScreen("settings", "Settings");

        manager.registerScreen(home);
        manager.registerScreen(profile);
        manager.registerScreen(settings);

        check("6.3  after register, count = 3",          manager.getScreenCount() == 3);
        check("6.4  getScreen('home') finds it",         manager.getScreen("home") == home);

        manager.changeScreen("home");
        check("6.5  changeScreen activates screen",      manager.getCurrentScreen() == home);
        check("6.6  home.isActive() = true",             home.isActive());
        check("6.7  canGoBack() false (no history)",     !manager.canGoBack());

        manager.changeScreen("profile");
        check("6.8  changeScreen to profile",            manager.getCurrentScreen() == profile);
        check("6.9  home.isActive() now false",          !home.isActive());
        check("6.10 canGoBack() true (home in history)", manager.canGoBack());

        manager.changeScreen("settings");
        check("6.11 now on settings",                    manager.getCurrentScreen() == settings);

        boolean wentBack = manager.goBack();
        check("6.12 goBack() returns true",              wentBack);
        check("6.13 goBack() returns to profile",        manager.getCurrentScreen() == profile);

        manager.goBack();
        check("6.14 second goBack() to home",            manager.getCurrentScreen() == home);

        boolean extraBack = manager.goBack();
        check("6.15 goBack() on empty returns false",    !extraBack);

        manager.clearHistory();
        check("6.16 clearHistory() empties stack",       !manager.canGoBack());
    }

    // =========================================================================
    // GROUP 7 — Iterator Pattern
    // =========================================================================

    private static void testIteratorPattern() {
        header("GROUP 7 — Iterator Pattern");

        // Build a nested structure:
        // screen
        //   ├── header (container)
        //   │     ├── logo (label)
        //   │     └── title (label)
        //   └── footer (label)

        GuiScreen screen = new GuiScreen("screen", "Test Screen");
        GuiContainer header = new GuiContainer("header");
        GuiLabel logo = new GuiLabel("logo", "LOGO");
        GuiLabel title = new GuiLabel("title", "Title");
        GuiLabel footer = new GuiLabel("footer", "Footer");

        header.addChild(logo);
        header.addChild(title);
        screen.addChild(header);
        screen.addChild(footer);

        // Shallow iteration (direct children only)
        List<String> shallowIds = new ArrayList<>();
        for (GuiElement child : screen) {
            shallowIds.add(child.getId());
        }

        check("7.1  shallow iteration finds 2 children", shallowIds.size() == 2);
        check("7.2  shallow has 'header'",               shallowIds.contains("header"));
        check("7.3  shallow has 'footer'",               shallowIds.contains("footer"));
        check("7.4  shallow does NOT have 'logo'",       !shallowIds.contains("logo"));

        // Deep iteration (all descendants)
        List<String> deepIds = new ArrayList<>();
        Iterator<GuiElement> deepIter = screen.deepIterator();
        while (deepIter.hasNext()) {
            deepIds.add(deepIter.next().getId());
        }

        check("7.5  deep iteration finds 5 elements",    deepIds.size() == 5);
        check("7.6  deep has 'screen' (root)",           deepIds.contains("screen"));
        check("7.7  deep has 'header'",                  deepIds.contains("header"));
        check("7.8  deep has 'logo'",                    deepIds.contains("logo"));
        check("7.9  deep has 'title'",                   deepIds.contains("title"));
        check("7.10 deep has 'footer'",                  deepIds.contains("footer"));

        // Using allDescendants() iterable
        int count = 0;
        for (GuiElement elem : screen.allDescendants()) {
            count++;
        }
        check("7.11 allDescendants() iterable works",    count == 5);

        // findDescendant recursive search
        check("7.12 findDescendant('logo') finds it",    screen.findDescendant("logo") == logo);
        check("7.13 findDescendant('missing') null",     screen.findDescendant("missing") == null);
    }

    // =========================================================================
    // GROUP 8 — SpriteRegion
    // =========================================================================

    private static void testSpriteRegion() {
        header("GROUP 8 — SpriteRegion");

        SpriteRegion sprite = new SpriteRegion("atlas.png", 32, 64, 16, 16);

        check("8.1  getAtlasPath() returns 'atlas.png'", "atlas.png".equals(sprite.getAtlasPath()));
        check("8.2  getX() returns 32",                  sprite.getX() == 32);
        check("8.3  getY() returns 64",                  sprite.getY() == 64);
        check("8.4  getWidth() returns 16",              sprite.getWidth() == 16);
        check("8.5  getHeight() returns 16",             sprite.getHeight() == 16);

        SpriteRegion offset = sprite.offset(16, 0);
        check("8.6  offset() creates new region",        offset != sprite);
        check("8.7  offset x = 48",                      offset.getX() == 48);
        check("8.8  offset y unchanged = 64",            offset.getY() == 64);
        check("8.9  offset same atlas",                  "atlas.png".equals(offset.getAtlasPath()));

        SpriteRegion grid = SpriteRegion.fromGrid("sheet.png", 2, 3, 32, 32);
        check("8.10 fromGrid x = 64 (col 2 * 32)",       grid.getX() == 64);
        check("8.11 fromGrid y = 96 (row 3 * 32)",       grid.getY() == 96);
        check("8.12 fromGrid width = 32",                grid.getWidth() == 32);

        check("8.13 toString() contains atlas",          sprite.toString().contains("atlas.png"));
    }

    // =========================================================================
    // GROUP 9 — Complex Nested Structure (Real-World Scenario)
    // =========================================================================

    private static void testComplexNestedStructure() {
        header("GROUP 9 — Complex Nested Structure");

        // Build a realistic menu structure:
        // mainMenu (screen)
        //   ├── header (container)
        //   │     └── titleLabel
        //   ├── buttonPanel (container)
        //   │     ├── playButton
        //   │     ├── optionsButton
        //   │     └── exitButton
        //   └── footer (container)
        //         └── versionLabel

        ScreenManager manager = new ScreenManager();

        GuiScreen mainMenu = new GuiScreen("mainMenu", "Main Menu", 800, 600);
        GuiScreen optionsMenu = new GuiScreen("options", "Options", 800, 600);

        manager.registerScreen(mainMenu);
        manager.registerScreen(optionsMenu);
        manager.changeScreen("mainMenu", false);

        // Header
        GuiContainer header = new GuiContainer("header", 0, 0, 800, 80);
        GuiLabel titleLabel = new GuiLabel("title", "My Game");
        header.addChild(titleLabel);

        // Button panel
        GuiContainer buttonPanel = new GuiContainer("buttonPanel", 300, 200, 200, 200);

        GuiButton playButton = new GuiButton("playBtn", "Play", 0, 0, 180, 40);
        GuiButton optionsButton = new GuiButton("optionsBtn", "Options", 0, 50, 180, 40);
        GuiButton exitButton = new GuiButton("exitBtn", "Exit", 0, 100, 180, 40);

        // Set up command for options button
        ChangeScreenCommand goToOptions = new ChangeScreenCommand(manager, "options");
        optionsButton.setCommand(goToOptions);

        buttonPanel.addChild(playButton);
        buttonPanel.addChild(optionsButton);
        buttonPanel.addChild(exitButton);

        // Footer
        GuiContainer footer = new GuiContainer("footer", 0, 550, 800, 50);
        GuiLabel versionLabel = new GuiLabel("version", "v1.0.0");
        footer.addChild(versionLabel);

        // Assemble screen
        mainMenu.addChild(header);
        mainMenu.addChild(buttonPanel);
        mainMenu.addChild(footer);

        check("9.1  mainMenu has 3 direct children",     mainMenu.getChildCount() == 3);

        // Count all descendants
        int totalElements = 0;
        for (GuiElement e : mainMenu.allDescendants()) {
            totalElements++;
        }
        check("9.2  total elements = 9",                 totalElements == 9);

        // Click options button - should change screen
        check("9.3  currently on mainMenu",              manager.getCurrentScreen() == mainMenu);
        optionsButton.click();
        check("9.4  click navigates to options",         manager.getCurrentScreen() == optionsMenu);

        // Go back
        manager.goBack();
        check("9.5  goBack returns to mainMenu",         manager.getCurrentScreen() == mainMenu);

        // Find nested element
        GuiElement foundVersion = mainMenu.findDescendant("version");
        check("9.6  findDescendant finds nested label",  foundVersion == versionLabel);

        // Update all elements
        mainMenu.update();
        check("9.7  update() propagates (no errors)",    true);

        // Demonstrate pattern combination:
        // COMPOSITE: mainMenu contains containers which contain elements
        // COMMAND: optionsButton executes ChangeScreenCommand
        // ITERATOR: we traversed all descendants with deepIterator

        System.out.println("\n  Pattern Summary:");
        System.out.println("    COMPOSITE: GuiScreen > GuiContainer > GuiButton/GuiLabel");
        System.out.println("    COMMAND:   GuiButton.click() -> ChangeScreenCommand.execute()");
        System.out.println("    ITERATOR:  deepIterator() traverses entire GUI tree");
    }

    // =========================================================================
    // HELPERS
    // =========================================================================

    private static void check(String label, boolean condition) {
        if (condition) {
            System.out.printf("  [PASS]  %s%n", label);
            passed++;
        } else {
            System.out.printf("  [FAIL]  %s%n", label);
            failed++;
        }
    }

    private static void banner(String text) {
        String line = "=".repeat(62);
        System.out.printf("%n%s%n  %s%n%s%n", line, text, line);
    }

    private static void header(String text) {
        System.out.printf("%n  -- %s --%n", text);
    }
}
