package sh.miles.packer.ui.packeditor.actions;

import java.util.List;

import lombok.NonNull;
import sh.miles.packer.pack.Pack;
import sh.miles.packer.pack.PackItem;
import sh.miles.packer.ui.itemeditor.ItemEditor;
import sh.miles.ui.console.Action;
import sh.miles.ui.console.ConsoleInput;
import sh.miles.ui.console.ConsoleMenu;

public class EditPackItem implements Action {

    private final Pack pack;

    public EditPackItem(@NonNull final Pack pack) {
        this.pack = pack;
    }

    @Override
    public boolean execute(@NonNull String input, @NonNull ConsoleInput console, @NonNull ConsoleMenu actingMenu) {
        console.mimicClear();
        final List<PackItem> items = pack.getContainer().getItemsUnsorted();
        if (items.isEmpty()) {
            System.out.println("No items to edit");
            return true;
        }
        final int index = console.getNumberOption("Enter the number of the item you want to edit",
                items.stream().map(PackItem::getName).toArray(String[]::new));
        if (index >= 0) {
            final PackItem item = items.get(index);
            final ConsoleMenu itemEditor = new ItemEditor(console.getScanner(), item, actingMenu);
            itemEditor.onOpen();
        } else {
            return false;
        }
        return true;
    }

    @Override
    public String getDescription() {
        return "Edit an item in the pack";
    }

}
