public  class Masmorra {

    // __ Atributs estàtics __
    protected static Sala[][] masmorra;
    protected static Tresor[] tresors;
    protected static Monstre[] monstres;
    protected static Personatge personatge;
    protected static int files;
    protected static int columnes;
    protected static String causaMort = null; // "monstre" o "pont"

    // __ Inicialitzacio __
    /**
     * @param M nombre de files
     * @param N nombre de columnes
     * @param tresors array de tresors
     * @param monstres array de monstres
     * @param p Personatge que jugarà la masmorra
     */
    public static void inicialitzar(int f, int c, Tresor[] tresors, Monstre[] monstres, Personatge p) {
        files = f;
        columnes = c;
        Masmorra.tresors = tresors;
        Masmorra.monstres = monstres;
        Masmorra.personatge = p;
        masmorra = new Sala[f][c];
        causaMort = null;

        // El personatge comença sempre a la sala superior esquerra
        personatge.posicio[0] = 0;
        personatge.posicio[1] = 0;

        generarMasmorra();
    }

    // __ Generació de la Masmorra __

    protected static void generarMasmorra() {
        for (int i = 0; i < files; i++) {
            for (int j = 0; j < columnes; j++) {
                Tresor t = tresorAleatori();
                Monstre m = monstreAleatori();

                // SalaComuna 60%, SalaPont 20%, SalaTeranyina 20%
                int rand = (int) (Math.random() * 10); // 0-9
                if (rand < 6) {
                    masmorra[i][j] = new SalaComuna(t, m, false);
                } else if (rand < 8) {
                    masmorra[i][j] = new SalaPont(t, m, false);
                } else {
                    masmorra[i][j] = new SalaTeranyina(t, m, false);
                }
            }
        }
    }

    protected static Tresor tresorAleatori() {
        if (Math.random() < 0.5 && tresors.length > 0) {
            return tresors[(int) (Math.random() * tresors.length)];
        }
        return null;
    }

    protected static Monstre monstreAleatori() {
        if (Math.random() < 0.5 && monstres.length > 0) {
            return monstres[(int) (Math.random() * monstres.length)];
        }
        return null;
    }

    // __ Impressió de la Masmorra __

    public static void mostrarMasmorra() {
        System.out.println("\n=== MASMORRA (" + files + "x" + columnes + ") ===");
        for (int i = 0; i < files; i++) {
            for (int j = 0; j < columnes; j++) {
                if (personatge.posicio[0] == i && personatge.posicio[1] == j) {
                    System.out.print("& ");   // posició actual del personatge
                } else if (masmorra[i][j].explorada) {
                    System.out.print("* ");   // sala explorada
                } else {
                    System.out.print("- ");   // sala sense explorar
                }
            }
            System.out.println();
        }
        System.out.println("& = tu   * = explorada   - = no explorada");
        System.out.println("============================");
    }

    // __ Mostrar Opcions __

    public static void mostrarOpcions() {
        Sala sala = getSalaActual();
        System.out.println("\nQuè vols fer?");

        if (!sala.explorada) {
            System.out.println("  [E] Explorar la sala");
        }
        System.out.println("  [M] Moure (N/S/E/O)");
        if (sala.monstre != null && sala.monstre.estaViu()) {
            System.out.println("  [A] Atacar a " + sala.monstre.nom + " (vida: " + sala.monstre.vida + ")");
        }
        System.out.println("  [P] Mostrar personatge");
    }

    // __ Accions principals __

    public static void explorar() {
        personatge.explorar(getSalaActual());
    }
    
    // public static void moure(char direccio) {
    //     Sala salaActual = getSalaActual();

    //     // Comprovar si pot sortir de la sala
    //     if (!salaActual.intentarSortir(personatge)) {
    //         // La sala pot haver causat dany (SalaPont)
    //         if (!personatge.estaViu()) {
    //             causaMort = "pont";
    //         }
    //         return;
    //     }

