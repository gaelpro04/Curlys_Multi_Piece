import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

//Clase que modela el juego
public class Juego {

    //Atributos que conforman parte del juego
    private ArrayList<Jugador> jugadores;
    private Domino domino;
    private Tridomino tridomino;
    private Mesa mesa;

    /**
     * Constructor por defecto de la clase, que inicializa los atributos iniciales del juego
     */
    public Juego()
    {
        jugadores = new ArrayList<>(2);
        for (int i = 0; i < 2; ++i) {
            jugadores.add(new Jugador("Jugador " + (i+1)));
        }

        domino = new Domino(false);
        tridomino = new Tridomino(false);
        mesa = new Mesa();
    }

    /**
     * Método que mezcla las fichas para que luego puedan ser seleccionadas aleatoriamente por los jugadores
     */
    private void mezclarFichas()
    {
        Collections.shuffle(tridomino.getTridomino());
        Collections.shuffle(domino.getDomino());
    }

    /**
     * Método para elaborar las manos de los jugadores
     */
    private void hacerManos()
    {
        int totalFichas = 11;
        int fichasTriples = 0;
        int fichasDobles = 0;
        Scanner sc = new Scanner(System.in);


        for (Jugador jugador : jugadores) {
            System.out.println("===" + jugador.getNombre() + "====");
            while (totalFichas != 10) {
                System.out.println("Cuantas fichas dobles quieres?");
                fichasDobles = sc.nextInt();
                System.out.println("Cuantas triples quieres?");
                fichasTriples = sc.nextInt();
                totalFichas = fichasDobles + fichasTriples;
            }

            for (int i = 0;i < fichasDobles; ++i) {
                jugador.getMano().add(tridomino.getTridomino().removeFirst());
            }
            for (int i = 0;i < fichasTriples; ++i) {
                jugador.getMano().add(domino.getDomino().removeFirst());
            }
            totalFichas = 11;
        }
        for (int i = 0; i < tridomino.getTridomino().size(); ++i) {
            mesa.getPozo().add(tridomino.getTridomino().removeFirst());
        }
        for (int i = 0; i < domino.getDomino().size(); ++i) {
            mesa.getPozo().add(domino.getDomino().removeFirst());
        }

    }

    private void colocarPrimeraFicha(Ficha fichaColocadaJ1, Ficha fichaColocadaJ2)
    {
        Scanner sc = new Scanner(System.in);
        Ficha fichaColocada;
        if (fichaColocadaJ1.sumaLados() > fichaColocadaJ2.sumaLados()) {
            fichaColocada = fichaColocadaJ1;
            sumarPuntos(fichaColocada, jugadores.getFirst());
            jugadores.get(1).getMano().add(fichaColocadaJ2);

        } else {
            fichaColocada = fichaColocadaJ2;
            sumarPuntos(fichaColocada, jugadores.get(1));
            jugadores.getFirst().getMano().add(fichaColocadaJ1);
        }

        System.out.println("Como la quieres colocar");

        Ficha fichaTemp = (fichaColocada.getTotalSentidos() == 4) ?
                new Ficha(fichaColocada,2) :
                new FichaTridomino((FichaTridomino) fichaColocada);


        int totalRotaciones = fichaColocada.getTotalSentidos();

        //Solamente se imprime la posición 0 y tres ya que para la primera ficha que es de dos
        //solamente se pueden poner esos sentidos
        if (fichaColocada.getTotalSentidos() == 4) {
            for (int i = 0; i < totalRotaciones; ++i) {
                if (i == 1 || i == 3) {
                    System.out.println((i) + ".==========");
                    System.out.println(fichaTemp);
                }
                fichaTemp.rotateRight();
            }
        } else {
            for (int i = 1; i <= totalRotaciones; ++i) {
                System.out.println(i + ".==========");
                fichaTemp.rotateRight();
                System.out.println(fichaTemp);
            }
        }

        int sentidoEscogido = sc.nextInt();
        if (sentidoEscogido >= 1 && sentidoEscogido <= totalRotaciones) {
            for (int i = 0; i < sentidoEscogido; ++i) {
                fichaColocada.rotateRight();
            }
            //FICHA COLOCADA
            mesa.getTablero().add(fichaColocada);
        }
    }

