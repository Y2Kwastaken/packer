package sh.miles.packer.ui.itemeditor;

import java.util.Scanner;

import lombok.NonNull;
import sh.miles.packer.pack.PackItem;
import sh.miles.packer.ui.itemeditor.actions.ChangeBaseCostAction;
import sh.miles.packer.ui.itemeditor.actions.ChangeDescriptionAction;
import sh.miles.packer.ui.itemeditor.actions.ChangeLinkAction;
import sh.miles.packer.ui.itemeditor.actions.ChangeNameAction;
import sh.miles.packer.ui.itemeditor.actions.ChangeQuantityAction;
import sh.miles.packer.ui.itemeditor.actions.ChangeWeightAction;
import sh.miles.ui.Menu;
import sh.miles.ui.console.Action;
import sh.miles.ui.console.ConsoleMenu;

public class ItemEditor extends ConsoleMenu {

    private final ConsoleMenu parentMenu;

    public ItemEditor(@NonNull final Scanner scanner, @NonNull final PackItem item, @NonNull final ConsoleMenu parent) {
        super("Item Editor", "Helps Edit an Item in the pack", scanner);
        this.parentMenu = parent;

        addAction("Change Name", new ChangeNameAction(item));
        addAction("Change Description", new ChangeDescriptionAction(item));
        addAction("Change Base Cost", new ChangeBaseCostAction(item));
        addAction("Change Weight", new ChangeWeightAction(item));
        addAction("Change Quantity", new ChangeQuantityAction(item));
        addAction("Change Link", new ChangeLinkAction(item));

        addAction("Close", Action.closeMenu(null));
    }

    @Override
    public Menu getParent() {
        return this.parentMenu;
    }
}
