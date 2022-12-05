package sh.miles.packer.ui.main.actions;

import lombok.NonNull;
import sh.miles.packer.data.PackerStorage;
import sh.miles.packer.pack.Pack;
import sh.miles.packer.ui.packeditor.PackEditor;
import sh.miles.ui.console.Action;
import sh.miles.ui.console.ConsoleInput;
import sh.miles.ui.console.ConsoleMenu;

public class CreatePackAction implements Action {

    private final PackerStorage storage;

    public CreatePackAction(PackerStorage storage) {
        this.storage = storage;
    }

    @Override
    public boolean execute(@NonNull String input, @NonNull ConsoleInput console, @NonNull ConsoleMenu actingMenu) {
        System.out.println("Pack Creator");
        System.out.println("============");
        final String name = console.getString("Please enter the name of the list: ");
        final Pack pack = new Pack(name);
        storage.insertPack(pack);
        final String message = String.format("List %s created do you want to edit it? [y/n] ", name);
        final String answer = console.getString(message);
        while (!answer.equals("y") && !answer.equals("n")) {
            if (answer.equals("y")) {
                console.mimicClear();
                final ConsoleMenu editor = new PackEditor(console.getScanner(), storage, pack, actingMenu);
                editor.onOpen();
            }
        }

        return true;
    }

    @Override
    public String getDescription() {
        return "Handles the creation of a new list";
    }

}
