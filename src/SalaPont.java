
public class SalaPont extends Sala {

	
//	Constructor
	SalaPont(Tresor tresor, Monstre monstre, boolean explorada){
		super(tresor, monstre, explorada);
	}
	
//	Metodos
	
	public boolean intentarSortir(Personatge p) {
		//el personaje hace una tirada con >--AGILITAT--<.
		
				int tiradaAgilitat = numAleatoriTirada();
				//si tiene exito --> TRUE

				System.out.println("[Pont] Tirada d'agilitat: " + tiradaAgilitat + " (necessites <= " + p.agilitat + ")");
				
				if(tiradaAgilitat <=  p.agilitat) {
					System.out.println("Has conseguido cruzar el puente con éxito.");
					return true;
				}
				//si falla --> -1 de vida y FALSE
				else {
					p.rebreDany(1);
					System.out.println("Has fallado al cruzar el puente -1 de vida.");
					return false;
				}
//				Fin metodo
	}


	
}
