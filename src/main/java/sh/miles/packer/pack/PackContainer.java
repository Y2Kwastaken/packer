package sh.miles.packer.pack;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.NonNull;

/**
 * Represents a container for pack items. It is used to store items into
 * different categories. If no category is specified, the item is stored in the
 * default category. the default category name is visible as a constant
 * {@value #DEFAULT_CATEGORY}
 */
public class PackContainer implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String DEFAULT_CATEGORY = "default";

    private final Map<String, PackList> items;

    public PackContainer() {
        this.items = new HashMap<>();
    }

    /**
     * Adds an item to the pack
     * 
     * @param category the category of the item
     * @param item     the item to add
     */
    public void putItem(@NonNull final String category, @NonNull final PackItem item) {
        if (!items.containsKey(category)) {
            items.put(category, new PackList());
        }
        items.get(category).addItem(item);
    }

    /**
     * Adds an item to the default category
     * 
     * @param item the item to add
     */
    public void putItem(@NonNull final PackItem item) {
        putItem(DEFAULT_CATEGORY, item);
    }

    /**
     * Removes an item from the pack
     * 
     * @param category the category of the item
     * @param index    the index of the item
     * @return the removed item or null if the item does not exist
     */
    public PackItem removeItem(@NonNull final String category, final int index) {
        if (!items.containsKey(category)) {
            return null;
        }

        return items.get(category).removeItem(index);
    }

    /**
     * Removes an item from the default category
     * 
     * @param index the index of the item
     * @return the removed item or null if the item does not exist
     */
    public PackItem removeItem(final int index) {
        return removeItem(DEFAULT_CATEGORY, index);
    }

    /**
     * Checks if the container contains the category
     * 
     * @param category the category to check
     * @return true if the category exists, false otherwise
     */
    public boolean hasCategory(@NonNull final String category) {
        return items.containsKey(category);
    }

    /**
     * Get the items in the given category
     * 
     * @param category the category to get the items from
     * @return the items in the given category or an empty list
     */
    public PackList getCategory(@NonNull final String category) {
        if (!items.containsKey(category)) {
            return new PackList();
        }

        return items.get(category);
    }

    /**
     * Get the items in the default category
     * 
     * @return the items in the default category or an empty list
     */
    public PackList getCategory() {
        return getCategory(DEFAULT_CATEGORY);
    }

    /**
     * Get the names of all categories
     * 
     * @return the names of all categories
     */
    public List<String> getCategories() {
        return items.keySet().stream().collect(Collectors.toList());
    }

    /**
     * Gets the all of the items in the container in a map string category name to
     * PackList
     * 
     * @return all items
     */
    public Map<String, PackList> getItems() {
        return new HashMap<>(this.items);
    }

    /**
     * Returns a list of all items in this container not sorted by category
     * 
     * @return a list of all items in this container
     */
    public List<PackItem> getItemsUnsorted() {
        return items.values().stream().flatMap(list -> list.getItems().stream()).collect(Collectors.toList());
    }

}
