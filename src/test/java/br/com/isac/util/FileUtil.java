package br.com.isac.util;

import java.io.File;
import java.nio.file.Files;
import org.springframework.util.ResourceUtils;

public class FileUtil {
    public static final String loadRequest(String filename) throws Exception {
        return loadFile("classpath:request/" + filename + ".json");
    }

    public static String loadResponse(String filename) throws Exception {
        return loadFile("classpath:response/" + filename + ".json");
    }

    private static String loadFile(String filePath) throws Exception {
        File file = ResourceUtils.getFile(filePath);
        return new String(Files.readAllBytes(file.toPath()));
    }
}
