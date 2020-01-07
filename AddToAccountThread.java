import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 添加至账户文件线程
 *
 * @author hzhang1
 * @date 2020-01-07
 */
public class AddToAccountThread implements Runnable{

  private Integer i;
  private CountDownLatch countDownLatch;
  AddToAccountThread(Integer i , CountDownLatch countDownLatch){
    this.i = i;
    this.countDownLatch = countDownLatch;
  }


  public void run() {

    Map<String, Integer> hashResultMap = null;
    try {
      hashResultMap = FileUtil
          .readForHash("/Users/listnova/Downloads/result/reHashResult/result" + i + ".txt",false);

      Map<String, Integer> countMap = FileUtil
          .readForHash("/Users/listnova/Downloads/guidang/account/account" + i + ".txt",true);

      Map<String, Integer> countResultMap = new LinkedHashMap<String, Integer>(100);
      for(String key : countMap.keySet()){
        countResultMap.put(key, ( hashResultMap.containsKey(key)?hashResultMap.get(key) : 0 ) + countMap.get(key));
      }

      FileUtil.write("/Users/listnova/Downloads/result/result/result" + i + ".txt",countResultMap);

      System.out.println(countDownLatch.getCount());
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      countDownLatch.countDown();
    }

  }
}
