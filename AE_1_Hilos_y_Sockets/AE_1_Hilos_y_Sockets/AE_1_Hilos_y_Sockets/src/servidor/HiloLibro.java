package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;


public class HiloLibro implements Runnable {
	
	private ListaLibros listaLibro;
	private Socket socketAlCliente;
	private Thread hilo;
	private static int numCliente = 0;

	/**
	 * Constructor del HiloLibro, que recibe el listado de los libros
	 * con el que interactúa y el Socket que mantiene la comunicación
	 * servidor al cliente.
	 * @param listaLibro
	 * @param socketAlCliente
	 */
	public HiloLibro(ListaLibros listaLibro, Socket socketAlCliente) {
		super();
		numCliente++;
		this.listaLibro = listaLibro;
		this.socketAlCliente = socketAlCliente;
		//Se crea e inicializa el hilo
		hilo = new Thread(this, "Cliente_"+numCliente);
		hilo.start();
	}
	
	/**
	 *Método run() del hilo HiloLibro.
	 *Cada vez que el hilo esté activo, se ejecutará este código
	 */
	public void run() {
		//Se crean las variables donde se almacenan la E/S de comunicación
		//servidor-cliente
		PrintStream salida = null;
		InputStreamReader entrada = null;
		BufferedReader entradaBuffer = null;
		
		//Variable boolean que permite ejecutar el código en bucle
		//hasta que se seleccione la opción salir (5)
		boolean continuar=true;
		
		try {		
			
			//Salida del servidor al cliente
			salida = new PrintStream(socketAlCliente.getOutputStream());
			//Entrada del servidor al cliente
			entrada = new InputStreamReader(socketAlCliente.getInputStream());
			entradaBuffer = new BufferedReader(entrada);
			//String con el mensaje enviado y recibido en la comunicación
			//servidor cliente
			String mensajeRecibido = "";
			String mensajeEnviado="";
				
			while(continuar) { //Mientras que no se seleccione Salir:
	
				//Mensaje recibido del cliente
				mensajeRecibido = entradaBuffer.readLine();
				//Se quita el formato opcion-contenido
				String mensajeSplit[] = mensajeRecibido.split("-");
				String opcion = mensajeSplit[0];
				String contenido = mensajeSplit[1];
			
				//Se analizan las opciones del menú
				switch(opcion) {
				case "1":
					//Buscar libro por ISBN
					//El mensaje se envia en formato isbn-autor-titulo-precio
					mensajeEnviado=listaLibro.formatoLibro(listaLibro.buscarPorIsbn(contenido));					
					break;
				
				case "2":
					//Buscar libro por título
					//El mensaje se envia en formato isbn-autor-titulo-precio
					mensajeEnviado=listaLibro.formatoLibro(listaLibro.buscarPorTitulo(contenido));					
					break;
				case "3":
					//Buscar libro por autor
					//El mensaje se envia en formato isbn-autor-titulo-precio
					mensajeEnviado=listaLibro.formatoLibros(listaLibro.buscarPorAutor(contenido));
					break;
				case "4":
					//Añadir un nuevo libro:
					listaLibro.nuevoLibro(mensajeSplit[1],mensajeSplit[2],mensajeSplit[3],Double.parseDouble(mensajeSplit[4]));
					mensajeEnviado= "Añadido: "+listaLibro.listaLibros.get(listaLibro.listaLibros.size()-1).toString();
					break;
				case "5":
					//Salir del menú
					mensajeEnviado="Salir";
					//Se sale del bucle
					continuar=false;
					break;
				default:
					//Opción no contemplada en el menú
					mensajeEnviado="Incorrecto";
				}
				
				salida.println(mensajeEnviado);			
				
			}				
				//Cerramos el socket
				socketAlCliente.close();			
			
		} catch (IOException e) {
			System.err.println("SERVIDOR: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("SERVIDOR: Error");
			e.printStackTrace();
		}
	}
}
