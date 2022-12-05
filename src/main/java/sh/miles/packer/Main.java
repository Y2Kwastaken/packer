package sh.miles.packer;

import java.util.Scanner;

import sh.miles.packer.data.PackerStorage;
import sh.miles.packer.ui.main.MainMenu;

public class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final PackerStorage storage = new PackerStorage();
        storage.useDatabase();
        storage.create("storage");
        MainMenu mainMenu = new MainMenu(scanner, storage);
        mainMenu.onOpen();
    }
}
