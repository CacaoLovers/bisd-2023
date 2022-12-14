package Task1;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Data
@AllArgsConstructor
public class Packet {
    private static final byte HEADER_1 = (byte) 0xe4;
    private static final byte HEADER_2 = (byte) 0x15;

    private static final byte FOOTER_1 = (byte) 0x00;
    private static final byte FOOTER_2 = (byte) 0x90;

    private byte type;
    private List<AwesomeField> fields = new ArrayList<>();

    private Packet(){

    }

    public static Packet create(int type){
        Packet packet = new Packet();
        packet.type = (byte) type;
        return packet;
    }

    public byte[] toByteArray(){
        try(ByteArrayOutputStream writer = new ByteArrayOutputStream()){
            writer.write(new byte[] {HEADER_1, HEADER_2});
            writer.write(type);
            for(AwesomeField field: fields){
                writer.write(new byte[] {field.getId(), field.getSize()});
                writer.write(field.getContent());
            }
            writer.write(new byte[] {FOOTER_1, FOOTER_2});
            return writer.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Packet parse(byte[] data){
        if (data[0] != HEADER_1 && data[1] != HEADER_2
                || data[data.length - 1] != FOOTER_2 && data[data.length - 2] != FOOTER_1){
            throw new IllegalArgumentException("Unknown packet format");
        }
        byte type = data[2];
        Packet awesomePacket = Packet.create(type);
        int offset = 3;
        while (true){
            if (data.length <= offset){
                return awesomePacket;
            }
            byte fieldId = data[offset];
            byte fieldSize = data[offset + 1];

            if (fieldId == FOOTER_1 && fieldSize == FOOTER_2){
                return awesomePacket;
            }

            byte[] content = new byte[fieldSize];
            if(fieldSize != 0) {
                System.arraycopy(data, offset + 2, content, 0, fieldSize);
            }

            AwesomeField awesomeField = new AwesomeField(fieldId, fieldSize, content);
            awesomePacket.getFields().add(awesomeField);

            offset += 2 + fieldSize;

        }
    }

    public AwesomeField getField(int id){
        Optional<AwesomeField> field = getFields().stream().filter(f -> f.getId() == (byte) id).findFirst();
        if (field.isEmpty()){
            throw new IllegalArgumentException("No field");
        }
        return field.get();
    }

    public <T> T getValue(int id, Class<T> clazz){
        AwesomeField field = getField(id);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(field.getContent())) {
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (T) ois.readObject();

        }catch (IOException e){
            throw new RuntimeException();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setValue(int id, Object value){
        AwesomeField field;
        try{
            field = getField(id);
        } catch (IllegalArgumentException e){
            field = new AwesomeField((byte)id);
        }
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream()){
            ObjectOutputStream ous = new ObjectOutputStream(bos);
            ous.writeObject(value);
            byte data[] = bos.toByteArray();
            if (data.length > 255){
                throw new IllegalArgumentException("Too match");
            }
            field.setSize((byte)data.length);
            field.setContent(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getFields().add(field);
    }

    @Data
    @AllArgsConstructor
    private static class AwesomeField {
        private byte id;
        private byte size;
        private byte[] content;

        public AwesomeField(byte id){
            this.id = id;
        }
    }

    public static boolean compare(byte[] arr, int lastItem) {
        return arr[lastItem - 1] == FOOTER_1 && arr [lastItem] == FOOTER_2;
    }
}
