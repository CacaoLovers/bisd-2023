package executors;

import holders.ServerHolder;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class ServerExecute {

    public static void main(String[] args) throws InterruptedException {

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        ServerHolder serverHolder = ServerHolder.create(5678, executor);
        while (true){
            executor.execute(serverHolder);
            if (executor.getActiveCount() >= 2) {
                try {
                    synchronized (executor) {
                        executor.wait();
                    }
                } catch (InterruptedException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
    }

}
