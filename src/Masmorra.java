public class Masmorra {

    // __ Atributs estàtics __
    protected Sala[][] masmorra;
    protected Tresor[] tresors;
    protected Monstre[] monstres;
    protected Personatge personatge;
    protected int files;
    protected int columnes;
    protected String causaMort = null; // "monstre" o "pont"

    // Constructor 
    /**
     * @param M nombre de files
     * @param N nombre de columnes
     * @param tresors array de tresors
     * @param monstres array de monstres
     * @param p Personatge que jugarà la masmorra
     */
    public void inicialitzar(int fil, int col, Tresor[] tresors, Monstre[] monstres, Personatge p) {
        this.files = fil;
        this.columnes = col;
        this.tresors = tresors;
        this.monstres = monstres;
        this.personatge = p;
        this.masmorra = new Sala[fil][col];
        this.causaMort = null;

        // El personatge comença sempre a la sala superior esquerra
        personatge.posicio[0] = 0;
        personatge.posicio[1] = 0;

        generarMasmorra();
    }

    // __ Generació de la Masmorra __

    protected void generarMasmorra() {
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

    protected Tresor tresorAleatori() {
        if (Math.random() < 0.5 && tresors.length > 0) {
            return tresors[(int) (Math.random() * tresors.length)];
        }
        return null;
    }

    protected Monstre monstreAleatori() {
        if (Math.random() < 0.5 && monstres.length > 0) {
            return monstres[(int) (Math.random() * monstres.length)];
        }
        return null;
    }

    // __ Impressió de la Masmorra __

    public void mostrarMasmorra() {
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

    public void mostrarOpcions() {
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

    public void explorar() {
        personatge.explorar(getSalaActual());
    }
   
    public void moure() {
    	personatge.moure();
    }

    public void atacar() {
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
    public boolean haFinalitzat() {
        if (!personatge.estaViu()) return true;
        if (haSortit()) return true;
        return false;
    }

    protected boolean haSortit() {
        int fila = personatge.posicio[0];
        int col  = personatge.posicio[1];
        return fila < 0 || fila >= files || col < 0 || col >= columnes;
    }

//    Ampliació
    public void mostrarResultats() {
        System.out.println("\n==============================");

        if (!personatge.estaViu()) {
            // DERROTA
            System.out.println("Has mort a la masmorra...");
            System.out.println("Causa de la mort: " + (causaMort != null ? causaMort : "desconeguda"));
            System.out.println("Experiència aconseguida: " + personatge.experiencia);
            System.out.println("Percentatge explorat: " + percentatgeExplorat() + "%");
        } else {
            // VICTÒRIA
            System.out.println("Has sortit de la masmorra! Enhorabona!");
            System.out.println("Experiència: " + personatge.experiencia);
            System.out.println("Nombre de tresors: " + numTresors());
            System.out.println("Total monedes d'or: " + totalMonedes());
            System.out.println("Vida restant: " + personatge.vida);
            System.out.println("Percentatge explorat: " + percentatgeExplorat() + "%");
        }

        System.out.println("==============================");
    }

    // __ Metodes auxiliars __

    public Sala getSalaActual() {
        int fila = personatge.posicio[0];
        int col  = personatge.posicio[1];
        // Guardem el rang per si de cas
        if (fila < 0 || fila >= files || col < 0 || col >= columnes) return null;
        return masmorra[fila][col];
    }

    // /**
    //  * Comprova si la direcció donada porta a dins la masmorra o a un extrem (sortida).
    //  */
    // public boolean direccioPermesa(char direccio) {
    //     // Totes les direccions estan permeses; sortir per un extrem és victòria.
    //     // Però evitem moure'ns en una direcció que no sigui N/S/E/O.
    //     char d = Character.toUpperCase(direccio);
    //     return d == 'N' || d == 'S' || d == 'E' || d == 'O';
    // }

    protected int percentatgeExplorat() {
        int explorades = 0;
        for (int i = 0; i < files; i++)
            for (int j = 0; j < columnes; j++)
                if (masmorra[i][j].explorada) explorades++;
        return (explorades * 100) / (files * columnes);
    }

    protected int numTresors() {
        int count = 0;
        for (Tresor t : personatge.equipament)
            if (t != null) count++;
        return count;
    }

    protected int totalMonedes() {
        int total = 0;
        for (Tresor t : personatge.equipament)
            if (t != null) total += t.valor;
        return total;
    }





    
}
