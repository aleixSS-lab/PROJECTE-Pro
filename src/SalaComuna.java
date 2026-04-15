
public class SalaComuna extends Sala {

	
	
//	Constructor
	SalaComuna(Tresor tresor, Monstre monstre, boolean explorada){
		super(tresor,monstre,explorada);
	}

		public boolean intentarSortir(Personatge p) {
			//siempre true
		return true;
		}
		
}
