//Clase ficha que implementa la interfaz Movible(es decir todos los métodos de la clase se deben declarar
// aquí para utilizarlos)
public class Ficha implements Movible{

    //Atributos protegidos ya que se utilizan como atributos en clases hijas
    protected int lado1;
    protected int lado2;
    protected boolean estaVolteada;

    /**
     * Constructor por defecto
     * @param lado1
     * @param lado2
     */
    public Ficha(int lado1, int lado2)
    {
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
    public Ficha(int lado1, int lado2, boolean estaVolteada)
    {
        this.lado1 = lado1;
        this.lado2 = lado2;
        this.estaVolteada = estaVolteada;
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
        return "[" + lado1 + "|" + lado2 + "]";
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //Métodos implementados de la clase Movible

    @Override
    public void rotateRight() {

    }

    @Override
    public void rotateLeft() {

    }
}
