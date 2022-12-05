package sh.miles.packer.ui.packeditor;

import java.util.Scanner;

import lombok.NonNull;
import sh.miles.packer.data.PackerStorage;
import sh.miles.packer.pack.Pack;
import sh.miles.packer.ui.packeditor.actions.AddPackItem;
import sh.miles.packer.ui.packeditor.actions.ChangeWeightUnit;
import sh.miles.packer.ui.packeditor.actions.DisplayPackAction;
import sh.miles.packer.ui.packeditor.actions.EditPackItem;
import sh.miles.ui.Menu;
import sh.miles.ui.console.Action;
import sh.miles.ui.console.ConsoleMenu;

public class PackEditor extends ConsoleMenu {

    private final ConsoleMenu parent;
    private final PackerStorage storage;
    private final Pack pack;

    public PackEditor(@NonNull final Scanner scanner, @NonNull final PackerStorage storage, @NonNull final Pack pack,
            @NonNull final ConsoleMenu parent) {
        super("Pack Editor", "Pack Editor manages editing your packs", scanner);
        this.parent = parent;
        this.storage = storage;
        this.pack = pack;

        addAction("Display Pack", new DisplayPackAction(pack));
        addAction("Add Pack Item", new AddPackItem(pack));
        addAction("Edit Pack Item", new EditPackItem(pack));
        addAction("Change Weight Unit", new ChangeWeightUnit(pack));

        addAction("Close", Action.closeMenu(null));
    }

    @Override
    public void onClose() {
        System.out.println("Closing pack editor");
        System.out.println("Updating pack in database");
        this.storage.updatePack(this.pack);
    }

    @Override
    public Menu getParent() {
        return this.parent;
    }

}
