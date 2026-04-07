
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
	public int numAleatoriTirada() {
		int max = 12;
		int min = 1;
		return (int) (Math.random() * (max - min + 1) + min);
	}
}
