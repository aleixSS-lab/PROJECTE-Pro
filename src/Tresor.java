
public class Tresor {

//	Atributs
	protected String nom;
	protected int valor;
	protected double pes;
	
//	Constructor
	
	Tresor(String nom, int valor, double pes){
		this.nom = nom;
		this.valor = valor;
		this.pes = pes;
	}
	
	
	
//	ToString
	
	@Override
		public String toString() {
			
			return "Nom: " + nom + ", Valor: " + valor + " en monedes d'or." ;
	
	}
}
