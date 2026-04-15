
public class Monstre implements Combatent {

//	Atributs
	
	protected String nom;
	protected int vida;
	protected int penalitzacio; // por fugida (num 0-3) ??
	protected int valorExperiencia; // = vidaMonstre x2
	
	
//	Constructores
	/**
	 * @param nom  
	 * @param vida	Vida del monstre
	 * @param penalitzacio de fugida (Vida que le resta al personatge quan surti de la sala)
	 */
	
	Monstre(String nom, int vida, int penalitzacio){
		this.nom = nom;
		this.vida = vida;
			if (penalitzacio >= 0 && penalitzacio <= 3) {
				this.penalitzacio = penalitzacio;
			}
			else {
				this.penalitzacio = 0;
			}
		this.valorExperiencia  = vida * 2; 
	}
	
//	Metodos de la interficie
	public int calcularAtac() {
		//Devuelve num aleatorio 1 - valor actual vidaMonstre
	return (int) (Math.random() * (vida - 1 + 1) + 1);	
	}

	public void rebreDany(int dany) {
		// recibe dany y lo resta a su vida
		this.vida -= dany;
		// si la vida es menor a 0, se pone a 0 (No puede tener vida negativa)
		if (this.vida < 0) {
			this.vida = 0;
		}
	}

	public boolean estaViu() {
		return this.vida > 0;
	}

// GET

// SETTERS
	
//	ToString
	
@Override
	public String toString() {
		return "Nom: " + nom + ", Vida: " + vida + "." ;
	}
	
}
