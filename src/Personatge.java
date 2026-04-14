import java.util.Scanner;

public class Personatge extends Masmorra implements Combatent{
	//	Atributs
	protected String nom;
	protected int vida; //entre 5-20
	protected int atac; //entre 1-4
	protected int experiencia; // pensar si double o int y empieza en 0
	protected int agilitat; //entre 4-11
	protected int forsa; //entre 4-11
	protected int[] posicio; //un matriz valor de fila y columna en que se encuentra el personaje (mirarlo bien)
	protected Tresor[] equipament; //serà un array de tresors de mida igual a la força del personatge, inicialment buit
	public int fila;
	public int columna;


	//Constructor
	public Personatge (String nom, int vida, int atac, int experiencia, int agilitat, int forsa, int fila, int columna) {
		this.nom = nom;
		this.vida = vida;
		this.atac = atac;
		this.experiencia = experiencia;
		this.agilitat = agilitat;
		this.forsa = forsa;
		this.posicio = new int[] {this.fila, this.columna};
		this.equipament = new Tresor[this.forsa];
		this.fila = fila;
		this.columna = columna;
	}



	//Metodos

	//atacar(Monstre m) de tipo monstruo

	//	El personatge calcula el dany del seu atac
	public void atacar(Monstre m) {
		int dany = calcularAtac();
		System.out.println("Has atacat i fet" + dany + "punts de dany.");
		//	El personatge fa el dany corresponent al monstre
		m.rebreDany(dany);

		//	Si el monstre no ha mort, calcula el dany del seu atac
		if (m.estaViu()) {
			int danyMonstre = m.calcularAtac();
			System.out.println("El monstre sobreviu i et treu" + danyMonstre + " de vida");
			m.rebreDany(danyMonstre);
		} else {
			//			Si en aquest atac es mata al monstre, se li sumarà al personatge un valor d’experiència igual al paràmetre valorExperiencia del monstre.
			System.out.println("Has derrotat al monstre");
			this.experiencia += m.valorExperiencia;

		}

	}


	//calcluarAtac() heredado de la interficie
	//devuelve un numero aleatorio entre 1 i el valor del atac del personaje
	public int calcularAtac() {
		return (int) (Math.random() * (this.atac - 1 + 1) + 1);
	}



	//explorar() explorar la sala en la que se encuentra	
	//	Explorar (si la sala encara no ha sigut explorada).
	public void explorar(Sala s) {
		if(s.explorada) {
			//	El jugador executarà el seu mètode “explorar” en la sala en què es troba actualment i 
			//	trobara el tresor que té la sala (si en té) i l’afegirà al seu equipament (si té lloc encara).
			if(s.tresor != null) {
				afegirTresor(s.tresor);
				System.out.println("Has trobat un tresor!");
			}
		} else {
			System.out.println("Ya està explorada!");
		}

	}	
	public void afegirTresor(Tresor nouTresor) {
		for (int i = 0; i < equipament.length; i++) {
			if(equipament[i] == null) {
				equipament[i] = nouTresor;
				System.out.println("Afegit a l'equipament!");
			}else {
				System.out.println("L'equipament està ple!");
			}
			break;
		}

	}


	//moure(char direccio) el personaje se mueve en una direccion ('N','S','E' o 'O')


	public void moure(char direccio, Sala s, Masmorra m, Personatge p) {
		//		Se li demanarà a quina direcció vol moure i el jugador executarà el seu mètode “moure” en aquesta direcció.

		Scanner teclat = new Scanner (System.in);
		System.out.println("A quina direcció vols moure't (N, S, E o O)");
		direccio =teclat.next().charAt(0);

		int contador = 0;
		
		//verificar de que esté bien puesto
		while (direccio != 'N' && direccio != 'S' && direccio != 'E' && direccio != 'O') {
			System.out.println("A quina direcció vols moure't (N, S, E o O)");
			direccio =teclat.next().charAt(0);	
			contador++;
		} 

		//Como hacer que se mueva de sala
		//Coger el char que se ha escrito
		//Mirar que sala hay y en que posiciones de la mazmorra esta
		//Poner en el atributo posición el valor principal, osea la entrada de la sala en la que apunta la direccion escogida
		m.getSalaActual().intentarSortir(p);
		if (s.intentarSortir(p) == true) {
			if(direccio == 'N') {
				fila--;

			}else if (direccio == 'S'){
				fila ++;
			}else if(direccio == 'E')
				columna++;
		}else {
			columna--;
		}
		
//		Si hi ha un monstre viu a la sala quan el personatge marxa, el personatge perd vida igual a la penalització de fugida del monstre.

		if(s.monstre.vida != 0) {
			vida -= s.monstre.penalitzacio;
		}

	}



	//ToString
	
	//nom, vida, agilitat, força, posicio dintre de la masmorra i tresors que te actualment al seu equipament
	@Override
	public String toString() {
		return "Nom: " + nom + ", Vida: " + vida + ", Agilitat: " + agilitat + ", Forsa: " + forsa + ", Posició: " + posicio + ", Equipament: " + equipament;
	}

	

}