    private void colocarFicha(Ficha fichaColocada)
    {
        int ultimaFichaIndex = mesa.getTablero().size() - 1;
        Scanner res = new Scanner(System.in);
        switch (fichaColocada.getTotalSentidos()) {
            case 4:

                if (mesa.getTablero().get(ultimaFichaIndex).getTotalSentidos() == 4) {
                    Ficha fichaAnterior = mesa.getTablero().get(ultimaFichaIndex);

                    if (fichaAnterior.getSentido().get(0) || fichaAnterior.getSentido().get(2)) {
                        if (fichaAnterior.getLado1() == fichaColocada.getLado1() && fichaAnterior.esMula()) {
                            fichaColocada.rotateRight();
                            mesa.getTablero().add(fichaColocada);
                        } else if (fichaAnterior.getLado1() == fichaColocada.getLado2() && fichaAnterior.esMula()) {
                            fichaColocada.rotateLeft();
                            mesa.getTablero().add(fichaColocada);
                        } else if (fichaAnterior.esMula() && fichaColocada.esMula() && fichaAnterior.getLado1() == fichaColocada.getLado1()) {
                            mesa.getTablero().add(fichaColocada);
                        }
                    } else if (fichaAnterior.getSentido().get(1)) {
                        if (fichaColocada.esMula() && fichaAnterior.getLado2() == fichaColocada.getLado2()) {
                            mesa.getTablero().add(fichaColocada);
                        } else if (fichaColocada.getLado1() == fichaAnterior.getLado2()) {
                            fichaColocada.rotateRight();
                            mesa.getTablero().add(fichaColocada);
                        } else if (fichaColocada.getLado2() == fichaAnterior.getLado2()) {
                            fichaColocada.rotateLeft();
                            mesa.getTablero().add(fichaColocada);
                        } else {
                            System.out.println("Ficha no valida");
                        }
                    } else if (fichaAnterior.getSentido().get(3)) {
                        if (fichaColocada.esMula() && fichaAnterior.getLado1() == fichaColocada.getLado1()) {
                            mesa.getTablero().add(fichaColocada);
                        } else if (fichaColocada.getLado1() == fichaAnterior.getLado1()) {
                            fichaColocada.rotateRight();
                            mesa.getTablero().add(fichaColocada);
                        } else if (fichaColocada.getLado2() == fichaAnterior.getLado1()) {
                            fichaColocada.rotateLeft();
                            mesa.getTablero().add(fichaColocada);
                        } else {
                            System.out.println("Ficha no valida");
                        }
                    }
                } else {
                    FichaTridomino fichaAnterior = (FichaTridomino) mesa.getTablero().get(ultimaFichaIndex);

                    if (fichaAnterior.getSentido().get(0) || fichaAnterior.getSentido().get(2) || fichaAnterior.getSentido().get(4)) {
                        if (fichaAnterior.getSentido().get(0)) {
                            if (fichaAnterior.getLado2() == fichaAnterior.getLado3() && fichaColocada.esMula() && fichaAnterior.getLado2() == fichaColocada.getLado2()) {
                                mesa.getTablero().add(fichaColocada);
                            } else if (fichaAnterior.getLado2() == fichaAnterior.getLado3() && (fichaColocada.getLado1() == fichaAnterior.getLado2() || fichaColocada.getLado2() == fichaAnterior.getLado2())) {
                                if (fichaColocada.getLado1() == fichaAnterior.getLado2()) {
                                    fichaColocada.rotateRight();
                                    mesa.getTablero().add(fichaColocada);
                                } else {
                                    fichaColocada.rotateLeft();
                                    mesa.getTablero().add(fichaColocada);
                                }
                            } else if (fichaAnterior.getLado2() == fichaColocada.getLado2() && fichaAnterior.getLado3() == fichaColocada.getLado1()) {
                                mesa.getTablero().add(fichaColocada);
                            } else if (fichaAnterior.getLado2() == fichaColocada.getLado1() && fichaAnterior.getLado3() == fichaColocada.getLado2()) {
                                fichaColocada.rotateRight();
                                fichaColocada.rotateRight();
                                mesa.getTablero().add(fichaColocada);
                            } else {
                                System.out.println("Ficha no valida");
                            }
                        } else if (fichaAnterior.getSentido().get(2)) {
                            if (fichaAnterior.getLado2() == fichaAnterior.getLado1() && fichaColocada.esMula() && fichaAnterior.getLado2() == fichaColocada.getLado2()) {
                                mesa.getTablero().add(fichaColocada);
                            } else if (fichaAnterior.getLado2() == fichaAnterior.getLado1() && (fichaColocada.getLado1() == fichaAnterior.getLado2() || fichaColocada.getLado2() == fichaAnterior.getLado2())) {
                                if (fichaColocada.getLado1() == fichaAnterior.getLado2()) {
                                    fichaColocada.rotateRight();
                                    mesa.getTablero().add(fichaColocada);
                                } else {
                                    fichaColocada.rotateLeft();
                                    mesa.getTablero().add(fichaColocada);
                                }
                            } else if (fichaAnterior.getLado2() == fichaColocada.getLado2() && fichaAnterior.getLado1() == fichaColocada.getLado1()) {
                                fichaColocada.rotateRight();
                                fichaColocada.rotateRight();
                                mesa.getTablero().add(fichaColocada);
                            } else if (fichaAnterior.getLado2() == fichaColocada.getLado1() && fichaAnterior.getLado1() == fichaColocada.getLado2()) {
                                mesa.getTablero().add(fichaColocada);
                            } else {
                                System.out.println("Ficha no valida");
                            }
                        } else if (fichaAnterior.getSentido().get(4)) {
                            if (fichaAnterior.getLado1() == fichaAnterior.getLado3() && fichaColocada.esMula() && fichaAnterior.getLado3() == fichaColocada.getLado2()) {
                                mesa.getTablero().add(fichaColocada);
                            } else if (fichaAnterior.getLado1() == fichaAnterior.getLado3() && (fichaColocada.getLado1() == fichaAnterior.getLado3() || fichaColocada.getLado2() == fichaAnterior.getLado3())) {
                                if (fichaColocada.getLado1() == fichaAnterior.getLado3()) {
                                    fichaColocada.rotateRight();
                                    mesa.getTablero().add(fichaColocada);
                                } else {
                                    fichaColocada.rotateLeft();
                                    mesa.getTablero().add(fichaColocada);
                                }
                            } else if (fichaAnterior.getLado1() == fichaColocada.getLado1() && fichaAnterior.getLado3() == fichaColocada.getLado2()) {
                                mesa.getTablero().add(fichaColocada);
                            } else if (fichaAnterior.getLado1() == fichaColocada.getLado2() && fichaAnterior.getLado3() == fichaColocada.getLado1()) {
                                fichaColocada.rotateRight();
                                fichaColocada.rotateRight();
                                mesa.getTablero().add(fichaColocada);
                            } else {
                                System.out.println("Ficha no valida");
                            }
                        } else {
                            System.out.println("Ficha no valida");
                        }
                    } else {
                        if (fichaAnterior.getSentido().get(1)) {
                            if (fichaColocada.esMula() && fichaColocada.getLado2() == fichaAnterior.getLado2()) {
                                mesa.getTablero().add(fichaColocada);
                            } else if (fichaColocada.getLado1() == fichaAnterior.getLado2()) {
                                fichaColocada.rotateRight();
                                mesa.getTablero().add(fichaColocada);
                            } else if (fichaColocada.getLado2() == fichaAnterior.getLado2()) {
                                fichaColocada.rotateLeft();
                                mesa.getTablero().add(fichaColocada);
                            } else {
                                System.out.println("Ficha no valida");
                            }
                        } else if (fichaAnterior.getSentido().get(3)) {
                            if (fichaColocada.esMula() && fichaColocada.getLado2() == fichaAnterior.getLado1()) {
                                mesa.getTablero().add(fichaColocada);
                            } else if (fichaColocada.getLado1() == fichaAnterior.getLado1()) {
                                fichaColocada.rotateRight();
                                mesa.getTablero().add(fichaColocada);
                            } else if (fichaColocada.getLado2() == fichaAnterior.getLado1()) {
                                fichaColocada.rotateLeft();
                                mesa.getTablero().add(fichaColocada);
                            } else {
                                System.out.println("Ficha no valida");
                            }
                        } else if (fichaAnterior.getSentido().get(5)) {
                            if (fichaColocada.esMula() && fichaColocada.getLado2() == fichaAnterior.getLado3()) {
                                mesa.getTablero().add(fichaColocada);
                            } else if (fichaColocada.getLado1() == fichaAnterior.getLado3()) {
                                fichaColocada.rotateRight();
                                mesa.getTablero().add(fichaColocada);
                            } else if (fichaColocada.getLado2() == fichaAnterior.getLado3()) {
                                fichaColocada.rotateLeft();
                                mesa.getTablero().add(fichaColocada);
                            } else {
                                System.out.println("Ficha no valida");
                            }
                        } else {
                            System.out.println("Ficha no valida");
                        }
                    }
                }
                break;
            case 6:
                FichaTridomino fichaColocadaN = (FichaTridomino) fichaColocada;
                if (mesa.getTablero().get(ultimaFichaIndex).getTotalSentidos() == 4) {
                    Ficha fichaAnterior = mesa.getTablero().get(ultimaFichaIndex);

                    if (fichaAnterior.getSentido().getFirst() || fichaAnterior.getSentido().get(2)) {
                        if (fichaAnterior.esMula()) {
                            if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaAnterior.getLado2() == fichaColocadaN.getLado3()) {
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado3() && fichaAnterior.getLado2() == fichaColocadaN.getLado3()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado1() == fichaColocadaN.getLado2() && fichaAnterior.getLado2() == fichaColocadaN.getLado2()) {
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado1() == fichaAnterior.lado1) {
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado3() == fichaAnterior.lado1) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado2() == fichaAnterior.lado1) {
                                fichaColocadaN.rotateLeft();
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else {
                                System.out.println("Ficha no valida");
                            }
                        } else if (fichaAnterior.getSentido().getFirst()) {
                            if (fichaAnterior.getLado1() == fichaColocadaN.getLado3() && fichaAnterior.getLado2() == fichaColocadaN.getLado1()) {
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado2() && fichaAnterior.getLado2() == fichaColocadaN.getLado3()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado1() && fichaAnterior.getLado2() == fichaColocadaN.getLado2()) {
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else {
                                System.out.println("Ficha no valida");
                            }
                        } else if (fichaAnterior.getSentido().get(2)) {
                            if (fichaAnterior.getLado2() == fichaColocadaN.getLado3() && fichaAnterior.getLado1() == fichaColocadaN.getLado1()) {
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado2() && fichaAnterior.getLado1() == fichaColocadaN.getLado3()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado1() && fichaAnterior.getLado1() == fichaColocadaN.getLado2()) {
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else {
                                System.out.println("Ficha no valida");
                            }
                        }
                    } else {
                        if (fichaAnterior.getSentido().get(1)) {
                            if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaColocadaN.getLado1() == fichaAnterior.getLado2()) {
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado3() && fichaColocadaN.getLado3() == fichaAnterior.getLado2()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado1() && fichaColocadaN.getLado2() == fichaAnterior.getLado2()) {
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado1()) {
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado2()) {
                                fichaColocadaN.rotateLeft();
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado3()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else {
                                System.out.println("Ficha no valida");
                            }

                        } else {
                            if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaColocadaN.getLado1() == fichaAnterior.getLado1()) {
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado3() && fichaColocadaN.getLado3() == fichaAnterior.getLado1()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado1() && fichaColocadaN.getLado2() == fichaAnterior.getLado1()) {
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado1()) {
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado2()) {
                                fichaColocadaN.rotateLeft();
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado3()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else {
                                System.out.println("Ficha no valida");
                            }
                        }
                    }
                } else {
                    FichaTridomino fichaAnterior = (FichaTridomino) mesa.getTablero().get(ultimaFichaIndex);
                    if (fichaAnterior.getSentido().getFirst() || fichaAnterior.getSentido().get(2) || fichaAnterior.getSentido().get(4)) {
                        if (fichaAnterior.getSentido().getFirst()) {
                            if (fichaAnterior.getLado3() == fichaAnterior.getLado2()) {
                                if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaAnterior.getLado3() == fichaColocadaN.getLado3()) {
                                    fichaColocadaN.rotateRight();
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado2() && fichaAnterior.getLado3() == fichaColocadaN.getLado3()) {
                                    fichaColocadaN.rotateRight();
                                    fichaColocadaN.rotateRight();
                                    fichaColocadaN.rotateRight();
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if ( fichaColocadaN.getLado1() == fichaColocadaN.getLado2() && fichaAnterior.getLado3() == fichaColocadaN.getLado2()) {
                                    fichaColocadaN.rotateLeft();
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado1() == fichaAnterior.getLado3()) {
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado3() == fichaAnterior.getLado3()) {
                                    fichaColocadaN.rotateRight();
                                    fichaColocadaN.rotateRight();
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado2() == fichaAnterior.getLado3()) {
                                    fichaColocadaN.rotateLeft();
                                    fichaColocadaN.rotateLeft();
                                    mesa.getTablero().add(fichaColocadaN);
                                } else {
                                    System.out.println("Ficha no valida");
                                }
                            } else if (fichaAnterior.getLado3() == fichaColocadaN.getLado3() && fichaAnterior.getLado2() == fichaColocadaN.getLado1()) {
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado3() == fichaColocadaN.getLado2() && fichaAnterior.getLado2() == fichaColocadaN.getLado3()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado3() == fichaColocadaN.getLado1() && fichaAnterior.getLado2() == fichaColocadaN.getLado2()) {
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else {
                                System.out.println("Ficha no valida");
                            }
                        } else if (fichaAnterior.getSentido().get(2)) {
                            if (fichaAnterior.getLado1() == fichaAnterior.getLado2()) {
                                if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaAnterior.getLado2() == fichaColocadaN.getLado3()) {
                                    fichaColocadaN.rotateRight();
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado2() && fichaAnterior.getLado2() == fichaColocadaN.getLado3()) {
                                    fichaColocadaN.rotateRight();
                                    fichaColocadaN.rotateRight();
                                    fichaColocadaN.rotateRight();
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado1() == fichaColocadaN.getLado2() && fichaAnterior.getLado2() == fichaColocadaN.getLado2()) {
                                    fichaColocadaN.rotateLeft();
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado1() == fichaAnterior.getLado2()) {
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado3() == fichaAnterior.getLado2()) {
                                    fichaColocadaN.rotateRight();
                                    fichaColocadaN.rotateRight();
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado2() == fichaAnterior.getLado2()) {
                                    fichaColocadaN.rotateLeft();
                                    fichaColocadaN.rotateLeft();
                                    mesa.getTablero().add(fichaColocadaN);
                                } else {
                                    System.out.println("Ficha no valida");
                                }
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado3() && fichaAnterior.getLado1() == fichaColocadaN.getLado1()) {
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado2() && fichaAnterior.getLado1() == fichaColocadaN.getLado3()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado1() && fichaAnterior.getLado1() == fichaColocadaN.getLado2()) {
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else {
                                System.out.println("Ficha no valida");
                            }
                        } else {
                            if (fichaAnterior.getLado1() == fichaAnterior.getLado3()) {
                                if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaAnterior.getLado1() == fichaColocadaN.getLado3()) {
                                    fichaColocadaN.rotateRight();
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado2() && fichaAnterior.getLado1() == fichaColocadaN.getLado3()) {
                                    fichaColocadaN.rotateRight();
                                    fichaColocadaN.rotateRight();
                                    fichaColocadaN.rotateRight();
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado1() == fichaColocadaN.getLado2() && fichaAnterior.getLado1() == fichaColocadaN.getLado2()) {
                                    fichaColocadaN.rotateLeft();
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado1() == fichaAnterior.getLado1()) {
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado3() == fichaAnterior.getLado1()) {
                                    fichaColocadaN.rotateRight();
                                    fichaColocadaN.rotateRight();
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado2() == fichaAnterior.getLado1()) {
                                    fichaColocadaN.rotateLeft();
                                    fichaColocadaN.rotateLeft();
                                    mesa.getTablero().add(fichaColocadaN);
                                } else {
                                    System.out.println("Ficha no valida");
                                }
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado3() && fichaAnterior.getLado3() == fichaColocadaN.getLado1()) {
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado2() && fichaAnterior.getLado3() == fichaColocadaN.getLado3()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado1() && fichaAnterior.getLado3() == fichaColocadaN.getLado2()) {
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else {
                                System.out.println("Ficha no valida");
                            }
                        }
                    } else {
                        if (fichaAnterior.getSentido().get(1)) {
                            if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaColocadaN.getLado3() == fichaAnterior.getLado2()) {
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado3() && fichaColocadaN.getLado2() == fichaAnterior.getLado2()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado1() == fichaColocadaN.getLado2() && fichaColocadaN.getLado1() == fichaAnterior.getLado2()) {
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado1()) {
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado3()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado2()) {
                                fichaColocadaN.rotateLeft();
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else {
                                System.out.println("Ficha no valida");
                            }
                        } else if (fichaAnterior.getSentido().get(3)) {
                            if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaColocadaN.getLado3() == fichaAnterior.getLado1()) {
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado3() && fichaColocadaN.getLado2() == fichaAnterior.getLado1()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado1() == fichaColocadaN.getLado2() && fichaColocadaN.getLado1() == fichaAnterior.getLado1()) {
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado1()) {
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado3()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado2()) {
                                fichaColocadaN.rotateLeft();
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else {
                                System.out.println("Ficha no valida");
                            }
                        } else {
                            if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaColocadaN.getLado3() == fichaAnterior.getLado3()) {
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado3() && fichaColocadaN.getLado2() == fichaAnterior.getLado3()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado1() == fichaColocadaN.getLado2() && fichaColocadaN.getLado1() == fichaAnterior.getLado3()) {
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado3() == fichaColocadaN.getLado1()) {
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado3() == fichaColocadaN.getLado3()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado3() == fichaColocadaN.getLado2()) {
                                fichaColocadaN.rotateLeft();
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else {
                                System.out.println("Ficha no valida");
                            }
                        }
                    }
                }
                break;
        }
    }

    private boolean verificarFicha(Ficha fichaColocada)
    {
        int ultimaFichaIndex = mesa.getTablero().size() - 1;

        switch (fichaColocada.getTotalSentidos()) {
            case 4:

                if (mesa.getTablero().get(ultimaFichaIndex).getTotalSentidos() == 4) {
                    Ficha fichaAnterior = mesa.getTablero().get(ultimaFichaIndex);

                    if (fichaAnterior.getSentido().get(0) || fichaAnterior.getSentido().get(2)) {
                        if (fichaAnterior.getLado1() == fichaColocada.getLado1() && fichaAnterior.esMula()) {
                            return true;
                        } else if (fichaAnterior.getLado1() == fichaColocada.getLado2() && fichaAnterior.esMula()) {
                            return true;
                        } else if (fichaAnterior.esMula() && fichaColocada.esMula() && fichaAnterior.getLado1() == fichaColocada.getLado1()) {
                            return true;
                        }
                    } else if (fichaAnterior.getSentido().get(1)) {
                        if (fichaColocada.esMula() && fichaAnterior.getLado2() == fichaColocada.getLado2()) {
                            return true;
                        } else if (fichaColocada.getLado1() == fichaAnterior.getLado2()) {
                            return true;
                        } else if (fichaColocada.getLado2() == fichaAnterior.getLado2()) {
                            return true;
                        }
                    } else if (fichaAnterior.getSentido().get(3)) {
                        if (fichaColocada.esMula() && fichaAnterior.getLado1() == fichaColocada.getLado1()) {
                            return true;
                        } else if (fichaColocada.getLado1() == fichaAnterior.getLado1()) {
                            return true;
                        } else if (fichaColocada.getLado2() == fichaAnterior.getLado1()) {
                            return true;
                        }
                    }
                } else {
                    FichaTridomino fichaAnterior = (FichaTridomino) mesa.getTablero().get(ultimaFichaIndex);

                    if (fichaAnterior.getSentido().get(0) || fichaAnterior.getSentido().get(2) || fichaAnterior.getSentido().get(4)) {
                        if (fichaAnterior.getSentido().get(0)) {
                            if (fichaAnterior.getLado2() == fichaAnterior.getLado3() && fichaColocada.esMula() && fichaAnterior.getLado2() == fichaColocada.getLado2()) {
                                return true;
                            } else if (fichaAnterior.getLado2() == fichaAnterior.getLado3() && (fichaColocada.getLado1() == fichaAnterior.getLado2() || fichaColocada.getLado2() == fichaAnterior.getLado2())) {
                                if (fichaColocada.getLado1() == fichaAnterior.getLado2()) {
                                    return true;
                                } else {
                                    return true;
                                }
                            } else if (fichaAnterior.getLado2() == fichaColocada.getLado2() && fichaAnterior.getLado3() == fichaColocada.getLado1()) {
                                return true;
                            } else if (fichaAnterior.getLado2() == fichaColocada.getLado1() && fichaAnterior.getLado3() == fichaColocada.getLado2()) {
                                return true;
                            }
                        } else if (fichaAnterior.getSentido().get(2)) {
                            if (fichaAnterior.getLado2() == fichaAnterior.getLado1() && fichaColocada.esMula() && fichaAnterior.getLado2() == fichaColocada.getLado2()) {
                                return true;
                            } else if (fichaAnterior.getLado2() == fichaAnterior.getLado1() && (fichaColocada.getLado1() == fichaAnterior.getLado2() || fichaColocada.getLado2() == fichaAnterior.getLado2())) {
                                if (fichaColocada.getLado1() == fichaAnterior.getLado2()) {
                                    return true;
                                } else {
                                    return true;
                                }
                            } else if (fichaAnterior.getLado2() == fichaColocada.getLado2() && fichaAnterior.getLado1() == fichaColocada.getLado1()) {
                                return true;
                            } else if (fichaAnterior.getLado2() == fichaColocada.getLado1() && fichaAnterior.getLado1() == fichaColocada.getLado2()) {
                                return true;
                            }
                        } else if (fichaAnterior.getSentido().get(4)) {
                            if (fichaAnterior.getLado1() == fichaAnterior.getLado3() && fichaColocada.esMula() && fichaAnterior.getLado3() == fichaColocada.getLado2()) {
                                return true;
                            } else if (fichaAnterior.getLado1() == fichaAnterior.getLado3() && (fichaColocada.getLado1() == fichaAnterior.getLado3() || fichaColocada.getLado2() == fichaAnterior.getLado3())) {
                                if (fichaColocada.getLado1() == fichaAnterior.getLado3()) {
                                    return true;
                                } else {
                                    return true;
                                }
                            } else if (fichaAnterior.getLado1() == fichaColocada.getLado1() && fichaAnterior.getLado3() == fichaColocada.getLado2()) {
                                return true;
                            } else if (fichaAnterior.getLado1() == fichaColocada.getLado2() && fichaAnterior.getLado3() == fichaColocada.getLado1()) {
                                return true;
                            }
                        }
                    } else {
                        if (fichaAnterior.getSentido().get(1)) {
                            if (fichaColocada.esMula() && fichaColocada.getLado2() == fichaAnterior.getLado2()) {
                                return true;
                            } else if (fichaColocada.getLado1() == fichaAnterior.getLado2()) {
                                return true;
                            } else if (fichaColocada.getLado2() == fichaAnterior.getLado2()) {
                                return true;
                            }
                        } else if (fichaAnterior.getSentido().get(3)) {
                            if (fichaColocada.esMula() && fichaColocada.getLado2() == fichaAnterior.getLado1()) {
                                return true;
                            } else if (fichaColocada.getLado1() == fichaAnterior.getLado1()) {
                                return true;
                            } else if (fichaColocada.getLado2() == fichaAnterior.getLado1()) {
                                return true;
                            }
                        } else if (fichaAnterior.getSentido().get(5)) {
                            if (fichaColocada.esMula() && fichaColocada.getLado2() == fichaAnterior.getLado3()) {
                                return true;
                            } else if (fichaColocada.getLado1() == fichaAnterior.getLado3()) {
                                return true;
                            } else if (fichaColocada.getLado2() == fichaAnterior.getLado3()) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 6:
                FichaTridomino fichaColocadaN = (FichaTridomino) fichaColocada;
                if (mesa.getTablero().get(ultimaFichaIndex).getTotalSentidos() == 4) {
                    Ficha fichaAnterior = mesa.getTablero().get(ultimaFichaIndex);

                    if (fichaAnterior.getSentido().getFirst() || fichaAnterior.getSentido().get(2)) {
                        if (fichaAnterior.esMula()) {
                            if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaAnterior.getLado2() == fichaColocadaN.getLado3()) {
                                return true;
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado3() && fichaAnterior.getLado2() == fichaColocadaN.getLado3()) {
                                return true;
                            } else if (fichaColocadaN.getLado1() == fichaColocadaN.getLado2() && fichaAnterior.getLado2() == fichaColocadaN.getLado2()) {
                                return true;
                            } else if (fichaColocadaN.getLado1() == fichaAnterior.lado1) {
                                return true;
                            } else if (fichaColocadaN.getLado3() == fichaAnterior.lado1) {
                                return true;
                            } else if (fichaColocadaN.getLado2() == fichaAnterior.lado1) {
                                return true;
                            }
                        } else if (fichaAnterior.getSentido().getFirst()) {
                            if (fichaAnterior.getLado1() == fichaColocadaN.getLado3() && fichaAnterior.getLado2() == fichaColocadaN.getLado1()) {
                                return true;
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado2() && fichaAnterior.getLado2() == fichaColocadaN.getLado3()) {
                                return true;
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado1() && fichaAnterior.getLado2() == fichaColocadaN.getLado2()) {
                                return true;
                            }
                        } else if (fichaAnterior.getSentido().get(2)) {
                            if (fichaAnterior.getLado2() == fichaColocadaN.getLado3() && fichaAnterior.getLado1() == fichaColocadaN.getLado1()) {
                                return true;
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado2() && fichaAnterior.getLado1() == fichaColocadaN.getLado3()) {
                                return true;
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado1() && fichaAnterior.getLado1() == fichaColocadaN.getLado2()) {
                                return true;
                            }
                        }
                    } else {
                        if (fichaAnterior.getSentido().get(1)) {
                            if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaColocadaN.getLado1() == fichaAnterior.getLado2()) {
                                return true;
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado3() && fichaColocadaN.getLado3() == fichaAnterior.getLado2()) {
                                return true;
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado1() && fichaColocadaN.getLado2() == fichaAnterior.getLado2()) {
                                return true;
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado1()) {
                                return true;
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado2()) {
                                return true;
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado3()) {
                                return true;
                            }
                        } else {
                            if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaColocadaN.getLado1() == fichaAnterior.getLado1()) {
                                return true;
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado3() && fichaColocadaN.getLado3() == fichaAnterior.getLado1()) {
                                return true;
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado1() && fichaColocadaN.getLado2() == fichaAnterior.getLado1()) {
                                return true;
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado1()) {
                                return true;
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado2()) {
                                return true;
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado3()) {
                                return true;
                            }
                        }
                    }
                } else {
                    FichaTridomino fichaAnterior = (FichaTridomino) mesa.getTablero().get(ultimaFichaIndex);
                    if (fichaAnterior.getSentido().getFirst() || fichaAnterior.getSentido().get(2) || fichaAnterior.getSentido().get(4)) {
                        if (fichaAnterior.getSentido().getFirst()) {
                            if (fichaAnterior.getLado3() == fichaAnterior.getLado2()) {
                                if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaAnterior.getLado3() == fichaColocadaN.getLado3()) {
                                    return true;
                                } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado2() && fichaAnterior.getLado3() == fichaColocadaN.getLado3()) {
                                    return true;
                                } else if ( fichaColocadaN.getLado1() == fichaColocadaN.getLado2() && fichaAnterior.getLado3() == fichaColocadaN.getLado2()) {
                                    return true;
                                } else if (fichaColocadaN.getLado1() == fichaAnterior.getLado3()) {
                                    return true;
                                } else if (fichaColocadaN.getLado3() == fichaAnterior.getLado3()) {
                                    return true;
                                } else if (fichaColocadaN.getLado2() == fichaAnterior.getLado3()) {
                                    return true;
                                }
                            } else if (fichaAnterior.getLado3() == fichaColocadaN.getLado3() && fichaAnterior.getLado2() == fichaColocadaN.getLado1()) {
                                return true;
                            } else if (fichaAnterior.getLado3() == fichaColocadaN.getLado2() && fichaAnterior.getLado2() == fichaColocadaN.getLado3()) {
                                return true;
                            } else if (fichaAnterior.getLado3() == fichaColocadaN.getLado1() && fichaAnterior.getLado2() == fichaColocadaN.getLado2()) {
                                return true;
                            }
                        } else if (fichaAnterior.getSentido().get(2)) {
                            if (fichaAnterior.getLado1() == fichaAnterior.getLado2()) {
                                if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaAnterior.getLado2() == fichaColocadaN.getLado3()) {
                                    return true;
                                } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado2() && fichaAnterior.getLado2() == fichaColocadaN.getLado3()) {
                                    return true;
                                } else if (fichaColocadaN.getLado1() == fichaColocadaN.getLado2() && fichaAnterior.getLado2() == fichaColocadaN.getLado2()) {
                                    return true;
                                } else if (fichaColocadaN.getLado1() == fichaAnterior.getLado2()) {
                                    return true;
                                } else if (fichaColocadaN.getLado3() == fichaAnterior.getLado2()) {
                                    return true;
                                } else if (fichaColocadaN.getLado2() == fichaAnterior.getLado2()) {
                                    return true;
                                }
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado3() && fichaAnterior.getLado1() == fichaColocadaN.getLado1()) {
                                return true;
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado2() && fichaAnterior.getLado1() == fichaColocadaN.getLado3()) {
                                return true;
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado1() && fichaAnterior.getLado1() == fichaColocadaN.getLado2()) {
                                return true;
                            }
                        } else {
                            if (fichaAnterior.getLado1() == fichaAnterior.getLado3()) {
                                if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaAnterior.getLado1() == fichaColocadaN.getLado3()) {
                                    return true;
                                } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado2() && fichaAnterior.getLado1() == fichaColocadaN.getLado3()) {
                                    return true;
                                } else if (fichaColocadaN.getLado1() == fichaColocadaN.getLado2() && fichaAnterior.getLado1() == fichaColocadaN.getLado2()) {
                                    return true;
                                } else if (fichaColocadaN.getLado1() == fichaAnterior.getLado1()) {
                                    return true;
                                } else if (fichaColocadaN.getLado3() == fichaAnterior.getLado1()) {
                                    return true;
                                } else if (fichaColocadaN.getLado2() == fichaAnterior.getLado1()) {
                                    return true;
                                }
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado3() && fichaAnterior.getLado3() == fichaColocadaN.getLado1()) {
                                return true;
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado2() && fichaAnterior.getLado3() == fichaColocadaN.getLado3()) {
                                return true;
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado1() && fichaAnterior.getLado3() == fichaColocadaN.getLado2()) {
                                return true;
                            }
                        }
                    } else {
                        if (fichaAnterior.getSentido().get(1)) {
                            if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaColocadaN.getLado3() == fichaAnterior.getLado2()) {
                                return true;
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado3() && fichaColocadaN.getLado2() == fichaAnterior.getLado2()) {
                                return true;
                            } else if (fichaColocadaN.getLado1() == fichaColocadaN.getLado2() && fichaColocadaN.getLado1() == fichaAnterior.getLado2()) {
                                return true;
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado1()) {
                                return true;
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado3()) {
                                return true;
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado2()) {
                                return true;
                            }
                        } else if (fichaAnterior.getSentido().get(3)) {
                            if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaColocadaN.getLado3() == fichaAnterior.getLado1()) {
                                return true;
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado3() && fichaColocadaN.getLado2() == fichaAnterior.getLado1()) {
                                return true;
                            } else if (fichaColocadaN.getLado1() == fichaColocadaN.getLado2() && fichaColocadaN.getLado1() == fichaAnterior.getLado1()) {
                                return true;
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado1()) {
                                return true;
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado3()) {
                                return true;
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado2()) {
                                return true;
                            }
                        } else {
                            if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaColocadaN.getLado3() == fichaAnterior.getLado3()) {
                                return true;
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado3() && fichaColocadaN.getLado2() == fichaAnterior.getLado3()) {
                                return true;
                            } else if (fichaColocadaN.getLado1() == fichaColocadaN.getLado2() && fichaColocadaN.getLado1() == fichaAnterior.getLado3()) {
                                return true;
                            } else if (fichaAnterior.getLado3() == fichaColocadaN.getLado1()) {
                                return true;
                            } else if (fichaAnterior.getLado3() == fichaColocadaN.getLado3()) {
                                return true;
                            } else if (fichaAnterior.getLado3() == fichaColocadaN.getLado2()) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return false;
    }

    /**
     * Método para sumar puntos
     */
    private void sumarPuntos(Ficha fichaColocada, Jugador jugadorActual)
    {
        if (fichaColocada.getTotalSentidos() == 4) {
            jugadorActual.acumularPuntuacion(fichaColocada.sumaLados());
            System.out.println("Has ganado " + fichaColocada.sumaLados());
        } else {
            FichaTridomino fichaColocadaN = (FichaTridomino) fichaColocada;
            jugadorActual.acumularPuntuacion(fichaColocadaN.sumaLados());
            System.out.println("Has ganado " + fichaColocadaN.sumaLados());
        }
    }



    /**
     * Método para determinar si ya acabó el juego
     */
    private boolean manosVacias()
    {
        for (Jugador jugador : jugadores) {
            if (jugador.getMano().isEmpty()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Método para determinar si ninguna ficha es valida
     */
    private boolean fichasValidas()
    {
        for (Jugador jugador : jugadores) {
            for (Ficha ficha : jugador.getMano()) {
                if (verificarFicha(ficha)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Método para determinar si ninguna ficha es valida
     */
    private boolean fichasValidasJugador(Jugador jugadorActual)
    {
        for (Ficha fichas : jugadorActual.getMano()) {
            if (verificarFicha(fichas)) {
                return true;
            }
        }
        return false;
    }

    private boolean fichasValidasPozo()
    {
        if (!mesa.getPozo().isEmpty()) {
            for (Ficha ficha : mesa.getPozo()) {
                if (verificarFicha(ficha)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Trabaja en conjunto con los demás métodos verificadores, método para saber si ya cabo
     * el juego
     * @return
     */
    private boolean yaAcaboJuego()
    {
        if (manosVacias()) {
            System.out.println("Hay manos vacias");
            return true;
        } else if (!fichasValidas()) {
            if (!fichasValidasPozo()) {
                System.out.println("====Pozo====");
                mesa.visualizarPozo();
                return true;
            }
        }
        return false;
    }

    /**
     * Método para determinar el ganador
     */
    private Jugador determinarGanador()
    {
        for (Jugador jugador : jugadores) {
            if (jugador.getMano().isEmpty()) {
                return jugador;
            }
        }
        return jugadores.getFirst().getPuntuacion() > jugadores.get(1).getPuntuacion() ? jugadores.getFirst() : jugadores.get(1);
    }

    /**
     * Método que se utiliza para jugar el juego
     */
    public void jugar()
    {
        boolean noTermino = true;
        int turnoActual = 0;
        mezclarFichas();
        hacerManos();
        ArrayList<Ficha> primerasFichas = new ArrayList<>(2);
        Scanner sc = new Scanner(System.in);
        Jugador ganador = null;

        while (noTermino) {
            Jugador jugadorActual = jugadores.get(turnoActual);
            System.out.println("===Turno de " + jugadorActual.getNombre() + "====");
            System.out.println("Puntos acumulados: " + jugadorActual.getPuntuacion());
            if (!mesa.getTablero().isEmpty()) {
                System.out.println("====Tablero====");
                mesa.visualizarTablero();
                System.out.println("===============");
            }

            if (mesa.getTablero().isEmpty() && jugadorActual.getMano().size() == 10) {
                System.out.println("===Mano del jugador===");
                jugadorActual.visualizarMano();
                System.out.println("Ingresa tu primera ficha(indice)");
                primerasFichas.add(jugadorActual.getMano().remove(sc.nextInt()));
                if (primerasFichas.size() == 2) {
                    if (primerasFichas.getFirst().sumaLados() > primerasFichas.get(1).sumaLados()) {
                        turnoActual = 1;
                    } else {
                        turnoActual = 0;
                    }
                }
            } else {

                if (mesa.getTablero().isEmpty()) {
                    colocarPrimeraFicha(primerasFichas.get(0), primerasFichas.get(1));
                } else {
                    if (fichasValidasJugador(jugadorActual)) {
                        jugadorActual.visualizarMano();
                        int index;
                        do {
                            System.out.println("Ingresa una ficha");
                            index = sc.nextInt();
                            if (verificarFicha(jugadorActual.getMano().get(index))) {
                                sumarPuntos(jugadorActual.getMano().get(index), jugadorActual);
                                colocarFicha(jugadorActual.getMano().remove(index));
                                //PRIMER CASO DE TERMINACIÓN DE JUEGO
                                if (yaAcaboJuego()) {
                                    ganador = determinarGanador();
                                    noTermino = false;
                                }
                                break;
                            } else {
                                System.out.println("Debes ingresar una ficha valida");
                            }
                        } while (!verificarFicha(jugadorActual.getMano().get(index)));

                    } else {
                        int index;
                        if (!mesa.getPozo().isEmpty()) {
                            jugadorActual.visualizarMano();
                            System.out.println("No tienes fichas validas");
                            System.out.println("Presiona enter para obtener dos fichas");
                            sc.nextLine();
                            sc.nextLine();
                            for (int i = 0; i < 2; ++i) {
                                jugadorActual.getMano().add(mesa.getPozo().removeFirst());
                            }
                            if (verificarFicha(jugadorActual.getMano().getLast()) || verificarFicha(jugadorActual.getMano().get(jugadorActual.getMano().size() - 2))) {
                                jugadorActual.visualizarMano();
                                System.out.println("Ingresa una ficha");

                                do {
                                    index = sc.nextInt();
                                    if (verificarFicha(jugadorActual.getMano().get(index))) {
                                        sumarPuntos(jugadorActual.getMano().get(index), jugadorActual);
                                        colocarFicha(jugadorActual.getMano().remove(index));
                                        if (yaAcaboJuego()) {
                                            ganador = determinarGanador();
                                            noTermino = false;
                                        }
                                        break;
                                    } else {
                                        System.out.println("Debes ingresa una ficha valida");
                                    }
                                } while(!verificarFicha(jugadorActual.getMano().get(index)));

                            } else {
                                System.out.println("Las nuevas fichas añadidas no son validas, presione enter para seguir");
                                sc.nextLine();
                                sc.nextLine();
                            }

                        } else {

                        }
                    }
                }
            }
            turnoActual = (turnoActual + 1) % jugadores.size();
        }

        System.out.println("===Tablero final===");
        mesa.visualizarTablero();
        System.out.println("El " + ganador.getNombre() + " ha ganado el juego!!!");
        System.out.println("Puntos finales");
        for (Jugador jugador : jugadores) {
            System.out.println(jugador.getNombre() + ": " + jugador.getPuntuacion());
        }



    }
}
