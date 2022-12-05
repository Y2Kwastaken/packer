package sh.miles.packer.ui.itemeditor.actions;

import lombok.NonNull;
import sh.miles.packer.pack.PackItem;
import sh.miles.packer.units.Weight;
import sh.miles.ui.console.Action;
import sh.miles.ui.console.ConsoleInput;
import sh.miles.ui.console.ConsoleMenu;

public class ChangeWeightAction implements Action {

    private final PackItem item;

    public ChangeWeightAction(@NonNull final PackItem item) {
        this.item = item;
    }

    @Override
    public boolean execute(@NonNull String input, @NonNull ConsoleInput console, @NonNull ConsoleMenu actingMenu) {
        System.out.println("Change Weight");
        System.out.println("Weight is the weight of the item per unit");
        System.out.println("Current Weight: " + item.getWeight().getValue() / item.getQuantity()
                + item.getWeight().getUnit().getSymbol() + " per unit");
        System.out.println("Enter a negative number to exit");
        final double weight = console.getDouble("New Weight: ");
        if (weight > 0) {
            item.setWeight(new Weight(item.getWeight().getUnit(), weight * item.getQuantity()));
        }
        return true;
    }

    @Override
    public String getDescription() {
        return "Change weight of the item";
    }

}
