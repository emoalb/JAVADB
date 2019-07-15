package com.softuni.jsonprocessingex.util.implementation;


import com.softuni.jsonprocessingex.util.FileIO;

import java.io.*;


public class FileIOImplementation implements FileIO {
    @Override
    public String fileContent(String path) throws IOException {
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append(System.lineSeparator());

        }
        return sb.toString().trim();
    }

    @Override
    public void writeToJSON(String path, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(content);
        writer.close();
    }

}
