package com.softuni.jsonprocessingex.util;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileIO {
    String fileContent(String path) throws IOException;
    void writeToJSON(String path,String content) throws IOException;
}
