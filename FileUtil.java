import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 红包文件读/写操作
 *
 * @author hzhang1
 * @date 2020-01-07
 */
public class FileUtil {

  public static Map<String, List<String>> readForReHash(String path) throws IOException {

    Map<String, List<String>> indexMap = new HashMap<String, List<String>>(100);
    BufferedReader br = new BufferedReader(new InputStreamReader(
        new FileInputStream(new File(path)), "UTF-8"));

    String lineTxt = null;
    while ((lineTxt = br.readLine()) != null) {
      String[] names = lineTxt.split(" ");
      List<String> stringList = null;

      Integer indexNo = Integer.valueOf(names[0]) % 100;
      String indexNoStr = indexNo.toString();

      if (indexMap.containsKey(indexNoStr)) {
        stringList = indexMap.get(indexNoStr);
        stringList.add(lineTxt);
        indexMap.put(indexNoStr, stringList);
      } else {
        stringList = new ArrayList<String>();
        stringList.add(lineTxt);
        indexMap.put(indexNoStr, stringList);
      }
    }
    br.close();
    return indexMap;

  }

  public static Map<String, Integer> readForHash(String path, boolean needSeq) throws IOException {

    Map<String, Integer> hashResultMap =
        needSeq ? new LinkedHashMap<String, Integer>(100) : new HashMap<String, Integer>(100);
    BufferedReader br = new BufferedReader(
        new InputStreamReader(new FileInputStream(new File(path)), "UTF-8"));

    String lineTxt = null;
    while ((lineTxt = br.readLine()) != null) {
      String[] names = lineTxt.split(" ");
      String resultKey = names[0];

      if (hashResultMap.containsKey(resultKey)) {
        Integer resultValue = hashResultMap.get(resultKey);
        hashResultMap.put(resultKey, Integer.valueOf(names[1]) + resultValue);
      } else {
        hashResultMap.put(resultKey, Integer.valueOf(names[1]));
      }
    }
    br.close();
    return hashResultMap;

  }

  public static void write(String path, List<String> stringList) {
    try {
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
          new File(path), true),
          "UTF-8"));

      for (String string : stringList) {
        bw.write(string);
        bw.newLine();
      }
      bw.close();
    } catch (Exception e) {
      System.err.println("write errors :" + e);
    }
  }

  public static void write(String path, Map<String, Integer> resultMap) {
    try {
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
          new File(path), true),
          "UTF-8"));

      for (String key : resultMap.keySet()) {
        bw.write(key + " " + resultMap.get(key));
        bw.newLine();
      }
      bw.close();
    } catch (Exception e) {
      System.err.println("write errors :" + e);
    }
  }

}
