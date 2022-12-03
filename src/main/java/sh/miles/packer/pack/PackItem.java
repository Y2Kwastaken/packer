package sh.miles.packer.pack;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sh.miles.packer.units.Weight;

/**
 * Represents an item that can be packed into a pack
 */
@AllArgsConstructor
@Getter
@Setter
public class PackItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private String link;
    private Weight weight;
    private int quantity;
    private double cost;

    @Override
    public String toString() {
        return String.format("%s{name=%s|description=%s|link=%s|weight=%s|quantity=%d|cost=%f}",
                getClass().getSimpleName(), name, description, link, weight, quantity, cost);
    }

}
