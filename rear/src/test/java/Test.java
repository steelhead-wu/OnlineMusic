import com.wll.enums.ResourcesPath;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @time 2025/2/25 19:23 周二
 */
public class Test {

    public static void main(String[] args) throws IOException {
        Path filepath = Paths.get(ResourcesPath.values()[0].toString());
        System.out.println(filepath);
//        System.out.println(filepath.);

    }
}
