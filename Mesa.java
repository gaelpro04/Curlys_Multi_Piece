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
        //En este caso no utilizamos lambdas ya que ocupamos enumerar cada ficha para
        //despues se tenga que elegir una, se haga mediante el indice seleccionado

        if (!pozo.isEmpty()) {
            for (int i = 0; i < pozo.size(); i++) {
                System.out.println("[" + i + "]=============== \n" + pozo.get(i).toString());
            }
        } else {
            System.out.println("El pozo está vacio");
        }



    }

    /**
     * Método para visualizar el estado actual del tablero del juego
     */
    public void visualizarTablero()
    {
        if (!tablero.isEmpty()) {
            System.out.println();
            for (int i = 0; i < tablero.size(); i++) {
                System.out.println(tablero.get(i).toString());
            }
        } else {
            System.out.println("El tablero está vacio");
        }
        System.out.println();
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
