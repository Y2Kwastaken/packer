package sh.miles.packer.ui.itemeditor.actions;

import lombok.NonNull;
import sh.miles.packer.pack.PackItem;
import sh.miles.ui.console.Action;
import sh.miles.ui.console.ConsoleInput;
import sh.miles.ui.console.ConsoleMenu;

public class ChangeLinkAction implements Action {

    private final PackItem item;

    public ChangeLinkAction(@NonNull final PackItem item) {
        this.item = item;
    }

    @Override
    public boolean execute(@NonNull String input, @NonNull ConsoleInput console, @NonNull ConsoleMenu actingMenu) {

        System.out.println("Change Link");
        System.out.println("Current Link: " + item.getLink());
        System.out.println("Enter \"-\" to empty the link");
        final String link = console.getString("New Link: ");
        if (!link.isEmpty()) {
            item.setLink(link);
        }else if(link.equals("-")){
            item.setLink("");
        }
        return true;
    }

    @Override
    public String getDescription() {
        return "Changes the link of the item";
    }

}
