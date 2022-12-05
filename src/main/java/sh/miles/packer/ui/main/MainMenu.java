package sh.miles.packer.ui.main;

import java.util.Scanner;

import lombok.NonNull;
import sh.miles.packer.data.PackerStorage;
import sh.miles.packer.ui.main.actions.CreatePackAction;
import sh.miles.packer.ui.main.actions.PackEditorOpenAction;
import sh.miles.ui.console.Action;
import sh.miles.ui.console.ConsoleMenu;

public class MainMenu extends ConsoleMenu {

    private final PackerStorage storage;

    public MainMenu(@NonNull final Scanner scanner, @NonNull final PackerStorage storage) {
        super("Packer", "A simple planner for your backpacking", scanner);
        this.storage = storage;

        addAction("Pack Editor", new PackEditorOpenAction(storage));
        addAction("Pack Creator", new CreatePackAction(storage));
        addAction("Close", Action.closeMenu(null));
    }

    @Override
    public void onClose() {
        System.out.println("Thank you for using packer");
        this.storage.close();
        System.exit(1);
    }

}
