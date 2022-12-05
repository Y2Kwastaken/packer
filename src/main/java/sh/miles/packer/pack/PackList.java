package sh.miles.packer.pack;

import java.io.Serializable;
import java.util.List;

import lombok.NonNull;
import sh.miles.packer.units.Weight;
import sh.miles.packer.units.WeightUnit;

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

    public int size() {
        return items.size();
    }

    public Weight getTotalWeight() {
        return items.stream().map(PackItem::getWeight).reduce(Weight::add).orElse(new Weight(WeightUnit.GRAMS, 0));
    }

    public double getTotalCost() {
        return items.stream().mapToDouble(PackItem::getCost).sum();
    }

    public void changeWeightUnit(@NonNull final WeightUnit unit){
        items.forEach(item -> item.setWeight(item.getWeight().convertTo(unit)));
    }

}
