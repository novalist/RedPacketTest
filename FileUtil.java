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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 红包文件读/写操作
 *
 * @author hzhang1
 * @date 2020-01-07
 */
public class FileUtil {

  static Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");

  public static Map<String, List<String>> readForReHash(String path) throws IOException {

    Map<String, List<String>> indexMap = new HashMap<String, List<String>>(100);

    try {
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
    }catch (Exception e){
      e.printStackTrace();
    }
    return indexMap;

  }

  public static Map<String, Long> readForHash(String path, boolean needSeq) throws IOException {

    Map<String, Long> hashResultMap =
        needSeq ? new LinkedHashMap<String, Long>(100) : new HashMap<String, Long>(100);
    try {

      BufferedReader br = new BufferedReader(
          new InputStreamReader(new FileInputStream(new File(path)), "UTF-8"));

      String lineTxt = null;
      while ((lineTxt = br.readLine()) != null) {
        lineTxt += "   0";
        String[] names = lineTxt.split(" ");
        String resultKey = names[0];

        Matcher isNum = pattern.matcher(names[1]);
        if(!isNum.matches()){
          names[1] = "0";
        }

        if (hashResultMap.containsKey(resultKey)) {
          Long resultValue = hashResultMap.get(resultKey);
          hashResultMap.put(resultKey, Long.valueOf(names[1]) + resultValue);
        } else {
          hashResultMap.put(resultKey, Long.valueOf(names[1]));
        }
      }
      br.close();
    }catch (Exception e){
      e.printStackTrace();
    }
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
      e.printStackTrace();
      System.err.println("write errors :" + e);
    }
  }

  public static void write(String path, Map<String, Long> resultMap) {
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
      e.printStackTrace();
      System.err.println("write errors :" + e);
    }
  }

}
