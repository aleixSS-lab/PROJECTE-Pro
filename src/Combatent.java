 
public interface Combatent {

	default int calcularAtac() {
		//Retorna el dany que farà en aquest torn
		return 0;
	}
	default void rebreDany(int quantitat) {
		//Resta la quantitat 
	}
	default boolean estaViu(){
		//Retorna true si la vida es > 0 i false en cas contrari
		return true;
	}
	
//		if (vida > 0){
//		 return true;
//		}	
//		else {
//			return false;
//		}	
}
