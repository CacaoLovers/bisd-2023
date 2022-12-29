package executors;

import holders.ClientHolder;

public class PlayerTwoExecute {
    public static void main(String[] args) {
        ClientHolder client = new ClientHolder("localhost", 5678);
        client.start();
    }
}
