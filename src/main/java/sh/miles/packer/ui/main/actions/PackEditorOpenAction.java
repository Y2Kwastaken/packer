package sh.miles.packer.ui.main.actions;

import java.util.List;
import java.util.stream.Collectors;

import lombok.NonNull;
import sh.miles.packer.data.PackerStorage;
import sh.miles.packer.pack.Pack;
import sh.miles.packer.ui.packeditor.PackEditor;
import sh.miles.ui.console.Action;
import sh.miles.ui.console.ConsoleInput;
import sh.miles.ui.console.ConsoleMenu;

public class PackEditorOpenAction implements Action {

    private final PackerStorage storage;

    public PackEditorOpenAction(PackerStorage storage) {
        this.storage = storage;
    }

    @Override
    public boolean execute(@NonNull String input, @NonNull ConsoleInput console, @NonNull ConsoleMenu actingMenu) {
        List<String> packs = storage.getAllPacks().stream().map(Pack::getName).collect(Collectors.toList());
        if (packs.isEmpty()) {
            System.out.println("No packs to edit");
            return false;
        }
        final String packName = console.getOption("Please select a pack to edit ", packs.toArray(String[]::new));
        final List<Pack> nameResults = storage.getPacksWithName(packName);
        final Pack packToEdit;
        if (nameResults.size() == 1) {
            packToEdit = nameResults.get(0);
        } else {
            final int index = console.getNumberOption("Please select a number of the pack to edit ",
                    nameResults.stream().map(Pack::getName).toArray(String[]::new));
            packToEdit = nameResults.get(index);
        }
        console.mimicClear();
        final ConsoleMenu editor = new PackEditor(console.getScanner(), storage, packToEdit, actingMenu);
        editor.onOpen();
        return true;
    }

    @Override
    public String getDescription() {
        return "Opens the pack editor";
    }

}
