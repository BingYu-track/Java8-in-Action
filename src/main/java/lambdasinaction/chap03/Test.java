package lambdasinaction.chap03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @version 1.0
 * @Description:
 * @author: bingyu
 * @date: 2021/7/11
 */
public class Test {


    public String processFile() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            return br.readLine();
        }
    }

    public static void main(String[] args) {

    }
}
