package sh.miles.packer.pack;

import java.io.Serializable;
import java.util.List;

public class PackList implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<PackItem> items;

    PackList() {
        this.items = new java.util.ArrayList<>();
    }

    public void addItem(PackItem item) {
        items.add(item);
    }

    public PackItem removeItem(int index) {
        return items.remove(index);
    }

    public PackItem getItem(int index) {
        return items.get(index);
    }

    public List<PackItem> getItems() {
        return new java.util.ArrayList<>(items);
    }

}
