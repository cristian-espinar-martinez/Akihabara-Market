package paquete.controlador;

import paquete.modelo.ClienteDAO;
import paquete.modelo.ClienteOtaku;
import paquete.vista.InterfazConsola;

import java.util.List;

public class ClientesController {

    private InterfazConsola interfaz; // Para interactuar con el usuario
    private ClienteDAO clienteDAO; // Para manejar datos de clientes

    public ClientesController() {
        interfaz = new InterfazConsola(); // Crear la interfaz de consola
        clienteDAO = new ClienteDAO(); // Crear objeto para gestionar clientes
    }

    public void mostrarMenu() {
        int opcion;
        do {
            interfaz.mostrarMenuClientes(); // Mostrar opciones al usuario
            opcion = interfaz.leerOpcionClientes(); // Leer la opción elegida

            switch (opcion) {
                case 1: // Añadir nuevo cliente
                    ClienteOtaku nuevo = interfaz.pedirDatosCliente(); // Pedir datos al usuario
                    clienteDAO.agregarCliente(nuevo); // Guardar cliente nuevo
                    interfaz.mostrarMensaje("Cliente agregado correctamente."); // Confirmar
                    break;

                case 2: // Consultar cliente por ID
                    int idBuscar = interfaz.pedirIdCliente(); // Pedir ID
                    ClienteOtaku clienteBuscado = clienteDAO.obtenerClientePorId(idBuscar); // Buscar cliente
                    interfaz.mostrarCliente(clienteBuscado); // Mostrar datos
                    break;

                case 3: // Ver todos los clientes
                    List<ClienteOtaku> lista = clienteDAO.obtenerTodosLosClientes(); // Obtener lista
                    interfaz.mostrarListaClientes(lista); // Mostrar todos
                    break;

                case 4: // Actualizar cliente
                    int idActualizar = interfaz.pedirIdCliente(); // Pedir ID a actualizar
                    ClienteOtaku clienteActualizar = clienteDAO.obtenerClientePorId(idActualizar); // Buscar cliente
                    if (clienteActualizar != null) {
                        interfaz.mostrarMensaje("Introduce los nuevos datos:"); // Avisar
                        ClienteOtaku actualizado = interfaz.pedirDatosCliente(); // Pedir nuevos datos
                        actualizado.setId(idActualizar); // Mantener ID igual
                        actualizado.setFechaRegistro(clienteActualizar.getFechaRegistro()); // Mantener fecha original
                        if (clienteDAO.actualizarCliente(actualizado)) { // Intentar actualizar
                            interfaz.mostrarMensaje("Cliente actualizado correctamente."); // Confirmar
                        } else {
                            interfaz.mostrarMensaje("Error al actualizar el cliente."); // Avisar error
                        }
                    } else {
                        interfaz.mostrarMensaje("Cliente no encontrado."); // Avisar si no existe
                    }
                    break;

                case 5: // Eliminar cliente
                    int idEliminar = interfaz.pedirIdCliente(); // Pedir ID a borrar
                    if (clienteDAO.eliminarCliente(idEliminar)) { // Intentar eliminar
                        interfaz.mostrarMensaje("Cliente eliminado correctamente."); // Confirmar
                    } else {
                        interfaz.mostrarMensaje("No se pudo eliminar el cliente o no existe."); // Avisar error
                    }
                    break;

                case 6: // Salir del menú clientes
                    interfaz.mostrarMensaje("Volviendo al menú principal..."); // Avisar salida
                    break;

                default:
                    interfaz.mostrarMensaje("Opción inválida. Intenta de nuevo."); // Opción incorrecta
                    break;
            }
        } while (opcion != 6); // Repetir hasta salir
    }
}
