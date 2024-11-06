import java.util.ArrayList;

public class Jugador {

    private String nombre;
    private int puntuacion;
    private ArrayList<Ficha> mano;

    public Jugador(String nombre)
    {
        this.nombre = nombre;
        this.puntuacion = 0;
        mano = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
