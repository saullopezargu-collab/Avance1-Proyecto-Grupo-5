import java.time.LocalDate;
import java.util.ArrayList;

//Clase nodo de la lista enlazada simple: cada Producto guarda sus datos y la referencia al siguiente.
public class Producto {
    //Atributos.
    private String nombre;
    private double precio;
    private String categoria;
    //Es null cuando el producto no vence.
    private LocalDate fechaVencimiento;
    private int cantidad;
    //Rutas de las imágenes, guardadas en la carpeta imagenes/ del proyecto.
    private final ArrayList<String> listaImagenes;
    private Producto siguiente;

    //Constructor: crea un producto sin imágenes y sin siguiente.
    public Producto(String nombre, double precio, String categoria,
                    LocalDate fechaVencimiento, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.fechaVencimiento = fechaVencimiento;
        this.cantidad = cantidad;
        this.listaImagenes = new ArrayList<>();
        this.siguiente = null;
    }

    //Getters.
    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public ArrayList<String> getListaImagenes() {
        return listaImagenes;
    }

    public Producto getSiguiente() {
        return siguiente;
    }

    //Setters.
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setSiguiente(Producto siguiente) {
        this.siguiente = siguiente;
    }

    //Añade la ruta de una imagen a la lista de imágenes.
    public void agregarImagen(String ruta) {
        listaImagenes.add(ruta);
    }

    //Retorna el costo total del producto (precio por cantidad).
    public double calcularCostoTotal() {
        return precio * cantidad;
    }

    @Override
    //Retorna los datos del producto en una sola línea.
    public String toString() {
        String vencimiento = (fechaVencimiento == null) ? "No aplica" : fechaVencimiento.toString();
        String imagenes = listaImagenes.isEmpty() ? "Sin imágenes" : listaImagenes.toString();
        return "Producto: " + nombre
                + " | Precio: " + precio
                + " | Categoría: " + categoria
                + " | Vencimiento: " + vencimiento
                + " | Cantidad: " + cantidad
                + " | Imágenes: " + imagenes;
    }
}
