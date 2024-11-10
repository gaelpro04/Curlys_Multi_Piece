import java.util.ArrayList;
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
    public void mezclarFichas()
    {
        Collections.shuffle(tridomino.getTridomino());
        Collections.shuffle(domino.getDomino());
    }

    /**
     * Método para elaborar las manos de los jugadores
     */
    public void hacerManos()
    {
        int totalFichas = 11;
        int fichasTriples = 0;
        int fichasDobles = 0;
        Scanner sc = new Scanner(System.in);


        for (Jugador jugador : jugadores) {
            while (totalFichas > 10) {
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
        }


    }

    public void colocarPrimeraFicha(Ficha fichaColocadaJ1, Ficha fichaColocadaJ2)
    {
        Scanner sc = new Scanner(System.in);
        Ficha fichaColocada;
        if (fichaColocadaJ1.sumaLados() > fichaColocadaJ2.sumaLados()) {
            fichaColocada = fichaColocadaJ1;
        } else {
            fichaColocada = fichaColocadaJ2;
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
                    System.out.println((i+1) + ".==========");
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

    public void colocarFicha(Ficha fichaColocada)
    {
        int ultimaFichaIndex = mesa.getTablero().size() - 1;

        switch (fichaColocada.getTotalSentidos()) {
            case 4:

                if (mesa.getTablero().get(ultimaFichaIndex).getTotalSentidos() == 4) {
                    Ficha fichaAnterior = mesa.getTablero().get(ultimaFichaIndex);

                    if (fichaAnterior.getSentido().get(0) || fichaAnterior.getSentido().get(2)) {
                        if (fichaAnterior.getLado1() == fichaColocada.getLado1() && fichaColocada.esMula()) {
                            mesa.getTablero().add(fichaColocada);
                        } else {
                            System.out.println("Ficha no valida");
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
                            } else if (fichaAnterior.getLado2() == fichaAnterior.getLado3() && fichaColocada.getLado1() == fichaAnterior.getLado2() || fichaColocada.getLado2() == fichaAnterior.getLado2()) {
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
                            } else if (fichaAnterior.getLado2() == fichaAnterior.getLado1() && fichaColocada.getLado1() == fichaAnterior.getLado2() || fichaColocada.getLado2() == fichaAnterior.getLado2()) {
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
                            } else if (fichaAnterior.getLado1() == fichaAnterior.getLado3() && fichaColocada.getLado1() == fichaAnterior.getLado3() || fichaColocada.getLado2() == fichaAnterior.getLado3()) {
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

                    if (fichaAnterior.getSentido().getFirst() && fichaAnterior.getSentido().get(2)) {
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
                            if (fichaAnterior.getLado2() == fichaColocadaN.getLado1()) {
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaColocadaN.getLado1() == fichaAnterior.getLado2()) {
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado3()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado3() && fichaColocadaN.getLado3() == fichaAnterior.getLado2()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado2()) {
                                fichaColocadaN.rotateLeft();
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado1() && fichaColocadaN.getLado2() == fichaAnterior.getLado2()) {
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else {
                                System.out.println("Ficha no valida");
                            }
                        } else {
                            if (fichaAnterior.getLado1() == fichaColocadaN.getLado1()) {
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaColocadaN.getLado1() == fichaAnterior.getLado1()) {
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado3()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado3() && fichaColocadaN.getLado3() == fichaAnterior.getLado1()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado2()) {
                                fichaColocadaN.rotateLeft();
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado1() && fichaColocadaN.getLado2() == fichaAnterior.getLado1()) {
                                fichaColocadaN.rotateLeft();
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
                                if (fichaColocadaN.getLado1() == fichaAnterior.getLado3()) {
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado3() == fichaAnterior.getLado3()) {
                                    fichaColocadaN.rotateRight();
                                    fichaColocadaN.rotateRight();
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado2() == fichaAnterior.getLado3()) {
                                    fichaColocadaN.rotateLeft();
                                    fichaColocadaN.rotateLeft();
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaAnterior.getLado3() == fichaColocadaN.getLado3()) {
                                    fichaColocadaN.rotateRight();
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado2() && fichaAnterior.getLado3() == fichaColocadaN.getLado3()) {
                                    fichaColocadaN.rotateRight();
                                    fichaColocadaN.rotateRight();
                                    fichaColocadaN.rotateRight();
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado1() == fichaColocadaN.getLado2() && fichaAnterior.getLado3() == fichaColocadaN.getLado2()) {
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
                                if (fichaColocadaN.getLado1() == fichaAnterior.getLado2()) {
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado3() == fichaAnterior.getLado2()) {
                                    fichaColocadaN.rotateRight();
                                    fichaColocadaN.rotateRight();
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado2() == fichaAnterior.getLado2()) {
                                    fichaColocadaN.rotateLeft();
                                    fichaColocadaN.rotateLeft();
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaAnterior.getLado2() == fichaColocadaN.getLado3()) {
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
                                if (fichaColocadaN.getLado1() == fichaAnterior.getLado1()) {
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado3() == fichaAnterior.getLado1()) {
                                    fichaColocadaN.rotateRight();
                                    fichaColocadaN.rotateRight();
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado2() == fichaAnterior.getLado1()) {
                                    fichaColocadaN.rotateLeft();
                                    fichaColocadaN.rotateLeft();
                                    mesa.getTablero().add(fichaColocadaN);
                                } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaAnterior.getLado1() == fichaColocadaN.getLado3()) {
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
                            if (fichaAnterior.getLado2() == fichaColocadaN.getLado1()) {
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado3()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado2()) {
                                fichaColocadaN.rotateLeft();
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaColocadaN.getLado3() == fichaAnterior.getLado2()) {
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado3() && fichaColocadaN.getLado2() == fichaAnterior.getLado2()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado1() == fichaColocadaN.getLado2() && fichaColocadaN.getLado1() == fichaAnterior.getLado2()) {
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else {
                                System.out.println("Ficha no valida");
                            }
                        } else if (fichaAnterior.getSentido().get(3)) {
                            if (fichaAnterior.getLado1() == fichaColocadaN.getLado1()) {
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado3()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado2()) {
                                fichaColocadaN.rotateLeft();
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaColocadaN.getLado3() == fichaAnterior.getLado1()) {
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado3() && fichaColocadaN.getLado2() == fichaAnterior.getLado1()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado1() == fichaColocadaN.getLado2() && fichaColocadaN.getLado1() == fichaAnterior.getLado1()) {
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else {
                                System.out.println("Ficha no valida");
                            }
                        } else {
                            if (fichaAnterior.getLado3() == fichaColocadaN.getLado1()) {
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado3() == fichaColocadaN.getLado3()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaAnterior.getLado3() == fichaColocadaN.getLado2()) {
                                fichaColocadaN.rotateLeft();
                                fichaColocadaN.rotateLeft();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaColocadaN.getLado3() == fichaAnterior.getLado3()) {
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado3() && fichaColocadaN.getLado2() == fichaAnterior.getLado3()) {
                                fichaColocadaN.rotateRight();
                                fichaColocadaN.rotateRight();
                                mesa.getTablero().add(fichaColocadaN);
                            } else if (fichaColocadaN.getLado1() == fichaColocadaN.getLado2() && fichaColocadaN.getLado1() == fichaAnterior.getLado3()) {
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
                        if (fichaAnterior.getLado1() == fichaColocada.getLado1() && fichaColocada.esMula()) {
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
                            } else if (fichaAnterior.getLado2() == fichaAnterior.getLado3() && fichaColocada.getLado1() == fichaAnterior.getLado2() || fichaColocada.getLado2() == fichaAnterior.getLado2()) {
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
                            } else if (fichaAnterior.getLado2() == fichaAnterior.getLado1() && fichaColocada.getLado1() == fichaAnterior.getLado2() || fichaColocada.getLado2() == fichaAnterior.getLado2()) {
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
                            } else if (fichaAnterior.getLado1() == fichaAnterior.getLado3() && fichaColocada.getLado1() == fichaAnterior.getLado3() || fichaColocada.getLado2() == fichaAnterior.getLado3()) {
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

                    if (fichaAnterior.getSentido().getFirst() && fichaAnterior.getSentido().get(2)) {
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
                            if (fichaAnterior.getLado2() == fichaColocadaN.getLado1()) {
                                return true;
                            } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaColocadaN.getLado1() == fichaAnterior.getLado2()) {
                                return true;
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado3()) {
                                return true;
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado3() && fichaColocadaN.getLado3() == fichaAnterior.getLado2()) {
                                return true;
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado2()) {
                                return true;
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado1() && fichaColocadaN.getLado2() == fichaAnterior.getLado2()) {
                                return true;
                            }
                        } else {
                            if (fichaAnterior.getLado1() == fichaColocadaN.getLado1()) {
                                return true;
                            } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaColocadaN.getLado1() == fichaAnterior.getLado1()) {
                                return true;
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado3()) {
                                return true;
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado3() && fichaColocadaN.getLado3() == fichaAnterior.getLado1()) {
                                return true;
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado2()) {
                                return true;
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado1() && fichaColocadaN.getLado2() == fichaAnterior.getLado1()) {
                                return true;
                            }
                        }
                    }
                } else {
                    FichaTridomino fichaAnterior = (FichaTridomino) mesa.getTablero().get(ultimaFichaIndex);
                    if (fichaAnterior.getSentido().getFirst() || fichaAnterior.getSentido().get(2) || fichaAnterior.getSentido().get(4)) {
                        if (fichaAnterior.getSentido().getFirst()) {
                            if (fichaAnterior.getLado3() == fichaAnterior.getLado2()) {
                                if (fichaColocadaN.getLado1() == fichaAnterior.getLado3()) {
                                    return true;
                                } else if (fichaColocadaN.getLado3() == fichaAnterior.getLado3()) {
                                    return true;
                                } else if (fichaColocadaN.getLado2() == fichaAnterior.getLado3()) {
                                    return true;
                                } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaAnterior.getLado3() == fichaColocadaN.getLado3()) {
                                    return true;
                                } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado2() && fichaAnterior.getLado3() == fichaColocadaN.getLado3()) {
                                    return true;
                                } else if (fichaColocadaN.getLado1() == fichaColocadaN.getLado2() && fichaAnterior.getLado3() == fichaColocadaN.getLado2()) {
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
                                if (fichaColocadaN.getLado1() == fichaAnterior.getLado2()) {
                                    return true;
                                } else if (fichaColocadaN.getLado3() == fichaAnterior.getLado2()) {
                                    return true;
                                } else if (fichaColocadaN.getLado2() == fichaAnterior.getLado2()) {
                                    return true;
                                } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaAnterior.getLado2() == fichaColocadaN.getLado3()) {
                                    return true;
                                } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado2() && fichaAnterior.getLado2() == fichaColocadaN.getLado3()) {
                                    return true;
                                } else if (fichaColocadaN.getLado1() == fichaColocadaN.getLado2() && fichaAnterior.getLado2() == fichaColocadaN.getLado2()) {
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
                                if (fichaColocadaN.getLado1() == fichaAnterior.getLado1()) {
                                    return true;
                                } else if (fichaColocadaN.getLado3() == fichaAnterior.getLado1()) {
                                    return true;
                                } else if (fichaColocadaN.getLado2() == fichaAnterior.getLado1()) {
                                    return true;
                                } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaAnterior.getLado1() == fichaColocadaN.getLado3()) {
                                    return true;
                                } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado2() && fichaAnterior.getLado1() == fichaColocadaN.getLado3()) {
                                    return true;
                                } else if (fichaColocadaN.getLado1() == fichaColocadaN.getLado2() && fichaAnterior.getLado1() == fichaColocadaN.getLado2()) {
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
                            if (fichaAnterior.getLado2() == fichaColocadaN.getLado1()) {
                                return true;
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado3()) {
                                return true;
                            } else if (fichaAnterior.getLado2() == fichaColocadaN.getLado2()) {
                                return true;
                            } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaColocadaN.getLado3() == fichaAnterior.getLado2()) {
                                return true;
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado3() && fichaColocadaN.getLado2() == fichaAnterior.getLado2()) {
                                return true;
                            } else if (fichaColocadaN.getLado1() == fichaColocadaN.getLado2() && fichaColocadaN.getLado1() == fichaAnterior.getLado2()) {
                                return true;
                            }
                        } else if (fichaAnterior.getSentido().get(3)) {
                            if (fichaAnterior.getLado1() == fichaColocadaN.getLado1()) {
                                return true;
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado3()) {
                                return true;
                            } else if (fichaAnterior.getLado1() == fichaColocadaN.getLado2()) {
                                return true;
                            } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaColocadaN.getLado3() == fichaAnterior.getLado1()) {
                                return true;
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado3() && fichaColocadaN.getLado2() == fichaAnterior.getLado1()) {
                                return true;
                            } else if (fichaColocadaN.getLado1() == fichaColocadaN.getLado2() && fichaColocadaN.getLado1() == fichaAnterior.getLado1()) {
                                return true;
                            }
                        } else {
                            if (fichaAnterior.getLado3() == fichaColocadaN.getLado1()) {
                                return true;
                            } else if (fichaAnterior.getLado3() == fichaColocadaN.getLado3()) {
                                return true;
                            } else if (fichaAnterior.getLado3() == fichaColocadaN.getLado2()) {
                                return true;
                            } else if (fichaColocadaN.getLado3() == fichaColocadaN.getLado1() && fichaColocadaN.getLado3() == fichaAnterior.getLado3()) {
                                return true;
                            } else if (fichaColocadaN.getLado2() == fichaColocadaN.getLado3() && fichaColocadaN.getLado2() == fichaAnterior.getLado3()) {
                                return true;
                            } else if (fichaColocadaN.getLado1() == fichaColocadaN.getLado2() && fichaColocadaN.getLado1() == fichaAnterior.getLado3()) {
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
        } else {
            FichaTridomino fichaColocadaN = (FichaTridomino) fichaColocada;
            jugadorActual.acumularPuntuacion(fichaColocadaN.sumaLados());
        }
    }



    /**
     * Método para determinar si ya acabó el juego
     */
    private boolean yaAcaboElJuego()
    {
        for (Jugador jugador : jugadores) {
            if (jugador.getMano().isEmpty()) {
                return true;
            }
        }
        return false;
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

        while (noTermino) {
            Jugador jugadorActual = jugadores.get(turnoActual);









            turnoActual = (turnoActual + 1) % jugadores.size();
        }

    }
}
