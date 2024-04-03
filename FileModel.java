package Notpad;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

public class FileModel {
    private String fileName;
    private String fileAddress;

    public String getFileName() {
        return fileName;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public String openFile(String fileAddress, String fileName) throws IOException {
        this.fileAddress = fileAddress;
        this.fileName = fileName;
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileAddress + fileName))) {
            return br.lines().collect(Collectors.joining("\n"));
        }
    }

    public void saveFile(String fileAddress, String fileName, String content) throws IOException {
        this.fileAddress = fileAddress;
        this.fileName = fileName;

        try (FileWriter fw = new FileWriter(fileAddress + fileName)) {
            fw.write(content);
        }
    }
}
