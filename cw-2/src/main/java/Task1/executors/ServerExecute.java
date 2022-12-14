package Task1.executors;

import Task1.ServerHolder;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerExecute {
    public static void main(String[] args) throws IOException {
        ThreadPoolExecutor serverPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        ServerHolder server = ServerHolder.createServer(403, serverPool);
        server.run();
    }

}
