import java.util.Arrays;
import java.util.Scanner;

public class Personatge extends Masmorra implements Combatent{
	//	Atributs
	protected String nom;
	protected int vida; //entre 5-20
	protected int atac; //entre 1-4
	protected int experiencia; //empieza en 0
	protected int agilitat; //entre 4-11
	protected int forsa; //entre 4-11
	protected int[] posicio; //un array valor de fila y columna en que se encuentra el personaje 
	protected Tresor[] equipament; //serà un array de tresors de mida igual a la força del personatge, inicialment buit


	//Constructor
		
	public Personatge (String nomConsola) {
		this.nom = nomConsola;
		this.vida = generarVida();
		this.atac = generarAtac();
		this.experiencia = 0;
		this.agilitat = generarAgilitat();
		this.forsa = generarForsa();
		this.posicio = new int[] {0,0}; // El personatge comença a la sala superior esquerra
		this.equipament = new Tresor[this.forsa];
	}



	//METODOS PARA GENERAR LOS ATRIBUTOS DE FORMA ALEATORIA

	// Num aleatorio para vida
	public static int generarVida() {
		return (int) (Math.random() * (20 - 5 + 1) + 5);
	}

	// Num aleatorio para atac
	public static int generarAtac() {
		return (int) (Math.random() * (4 - 1 + 1) + 1);
	}

	// Num aleatorio para agilitat
	public static int generarAgilitat() {
		return (int) (Math.random() * (11 - 4 + 1) + 4);
	}

	// Num aleatorio para forsa
	public static int generarForsa() {
		return (int) (Math.random() * (11 - 4 + 1) + 4);
	}

	// METODOS DE LA INTERFICIE COMBATENT
		//calcluarAtac() heredado de la interficie
		//devuelve un numero aleatorio entre 1 i el valor del atac del personaje
	public int calcularAtac() {
		return (int) (Math.random() * (this.atac - 1 + 1) + 1);
	}

 	public void rebreDany(int quantitat) {
        this.vida -= quantitat;
        if (this.vida < 0) this.vida = 0;
    }

    public boolean estaViu() {
        return this.vida > 0;
    }
	// METODOS
		//atacar(Monstre m) de tipo monstruo

		//	El personatge calcula el dany del seu atac
	public void atacar(Monstre m) {

		System.out.println(this.nom + " ataca al monstre " + m.nom + "!");

		int danyPersonatge = this.calcularAtac();
			//	El personatge fa el dany corresponent al monstre
		System.out.println("Has atacat i fet " + danyPersonatge + " punts de dany.");
			//	El personatge fa el dany corresponent al monstre
		m.rebreDany(danyPersonatge);

		System.out.println(this.nom + " ha atacat a " + m.nom + " i li ha fet " + danyPersonatge + " de dany. Vida restant del monstre: " + m.vida);

			//	Si el monstre no ha mort, calcula el dany del seu atac
		if (m.estaViu()) {
			int danyMonstre = m.calcularAtac();
			System.out.println("El monstre sobreviu i et treu " + danyMonstre + " de vida");
			this.rebreDany(danyMonstre);

		} else {
			//	Si en aquest atac es mata al monstre, se li sumarà al personatge un valor d’experiència igual al paràmetre valorExperiencia del monstre.
			System.out.println("Has derrotat al monstre " + m.nom + "! Has guanyat " + m.valorExperiencia + " d'experiència.");
			this.experiencia += m.valorExperiencia;

		}

	}


	//explorar() explorar la sala en la que se encuentra	
	//	Explorar (si la sala encara no ha sigut explorada).
	public void explorar(Sala s) {
			//	Si la sala ja ha sigut explorada, el personatge no fa res.
		if(s.explorada) {
			System.out.println("Aquesta sala ja ha sigut explorada.");
		}

		// Si no ha estat desconeguda, el personatge la marca com a explorada i si hi ha un tresor, l’agafa i l’afegeix al seu equipament i si hi ha un monstre, el personatge es prepara per al combat.
			s.explorada = true;
			System.out.println("Explorant la sala...");

			// Si hi ha un tresor, l’agafa i l’afegeix al seu equipament
		if(s.tresor != null) {
			System.out.println("Has trobat un tresor!");
			// Es recorre l'equipament per trobar un espai buit
			for (int i = 0; i < equipament.length; i++) {
				if(equipament[i] == null) {
					equipament[i] = s.tresor;
					System.out.println("Afegit a l'equipament! " + s.tresor);
				}else {
					System.out.println("L'equipament està ple!");
				}
		
		}
	} else {
			System.out.println("La sala no té cap tresor.");
		}	
		// Si hi ha un monstre, el personatge es prepara per al combat.
		if(s.monstre != null && s.monstre.estaViu()) {
			System.out.println("Has trobat un monstre!" + s.monstre);
		}
	}

	// moure(char direccio) el personaje se mueve en una direccion ('N','S','E' o 'O')
		
	public void moure(char direccio) {
		Scanner teclado = new Scanner(System.in);

		System.out.print("Introdueix una direcció (N, S, E, O): ");
			direccio = teclado.next().charAt(0);
		//  Hacer un bucle para pedir que el usuario ingrese una dirección válida hasta que lo haga correctamente -->
		while(direccio!= 'N' && direccio != 'S' &&direccio != 'E' && direccio != 'O') {
			System.out.println("Direcció no vàlida. Utilitza N, S, E o O.");
			System.out.print("Introdueix una direcció (N, S, E, O): ");
			direccio = teclado.next().charAt(0);
		}
		

		Sala salaActual = getSalaActual();

		// S'executa el metode de intentarSoritr() de la sala actual.
		salaActual.intentarSortir(Personatge.this);
			if(salaActual.intentarSortir(Personatge.this) == true) {
				// FER ELS MOVIMENTS CORRESPONENTS A CADA DIRECCIÓ
			switch (direccio) {
				case 'N':
					this.posicio[0]--;
					break;
				case 'S':
					this.posicio[0]++;
					break;
				case 'E':
					this.posicio[1]++;
					break;
				case 'O':
					this.posicio[1]--;
					break;
		}	

		if (getSalaActual().monstre != null && getSalaActual().monstre.estaViu()) {
			System.out.println("Hi havia un monstre viu a la sala! Tens penalització per fugida! Perds " + getSalaActual().monstre.penalitzacio + " de dany.");
			this.vida -= getSalaActual().monstre.penalitzacio;
			if (this.vida <= 0) {
				haFinalitzat();
				causaMort = "Penalització de fugida, perdida total de vida.";
				System.out.println("Has mort a causa de la penalització de fugida! Causa de mort: " + causaMort);
			}
			System.out.println("Vida restant: " + this.vida);
			this.rebreDany(getSalaActual().monstre.penalitzacio);
		}

		}
		else{
		System.out.println("No pots sortir d'aquesta sala! Intenta una altra direcció o explora la sala.");
		}
			

		teclado.close();
	}
	

	//ToString
	
	//nom, vida, agilitat, força, posicio dintre de la masmorra i tresors que te actualment al seu equipament
	@Override
	public String toString() {
		return "Nom: " + nom + "|| Vida: " + vida + "|| Agilitat: " + agilitat + "|| Forsa: " + forsa + "|| Posició: {" + posicio[0] + "," + posicio[1] + "} || Equipament{ " + Arrays.toString(equipament) + " }";
	}

	

}
