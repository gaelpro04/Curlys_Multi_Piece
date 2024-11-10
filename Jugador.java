import java.util.ArrayList;

//Clase que permite modelar un jugador que en este caso un jugador especifo del juego Curlys multi piece
public class Jugador {

    //Atributos que conforman el jugador
    private String nombre;
    private int puntuacion;
    private ArrayList<Ficha> mano;

    /**
     * Constructor por defecto que solamente pide el nombre del jugador
     * @param nombre
     */
    public Jugador(String nombre)
    {
        this.nombre = nombre;
        this.puntuacion = 0;
        mano = new ArrayList<>();
    }

    ///////////////////////////////////////////////////////////////////////////////////
    //Getters y setters de los atributos a excepción del set de puntuación que este acumula
    //en vez de cambiarlo completamente

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void acumularPuntuacion(int puntuacionAcumulada)
    {
        puntuacion += puntuacionAcumulada;
    }

    public int getPuntuacion()
    {
        return puntuacion;
    }

    public ArrayList<Ficha> getMano() {
        return mano;
    }

    //////////////////////////////////////////////////////////////////////////////////

    /**
     * Método que visualiza toda la mano del jugador en consola
     */
    public void visualizarMano()
    {
        int cont = 0;
        for (int i = 0; i < mano.size(); i++) {
            System.out.println("[" + i + "]=============== \n" + mano.get(i).toString());
        }

    }

    /**
     * Método que hace que sean visibles las cartas
     */
    public void mostrarMano()
    {
        mano.forEach(ficha -> ficha.setEstaVolteada(false));
    }

    /**
     * Método que oculta las cartas
     */
    public void ocultarMano()
    {
        mano.forEach(ficha -> ficha.setEstaVolteada(true));
    }

}
