public class Masmorra {

    // __ Atributs __
    private static Sala[][] masmorra;
    private static Tresor[] tresors;
    private static Monstre[] monstres;
    private static Personatge personatge;
    private static int files;
    private static int columnes;

    // __ Inicialitzacio __

    public static void inicialitzar(int M, int N, Tresor[] tresors, Monstre[] monstres, Personatge p) {
        files = M;
        columnes = N;
        Masmorra.tresors = tresors;
        Masmorra.monstres = monstres;
        Masmorra.personatge = p;
        masmorra = new Sala[M][N];
        generarMasmorra();
    }

    // __ Generacio de la Masmorra __

    private static void generarMasmorra() {
        for (int i = 0; i < files; i++) {
            for (int j = 0; j < columnes; j++) {

                // Tresor i monstre aleatoris (poden ser null)
                Tresor t = tresorAleatori();
                Monstre m = monstreAleatori();

                // Percentatges: SalaComuna 60%, SalaPont 20%, SalaTeranyina 20%
                int rand = (int) (Math.random() * 10); // 0-9

                if (rand < 6) {                        // 0-5 → 60%
                    masmorra[i][j] = new SalaComuna(t, m, false);
                } else if (rand < 8) {                 // 6-7 → 20%
                    masmorra[i][j] = new SalaPont(t, m, false);
                } else {                               // 8-9 → 20%
                    masmorra[i][j] = new SalaTeranyina(t, m, false);
                }
            }
        }
    }

    private static Tresor tresorAleatori() {
        // 50% de probabilitat de tenir tresor
        if (Math.random() < 0.5) {
            int idx = (int) (Math.random() * tresors.length);
            return tresors[idx];
        }
        return null;
    }

    private static Monstre monstreAleatori() {
        // 50% de probabilitat de tenir monstre
        if (Math.random() < 0.5) {
            int idx = (int) (Math.random() * monstres.length);
            return monstres[idx];
        }
        return null;
    }

    // __ Impressio de la Masmorra __

    public static void mostrarMasmorra() {
        System.out.println("\n=== MASMORRA ===");
        for (int i = 0; i < files; i++) {
            for (int j = 0; j < columnes; j++) {

                // Posicio actual del personatge (falta hacer el atributo de posicion de personaje )
                if (personatge.fila == i && personatge.columna == j) {
                    System.out.print("& ");
                }
                // Sala explorada
                else if (masmorra[i][j].explorada) {
                    System.out.print("* ");
                }
                // Sala sense explorar
                else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
        System.out.println("================");
        System.out.println("& = tu   * = explorada   - = no explorada");
    }

    // __ Opcions __

    public static void mostrarOpcions() {
        Sala salaActual = getSalaActual();

        System.out.println("\n¿Qué vols fer?");
        System.out.println("[M] Moure (N/S/E/O)");

        // Explorar només si la sala no ha estat explorada
        if (!salaActual.explorada) {
            System.out.println("[E] Explorar la sala");
        }

        // Atacar només si hi ha un monstre viu a la sala
        if (salaActual.monstre != null && salaActual.monstre.vida > 0) {
            System.out.println("[A] Atacar a " + salaActual.monstre.nom);
        }
    }

    // __ Sala Actual __

    public static Sala getSalaActual() {
//    	(Falta hacer la posicon actual del personaje)
        return masmorra[personatge.fila][personatge.columna];
    }

    // __ Comprovar Fi del Joc __

    /**
     * El joc acaba si:
     * - El personatge mor (vida <= 0)
     * - Totes les sales han estat explorades
     */
    public static boolean haFinalitzat() {
        // El personatge ha mort
        if (personatge.vida <= 0) {
            return true;
        }

        // Totes les sales explorades
        for (int i = 0; i < files; i++) {
            for (int j = 0; j < columnes; j++) {
                if (!masmorra[i][j].explorada) {
                    return false; // Encara hi ha sales per explorar
                }
            }
        }
        return true;
    }

    // __ Mostrar Resultats __

    public static void mostrarResultats() {
        System.out.println("\n====== FI DEL JOC ======");

        if (personatge.vida <= 0) {
            System.out.println("Has mort a la masmorra...");
        } else {
            System.out.println("Has explorat tota la masmorra!");
        }

        System.out.println("\n-- Resum del personatge --");
        System.out.println(personatge);
        System.out.println("========================");
    }

    // __ Comprovar si la direccio es valida __

    public static boolean direccioValida(char direccio) {
//    	Falta hacer aun la posicion de Personaje
        int novaFila    = personatge.fila;
        int novaColumna = personatge.columna;

        switch (Character.toUpperCase(direccio)) {
            case 'N': novaFila--;    break;
            case 'S': novaFila++;    break;
            case 'E': novaColumna++; break;
            case 'O': novaColumna--; break;
        }

        return novaFila >= 0 && novaFila < files &&
               novaColumna >= 0 && novaColumna < columnes;
    }
}