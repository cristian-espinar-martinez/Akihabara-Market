package paquete.Principal;

import paquete.controlador.ProductosController;
import paquete.controlador.ClientesController;
import paquete.modelo.Setup;
import paquete.vista.InterfazConsola;

public class Principal {

    public static void main(String[] args) {
        // Crear instancia de Setup para cargar datos de ejemplo en la base de datos
        Setup setup = new Setup();
        setup.cargarProductosEjemplo();   // Carga productos de ejemplo
        setup.cargarClientesEjemplo();    // Carga clientes de ejemplo

        // Crear instancia de la interfaz de consola para interactuar con el usuario
        InterfazConsola interfaz = new InterfazConsola();
        int opcion;

        // Bucle principal del programa que muestra el menú y ejecuta la opción elegida
        do {
            interfaz.mostrarMenuPrincipal();               // Mostrar menú principal
            opcion = interfaz.leerOpcionMenuPrincipal();   // Leer opción seleccionada por el usuario

            switch (opcion) {
                case 1:
                    // Opción para manejar productos
                    ProductosController productosController = new ProductosController();
                    productosController.mostrarMenu();       // Mostrar menú de productos
                    break;

                case 2:
                    // Opción para manejar clientes
                    ClientesController clientesController = new ClientesController();
                    clientesController.mostrarMenu();        // Mostrar menú de clientes
                    break;

                case 3:
                    // Opción para salir del programa
                    interfaz.mostrarMensaje("Saliendo del sistema. ¡Hasta pronto!");
                    break;

                default:
                    // Opción inválida
                    interfaz.mostrarMensaje("Opción inválida.");
            }

        } while (opcion != 3); // Repetir hasta que el usuario elija salir (opción 3)
    }
}
