package sh.miles.ui.console;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import sh.miles.ui.Menu;

public class ConsoleMenu implements Menu {

    private final String name;
    private final String description;
    private final ConsoleInput console;
    private final Map<String, Action> actions;

    public ConsoleMenu(String name, String description, Scanner scanner) {
        this.name = name;
        this.description = description;
        this.console = new ConsoleInput(scanner);
        this.actions = new LinkedHashMap<>();
    }

    @Override
    public void onOpen() {
        while (true) {
            System.out.println(name);
            System.out.println(description);
            System.out.println();
            int numeration = 1;
            for (Entry<String, Action> entry : actions.entrySet()) {
                System.out.println(
                        String.format("[%d] %s - %s", numeration, entry.getKey(), entry.getValue().getDescription()));
                numeration++;
            }
            final int input = console.getInt("> ");
            if (input <= 0 || input > actions.size()) {
                System.out.println("Invalid input please try selecting a valid option");
                continue;
            }

            final String key = (String) actions.keySet().toArray()[input - 1];
            final Action action = actions.get(key);
            if (action.execute(key, console, this)) {
                continue;
            } else {
                System.out.println("An Error occured when executing this action");
            }

            for (int i = 0; i < 100; i++) {
                System.out.println();
            }
        }
    }

    public void addAction(String name, Action action) {
        actions.put(name, action);
    }

    public void removeAction(String name) {
        actions.remove(name);
    }

    @Override
    public Menu getParent() {
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

}
