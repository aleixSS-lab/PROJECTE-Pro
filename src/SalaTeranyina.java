
public class SalaTeranyina extends Sala{

	
	
//	Constructor
	
	SalaTeranyina(Tresor tresor, Monstre monstre, boolean explorada){
		super(tresor,monstre,explorada);
	}
	
	
//	Metodos

@Override
public boolean intentarSortir(Personatge p) {
		//el personaje hace una tirada con >--FUERZA--<.
		
		int tiradaForsa = numAleatoriTirada();
		//si tiene exito --> TRUE
		
		if(tiradaForsa <=  p.forsa) {
			return true;
		}
		//si falla --> se queda en el mismo sitio y FALSE
		else {
			return false;
		}
//		Fin metodo
	}



}
