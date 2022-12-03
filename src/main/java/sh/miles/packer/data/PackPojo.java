package sh.miles.packer.data;

import lombok.AllArgsConstructor;
import sh.miles.prelude.pojo.IPojo;
import sh.miles.prelude.pojo.PojoValue;

@AllArgsConstructor
public class PackPojo implements IPojo {

    @PojoValue(column = "key")
    private String name;
    @PojoValue(column = "pack")
    private String base64Pack;

    public PackPojo() {
        // reserved for CSVPojo
    }

    public String getName() {
        return name;
    }

    public String getBase64() {
        return base64Pack;
    }

}
