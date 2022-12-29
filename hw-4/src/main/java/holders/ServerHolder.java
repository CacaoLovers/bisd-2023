package holders;

import models.Player;
import models.Field;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
public class ServerHolder implements Runnable{

    private static final int MAX_PLAYERS = 2;

    private InputStream in;
    private OutputStream out;
    private ServerSocket serverSocket;

    private ArrayList<Socket> sockets = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayDeque<Player> moves = new ArrayDeque<>();
    private Field field = Field.builder()
            .fieldArea(new byte[][]{
                    {0, 0, 0},
                    {0, 0, 0},
                    {0, 0, 0}
            })
            .build();;
    private char[] symbols = new char[] {'X', 'O'};


    private int socketId;

    public ServerHolder(){}

    public static ServerHolder create(Integer port){
        try {
            ServerHolder server = new ServerHolder();
            server.serverSocket = new ServerSocket(port);
            System.out.println("Сервер запущен");
            System.out.println("Ожидание игроков");
            return server;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void run(){
        try {
            Socket clientSocket;
            Player player;

            while(sockets.size() != MAX_PLAYERS){
                clientSocket = serverSocket.accept();

                if (!sockets.contains(clientSocket)){
                    sockets.add(clientSocket);
                    socketId = sockets.lastIndexOf(clientSocket);
                    player = Player.builder()
                            .playerId(sockets.lastIndexOf(clientSocket))
                            .socket(clientSocket)
                            .symbol(symbols[socketId])
                            .build();
                    players.add(player);
                    moves.addLast(player);
                }

                in = new BufferedInputStream(clientSocket.getInputStream());
                out = clientSocket.getOutputStream();
                System.out.println("Игрок #" + socketId + " подключился");
                out.write(socketId);
            }

            System.out.println("Игра начинается");

            for(Socket socket: sockets){
                socket.getOutputStream().write(1);
            }

            Player playerMove = moves.removeFirst();
            OutputStream outputStream;

            while (!serverSocket.isClosed()){

                outputStream = playerMove.getSocket().getOutputStream();
                outputStream.write(field.fieldToByte());
                outputStream.flush();

                byte[] dataFromSocket = readInput(playerMove.getSocket().getInputStream());

                if (dataFromSocket.length == 0){
                    continue;
                }

                field.getFieldArea()[dataFromSocket[0]][dataFromSocket[1]] = (byte) playerMove.getSymbol();

                System.out.println("Игрок " + playerMove.getPlayerId() + " сходил");
                Field.showField(field.fieldToByte());

                if (Field.checkWin(field.getFieldArea(), (byte) playerMove.getSymbol())){
                    outputStream.write(new byte[] {(byte) 'W', 0, 0, 0, 0, 0, 0, 0, 0});
                    outputStream.flush();

                    Player playerLoser = moves.removeFirst();
                    outputStream = playerLoser.getSocket().getOutputStream();
                    outputStream.write(new byte[] {(byte) 'L', 0, 0, 0, 0, 0, 0, 0, 0});
                    outputStream.flush();
                    playerMove.getSocket().close();
                    playerLoser.getSocket().close();
                    System.out.println("Игрок " + playerMove.getPlayerId() + " победил");
                    System.out.println("Игра завершена");
                    break;
                }

                moves.addLast(playerMove);
                playerMove = moves.removeFirst();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] readInput(InputStream input){
        try {
            int counter = 0;
            int b;
            byte[] toByte = new byte[2];
            while (true) {
                if (!((b = input.read()) > -1)) break;
                toByte[counter++] = (byte) b;
                if(counter == 2){
                    break;
                }
            }
            return toByte;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
