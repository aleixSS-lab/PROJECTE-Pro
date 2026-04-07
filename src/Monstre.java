
public class Monstre implements Combatent {

//	Atributs
	
	protected String nom;
	protected int vida;
	
	
		//Se necesitan para...
	protected int penalitzacio; // por fugida (num 0-3) ??
	protected int valorExperiencia; // = vidaMonstre x2
	
	
//	Constructores
	/**
	 * @param nom 
	 * @param vida
	 * @param penalitzacio
	 */
	
	Monstre(String nom, int vida, int penalitzacio){
		this.nom = nom;
		this.vida = vida;
		this.penalitzacio = penalitzacio;
			if (penalitzacio >= 0 && penalitzacio <= 3) {
				this.penalitzacio = penalitzacio;
			}
			else {
				this.penalitzacio = 0;
			}
		this.valorExperiencia  = vida * 2; 
	}
	
//	Metodos
	public int calcularAtac() {
		//Devuelve num aleatorio 1 - valor actual vidaMonstre
	int max = vida;
	int min = 1;
	
	return (int) (Math.random() * (max - min + 1) + min);	
	}

// GET

// SETTERS
	
//	ToString
	
@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Nom: " + nom + ", Vida: " + vida + "." ;
	}
	
}
