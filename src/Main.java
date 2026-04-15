import java.util.Scanner;

public class Main {
	 public static void main(String[] args) {
		 System.out.println("\u001B[35m/$$$$$$$                                                               /$$$$$$                                  /$$    ");
	        System.out.println("| $$__  $$                                                             /$$__  $$                                | $$    ");
	        System.out.println("| $$  \\ $$  /$$$$$$  /$$$$$$$   /$$$$$$   /$$$$$$   /$$$$$$  /$$$$$$$ | $$  \\ $$ /$$   /$$  /$$$$$$   /$$$$$$$ /$$$$$$  ");
	        System.out.println("| $$  | $$ |____  $$| $$__  $$ /$$__  $$ /$$__  $$ /$$__  $$| $$__  $$| $$  | $$| $$  | $$ /$$__  $$ /$$_____/|_  $$_/  ");
	        System.out.println("| $$  | $$  /$$$$$$$| $$  \\ $$| $$  \\ $$| $$$$$$$$| $$  \\ $$| $$  \\ $$| $$  | $$| $$  | $$| $$$$$$$$|  $$$$$$   | $$    ");
	        System.out.println("| $$  | $$ /$$__  $$| $$  | $$| $$  | $$| $$_____/| $$  | $$| $$  | $$| $$/$$ $$| $$  | $$| $$_____/ \\____  $$  | $$ /$$");
	        System.out.println("| $$$$$$$/|  $$$$$$$| $$  | $$|  $$$$$$$|  $$$$$$$|  $$$$$$/| $$  | $$|  $$$$$$/|  $$$$$$/|  $$$$$$$ /$$$$$$$/  |  $$$$/");
	        System.out.println("|_______/  \\_______/|__/  |__/ \\____  $$ \\_______/ \\______/ |__/  |__/ \\____ $$$ \\______/  \\_______/|_______/    \\___/  ");
	        System.out.println("                               /$$  \\ $$                                    \\__/                                        ");
	        System.out.println("                              |  $$$$$$/                                                                                ");
	        System.out.println("                               \\______/                                                                                 ");
	     
//	   SALTO DE LINIA     
	 System.out.println("\u001B[0m");
	 System.out.println();
	 System.out.println();
	 
	 Scanner teclado = new Scanner(System.in);
	 
	//Crear personatge
	System.out.println("Hola, com et voldrias cridar?");
	System.out.print("Introdueix el teu nom: ");
	String nomConsola = teclado.next();
	Personatge per1 = new Personatge(nomConsola);
	System.out.println("Bienvenido a la mazmorra, " + per1.nom + "!");

	// Crear tresors
	Tresor cartaMagica = new Tresor("Carta Magica", 10, 1.2);
	Tresor vara = new Tresor("Vara", 25, 2.6);

	//Crear monstres
	Monstre goblin = new Monstre("Goblin", 100, 1);
	Monstre drac = new Monstre("Drac", 100, 2);
	
	//Iniciar la mazmorra
	Masmorra.inicialitzar(10, 10, null, null, per1);
	//Pedir opciones al personatge y mostrar mazmorra

	




	//	El personaje comenzara en la sala de arriba a la izquierda de la mazmorra en cada turno se le dara 3 opciones al jugador.
			//(while con contador de turno, cada turno se le dara las 3 opciones, hasta que el juego no se acabe el bucle no acabara)
			
	// El jugador pedira el metodo explorar en la sala que esta actualmente, onseguira el tesoro (si hay) y se lo pondra en su inventario (si es que tiene hueco)
			//recorrer array para saber si esta ocupado, si no esta ocupado añadir en ultima posicion el equipamiento  
			//equipament[] += "nombre Tesoro"
			//si esta ocupado donar por consola que el equipamiento esta al maximo y no le cabe el tesoro.
			
			

		
}
}