    //     // Penalització de fugida si hi ha monstre viu
    //     if (salaActual.monstre != null && salaActual.monstre.estaViu()) {
    //         int pen = salaActual.monstre.penalitzacio;
    //         if (pen > 0) {
    //             personatge.rebreDany(pen);
    //             System.out.println("  ⚠ El monstre t'ataca mentre fuges! Perds " + pen + " de vida. Vida restant: " + personatge.vida);
    //             if (!personatge.estaViu()) {
    //                 causaMort = "monstre";
    //                 return;
    //             }
    //         }
    //     }

    //     // Moure el personatge (actualitza posicio[])
    //     personatge.moure(direccio);

    //     // Comprovar si ha sortit per un extrem (victòria)
    //     if (haSortit()) {
    //         return; // haFinalitzat() ho detectarà
    //     }

    //     System.out.println("  T'has mogut a la posició [" + personatge.posicio[0] + ", " + personatge.posicio[1] + "].");
    // }

    public static void atacar() {
        Sala sala = getSalaActual();
        if (sala.monstre == null || !sala.monstre.estaViu()) {
            System.out.println("No hi ha cap monstre viu a la sala.");
            return;
        }
        personatge.atacar(sala.monstre);

        if (!personatge.estaViu()) {
            causaMort = "monstre";
        }
    }

    // __ Fi del Joc __

    /**
     * El joc acaba si:
     * - El personatge mor (vida <= 0)
     * - El personatge surt per un extrem de la masmorra
     */
    public static boolean haFinalitzat() {
        if (!personatge.estaViu()) return true;
        if (haSortit()) return true;
        return false;
    }

    protected static boolean haSortit() {
        int fila = personatge.posicio[0];
        int col  = personatge.posicio[1];
        return fila < 0 || fila >= files || col < 0 || col >= columnes;
    }

    public static void mostrarResultats() {
        System.out.println("\n==============================");

        if (!personatge.estaViu()) {
            // DERROTA
            System.out.println("💀 Has mort a la masmorra...");
            System.out.println("Causa de la mort: " + (causaMort != null ? causaMort : "desconeguda"));
            System.out.println("Experiència aconseguida: " + personatge.experiencia);
            System.out.println("Percentatge explorat: " + percentatgeExplorat() + "%");
        } else {
            // VICTÒRIA
            System.out.println("🏆 Has sortit de la masmorra! Enhorabona!");
            System.out.println("Experiència: " + personatge.experiencia);
            System.out.println("Nombre de tresors: " + numTresors());
            System.out.println("Total monedes d'or: " + totalMonedes());
            System.out.println("Vida restant: " + personatge.vida);
            System.out.println("Percentatge explorat: " + percentatgeExplorat() + "%");
        }

        System.out.println("==============================");
    }

    // __ Metodes auxiliars __

    public static Sala getSalaActual() {
        int fila = personatge.posicio[0];
        int col  = personatge.posicio[1];
        // Guardem el rang per si de cas
        if (fila < 0 || fila >= files || col < 0 || col >= columnes) return null;
        return masmorra[fila][col];
    }

    // /**
    //  * Comprova si la direcció donada porta a dins la masmorra o a un extrem (sortida).
    //  */
    // public static boolean direccioPermesa(char direccio) {
    //     // Totes les direccions estan permeses; sortir per un extrem és victòria.
    //     // Però evitem moure'ns en una direcció que no sigui N/S/E/O.
    //     char d = Character.toUpperCase(direccio);
    //     return d == 'N' || d == 'S' || d == 'E' || d == 'O';
    // }

    protected static int percentatgeExplorat() {
        int explorades = 0;
        for (int i = 0; i < files; i++)
            for (int j = 0; j < columnes; j++)
                if (masmorra[i][j].explorada) explorades++;
        return (explorades * 100) / (files * columnes);
    }

    protected static int numTresors() {
        int count = 0;
        for (Tresor t : personatge.equipament)
            if (t != null) count++;
        return count;
    }

    protected static int totalMonedes() {
        int total = 0;
        for (Tresor t : personatge.equipament)
            if (t != null) total += t.valor;
        return total;
    }





    
}
