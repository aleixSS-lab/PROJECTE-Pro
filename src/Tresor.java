
public class Tresor {

//	Atributs
	protected String nom;
	protected int valor;
	protected double pes;
	
//	Constructor
	/**
	 * Constructor de la clase Tresor
	 * @param nom Nombre del tresor.
	 * @param valor Valor del tesoro en monedas de oro.
	 * @param pes Pes del tresor en double.
	 */
	Tresor(String nom, int valor, double pes){
		this.nom = nom;
		this.valor = valor;
		this.pes = pes;
	}
	
	
	// Métodos de getter i setter de nom, valor y pes. || Se necesitan de verdad? y para que? || Se pueden eliminar?
	public String setNom(String n) {
		 return this.nom = n;
	}
	public int setValor(int v) {
		 return this.valor = v;
	}
	public double setPes(double p) {
		 return this.pes = p;
	}
	
	public String getNom(String n) {
		return this.nom;
	}
	public int getValor(String v) {
		return this.valor;
	}
	public double getPes(String p) {
		return this.pes;
	}

//	ToString
	@Override
		public String toString() {
			
			return "Nom: " + nom + ", Valor: " + valor + " en monedes d'or." ;
	
	}
}
