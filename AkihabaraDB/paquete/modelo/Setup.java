package paquete.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Setup {

    // Método para cargar productos de ejemplo en la base de datos
    public void cargarProductosEjemplo() {
        ProductoDAO productoDAO = new ProductoDAO();

        System.out.println("Comprobando y agregando productos de ejemplo...");

        // Array con productos de ejemplo, con id 0 (para que la BD asigne el id)
        ProductoOtaku[] productosEjemplo = {
            new ProductoOtaku(0, "Figura de Anya Forger", "Figura", 59.95, 8),
            new ProductoOtaku(0, "Manga Chainsaw Man Vol.1", "Manga", 9.99, 20),
            new ProductoOtaku(0, "Póster Studio Ghibli Colección", "Póster", 15.50, 15)
        };

        // Para cada producto, se busca en la BD si ya existe un nombre igual
        for (ProductoOtaku producto : productosEjemplo) {
            List<ProductoOtaku> encontrados = productoDAO.buscarProductosPorNombre(producto.getNombre());
            boolean existeExacto = false;
            // Recorremos los productos encontrados para verificar coincidencia exacta ignorando mayúsculas/minúsculas
            for (ProductoOtaku p : encontrados) {
                if (p.getNombre().equalsIgnoreCase(producto.getNombre())) {
                    existeExacto = true;
                    break;
                }
            }

            // Si no existe un producto con el mismo nombre, se agrega a la base
            if (!existeExacto) {
                productoDAO.agregarProducto(producto);
                System.out.println("Producto agregado: " + producto.getNombre());
            } else {
                System.out.println("Producto ya existe, no se agrega: " + producto.getNombre());
            }
        }

        System.out.println("Carga de productos completada.");
    }

    // Método para cargar clientes de ejemplo en la base de datos
    public void cargarClientesEjemplo() {
        ClienteDAO clienteDAO = new ClienteDAO();

        System.out.println("Comprobando y agregando clientes de ejemplo...");

        // Lista de clientes de ejemplo con id definido y fecha actual
        List<ClienteOtaku> clientesEjemplo = new ArrayList<>();
        clientesEjemplo.add(new ClienteOtaku(1, "Sakura Haruno", "sakura@example.com", "123456789", LocalDate.now()));
        clientesEjemplo.add(new ClienteOtaku(2, "Naruto Uzumaki", "naruto@example.com", "987654321", LocalDate.now()));
        clientesEjemplo.add(new ClienteOtaku(3, "Ichigo Kurosaki", "ichigo@example.com", "555666777", LocalDate.now()));

        // Para cada cliente, buscamos si ya existe uno con el mismo nombre
        for (ClienteOtaku cliente : clientesEjemplo) {
            List<ClienteOtaku> encontrados = clienteDAO.buscarClientesPorNombre(cliente.getNombre());
            boolean existeExacto = false;
            // Recorremos los clientes encontrados para verificar coincidencia exacta ignorando mayúsculas/minúsculas
            for (ClienteOtaku c : encontrados) {
                if (c.getNombre().equalsIgnoreCase(cliente.getNombre())) {
                    existeExacto = true;
                    break;
                }
            }

            // Si no existe cliente con ese nombre, se agrega
            if (!existeExacto) {
                clienteDAO.agregarCliente(cliente);
                System.out.println("Cliente agregado: " + cliente.getNombre());
            } else {
                System.out.println("Cliente ya existe, no se agrega: " + cliente.getNombre());
            }
        }

        System.out.println("Carga de clientes completada.");
    }

    // Método de utilidad que ejecuta la carga de productos y clientes juntos
    public void cargarDatosEjemplo() {
        cargarProductosEjemplo();
        cargarClientesEjemplo();
    }
}
