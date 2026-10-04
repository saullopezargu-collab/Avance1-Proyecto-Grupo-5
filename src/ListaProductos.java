import java.time.LocalDate;

//Lista enlazada simple de productos; su único atributo es la referencia al primero. El nombre identifica a cada producto.
public class ListaProductos {
    private Producto primero;

    //Constructor: crea una lista vacía.
    public ListaProductos() {
        this.primero = null;
    }

    //Indica si la lista no tiene productos.
    public boolean estaVacia() {
        return primero == null;
    }

    //Inserta un producto al inicio de la lista.
    public void insertarProductoInicio(Producto nuevoProducto) {
        nuevoProducto.setSiguiente(primero);
        primero = nuevoProducto;
    }

    //Inserta un producto al final de la lista.
    public void insertarProductoFinal(Producto nuevoProducto) {
        if (primero == null) {
            primero = nuevoProducto;
            return;
        }
        Producto productoTemp = primero;
        while (productoTemp.getSiguiente() != null) {
            productoTemp = productoTemp.getSiguiente();
        }
        productoTemp.setSiguiente(nuevoProducto);
    }

    //Busca un producto por nombre; retorna null si no existe.
    public Producto buscarProducto(String nombreBuscar) {
        Producto productoActual = primero;
        while (productoActual != null
                && !productoActual.getNombre().equalsIgnoreCase(nombreBuscar)) {
            productoActual = productoActual.getSiguiente();
        }
        return productoActual;
    }

    //Modifica los datos de un producto; retorna el producto o null si no existe.
    public Producto modificarProducto(String nombreModificar, String nuevoNombre, double nuevoPrecio,
                                      String nuevaCategoria, LocalDate nuevaFecha, int nuevaCantidad) {
        Producto producto = buscarProducto(nombreModificar);
        if (producto != null) {
            producto.setNombre(nuevoNombre);
            producto.setPrecio(nuevoPrecio);
            producto.setCategoria(nuevaCategoria);
            producto.setFechaVencimiento(nuevaFecha);
            producto.setCantidad(nuevaCantidad);
        }
        return producto;
    }

    //Añade la ruta de una imagen a un producto; retorna false si el producto no existe.
    public boolean agregarImagen(String nombreProducto, String ruta) {
        Producto producto = buscarProducto(nombreProducto);
        if (producto == null) {
            return false;
        }
        producto.agregarImagen(ruta);
        return true;
    }

    //Elimina un producto por nombre y lo retorna; retorna null si no existe.
    public Producto eliminarProducto(String nombreEliminar) {
        if (primero == null) {
            return null;
        }
        if (primero.getNombre().equalsIgnoreCase(nombreEliminar)) {
            Producto eliminado = primero;
            primero = primero.getSiguiente();
            eliminado.setSiguiente(null);
            return eliminado;
        }
        Producto anterior = primero;
        Producto actual = primero.getSiguiente();
        while (actual != null && !actual.getNombre().equalsIgnoreCase(nombreEliminar)) {
            anterior = actual;
            actual = actual.getSiguiente();
        }
        if (actual != null) {
            anterior.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(null);
        }
        return actual;
    }

    //Recorre la lista e imprime cada producto.
    public void mostrarLista() {
        if (primero == null) {
            System.out.println("La lista se encuentra vacía.");
            return;
        }
        Producto productoActual = primero;
        while (productoActual != null) {
            System.out.println(productoActual);
            productoActual = productoActual.getSiguiente();
        }
    }

    //Recorre la lista e imprime el costo total de cada producto y el costo total acumulado.
    public void mostrarReporteCostos() {
        if (primero == null) {
            System.out.println("La lista se encuentra vacía.");
            return;
        }
        double costoAcumulado = 0;
        Producto productoActual = primero;
        System.out.println("------------------------------");
        while (productoActual != null) {
            double costo = productoActual.calcularCostoTotal();
            System.out.println(productoActual.getNombre() + ": " + productoActual.getCantidad() + " x "
                    + productoActual.getPrecio() + " = "
                    + costo);
            costoAcumulado += costo;
            productoActual = productoActual.getSiguiente();
        }
        System.out.println("------------------------------");
        System.out.println("COSTO TOTAL ACUMULADO: " + costoAcumulado);
    }
}
