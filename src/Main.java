public class Main {
	 public static void main(String[] args) {
	//Crear personatge
	Personatge Pepe = new Personatge("Pepe");
	System.out.println("Bienvenido a la mazmorra, " + Pepe.nom + "!");

	// Crear tresors
	Tresor cartaMagica = new Tresor("Carta Magica", 10, 1.2);

	//Crear monstres
	//Iniciar la mazmorra
	Masmorra.inicialitzar(10, 10, null, null, Pepe);
	//Pedir opciones al personatge y mostrar mazmorra
	Masmorra.mostrarOpcions();
	




	//	El personaje comenzara en la sala de arriba a la izquierda de la mazmorra en cada turno se le dara 3 opciones al jugador.
			//(while con contador de turno, cada turno se le dara las 3 opciones, hasta que el juego no se acabe el bucle no acabara)
			
	// El jugador pedira el metodo explorar en la sala que esta actualmente, onseguira el tesoro (si hay) y se lo pondra en su inventario (si es que tiene hueco)
			//recorrer array para saber si esta ocupado, si no esta ocupado añadir en ultima posicion el equipamiento  
			//equipament[] += "nombre Tesoro"
			//si esta ocupado donar por consola que el equipamiento esta al maximo y no le cabe el tesoro.
			
			

		
}
}

