package sh.miles.packer.ui.packeditor.actions;

import lombok.NonNull;
import sh.miles.packer.pack.Pack;
import sh.miles.packer.pack.PackItem;
import sh.miles.packer.ui.itemeditor.ItemEditor;
import sh.miles.packer.units.Weight;
import sh.miles.packer.units.WeightUnit;
import sh.miles.ui.console.Action;
import sh.miles.ui.console.ConsoleInput;
import sh.miles.ui.console.ConsoleMenu;

public class AddPackItem implements Action {

    private final Pack pack;

    public AddPackItem(@NonNull final Pack pack) {
        this.pack = pack;
    }

    @NonNull
    @Override
    public boolean execute(final String input, final ConsoleInput console, final ConsoleMenu actingMenu) {

        System.out.println("Add Pack Item");
        String[] categories = pack.getContainer().getCategories().toArray(String[]::new);
        if (categories.length == 0) {
            categories = new String[] { "Default" };
        }

        final int categoryIndex = console.getNumberOption("Select the category of the item", categories);
        if (categoryIndex < 0) {
            return false;
        }
        final String category = categories[categoryIndex];
        final PackItem item = new PackItem("new item", "", "", new Weight(WeightUnit.OUNCES, 0.0), 0, 0.0);
        pack.getContainer().putItem(category, item);

        System.out.println("Opening the item editor...");
        final ConsoleMenu itemEditor = new ItemEditor(console.getScanner(), item, actingMenu);
        itemEditor.onOpen();

        return true;
    }

    @Override
    public String getDescription() {
        return "Add a new item to the pack";
    }

}
