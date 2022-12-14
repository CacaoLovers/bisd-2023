package Task1;

import utils.SocketUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerHolder implements Runnable{
    private InputStream inputStream;
    private OutputStream outputStream;
    private Integer port;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ThreadPoolExecutor serverPool;


    public static ServerHolder createServer(Integer port, ThreadPoolExecutor serverPool) throws IOException {
        ServerHolder server = new ServerHolder();
        server.port = port;
        server.serverSocket = new ServerSocket(port);
        server.serverPool = serverPool;
        return server;
    }

    @Override
    public void run() {
        try {
            clientSocket = serverSocket.accept();
            outputStream = clientSocket.getOutputStream();
            inputStream = new BufferedInputStream(clientSocket.getInputStream());

            while (true) {
                byte[] data = SocketUtils.readInput(inputStream);
                Packet packet = Packet.parse(data);

                if (packet.getType() == 1) {

                    Packet packet1 = Packet.create(2);
                    packet1.setValue(1, fromCaesarToLatin(packet.getValue(1, String.class),
                            packet.getValue(2, Integer.class)));

                    outputStream.write(packet1.toByteArray());
                    synchronized (serverPool) {
                        serverPool.notifyAll();
                    }
                    break;
                }

                outputStream.flush();
            }
        } catch (IOException e) {
            System.out.println("Wrong");
        }
    }

    private static String fromCaesarToLatin(String message, Integer out){
        String resultMessage = "";
        for(char ch: message.toCharArray()){
            if (ch < 'a' || ch > 'z'){
                return "Wrong symbol";
            }
            if (ch - out < 'a'){
                resultMessage += (char) (ch + 26 - out);
            } else {
                resultMessage += (char) (ch - out);
            }
        }
        return resultMessage;
    }

}
