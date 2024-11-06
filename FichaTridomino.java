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

    public boolean esMula()
    {
        if (super.getLado1() == super.getLado2() && lado3 == super.getLado1()) {
            return true;
        }
        return false;
    }

    public int getLado3() {
        return lado3;
    }

    public void setLado3(int lado3) {
        this.lado3 = lado3;
    }

    public int getLado2() {
        return super.getLado2();
    }

    public void setLado2(int lado2) {
        super.setLado2(lado2);
    }
}
