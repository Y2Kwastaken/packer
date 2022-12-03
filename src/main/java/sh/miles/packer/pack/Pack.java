package sh.miles.packer.pack;

import java.io.IOException;
import java.io.Serializable;

import lombok.Getter;
import sh.miles.packer.Utils;

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
