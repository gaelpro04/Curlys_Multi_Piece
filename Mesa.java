import java.util.ArrayList;

//Clase que simula un tablero de juego domino
public class Mesa {

    //Atributos esenciales de la mesa donde consta del tablero del juego y
    // el pozo del juego
    private ArrayList<Ficha> tablero;
    private ArrayList<Ficha> pozo;

    /**
     * Constructor por defecto de mesa
     */
    public Mesa()
    {
        tablero = new ArrayList<>();
        pozo = new ArrayList<>();
    }

    /**
     * Método para visualizar el pozo
     */
    public void visualizarPozo()
    {
        pozo.forEach(ficha -> System.out.println(ficha.toString()));
    }

    /**
     * Método para visualizar el estado actual del tablero del juego
     */
    public void visualizarTablero()
    {
        tablero.forEach(ficha -> System.out.println(ficha.toString()));
    }

    //////////////////////////////////////////////////////////////////////////////
    //Sección de getters y setters de la clase

    public ArrayList<Ficha> getTablero() {
        return tablero;
    }

    public void setTablero(ArrayList<Ficha> tablero) {
        this.tablero = tablero;
    }

    public ArrayList<Ficha> getPozo() {
        return pozo;
    }

    public void setPozo(ArrayList<Ficha> pozo) {
        this.pozo = pozo;
    }
}
