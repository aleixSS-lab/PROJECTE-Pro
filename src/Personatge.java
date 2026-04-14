import java.util.Arrays;

public class Personatge extends Masmorra implements Combatent{
	//	Atributs
	protected String nom;
	protected int vida; //entre 5-20
	protected int atac; //entre 1-4
	protected int experiencia; // pensar si double o int y empieza en 0
	protected int agilitat; //entre 4-11
	protected int forsa; //entre 4-11
	protected int[] posicio; //un array valor de fila y columna en que se encuentra el personaje 
	protected Tresor[] equipament; //serà un array de tresors de mida igual a la força del personatge, inicialment buit


	//Constructor
	public Personatge (String nom, int vida, int atac, int experiencia, int agilitat, int forsa) {
		this.nom = nom;
		this.vida = vida;
		this.atac = atac;
		this.experiencia = experiencia;
		this.agilitat = agilitat;
		this.forsa = forsa;
		this.posicio = new int[] {0,0}; // El personatge comença a la sala superior esquerra
		this.equipament = new Tresor[this.forsa];
	}



	//Metodos


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
			break;
		}
	} else {
			System.out.println("La sala no té cap tresor.");
		}	
		// Si hi ha un monstre, el personatge es prepara per al combat.
		if(s.monstre != null && s.monstre.estaViu()) {
			System.out.println("Has trobat un monstre!" + s.monstre);
		}
	}

	//moure(char direccio) el personaje se mueve en una direccion ('N','S','E' o 'O')

	public void moure(char direccio) {
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
			default:
				System.out.println("Direcció no vàlida. Utilitza N, S, E o O.");
		}	
	}
	



	//ToString
	
	//nom, vida, agilitat, força, posicio dintre de la masmorra i tresors que te actualment al seu equipament
	@Override
	public String toString() {
		return "Nom: " + nom + "|| Vida: " + vida + "|| Agilitat: " + agilitat + "|| Forsa: " + forsa + "|| Posició: {" + posicio[0] + "," + posicio[1] + "} || Equipament{ " + Arrays.toString(equipament) + " }";
	}

	

}
