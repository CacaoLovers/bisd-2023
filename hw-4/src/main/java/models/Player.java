package models;

import lombok.Builder;
import lombok.Data;

import java.net.Socket;


@Data
@Builder
public class Player{

    private Integer playerId;
    private char symbol;
    private Socket socket;

}
