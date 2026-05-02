
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
		System.out.println("[Teranyina] Tirada de força: " + tiradaForsa + " (necessites <= " + p.forsa + ")");

		if(tiradaForsa <=  p.forsa) {
			System.out.println(" Has superat la teranyina!");
			return true;
		}
		//si falla --> se queda en el mismo sitio y FALSE
		else {
			System.out.println(" No has superat la teranyina, et quedes atrapat!");
			return false;
		}
//		Fin metodo
	}

}
