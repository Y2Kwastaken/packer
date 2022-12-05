package sh.miles.ui.console;

import lombok.NonNull;

public interface Action {

    boolean execute(@NonNull final String input, @NonNull final ConsoleInput console,
            @NonNull final ConsoleMenu actingMenu);

    String getDescription();

    public static Action openMenu(final ConsoleMenu openingMenu, final String description) {
        return new Action() {
            @Override
            public boolean execute(String input, ConsoleInput console, ConsoleMenu actingMenu) {
                openingMenu.onOpen();
                return true;
            }

            @Override
            public String getDescription() {
                return description;
            }
        };
    }

    public static Action closeMenu(final String description) {
        return new Action() {
            @Override
            public boolean execute(String input, ConsoleInput console, ConsoleMenu actingMenu) {
                actingMenu.onClose();
                console.mimicClear();
                if (actingMenu.getParent() != null) {
                    actingMenu.getParent().onOpen();
                }
                return true;
            }

            @Override
            public String getDescription() {
                final String showingDesc;
                if (description == null) {
                    showingDesc = "Closes this menu";
                } else {
                    showingDesc = description;
                }
                return showingDesc;
            }
        };
    }
}
