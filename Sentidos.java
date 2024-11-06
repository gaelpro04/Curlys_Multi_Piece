import java.util.ArrayList;

public class Sentidos {

    ArrayList<Boolean> sentido;

    public Sentidos(int tipoDomino) {

        if (tipoDomino == 2) {
            sentido = new ArrayList<>(4);
            for (int i = 0; i < 4; i++) {
                sentido.add(false);
            }
            sentido.set(0,true);
        } else if (tipoDomino == 3) {
            sentido = new ArrayList<>(6);
            for (int i = 0; i < 6; i++) {
                sentido.add(false);
            }
            sentido.set(0,true);
        } else {
            System.err.println("Solamente se permiten de 3 y 2");
        }
    }

    public ArrayList<Boolean> getSentido()
    {
        return sentido;
    }
}
