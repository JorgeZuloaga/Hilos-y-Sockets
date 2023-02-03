package servidor;

import java.util.ArrayList;
import java.util.List;

import entidad.Libro;

public class ListaLibros {
	
	static List<Libro> listaLibros; //Lista para almacenar los libros	
	
	static{
		//Se crea el array list de libros
		listaLibros = new ArrayList<Libro>();
		//Se crean 5 Libros con sus correspondientes atributos ISBN, autor, titulo, precio
		Libro l1 = new Libro("9788466367844","George R.R Martin", "Fuego y sangre", 14.95);
		Libro l2 = new Libro("9788401027710","Stephen King", "Cuento de hadas", 23.65);
		Libro l3 = new Libro("9788491738022","George R.R Martin", "Juego de tronos", 14.21);
		Libro l4 = new Libro("9788431673963","Miguel de Cervantes", "Don Quijote de la Mancha", 20.61);
		Libro l5 = new Libro("9788445011119","J.R.R Tolkien","El señor de los anillos", 61.75);
		//Se añaden los libros al array list
		listaLibros.add(l1);
		listaLibros.add(l2);
		listaLibros.add(l3);
		listaLibros.add(l4);
		listaLibros.add(l5);
	}


	/**
	 * Método para buscar un libro por su isbn mediante la coincidencia
	 * con el atributo isbn
	 * @param isbn 
	 * @return objeto tipo Libro
	 */
	public Libro buscarPorIsbn(String isbn) {
		Libro miLibro=null; //Se inicializa sin valor
		for (Libro l : listaLibros) {
			if(l.getIsbn().equals(isbn)) {
				miLibro=l;
			}
		}
		return miLibro;
	}
	

	/**
	 * Método que busca un libro por su título mediante la coincidencia
	 * del atributo titulo
	 * @param titulo
	 * @return objeto tipo Libro
	 */
	public Libro buscarPorTitulo(String titulo) {
		Libro miLibro=null;
		for (Libro l : listaLibros) {
			if(l.getTitulo().equals(titulo)) {
				miLibro=l;
			}
		}
		return miLibro;
	}
	
	/**
	 * Método para buscar un libro por su autor en el listado
	 * mediante la coincidencia con el atributo autor
	 * @param autor
	 * @return Listado con los libros de dicho autor
	 */
	public List<Libro> buscarPorAutor(String autor) {
		List<Libro> misLibros=new ArrayList<Libro>();
		for (Libro l : listaLibros) {
			if (l.getAutor().equals(autor)) {
				misLibros.add(l);
			}
		}		
		return misLibros;
		
	}
	/**
	 * Método que dará formato al mensaje enviado cuando se envía
	 * la información de un solo libro:
	 * isbn-autor-titulo-precio
	 * @param libro
	 * @return cadena de caracteres con el formato acordado
	 */
	public String formatoLibro(Libro libro) {
		return libro.getIsbn()+"-"+libro.getAutor()+"-"+libro.getTitulo()+"-"+libro.getPrecio();
	}
	
	/**
	 * Método que dará formato al mensaje enviado cuando se envía
	 * la información de uno o más libros.
	 * El formato es el mismo que en el método formatoLibro,
	 * separando los distintos libros con el caracter '/':
	 * formatoLibro+formatoLibro
	 * @param listado
	 * @return
	 */
	public String formatoLibros(List<Libro> listado) {
		String librosCadena="";
		for (Libro l : listado) {
			// Se concatenan los libros, separándolos por '/'
			librosCadena+=formatoLibro(l)+"/";
		}
		//Se devuelve la cadena de libros con el mismo autor, eliminando el último '/'
		return librosCadena.substring(0, librosCadena.length()-1);
	}
	
	/**
	 * Método que añade un nuevo libro, recibiendo todos los parámetros
	 * del tipo Libro
	 * @param isbn
	 * @param autor
	 * @param titulo
	 * @param precio
	 */
	public void nuevoLibro (String isbn, String autor, String titulo, double precio) {
		//Se crea un nuevo libro con los datos introducidos
		Libro nuevo = new Libro(isbn, autor, titulo, precio);
		//Se añade a la lista de libros
		listaLibros.add(nuevo);
	}


}
