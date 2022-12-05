package sh.miles.packer.ui.packeditor.actions;

import java.util.List;
import java.util.stream.Stream;

import lombok.NonNull;
import sh.miles.packer.pack.Pack;
import sh.miles.packer.units.WeightUnit;
import sh.miles.ui.console.Action;
import sh.miles.ui.console.ConsoleInput;
import sh.miles.ui.console.ConsoleMenu;

public class ChangeWeightUnit implements Action {

    private final Pack pack;

    public ChangeWeightUnit(@NonNull final Pack pack) {
        this.pack = pack;
    }

    @Override
    public boolean execute(@NonNull String input, @NonNull ConsoleInput console, @NonNull ConsoleMenu actingMenu) {

        final List<String> units = Stream.of(WeightUnit.values()).map(WeightUnit::getSymbol).toList();
        final String unit = console.getOption("Select a weight unit: ", units.toArray(String[]::new));
        final WeightUnit newUnit = WeightUnit.getUnitFromString(unit);
        
        pack.getContainer().getItems().values().forEach(list -> list.changeWeightUnit(newUnit));
        
        return true;
    }

    @Override
    public String getDescription() {
        return "Change weight unit";
    }

}
