package sh.miles.packer.ui.packeditor.actions;

import java.util.Map;
import java.util.Map.Entry;

import lombok.NonNull;
import sh.miles.packer.pack.Pack;
import sh.miles.packer.pack.PackItem;
import sh.miles.packer.pack.PackList;
import sh.miles.ui.console.Action;
import sh.miles.ui.console.ConsoleInput;
import sh.miles.ui.console.ConsoleMenu;

public class DisplayPackAction implements Action {

    private final Pack pack;

    public DisplayPackAction(@NonNull final Pack pack) {
        this.pack = pack;
    }

    @Override
    public boolean execute(@NonNull String input, @NonNull ConsoleInput console, @NonNull ConsoleMenu actingMenu) {
        console.mimicClear();
        System.out.println("Pack Display Editor");
        System.out.println("===========");
        System.out.println("Name: " + pack.getName());
        System.out.println("Number of Items: " + pack.getContainer().getItemsUnsorted().size());
        System.out.println("Number of Categories: " + pack.getContainer().getCategories().size());
        System.out.println("Total Weight: " + pack.getTotalWeight().getValue() + " "
                + pack.getTotalWeight().getUnit().getSymbol());
        System.out.println("Total Cost: " + pack.getTotalCost());
        System.out.println("===========");

        boolean fullList = console.getBoolean("Display full list of items? (true/false) ");
        if (fullList) {
            final Map<String, PackList> items = pack.getContainer().getItems();
            for (Entry<String, PackList> entry : items.entrySet()) {
                final String category = entry.getKey();
                final PackList list = entry.getValue();
                System.out.println("Category: " + category);
                System.out.println("Number of Items: " + list.size());
                System.out.println("Total Weight: " + list.getTotalWeight().getValue() + " "
                        + list.getTotalWeight().getUnit().getSymbol());
                System.out.println("Total Cost: " + list.getTotalCost());
                System.out.println("===========");
                System.out.println("Items:");
                for (int i = 0; i < list.size(); i++) {
                    final PackItem item = list.getItem(i);
                    System.out.println("Name: " + item.getName());
                    System.out.println("Description: " + item.getDescription());
                    System.out.println("Weight: " + item.getWeight().getValue() + " "
                            + item.getWeight().getUnit().getSymbol());
                    System.out.println("Cost: " + item.getCost());
                    System.out.println("Link: " + item.getLink());
                    System.out.println("Quantity: " + item.getQuantity());
                    System.out.println("===========");
                    System.out.println();
                }
            }
        }
        console.waitForInput("Press enter to continue...");
        console.mimicClear();
        return true;
    }

    @Override
    public String getDescription() {
        return "Displays information about the pack as well as a list of items";
    }

}
