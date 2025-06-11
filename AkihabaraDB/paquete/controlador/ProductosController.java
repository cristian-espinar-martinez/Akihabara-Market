package paquete.controlador;

import paquete.modelo.ProductoDAO;
import paquete.modelo.ProductoOtaku;
import paquete.vista.InterfazConsola;
import paquete.IA.IA;  // Importa la clase IA para usarla

import java.util.List;

public class ProductosController {

    private InterfazConsola interfaz; // Para mostrar y leer datos en consola
    private ProductoDAO productoDAO; // Para manejar datos de productos
    private IA ia;  // Instancia para usar IA en ciertas funciones

    public ProductosController() {
        interfaz = new InterfazConsola(); // Crear interfaz de consola
        productoDAO = new ProductoDAO(); // Crear gestor de productos
        ia = new IA();  // Crear instancia de IA
    }

    // Método que muestra el menú y controla todo el flujo de productos
    public void mostrarMenu() {
        int opcion;
        do {
            interfaz.mostrarMenuProductos(); // Mostrar opciones del menú
            opcion = interfaz.leerOpcionProductos(); // Leer opción del usuario

            switch (opcion) {
                case 1: // Añadir producto
                    ProductoOtaku nuevo = interfaz.pedirDatosProducto(); // Pedir datos
                    productoDAO.agregarProducto(nuevo); // Guardar producto
                    interfaz.mostrarMensaje("Producto agregado correctamente."); // Confirmar
                    break;

                case 2: // Consultar producto por ID
                    int idBuscar = interfaz.pedirIdProducto(); // Pedir ID
                    ProductoOtaku productoBuscado = productoDAO.obtenerProductoPorId(idBuscar); // Buscar
                    interfaz.mostrarProducto(productoBuscado); // Mostrar info
                    break;

                case 3: // Ver todos los productos
                    List<ProductoOtaku> lista = productoDAO.obtenerTodosLosProductos(); // Lista productos
                    interfaz.mostrarListaProductos(lista); // Mostrar lista
                    break;

                case 4: // Actualizar producto
                    int idActualizar = interfaz.pedirIdProducto(); // Pedir ID
                    ProductoOtaku prodActualizar = productoDAO.obtenerProductoPorId(idActualizar); // Buscar
                    if (prodActualizar != null) {
                        interfaz.mostrarMensaje("Introduce los nuevos datos:"); // Avisar
                        ProductoOtaku actualizado = interfaz.pedirDatosProducto(); // Pedir nuevos datos
                        actualizado.setId(idActualizar); // Mantener ID
                        if (productoDAO.actualizarProducto(actualizado)) { // Intentar actualizar
                            interfaz.mostrarMensaje("Producto actualizado correctamente."); // Confirmar
                        } else {
                            interfaz.mostrarMensaje("Error al actualizar el producto."); // Error
                        }
                    } else {
                        interfaz.mostrarMensaje("Producto no encontrado."); // No existe
                    }
                    break;

                case 5: // Eliminar producto
                    int idEliminar = interfaz.pedirIdProducto(); // Pedir ID
                    if (productoDAO.eliminarProducto(idEliminar)) { // Intentar borrar
                        interfaz.mostrarMensaje("Producto eliminado correctamente."); // Confirmar
                    } else {
                        interfaz.mostrarMensaje("No se pudo eliminar el producto o no existe."); // Error
                    }
                    break;

                case 6: // Generar descripción con IA
                    int idDesc = interfaz.pedirIdProducto(); // Pedir ID
                    ProductoOtaku prodDesc = productoDAO.obtenerProductoPorId(idDesc); // Buscar producto
                    if (prodDesc != null) {
                    	// Crear prompt para IA con nombre y categoría
                    	String promptDesc = String.format(
                    		    "Por favor, genera una descripción comercial breve, atractiva y profesional en español para el producto otaku llamado '%s', que pertenece a la categoría '%s'.",
                    		    prodDesc.getNombre(), prodDesc.getCategoria());
                        interfaz.mostrarMensaje("Generando descripción con IA..."); // Avisar
                        String descripcion = ia.obtenerRespuesta(promptDesc); // Obtener texto IA
                        interfaz.mostrarMensaje("Descripción generada:"); // Mostrar resultado
                        interfaz.mostrarMensaje(descripcion);
                    } else {
                        interfaz.mostrarMensaje("Producto no encontrado."); // No existe
                    }
                    break;

                case 7: // Sugerir categoría con IA
                    String nombreNuevo = interfaz.pedirNombreBusqueda(); // Pedir nombre para buscar
                    // Crear prompt para sugerir categoría
                    String promptCat = String.format(
                        "Para un producto otaku llamado '%s', sugiere una categoría adecuada de esta lista: Figura, Manga, Póster, Llavero, Ropa, Videojuego, Otro.",
                        nombreNuevo);
                    interfaz.mostrarMensaje("Generando sugerencia de categoría con IA..."); // Avisar
                    String categoriaSugerida = ia.obtenerRespuesta(promptCat); // Obtener respuesta IA
                    interfaz.mostrarMensaje("Categoría sugerida:"); // Mostrar sugerencia
                    interfaz.mostrarMensaje(categoriaSugerida);
                    break;

                case 8: // Salir del menú
                    interfaz.mostrarMensaje("Saliendo de la aplicación..."); // Avisar salida
                    break;

                default:
                    interfaz.mostrarMensaje("Opción inválida. Intenta de nuevo."); // Opción incorrecta
                    break;
            }
        } while (opcion != 8); // Repetir hasta salir
    }
}
