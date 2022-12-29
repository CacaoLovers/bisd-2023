package holders;

import models.Field;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientHolder{
    private Socket socket;

    private InputStream in;
    private OutputStream out;

    private int playerId;
    private Scanner scanner = new Scanner(System.in);

    public ClientHolder(String address, int port){
        try {
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

            gameProcessing();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void gameProcessing() {
        try {
            int x, y;
            byte[][] fieldArea;
            byte[] field = null;
            boolean repeat = false;
            while (!socket.isClosed()) {
                if (!repeat) {
                    field = readInput(in);
                    if (field[0] == (byte) 'W') {
                        System.out.println("Поздравляем, вы выйграли!");
                        socket.close();
                        break;
                    } else if (field[0] == (byte) 'L') {
                        System.out.println("Ха-ха, вы продули, пока дули");
                        socket.close();
                        break;
                    }
                }

                fieldArea = Field.byteToField(field);
                Field.showField(field);
                x = scanner.nextInt();
                y = scanner.nextInt();

                if (x > 2 || y > 2 || x < 0 || y < 0) {
                    System.out.println("Неверные координаты");
                    repeat = true;
                    continue;
                }
                if (fieldArea[x][y] != 0) {
                    System.out.println("Ячейка уже занята");
                    repeat = true;
                    continue;
                }

                repeat = false;
                out.write(new byte[]{(byte) x, (byte) y});
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] readInput(InputStream input){
        try {
            int counter = 0;
            int b;
            byte[] toByte = new byte[9];

            do {
                b = input.read();
                toByte[counter++] = (byte) b;
            } while (counter != 9);

            return toByte;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
