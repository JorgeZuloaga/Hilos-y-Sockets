package entidad;

public class Libro {
	
	//Creo los atributos de la clase Libro
	
	private String isbn;
	private String autor;
	private String titulo;
	private double precio;
	
	//Creo el constructor de la clase Libro
	
	public Libro(String isbn, String autor, String titulo, double precio) {
		super();
		this.isbn = isbn;
		this.autor = autor;
		this.titulo = titulo;
		this.precio = precio;
	}
	
	//Creo los getter y setter de la clase Libro

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	//Creo método toString de la clase Libro

	@Override
	public String toString() {
		return "Libro [isbn=" + isbn + ", autor=" + autor + ", titulo=" + titulo + ", precio=" + precio + "]";
	}	

}
