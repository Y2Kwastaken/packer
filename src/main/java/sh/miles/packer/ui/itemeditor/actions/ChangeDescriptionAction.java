package sh.miles.packer.ui.itemeditor.actions;

import lombok.NonNull;
import sh.miles.packer.pack.PackItem;
import sh.miles.ui.console.Action;
import sh.miles.ui.console.ConsoleInput;
import sh.miles.ui.console.ConsoleMenu;

public class ChangeDescriptionAction implements Action {

    private final PackItem item;

    public ChangeDescriptionAction(@NonNull final PackItem item) {
        this.item = item;
    }

    @Override
    public boolean execute(@NonNull String input, @NonNull ConsoleInput console, @NonNull ConsoleMenu actingMenu) {

        System.out.println("Change Description");
        System.out.println("Current Description: " + item.getDescription());
        System.out.println("Enter \"-\" to empty the description");
        final String description = console.getString("New Description: ");
        if (!description.isEmpty()) {
            item.setDescription(description);
        }else if(description.equals("-")){
            item.setDescription("");
        }

        return true;
    }

    @Override
    public String getDescription() {
        return "Changes the description of the item";
    }

}
