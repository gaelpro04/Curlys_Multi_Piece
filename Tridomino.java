import java.util.ArrayList;

//Clase que simula un tridomino completo de triple 6
public class Tridomino {

    //Atributos, para almacenar el tridomino completo(es decir todas las fichas) y
    // un atributo del total de fichas
    private ArrayList<FichaTridomino> fichas;
    private int totalFichas;

    /**
     * Constructor por defecto, donde el parametro sirve si se quiere una colección del tridomino vacio o no
     * @param vacio
     */
    public Tridomino(boolean vacio)
    {
        //Formula para determinar el total de fichas de acuerdo al triple máximo del triDomino
        totalFichas = ((5+1)*(5+2)*(5+3))/6;

        //Si es true, eso quiere decir que haremos una colección vacio
        if (vacio) {
            fichas = new ArrayList<>();

            //En caso que sea falso, es quiere decir que haremos del tridomino completo
        } else {
            //Inicio el atributo fichas con el totalMaximo de fichas
            fichas = new ArrayList<>(totalFichas);

            //Ciclo para generar el tridomino, consta de tres que despues del primero empiezan con el contador del ciclo
            //anterior
            for (int i = 0; i <= 5; ++i) {
                for (int j = i; j <= 5; ++j) {
                    for (int k = j; k <= 5; ++k) {
                        fichas.add(new FichaTridomino(i,j,k,false));
                    }
                }
            }
        }
    }

    /**
     * Método para regresar la cantidad de fichas
     */
    public int getTotalFichas()
    {
        return totalFichas;
    }

    /**
     * Método para regresar la colección entera y poderla manipular facilmente
     * @return
     */
    public ArrayList<FichaTridomino> getTridomino()
    {
        return fichas;
    }

    /**
     * Método para mostrar todas la fichas del tridomino
     */
    public void mostrarTridomino()
    {
        fichas.forEach(fichaTridomino -> System.out.println(fichaTridomino.toString()));
    }
}
