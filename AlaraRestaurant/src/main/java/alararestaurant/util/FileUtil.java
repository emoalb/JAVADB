package alararestaurant.util;

import java.io.IOException;

public interface FileUtil {
    String fileContent(String path) throws IOException;
    void writeToJSON(String path,String content) throws IOException;
}
