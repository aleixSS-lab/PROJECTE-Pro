
public abstract class Sala {

//	Atributs
	
	Tresor tresor; // contiene un tesoro aleatorio o nulo
	Monstre monstre; // contiene monstruo aleatorio o nulo
	boolean explorada; 
	
//	Constructor
	
	Sala(Tresor tresor, Monstre monstre, boolean explorada){
		this.tresor = tresor;
		this.monstre = monstre;
		this.explorada = explorada;
	}
	
//	Metodos
		//Define la logica para permetir (o no) sortir de la sala.
	public abstract boolean intentarSortir(Personatge p);
	
//	tirada
		//Las tiradas se realizan obteniendo un numero del 1 al 12.
		//Si el valor es <= que la fuerza del personaje tiene exito si no falla.
	static public int numAleatoriTirada() {
		return (int) (Math.random() * (12 - 1 + 1) + 1);
	}

	// Métodos de getter i setter de nom, valor y pes. || Se necesitan de verdad? y para que? || Se pueden eliminar?
	public void setTresor(Tresor t) {
		
		 this.tresor = t;
	}

	public void setMonstre(Monstre m){
		this.monstre = m;
	}

	public void setExplorada(boolean e) {
		this.explorada = e;
	}	

	public Tresor getTresor() {
		return this.tresor;
	}

	public Monstre getMonstre() {
		return this.monstre;
	}

	public boolean getExplorada() {
		return this.explorada;
	}
	

}
