
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
				
				if(tiradaAgilitat <=  p.agiitat) {
					return true;
				}
				//si falla --> -1 de vida y FALSE
				else {
					p.vida --;
					return false;
				}
//				Fin metodo
	}
}
