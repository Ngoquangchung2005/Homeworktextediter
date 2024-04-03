package Notpad;

import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileController {
    GUIView gui;
    String fileName;
    String fileAddress;

    public FileController(GUIView gui) {
        this.gui = gui;
    }

    public void newFile() {
        gui.textArea.setText("");
        gui.window.setTitle("New");
        fileName = null;
        fileAddress = null;
    }

    public void open() {
        FileDialog fd = new FileDialog(gui.window, "Open", FileDialog.LOAD);
        fd.setVisible(true);
        if (fd.getFile() != null) {
            fileName = fd.getFile();
            fileAddress = fd.getDirectory();
            gui.window.setTitle(fileName);
        }
        System.out.println("File address and file name " + fileAddress + fileName);
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileAddress + fileName));
            gui.textArea.setText("");
            String line = null;
            while ((line = br.readLine()) != null) {
                gui.textArea.append(line + "\n");
            }
            br.close();
        } catch (Exception e) {
            System.out.println("FILE NOT OPENED!");
        }
    }

    public void save() {
        if (fileName == null) {
            saveAs();
        } else {
            try {
                FileWriter fw = new FileWriter(fileAddress + fileName);
                fw.write(gui.textArea.getText());
                gui.window.setTitle(fileName);
                fw.close();
            } catch (IOException e) {
                System.out.println("SOMETHING WENT WRONG!");
            }
        }
    }

    public void saveAs() {
        FileDialog fd = new FileDialog(gui.window, "Save", FileDialog.SAVE);
        fd.setVisible(true);
        if (fd.getFile() != null) {
            fileName = fd.getFile();
            fileAddress = fd.getDirectory();
            gui.window.setTitle(fileName);
        }
        try {
            FileWriter fw = new FileWriter(fileAddress + fileName);
            fw.write(gui.textArea.getText());
            fw.close();
        } catch (IOException e) {
            System.out.println("SOMETHING WENT WRONG! ");
        }
    }

    public void exit() {
        System.exit(0);
    }

    // Duyệt thư mục đệ quy và trả về danh sách tất cả các tệp
    public List<String> getAllFilesInDirectory(String directory) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(directory), FileVisitOption.FOLLOW_LINKS)) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        }
    }

    // Sử dụng lambda expression cho xử lý sự kiện
    public void setupEventHandlers() {
        // Ví dụ: Xử lý sự kiện khi nhấn nút "New"
        gui.iNew.addActionListener((event) -> {
            newFile();
        });

        // Xử lý sự kiện khi nhấn nút "Open"
        gui.iOpen.addActionListener((event) -> {
            open();
        });

        // Xử lý sự kiện khi nhấn nút "Save"
        gui.iSave.addActionListener((event) -> {
            save();
        });

        // Xử lý sự kiện khi nhấn nút "Save As"
        gui.iSaveAs.addActionListener((event) -> {
            saveAs();
        });

        // Xử lý sự kiện khi nhấn nút "Exit"
        gui.iExit.addActionListener((event) -> {
            exit();
        });
    }
}
