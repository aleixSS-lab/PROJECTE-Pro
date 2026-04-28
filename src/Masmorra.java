import java.util.Scanner;

public  class Masmorra {

    // __ Atributs estàtics __
    protected static Sala[][] masmorra;
    protected static Tresor[] tresors;
    protected static Monstre[] monstres;
    protected static Personatge personatge;
    protected static int files;
    protected static int columnes;
    protected static String causaMort = null; // "monstre" o "pont"

    // Constructor 
    /**
     * @param M nombre de files
     * @param N nombre de columnes
     * @param tresors array de tresors
     * @param monstres array de monstres
     * @param p Personatge que jugarà la masmorra
     */
    
//    Mirar los arrays
    
    public static void inicialitzar(int fil, int col, Tresor[] tresors, Monstre[] monstres, Personatge p) {
        files = fil;
        columnes = col;
        Masmorra.tresors = tresors;
        Masmorra.monstres = monstres;
        personatge = p;
        masmorra = new Sala[fil][col];
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
        System.out.println("MASMORRA (" + files + "x" + columnes + ")");
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

    //  Mostrar Opcions 

    public static void mostrarOpcions() {
    	Scanner teclado = new Scanner(System.in);
    	
        Sala sala = Masmorra.getSalaActual();
        
        System.out.println("Què vols fer?");
        
        if (!sala.explorada) {
            System.out.println(" Explorar la sala");
        }  
        System.out.println("Moure");
        if (sala.monstre != null && sala.monstre.estaViu()) {
            System.out.println("Atacar a " + sala.monstre.nom + " (vida: " + sala.monstre.vida + ")");
        }
        
        
//      MIRAR QUE A ESCOJIDO
        String escollirOpcio = teclado.next();
        
        if(escollirOpcio.equalsIgnoreCase("Explorar la sala")) {
        	Masmorra.explorar();
        }
        else if(escollirOpcio.equalsIgnoreCase("Moure")) {
        	System.out.print("Introdueix una direcció (N, S, E, O): ");
        	char escollirDireccio = teclado.next().charAt(0);
        	Masmorra.personatge.moure(escollirDireccio);
        }
        
        System.out.println("Mostrar personatge");
        teclado.close();
    }

    // Accions principals 

    public static void explorar() {
        personatge.explorar(getSalaActual());
    }
   
    public static void moure() {
    	personatge.moure();
    }

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

    // Fi del Joc

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

//    Ampliació
    public static void mostrarResultats() {
        System.out.println("==============================");

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

    //  Metodes auxiliars 

    public static Sala getSalaActual() {
        int fila = personatge.posicio[0];
        int col  = personatge.posicio[1];
        // Guardem el rang per si de cas
        if (fila < 0 || fila >= files || col < 0 || col >= columnes) {
        	return masmorra[fila][col];
        }
        return null;
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



    public static void main(String[] args) {
		 System.out.println("\u001B[35m/$$$$$$$                                                               /$$$$$$                                  /$$    ");
	        System.out.println("| $$__  $$                                                             /$$__  $$                                | $$    ");
	        System.out.println("| $$  \\ $$  /$$$$$$  /$$$$$$$   /$$$$$$   /$$$$$$   /$$$$$$  /$$$$$$$ | $$  \\ $$ /$$   /$$  /$$$$$$   /$$$$$$$ /$$$$$$  ");
	        System.out.println("| $$  | $$ |____  $$| $$__  $$ /$$__  $$ /$$__  $$ /$$__  $$| $$__  $$| $$  | $$| $$  | $$ /$$__  $$ /$$_____/|_  $$_/  ");
	        System.out.println("| $$  | $$  /$$$$$$$| $$  \\ $$| $$  \\ $$| $$$$$$$$| $$  \\ $$| $$  \\ $$| $$  | $$| $$  | $$| $$$$$$$$|  $$$$$$   | $$    ");
	        System.out.println("| $$  | $$ /$$__  $$| $$  | $$| $$  | $$| $$_____/| $$  | $$| $$  | $$| $$/$$ $$| $$  | $$| $$_____/ \\____  $$  | $$ /$$");
	        System.out.println("| $$$$$$$/|  $$$$$$$| $$  | $$|  $$$$$$$|  $$$$$$$|  $$$$$$/| $$  | $$|  $$$$$$/|  $$$$$$/|  $$$$$$$ /$$$$$$$/  |  $$$$/");
	        System.out.println("|_______/  \\_______/|__/  |__/ \\____  $$ \\_______/ \\______/ |__/  |__/ \\____ $$$ \\______/  \\_______/|_______/    \\___/  ");
	        System.out.println("                               /$$  \\ $$                                    \\__/                                        ");
	        System.out.println("                              |  $$$$$$/                                                                                ");
	        System.out.println("                               \\______/                                                                                 ");
	     
//	   SALTO DE LINIA     
	 System.out.println("\u001B[0m");
	 System.out.println();
	 System.out.println();
	
	 Scanner teclado = new Scanner(System.in);
	 
	//CREAR PERSONATGE
	System.out.println("Hola, com et voldrias anomenar?");
	System.out.print("Introdueix el teu nom: ");
	String nomConsola = teclado.next();
	
	Personatge per1 = new Personatge(nomConsola);
	System.out.println("\u001b[0;38;5;205;49m");
	System.out.println("BENVOLGUT/DA A LA MASMORRA, " + per1.nom + "!");

	System.out.println();
	
//	MOSTRAR STATS
	System.out.println("ESTADISTICAS DE " + per1.nom + ":");
	System.out.println("Vida: " + per1.vida);
	System.out.println("Agilitat: " + per1.agilitat);
	System.out.println("Atac: " + per1.atac);
	System.out.println("Força: " + per1.forsa);
	System.out.println("Experiencia: " + per1.experiencia);
	
	System.out.println("\u001B[0m");
	
	// Crear un array para tresors
	Tresor[] tresors = {
			new Tresor("Espasa màgica",150,3.5),
			new Tresor("Elm d'or",200,2.0),
			new Tresor("Anell de poder",300,0.1),
			new Tresor("Etecladout antic",100,5.0),
			new Tresor("Poció d'or",50,0.5),
			new Tresor("Mapa del tresor", 75,0.2),
			new Tresor("Carta Magica", 10,1.2),
			new Tresor("Vara", 25,2.6)
	};

//	Crear un array para monstres
	Monstre[] monstres = {
			new Monstre("Goblin",5,1),
			new Monstre("Esquelet",7,2),
			new Monstre("Troll",12,3),
			new Monstre("Drac petit",10,3),
			new Monstre("Fantasma",6,0),
			new Monstre("Llop gegant",8,2),
			new Monstre("Goblin", 100,1),
			new Monstre("Drac", 100,2)
	};
	 
	
	//Iniciar la mazmorra
	inicialitzar(10, 10, tresors, monstres, per1);
	
	//Pedir opciones al personatge y mostrar mazmorra

	while(!haFinalitzat()) {
		mostrarOpcions();
		mostrarMasmorra();
	}
	haFinalitzat();
	
	

	
}


	//	El personaje comenzara en la sala de arriba a la izquierda de la mazmorra en cada turno se le dara 3 opciones al jugador.
			//(while con contador de turno, cada turno se le dara las 3 opciones, hasta que el juego no se acabe el bucle no acabara)
			
	// El jugador pedira el metodo explorar en la sala que esta actualmente, onseguira el tesoro (si hay) y se lo pondra en su inventario (si es que tiene hueco)
			//recorrer array para saber si esta ocupado, si no esta ocupado añadir en ultima posicion el equipamiento  
			//equipament[] += "nombre Tesoro"
			//si esta ocupado donar por consola que el equipamiento esta al maximo y no le cabe el tesoro.
			
			

		
    
    

    
}
