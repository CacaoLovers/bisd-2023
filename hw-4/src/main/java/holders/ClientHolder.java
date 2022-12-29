package holders;

import models.Field;
import models.Player;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class ClientHolder{
    private String address;
    private int port;

    private Socket socket;

    private InputStream in;
    private OutputStream out;

    private int playerId;
    private Scanner scanner;

    public ClientHolder(String address, int port){
        try {
            this.address = address;
            this.port = port;
            socket = new Socket(address, port);
            in = new BufferedInputStream(socket.getInputStream());
            out = socket.getOutputStream();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start(){
        try {
            playerId = in.read();
            System.out.println("Ваш ID " + playerId);

            if (in.read() == 1){
                System.out.println("Игра начинается!");
            } else {
                System.out.println("Никто не пришел на фан встречу((");
                socket.close();
            }

            scanner = new Scanner(System.in);
            int x, y;
            byte[][] fieldArea;
            byte[] field = null;
            boolean repeat = false;
            while (true){
                if (!repeat) {
                    field = readInput(in);
                    if (field[0] == (byte) 'W'){
                        System.out.println("Поздравляем, вы выйграли!");
                        socket.close();
                        break;
                    } else if (field[0] == (byte) 'L'){
                        System.out.println("Ха-ха, вы продули, пока дули");
                        socket.close();
                        break;
                    }
                }
                fieldArea = Field.byteToField(field);
                Field.showField(field);
                x = scanner.nextInt();
                y = scanner.nextInt();
                if(x > 2 || y > 2 || x < 0 || y < 0){
                    System.out.println("Неверные координаты");
                    repeat = true;
                    continue;
                }
                if (fieldArea[x][y] != 0){
                    System.out.println("Ячейка уже занята");
                    repeat = true;
                    continue;
                }
                repeat = false;
                out.write(new byte[]{(byte)x, (byte)y});
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private byte[] extendArray(byte[] oldArray) {
        int oldSize = oldArray.length;
        byte[] newArray = new byte[oldSize * 2];
        System.arraycopy(oldArray, 0, newArray, 0, oldSize);
        return newArray;
    }

    public byte[] readInput(InputStream input){
        try {
            int counter = 0;
            int b;
            byte[] toByte = new byte[9];
            while (true) {
                b = input.read();
                toByte[counter++] = (byte) b;
                if(counter == 9){
                    break;
                }
            }
            return toByte;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
