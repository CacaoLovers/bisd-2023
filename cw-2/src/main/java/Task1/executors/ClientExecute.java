package Task1.executors;

import Task1.ClientHolder;
import Task1.Packet;

import java.io.IOException;
import java.util.Scanner;

public class ClientExecute {
    public static void main(String[] args) throws IOException {
        ClientHolder client = ClientHolder.init(403, "localhost");
        System.out.println("Input message");
        Scanner scanner = new Scanner(System.in);

        String message = scanner.nextLine();

        System.out.println("Input out");

        Integer out = scanner.nextInt();
        Packet packet = Packet.create(1);
        packet.setValue(1, message);
        packet.setValue(2, out);
        client.sendMessage(packet);

    }
}
