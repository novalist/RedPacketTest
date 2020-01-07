import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 红包
 *
 * @author hzhang1
 * @date 2020-01-07
 */
public class RedPacketTest {

  /**
   * 读取红包文件
   *
   * @param num 文件数
   * @throws IOException 异常
   */
  public void read(Integer num) throws IOException {

    int availProcessors = Runtime.getRuntime().availableProcessors();
    BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(num);
    ExecutorService executorService = new ThreadPoolExecutor(availProcessors, availProcessors * 2,
        10,
        TimeUnit.MINUTES, blockingQueue);
    CountDownLatch countDownLatch = new CountDownLatch(num);
    try {

      for (int i = 0; i < num; i++) {

        System.out.println("================file " + i + " re hash start=======");
        new ReadRedThread(i, countDownLatch).run();
//        executorService.submit(new ReadRedThread(i, countDownLatch));
        System.out.println("================file " + i + " re hash end=======");
      }

    } catch (Exception e) {
      System.err.println("read errors :" + e);
    }


    try {
      countDownLatch.await();
      executorService.shutdown();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }


  /**
   * 汇总添加至账户文件
   *
   * @param num 文件数
   * @throws IOException 异常
   */
  public void addToAccountFile(Integer num) throws IOException {

    int availProcessors = Runtime.getRuntime().availableProcessors();
    BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(num);
    ExecutorService executorService = new ThreadPoolExecutor(availProcessors, availProcessors * 2,
        10,
        TimeUnit.MINUTES, blockingQueue);

    CountDownLatch countDownLatch = new CountDownLatch(num);
    for (int i = 0; i < num; i++) {

      System.out.println("================file " + i + " addToAccountFile start=======");
      new AddToAccountThread(i, countDownLatch).run();
      //executorService.submit(new AddToAccountThread(i, countDownLatch));
      System.out.println("================file " + i + " addToAccountFile end=======");
    }

    try {
      countDownLatch.await();
      executorService.shutdown();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException {

    System.out.println("================start=======");
    RedPacketTest redPacketTest = new RedPacketTest();
    redPacketTest.read(50);

    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println("================addToAccountFile start=======");

    redPacketTest.addToAccountFile(100);
    System.out.println("================end=======");
  }

}
