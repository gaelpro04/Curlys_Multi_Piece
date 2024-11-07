import java.util.ArrayList;

//Clase que modela un domino de doble 6
public class Domino {

    //Atributos, una colección para almacenar todo el domino
    private ArrayList<Ficha> fichas;
    //Otra para saber el total fichas
    private int totalFichas;

    /**
     * Constructor por defecto que pide si lo quiere vacio o no con el parametro vacio
     * @param vacio
     */
    public Domino(boolean vacio)
    {
        //Calculo del total de fichas
        totalFichas = ((6+1)*(6+2))/2;

        //Si lo pide vacio, simplemente arroja un domino vacio
        if (vacio) {
            fichas = new ArrayList<>();

            //Si el caso es contrario entonces se procede a generar el domino completo
        } else {
            fichas = new ArrayList<>(totalFichas);

            //Se utilizan de dos ciclos para generar el domino donde el ciclo iterado toma como contador inicial i
            for (int i = 0; i <= 6; ++i) {
                for (int j = i; j <= 6; ++j) {
                    fichas.add(new Ficha(i,j,false, 2));
                }
            }
        }
    }

    /**
     * Método para obtener la cantidad de fichas
     */
    public int getTotalFichas()
    {
        return totalFichas;
    }

    /**
     * Método para obtener la colección de fichas y poder manipularla más sencillamente
     * @return
     */
    public ArrayList<Ficha> getDomino()
    {
        return fichas;
    }

    /**
     * Método para mostrar el domino
     */
    public void mostrarDomino()
    {
        int cont = 0;
        for (Ficha ficha : fichas) {

            System.out.print(ficha.toString() + " ");
            ++cont;
            if (cont == 7) {
                System.out.println();
                cont = 0;
            }
        }
    }
}
