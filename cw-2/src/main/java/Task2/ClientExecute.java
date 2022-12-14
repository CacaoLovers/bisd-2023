package Task2;

import Task1.ClientHolder;
import Task1.Packet;

import java.io.IOException;
import java.util.Scanner;

public class ClientExecute {
    public static void main(String[] args) throws IOException {
        ClientHolder client = ClientHolder.init(404, "localhost");
        System.out.println("Input number");
        Scanner scanner = new Scanner(System.in);

        Integer number = scanner.nextInt();

        Integer out = scanner.nextInt();
        Packet packet = Packet.create(2);
        packet.setValue(1, number);
        client.sendMessage(packet);

    }
}
