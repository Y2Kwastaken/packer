package sh.miles.packer;

import sh.miles.packer.data.PackerStorage;
import sh.miles.packer.pack.Pack;

public class Main {
    public static void main(String[] args) {

        PackerStorage storage = new PackerStorage();
        storage.create("test");
        
        storage.insertPack(new Pack("Test"));

        storage.close();
    }
}
