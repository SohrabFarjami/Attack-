package sohrabfarjami.com;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class SaveManager {
    private static Kryo kryo;

    static {
        kryo = new Kryo();
        kryo.setReferences(true); // Handles your shared object references automatically
        kryo.setRegistrationRequired(false); // Works with all your classes automatically
    }

    public static void save(Object gameState, String filename) {
        try {
            Output output = new Output(Gdx.files.local(filename).write(false));
            kryo.writeObject(output, gameState);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T load(String filename, Class<T> type) {
        try {
            Input input = new Input(Gdx.files.local(filename).read());
            T obj = kryo.readObject(input, type);
            input.close();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] saveToBytes(Object gameState) {
        try {
            Output output = new Output(1024, -1);
            kryo.writeObject(output, gameState);
            return output.toBytes();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
