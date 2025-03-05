import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @time 2025/2/25 19:23 周二
 */
public class Test {

    public static void main(String[] args) throws IOException {
        String str1 = "The quick brown fox jumps over the lazy dog. It barked.";
        String str2 = "aabfooaabfooabfoobkkk";
        Pattern pattern2 = Pattern.compile("a*b");

        System.out.println(str2.replaceAll(pattern2.toString(), "-"));

    }
}
