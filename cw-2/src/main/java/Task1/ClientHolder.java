package Task1;

import utils.SocketUtils;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class ClientHolder {

    private InputStream inputStream;
    private OutputStream outputStream;
    private Socket socket;

    public static ClientHolder init(Integer port, String host) throws IOException {
        ClientHolder clientHolder = new ClientHolder();
        clientHolder.socket = new Socket(host, port);
        clientHolder.inputStream = clientHolder.socket.getInputStream();
        clientHolder.outputStream = clientHolder.socket.getOutputStream();
        return clientHolder;
    }

    public void sendMessage(Packet packet) throws IOException {
        outputStream.write(packet.toByteArray());
        outputStream.flush();

        byte[] data = SocketUtils.readInput(inputStream);
        Packet responsePacket = Packet.parse(data);

        if (packet.getType() == 1) {

            String value1 = responsePacket.getValue(1, String.class);
            System.out.println("Message: " + value1);
        } else if (packet.getType() == 2){
            Scanner scanner = new Scanner(System.in);
            Integer dreamNum = responsePacket.getValue(1, Integer.class);
            String messageOut;
            while (true){
            Integer number = scanner.nextInt();

                if (dreamNum > number){
                    messageOut = "Меньше";
                } else if (dreamNum < number) {
                    messageOut = "Больше";
                } else {
                    break;
                }
            }

        }
    }


}
