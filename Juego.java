import java.util.ArrayList;
import java.util.Collections;

//Clase que modela el juego
public class Juego {

    //Atributos que conforman parte del juego
    private ArrayList<Jugador> jugadores;
    private Domino domino;
    private Tridomino tridomino;
    private Mesa mesa;

    /**
     * Constructor por defecto de la clase, que inicializa los atributos iniciales del juego
     */
    public Juego()
    {
        jugadores = new ArrayList<>(2);
        domino = new Domino(false);
        tridomino = new Tridomino(false);
        mesa = new Mesa();
    }

    /**
     * Método que mezcla las fichas para que luego puedan ser seleccionadas aleatoriamente por los jugadores
     */
    public void mezclarFichas()
    {
        //Primero se crea la variable de referencia para hacer más simplificado el código
        ArrayList<Ficha> fichasRevueltas = mesa.getPozo();

        //Se almacenan todas las fichas del domino al pozo del juego
        for (int i = 0; i < domino.getTotalFichas(); ++i) {
            fichasRevueltas.add(domino.getDomino().removeFirst());
        }

        //Se almacenaa las fichas del tridomino al pozo del juego
        for (int i = 0; i < tridomino.getTotalFichas(); ++i) {
            fichasRevueltas.add(tridomino.getTridomino().removeFirst());
        }

        //Luego se revuelven todas las fichas y hacemos todas las cartas ocultas con un lambda para que el jugador
        //"pueda" escoger aleatoriamente las fichas
        Collections.shuffle(fichasRevueltas);
        fichasRevueltas.forEach(ficha -> ficha.setEstaVolteada(false));

        //Por ultimo se visualiza las fichas.
        mesa.visualizarPozo();
    }

    /**
     * Método para elaborar las manos de los jugadores que en este caso lo escogeran
     */
    public void hacerManos()
    {
        System.out.println("Simon");
    }


    /**
     * Método que se utiliza para jugar el juego
     */
    public void jugar()
    {
        System.out.println("SImon");
    }
}
