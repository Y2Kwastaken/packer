package sh.miles.packer.ui.itemeditor.actions;

import lombok.NonNull;
import sh.miles.packer.pack.PackItem;
import sh.miles.ui.console.Action;
import sh.miles.ui.console.ConsoleInput;
import sh.miles.ui.console.ConsoleMenu;

public class ChangeQuantityAction implements Action {

    private final PackItem item;

    public ChangeQuantityAction(@NonNull final PackItem item) {
        this.item = item;
    }

    @Override
    public boolean execute(@NonNull String input, @NonNull ConsoleInput console, @NonNull ConsoleMenu actingMenu) {

        System.out.println("Change Quantity");
        System.out.println("Current Quantity: " + item.getQuantity());
        System.out.println("Enter negative number to exit");
        final int quantity = console.getInt("New Quantity: ");
        if (quantity > 0) {
            item.setQuantity(quantity);
        }

        return true;
    }

    @Override
    public String getDescription() {
        return "Change quantity of the item";
    }

}
