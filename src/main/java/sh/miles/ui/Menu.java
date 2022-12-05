package sh.miles.ui;

/**
 * An abstracted object representing a possible menu object
 */
public interface Menu {

    /**
     * Triggered when the user opens the menu
     */
    default void onOpen() {
    }

    /**
     * Triggered when the user closes the menu this could be by cleaning up data or
     * another operation
     */
    default void onClose() {
    }

    /**
     * Possible null value that represents a parent menu
     * 
     * @return the parent menu
     */
    Menu getParent();

    /**
     * The name of the menu that will be displayed to the user on the menu bar
     * 
     * @return
     */
    String getName();

    /**
     * A description of the menu that will be displayed to the user on the menu bar
     * and in the menu
     * 
     * @return
     */
    String getDescription();
}
