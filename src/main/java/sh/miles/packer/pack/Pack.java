package sh.miles.packer.pack;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import sh.miles.packer.Utils;
import sh.miles.packer.units.Weight;
import sh.miles.packer.units.WeightUnit;

public class Pack implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    private final String name;
    @Getter
    private final PackContainer container;

    public Pack(final String name) {
        this.name = name;
        this.container = new PackContainer();
    }

    public Pack(final String name, final PackContainer container) {
        this.name = name;
        this.container = container;
    }

    public Weight getTotalWeight() {
        final List<PackItem> items = container.getItemsUnsorted();
        return items.stream().map(PackItem::getWeight).reduce(Weight::add).orElse(new Weight(WeightUnit.POUNDS, 0));
    }

    public double getTotalCost() {
        final List<PackItem> items = container.getItemsUnsorted();
        return items.stream().mapToDouble(PackItem::getCost).sum();
    }

    public byte[] toBytes() {
        try {
            return Utils.serialize(this);
        } catch (IOException e) {
            throw new IllegalStateException("Could not serialize pack", e);
        }
    }

    public String toBase64() {
        return Utils.toBase64(toBytes());
    }

    public static Pack fromBytes(final byte[] bytes) {
        try {
            return Utils.deserialize(bytes, Pack.class);
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("Could not deserialize pack", e);
        }
    }

    public static Pack fromBase64(final String base64) {
        return fromBytes(Utils.fromBase64(base64));
    }

}
