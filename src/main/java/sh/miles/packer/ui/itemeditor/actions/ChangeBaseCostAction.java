package sh.miles.packer.ui.itemeditor.actions;

import lombok.NonNull;
import sh.miles.packer.pack.PackItem;
import sh.miles.ui.console.Action;
import sh.miles.ui.console.ConsoleInput;
import sh.miles.ui.console.ConsoleMenu;

public class ChangeBaseCostAction implements Action {

    private final PackItem item;

    public ChangeBaseCostAction(@NonNull final PackItem item) {
        this.item = item;
    }

    @Override
    public boolean execute(@NonNull String input, @NonNull ConsoleInput console, @NonNull ConsoleMenu actingMenu) {

        System.out.println("Change Base Cost");
        System.out.println("Base Cost is the cost of the item per unit");
        System.out.println("Current Base Cost: " + item.getCost() / item.getQuantity() + " per unit");
        System.out.println("Enter negative number to exit");
        final double baseCost = console.getDouble("New Base Cost: ");
        if (baseCost > 0) {
            item.setCost(baseCost * item.getQuantity());
        }
        return true;
    }

    @Override
    public String getDescription() {
        return "Change base cost of the item";
    }

}
