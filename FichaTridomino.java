
//Clase hija FichaTridomino, que extiende a ficha e implementa Movible, es decir obtendrá todos los métodos de esta
//misma clase
public class FichaTridomino extends Ficha implements Movible {

    private int lado3;

    /**
     * Constructor por defecto de una ficha triple
     * @param lado1
     * @param lado2
     * @param lado3
     */
    public FichaTridomino(int lado1, int lado2, int lado3)
    {
        //Se utiliza de super para inicializar los dos atributos que se en ecuentran en Ficha
        super(lado1,lado2);
        this.lado3 = lado3;
    }

    /**
     * Constructor para definir desde el inicio el estado de vista de la ficha
     * @param lado1
     * @param lado2
     * @param lado3
     * @param estaVolteada
     */
    public FichaTridomino(int lado1, int lado2, int lado3, boolean estaVolteada)
    {
        super(lado1,lado2, estaVolteada);
        this.lado3 = lado3;
    }

    /**
     * Método que permite determinar si es triple, es decir sus tres lados iguales
     * @return
     */
    public boolean esMula()
    {
        if (super.getLado1() == super.getLado2() && lado3 == super.getLado1()) {
            return true;
        }
        return false;
    }

    /**
     * Método que sirve para sumar los lados de la ficha
     * @return
     */
    public int sumarLados()
    {
        return sumaLados() + lado3;
    }

    //Método para obtener los atributos a String(Simula la trificha)
    @Override
    public String toString()
    {
        return "[" + super.getLado1() + "|" + super.getLado2() + "|" + lado3 + "]";
    }

    ////////////////////////////////////////////////////////////////////////////////////
    //Sección de getters y setters

    public int getLado3()
    {
        return lado3;
    }

    public void setLado3(int lado3)
    {
        this.lado3 = lado3;
    }

    public int getLado2()
    {
        return super.getLado2();
    }

    public void setLado2(int lado2)
    {
        super.setLado2(lado2);
    }

    public int getLado1()
    {
        return super.getLado1();
    }

    public void setLado1(int lado1)
    {
        super.setLado1(lado1);
    }

    ////////////////////////////////////////////////////////////////////////////////////
    //Sección de métodos implementados de la clase Movible

    @Override
    public void rotateRight()
    {

    }

    @Override
    public void rotateLeft()
    {

    }
}

