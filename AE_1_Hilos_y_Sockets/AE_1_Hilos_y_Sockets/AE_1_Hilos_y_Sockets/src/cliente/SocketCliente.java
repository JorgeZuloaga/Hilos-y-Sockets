package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketCliente {
	
	//IP y Puerto a la que nos vamos a conectar
	public static final int PUERTO = 2022;
	public static final String IP_SERVER = "localhost";
	
	public static void main(String[] args) {
		System.out.println("        APLICACIÓN CLIENTE LIBROS         ");
		System.out.println("-----------------------------------");
		
		InetSocketAddress direccionServidor = new InetSocketAddress(IP_SERVER, PUERTO);

		try (Scanner sc = new Scanner(System.in)){
			
			Socket socketAlServidor = new Socket();
			
			String contenido="";
			String opcionMenu="";
			boolean continuar=true; //Comunicación en bucle hasta pulsar 5
					
			//Establecemos la conexión
			System.out.println("CLIENTE: Esperando a que el servidor acepte la conexión");
			socketAlServidor.connect(direccionServidor);			
			System.out.println("CLIENTE: Conexion establecida... a " + IP_SERVER 
					+ " por el puerto " + PUERTO);		
			
			//Creamos el objeto que nos va a permitir leer la salida del servidor
			InputStreamReader entrada = new InputStreamReader(socketAlServidor.getInputStream());
					
			//Esta clase nos ayuda a leer datos del servidor linea a linea en vez de 
			//caracter a caracter como la clase InputStreamReader
			BufferedReader bf = new BufferedReader(entrada);
			
			//Creamos el objeto que nos permite mandar informacion al servidor
			PrintStream salida = new PrintStream(socketAlServidor.getOutputStream());
			
			//Mostramos el menú y pedimos al cliente que introduzca la opción
			//del menú a consultar
			while(continuar) {
				System.out.println("     M E N Ú     ");
				System.out.println("   Selecciona una opción:");
				System.out.println(" 1. Consultar libro por ISBN");
				System.out.println(" 2. Consultar libro por título");
				System.out.println(" 3. Consultar libro/s por autor");
				System.out.println(" 4. Añadir un nuevo libro");
				System.out.println(" 5. Salir");
				
				//Almacenamos en opcionMenu la opción seleccionada por el usuario
				opcionMenu = sc.nextLine();
				
				//Se crea un switch-case con los distintas opciones del menú
				//En la variable contenido, se almacenan los datos introducidos
				//por el cliente
				switch(opcionMenu) {
				case "1":
					//Requerimiento 1
					System.out.println(" Introduzca ISBN del libro:");
					contenido=sc.nextLine();
					break;			
				case "2":
					//Requerimiento 1
					System.out.println(" Introduzca título del libro:");
					contenido=sc.nextLine();
					break;
				case "3":
					//Requerimiento 2
					System.out.println(" Introduzca el nombre del autor del libro:");
					contenido=sc.nextLine();
					break;
				case "4":
					//Requerimiento 3
					System.out.println(" Introduzca los datos del libro:");
					//En este caso, al cliente se le van pidiendo los distintos campos
					//para completar la información del libro.
					//Los distintos datos se concatenan en la variable contenido, separándolos
					//por el caracter '-', de forma que se obtiene una cadena con el siguiente formato:
					//isbn-autor-titulo-precio
					System.out.println(" ISBN:");
					contenido=sc.nextLine();
					System.out.println(" Autor:");
					contenido=contenido+"-"+sc.nextLine();
					System.out.println(" Título del libro:");
					contenido=contenido+"-"+sc.nextLine();
					System.out.println(" Precio (€):");
					contenido=contenido+"-"+sc.nextLine();
					break;
				case "5":
					//Requerimiento 1, para finalizar la comunicación
					contenido=" ";
					continuar=false; //Se sale del bucle
					break;
				default:
					//Cualquier opción distinta a las anteriores, enviará
					//un mensaje de opción no contemplada
					contenido="Opción no contemplada, inténtalo de nuevo";
					
				}
				//Mandamos la información por el Stream
				//String mensajeEnviado=opcionMenu+"-"+contenido;
				//salida.println(mensajeEnviado);
				salida.println(opcionMenu+"-"+contenido);			
				
				System.out.println("CLIENTE: Esperando al resultado del servidor...");
				//En la siguiente linea se va a quedar parado el hilo principal
				//de ejecución hasta que el servidor responda, es decir haga un println
				String mensajeRecibido = bf.readLine();
				
				System.out.println("CLIENTE: Los datos consultados son:");
				
				//Una vez recibida la respuesta por parte del servidor, se quitará el formato
				//al mensaje recibido, que llega en formato isbn-autor-titulo-precio
				//para mostrarlo por pantalla
				
				if (opcionMenu.equals("1") || opcionMenu.equals("2") || opcionMenu.equals("3")) {
					//En caso de realizar una consulta de un libro por isbn/titulo/autor	
					String mensajeLargo=mensajeRecibido;
					//Se comprueba si llegan datos de más de un libro
					if(mensajeRecibido.contains("/")) {
						//Se crea un array con los distintos libros separados
						String[] librosRecibido = mensajeLargo.split("/");
						for (String s : librosRecibido) {
							//Se recorre el array de libros y se separan los diferentes
							//atributos del libro
							String[] datosLibro=s.split("-");
							//Se crea un array de string con los distintos campos, para que al imprimirlo
							//se concatene con los datos enviados
							String[] descripcionDatos= {"ISBN: ", ", autor: ", ", titulo: ", ", precio: "};
							for (int i=0; i<datosLibro.length;i++) {
								System.out.print(descripcionDatos[i]+datosLibro[i]);
							}
							System.out.println();
						}
					}
					else {
						//En caso de ser un solo libro, se separan los distintos atributos
						//del libro
						String[] datosLibro=mensajeRecibido.split("-");
						//Se crea un array de string con los distintos campos, para que al imprimirlo
						//se concatene con los datos enviados
						String[] descripcionDatos= {"ISBN: ", " autor: ", " titulo: ", " precio: "};
						for (int i=0; i<datosLibro.length;i++) {
							System.out.print(descripcionDatos[i]+datosLibro[i]);
						}
					}
				}
				else {
					//En cualquiera de las otras opciones
					System.out.println(mensajeRecibido);
				}
				
				System.out.println();			
				
				}//while-close
				
				socketAlServidor.close();
				
		} catch (UnknownHostException e) { //Excepción de servidor no encontrado
			System.err.println("CLIENTE: No encuentro el servidor en la dirección" + IP_SERVER);
			e.printStackTrace();
		} catch (IOException e) { //Excepción E/S
			System.err.println("CLIENTE: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) { //Excepción
			System.err.println("CLIENTE: Error -> " + e);
			e.printStackTrace();
		}
		
		System.out.println("CLIENTE: Fin del programa");
	}

}