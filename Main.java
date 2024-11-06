public class Main {
    public static void main(String[] args) {

        Tridomino fichas = new Tridomino(false);
        fichas.mostrarTridomino();

        System.out.println("\n");

        Domino fichitaspro = new Domino(false);
        fichitaspro.mostrarDomino();

        Ficha fichitaprosito = new Ficha(1,2,2);
        fichitaprosito.rotateRight();
        fichitaprosito.rotateRight();
        System.out.println(fichitaprosito);

        Ficha fichitapro2 = new FichaTridomino(1,2,3);
        System.out.println(fichitapro2);
        fichitapro2.rotateRight();
        System.out.println(fichitapro2);
        fichitapro2.rotateRight();
        System.out.println(fichitapro2);
        fichitapro2.rotateRight();
        System.out.println(fichitapro2);
        fichitapro2.rotateRight();
        System.out.println(fichitapro2);
        fichitapro2.rotateRight();
        System.out.println(fichitapro2);
        fichitapro2.rotateRight();
        System.out.println(fichitapro2);
    }
}
