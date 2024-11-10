import java.util.ArrayList;
import java.util.Collections;

//Clase ficha que implementa la interfaz Movible(es decir todos los métodos de la clase se deben declarar
// aquí para utilizarlos)
public class Ficha implements Movible{

    //Atributos protegidos ya que se utilizan como atributos en clases hijas
    protected int lado1;
    protected int lado2;
    protected boolean estaVolteada;
    protected Sentidos sentido;

    /**
     * Constructor por defecto
     * @param lado1
     * @param lado2
     */
    public Ficha(int lado1, int lado2, int sentido)
    {
        this.sentido = new Sentidos(sentido);
        this.lado1 = lado1;
        this.lado2 = lado2;
        estaVolteada = false;
    }

    /**
     * Constructor que permite definir desde el inicio el estado de la ficha
     * @param lado1
     * @param lado2
     * @param estaVolteada
     */
    public Ficha(int lado1, int lado2, boolean estaVolteada, int sentido)
    {
        this.sentido = new Sentidos(sentido);
        this.lado1 = lado1;
        this.lado2 = lado2;
        this.estaVolteada = estaVolteada;
    }

    public Ficha(Ficha fichaCopiada, int sentido)
    {
        this.sentido = new Sentidos(sentido);
        this.lado1 = fichaCopiada.lado1;
        this.lado2 = fichaCopiada.lado2;
        this.estaVolteada = fichaCopiada.estaVolteada;
    }

    /**
     * Método que verifica si es mula la ficha
     * @return
     */
    public boolean esMula()
    {
        if (lado1 == lado2) {
            return true;
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //Sección de getters y setters

    public int getLado1() {
        return lado1;
    }

    public void setLado1(int lado1) {
        this.lado1 = lado1;
    }

    public int getLado2() {
        return lado2;
    }

    public void setLado2(int lado2) {
        this.lado2 = lado2;
    }

    public boolean isEstaVolteada() {
        return estaVolteada;
    }

    public void setEstaVolteada(boolean estaVolteada) {
        this.estaVolteada = estaVolteada;
    }

    public ArrayList<Boolean> getSentido()
    {
        return sentido.getSentido();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Método que suma los lados de la ficha
     * @return
     */
    public int sumaLados()
    {
        return lado1 + lado2;
    }

    /**
     * Método que regresa una cadena de los atributos(toString, es decir simula la ficha en este caso en consola)
     * @return
     */
    @Override
    public String toString()
    {
        //Si está volteada regresará la ficha oculta, tomando en cuenta sus dos orientaciones
        if (estaVolteada) {
            if (sentido.getSentido().get(1) || sentido.getSentido().get(3)) {
                return "\n-\n";
            } else {
                return "[   ]";
            }
            //En dado caso que no lo esté simplemente se imprime de acuerdo al sentido que tiene.
        } else {
            if (sentido.getSentido().getFirst()) {
                return "[" + lado1 + "|" + lado2 + "]";
            } else if (sentido.getSentido().get(1)) {
                return lado1 + "\n" + "-\n" + lado2;
            } else if (sentido.getSentido().get(2)) {
                return "[" + lado2 + "|" + lado1 + "]";
            } else if (sentido.getSentido().get(3)) {
                return lado2 + "\n" + "-\n" + lado1;
            }
        }
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //Métodos implementados de la clase Movible

    @Override
    public void rotateRight()
    {
        int index = sentido.getSentido().indexOf(true);
        Collections.fill(sentido.getSentido(), false);

        if (index == (getSentido().size() - 1)) {
            sentido.getSentido().set(0,true);
        } else {
            sentido.getSentido().set((index+1), true);
        }
    }

    @Override
    public void rotateLeft()
    {
        int index = getSentido().indexOf(true);
        Collections.fill(sentido.getSentido(),false);

        if (index == 0) {
            sentido.getSentido().set((getSentido().size()-1), true);
        } else {
            sentido.getSentido().set((index-1),true);
        }
    }

    @Override
    public int getTotalSentidos()
    {
        return sentido.getSentido().size();
    }
}
