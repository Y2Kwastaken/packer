package sh.miles.packer.ui.itemeditor.actions;

import lombok.NonNull;
import sh.miles.packer.pack.PackItem;
import sh.miles.ui.console.Action;
import sh.miles.ui.console.ConsoleInput;
import sh.miles.ui.console.ConsoleMenu;

public class ChangeNameAction implements Action {

    private final PackItem item;

    public ChangeNameAction(@NonNull final PackItem item) {
        this.item = item;
    }

    @Override
    public boolean execute(@NonNull String input, @NonNull ConsoleInput console, @NonNull ConsoleMenu actingMenu) {
        System.out.println("Change Name");
        System.out.println("Current Name: " + item.getName());
        System.out.println("Enter \"-\" to empty the description");
        final String name = console.getString("New Name: ");
        if (!name.isEmpty()) {
            item.setName(name);
        } else if (name.equals("-")) {
            item.setName(" ");
        }
        return true;
    }

    @Override
    public String getDescription() {
        return "Changes the name of the item";
    }

}
