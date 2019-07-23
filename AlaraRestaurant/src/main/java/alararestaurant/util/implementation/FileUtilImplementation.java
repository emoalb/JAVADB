package alararestaurant.util.implementation;


import alararestaurant.util.FileUtil;

import java.io.*;


public class FileUtilImplementation implements FileUtil {
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
