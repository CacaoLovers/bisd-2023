package models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;

@Data
@Builder
@AllArgsConstructor
public class Field {
    private byte[][] fieldArea;

    public byte[] fieldToByte(){
        int count = 0;
        byte[] toByte = new byte[9];
        for(byte[] diag: fieldArea){
            for(byte vertical: diag){
                toByte[count++] = vertical;
            }
        }
        return toByte;
    }

    public static byte[][] byteToField(byte[] field){
        byte[][] toField = new byte[3][3];
        for(int i = 0; i < field.length; i++){
            toField[i/3][i%3] = field[i];
        }
        return toField;
    }

    public void updateField(byte[] field){
        for(int i = 0; i < field.length; i++){
            fieldArea[i/3][i%3] = field[i];
        }
    }

    public static void showField(byte[] fieldIn){
        byte[][] field = Field.byteToField(fieldIn);
        for(byte[] diag: field){
            for(byte vertical: diag){
                System.out.print((char) vertical + " ");
            }
            System.out.println();
        }
    }


}
