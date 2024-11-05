public class FichaTridomino extends Ficha implements Movible {

    private int lado3;

    public FichaTridomino(int lado1, int lado2, int lado3)
    {
        super(lado1,lado2);
        this.lado3 = lado3;
    }

    public FichaTridomino(int lado1, int lado2, int lado3, boolean estaVolteada)
    {
        super(lado1,lado2, estaVolteada);
        this.lado3 = lado3;
    }















}
