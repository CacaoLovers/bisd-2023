package models;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


@Data
@Builder
public class Player{

    private Integer playerId;
    private char symbol;
    private Socket socket;

}
