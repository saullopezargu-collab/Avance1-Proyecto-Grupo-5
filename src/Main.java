import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

//Clase principal: contiene el menú de consola y la rutina main().
public class Main {
    //Entrada y salida estándar.
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintStream out = System.out;

    //Lista de productos del inventario.
    private static final ListaProductos listaProductos = new ListaProductos();
    private static final String DIRECTORIO_IMAGENES = "imagenes/";

    //Rutina principal: invoca el menú.
    public static void main(String[] args) throws IOException {
        menu();
    }

    //Menú de consola para interactuar con la lista de productos.
    public static void menu() throws IOException {
        int opcion;
        do {
            out.println("===== GESTIÓN DE INVENTARIO =====");
            out.println("1. Insertar producto al inicio");
            out.println("2. Insertar producto al final");
            out.println("3. Buscar producto");
            out.println("4. Modificar producto");
            out.println("5. Agregar imagen a un producto");
            out.println("6. Eliminar producto");
            out.println("7. Mostrar lista de productos");
            out.println("8. Reporte de costos totales");
            out.println("0. Salir");
            opcion = leerEntero("Seleccione una opción: ");
            out.println();
            switch (opcion) {
                case 1:
                    insertarProducto(true);
                    break;
                case 2:
                    insertarProducto(false);
                    break;
                case 3:
                    buscarProducto();
                    break;
                case 4:
                    modificarProducto();
                    break;
                case 5:
                    agregarImagen();
                    break;
                case 6:
                    eliminarProducto();
                    break;
                case 7:
                    listaProductos.mostrarLista();
                    break;
                case 8:
                    listaProductos.mostrarReporteCostos();
                    break;
                case 0:
                    out.println("¡Hasta pronto!");
                    break;
                default:
                    out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    //Pide los datos de un producto y lo inserta al inicio o al final de la lista.
    private static void insertarProducto(boolean alInicio) throws IOException {
        String nombre = leerTextoNoVacio("Nombre: ");
        if (listaProductos.buscarProducto(nombre) != null) {
            out.println("Ya existe un producto con ese nombre.");
            return;
        }
        double precio = leerDecimal("Precio: ");
        String categoria = leerTextoNoVacio("Categoría: ");
        LocalDate fecha = leerFecha("Fecha de vencimiento (AAAA-MM-DD, ENTER si no aplica): ");
        int cantidad = leerEntero("Cantidad: ");

        Producto producto = new Producto(nombre, precio, categoria, fecha, cantidad);
        if (alInicio) {
            listaProductos.insertarProductoInicio(producto);
        } else {
            listaProductos.insertarProductoFinal(producto);
        }
        out.println("Producto insertado correctamente.");
    }

    //Busca un producto por nombre e imprime sus datos.
    private static void buscarProducto() throws IOException {
        Producto producto = listaProductos.buscarProducto(leerTextoNoVacio("Nombre del producto: "));
        if (producto == null) {
            out.println("El producto no se encontró en la lista.");
        } else {
            out.println(producto);
        }
    }

    //Pide los nuevos datos de un producto y los actualiza.
    private static void modificarProducto() throws IOException {
        String nombre = leerTextoNoVacio("Nombre del producto a modificar: ");
        if (listaProductos.buscarProducto(nombre) == null) {
            out.println("El producto no se encontró en la lista.");
            return;
        }
        String nuevoNombre = leerTextoNoVacio("Nuevo nombre: ");
        Producto existente = listaProductos.buscarProducto(nuevoNombre);
        if (existente != null && !nuevoNombre.equalsIgnoreCase(nombre)) {
            out.println("Ya existe otro producto con ese nombre.");
            return;
        }
        double precio = leerDecimal("Nuevo precio: ");
        String categoria = leerTextoNoVacio("Nueva categoría: ");
        LocalDate fecha = leerFecha("Nueva fecha de vencimiento (AAAA-MM-DD, ENTER si no aplica): ");
        int cantidad = leerEntero("Nueva cantidad: ");

        listaProductos.modificarProducto(nombre, nuevoNombre, precio, categoria, fecha, cantidad);
        out.println("Producto modificado correctamente.");
    }

    //Agrega la ruta de una imagen a un producto.
    private static void agregarImagen() throws IOException {
        String nombre = leerTextoNoVacio("Nombre del producto: ");
        if (listaProductos.buscarProducto(nombre) == null) {
            out.println("El producto no se encontró en la lista.");
            return;
        }
        String archivo = leerTextoNoVacio("Archivo de imagen (ej. leche.jpg, en '" + DIRECTORIO_IMAGENES + "'): ");
        String ruta = DIRECTORIO_IMAGENES + archivo;
        if (!new File(ruta).exists()) {
            out.println("Aviso: el archivo '" + ruta + "' no existe en la carpeta del proyecto.");
        }
        listaProductos.agregarImagen(nombre, ruta);
        out.println("Imagen agregada correctamente.");
    }

    //Elimina un producto por nombre.
    private static void eliminarProducto() throws IOException {
        Producto eliminado = listaProductos.eliminarProducto(leerTextoNoVacio("Nombre del producto a eliminar: "));
        if (eliminado == null) {
            out.println("El producto no se encontró en la lista.");
        } else {
            out.println("Se eliminó el producto '" + eliminado.getNombre() + "'.");
        }
    }

    //Lee una línea; si la entrada se cierra, termina el programa.
    private static String leerLinea() throws IOException {
        String linea = in.readLine();
        if (linea == null) {
            out.println("Entrada finalizada. Cerrando el programa.");
            System.exit(0);
        }
        return linea;
    }

    //Métodos de lectura con validación.
    private static int leerEntero(String mensaje) throws IOException {
        while (true) {
            out.print(mensaje);
            String linea = leerLinea();
            try {
                int valor = Integer.parseInt(linea.trim());
                if (valor >= 0) {
                    return valor;
                }
                out.println("El valor no puede ser negativo.");
            } catch (NumberFormatException e) {
                out.println("Entrada inválida. Ingrese un número entero.");
            }
        }
    }

    private static double leerDecimal(String mensaje) throws IOException {
        while (true) {
            out.print(mensaje);
            String linea = leerLinea();
            try {
                double valor = Double.parseDouble(linea.trim().replace(',', '.'));
                if (valor >= 0) {
                    return valor;
                }
                out.println("El valor no puede ser negativo.");
            } catch (NumberFormatException e) {
                out.println("Entrada inválida. Ingrese un número (ej. 1500.50).");
            }
        }
    }

    private static String leerTextoNoVacio(String mensaje) throws IOException {
        while (true) {
            out.print(mensaje);
            String linea = leerLinea().trim();
            if (!linea.isEmpty()) {
                return linea;
            }
            out.println("Este campo no puede estar vacío.");
        }
    }

    //Si el campo se deja vacío retorna null (el producto no vence).
    private static LocalDate leerFecha(String mensaje) throws IOException {
        while (true) {
            out.print(mensaje);
            String linea = leerLinea().trim();
            if (linea.isEmpty()) {
                return null;
            }
            try {
                return LocalDate.parse(linea);
            } catch (DateTimeParseException e) {
                out.println("Fecha inválida. Use el formato AAAA-MM-DD.");
            }
        }
    }
}
