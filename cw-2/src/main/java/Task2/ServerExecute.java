package Task2;

import Task1.ServerHolder;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerExecute {
    public static void main(String[] args) throws IOException {
        ThreadPoolExecutor serverPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        ServerHolder server = ServerHolder.createServer(404, serverPool);
        server.run();
    }
}
