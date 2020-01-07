import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 读红包文件线程
 *
 * @author hzhang1
 * @date 2020-01-07
 */
public class ReadRedThread implements Runnable{

  private Integer i;
  private CountDownLatch countDownLatch;
  ReadRedThread(Integer i,CountDownLatch countDownLatch){
    this.i = i;
    this.countDownLatch = countDownLatch;
  }

  public void run() {

    try {
      Map<String, List<String>> indexMap = FileUtil
          .readForReHash("/Users/listnova/Downloads/guidang/red/red" + i + ".txt");

      for(String key : indexMap.keySet()) {
        FileUtil.write("/Users/listnova/Downloads/result/reHashResult/result" + key + ".txt", indexMap.get(key));
      }

      System.out.println(countDownLatch.getCount());
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      countDownLatch.countDown();
    }

  }
}
