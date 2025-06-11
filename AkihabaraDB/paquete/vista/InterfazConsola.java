package paquete.vista;

import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import paquete.modelo.ProductoOtaku;
import paquete.modelo.ClienteOtaku;  // Asegúrate de tener esta clase creada

public class InterfazConsola {
    private Scanner scanner;

    public InterfazConsola() {
        // Inicializa el scanner para leer la entrada del usuario
        scanner = new Scanner(System.in);
    }

    // Menú principal para seleccionar módulo (productos, clientes, salir)
    public void mostrarMenuPrincipal() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Gestión de Productos");
        System.out.println("2. Gestión de Clientes");
        System.out.println("3. Salir");
        System.out.print("Elige una opción: ");
    }

    // Lee y valida la opción del menú principal (1 a 3)
    public int leerOpcionMenuPrincipal() {
        int opcion = -1;
        while (opcion == -1) {
            String entrada = scanner.nextLine();
            try {
                opcion = Integer.parseInt(entrada);
                if (opcion < 1 || opcion > 3) {
                    System.out.print("Opción inválida. Por favor, ingresa un número entre 1 y 3: ");
                    opcion = -1;
                }
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Escribe un número válido: ");
            }
        }
        return opcion;
    }

    // Menú de opciones para la gestión de productos
    public void mostrarMenuProductos() {
        System.out.println("\n=== MENÚ PRODUCTOS ===");
        System.out.println("1. Añadir nuevo producto al inventario.");
        System.out.println("2. Consultar un producto específico por su ID.");
        System.out.println("3. Ver todos los productos del inventario.");
        System.out.println("4. Actualizar la información de un producto existente.");
        System.out.println("5. Eliminar un producto del inventario.");
        System.out.println("6. Generar descripción con IA.");
        System.out.println("7. Sugerir categoría con IA.");
        System.out.println("8. Volver al menú principal.");
        System.out.print("Elige una opción: ");
    }

    // Lee y valida la opción del menú productos (1 a 8)
    public int leerOpcionProductos() {
        int opcion = -1;
        while (opcion == -1) {
            String entrada = scanner.nextLine();
            try {
                opcion = Integer.parseInt(entrada);
                if (opcion < 1 || opcion > 8) {
                    System.out.print("Opción inválida. Por favor, ingresa un número entre 1 y 8: ");
                    opcion = -1;
                }
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Escribe un número válido: ");
            }
        }
        return opcion;
    }

    // Menú de opciones para la gestión de clientes
    public void mostrarMenuClientes() {
        System.out.println("\n=== MENÚ CLIENTES ===");
        System.out.println("1. Añadir nuevo cliente.");
        System.out.println("2. Consultar cliente por ID.");
        System.out.println("3. Ver todos los clientes.");
        System.out.println("4. Actualizar cliente.");
        System.out.println("5. Eliminar cliente.");
        System.out.println("6. Volver al menú principal.");
        System.out.print("Elige una opción: ");
    }

    // Lee y valida la opción del menú clientes (1 a 6)
    public int leerOpcionClientes() {
        int opcion = -1;
        while (opcion == -1) {
            String entrada = scanner.nextLine();
            try {
                opcion = Integer.parseInt(entrada);
                if (opcion < 1 || opcion > 6) {
                    System.out.print("Opción inválida. Por favor, ingresa un número entre 1 y 6: ");
                    opcion = -1;
                }
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Escribe un número válido: ");
            }
        }
        return opcion;
    }

    // Muestra la lista completa de productos, o mensaje si está vacía
    public void mostrarListaProductos(List<ProductoOtaku> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay productos para mostrar.");
        } else {
            System.out.println("\n--- Lista de Productos ---");
            for (ProductoOtaku p : lista) {
                System.out.println(formatearProducto(p));
            }
        }
    }

    // Muestra un solo producto o mensaje si no existe
    public void mostrarProducto(ProductoOtaku p) {
        if (p != null) {
            System.out.println(formatearProducto(p));
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    // Pide al usuario los datos necesarios para crear un nuevo producto
    public ProductoOtaku pedirDatosProducto() {
        System.out.println("\n--- Nuevo Producto ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Categoría: ");
        String categoria = scanner.nextLine();

        double precio = -1;
        while (precio < 0) {
            System.out.print("Precio: ");
            String inputPrecio = scanner.nextLine();
            try {
                precio = Double.parseDouble(inputPrecio);
                if (precio < 0) {
                    System.out.println("El precio debe ser un número positivo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingresa un número válido para el precio.");
            }
        }

        int stock = -1;
        while (stock < 0) {
            System.out.print("Stock: ");
            String inputStock = scanner.nextLine();
            try {
                stock = Integer.parseInt(inputStock);
                if (stock < 0) {
                    System.out.println("El stock debe ser un número entero positivo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingresa un número entero válido para el stock.");
            }
        }

        // Se devuelve un nuevo objeto ProductoOtaku con ID 0 (que debe asignar la base)
        return new ProductoOtaku(0, nombre, categoria, precio, stock);
    }

    // Solicita al usuario un ID de producto válido (entero positivo)
    public int pedirIdProducto() {
        int id = -1;
        while (id < 0) {
            System.out.print("Introduce el ID del producto: ");
            String input = scanner.nextLine();
            try {
                id = Integer.parseInt(input);
                if (id < 0) {
                    System.out.println("El ID debe ser un número positivo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingresa un número válido para el ID.");
            }
        }
        return id;
    }

    // Solicita al usuario el nombre de un producto (para búsqueda o creación)
    public String pedirNombreBusqueda() {
        System.out.print("Introduce el nombre del nuevo producto: ");
        return scanner.nextLine();
    }

    // Muestra la lista completa de clientes, o mensaje si está vacía
    public void mostrarListaClientes(List<ClienteOtaku> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay clientes para mostrar.");
        } else {
            System.out.println("\n--- Lista de Clientes ---");
            for (ClienteOtaku c : lista) {
                System.out.println(formatearCliente(c));
            }
        }
    }

    // Muestra un cliente o mensaje si no existe
    public void mostrarCliente(ClienteOtaku c) {
        if (c != null) {
            System.out.println(formatearCliente(c));
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    // Pide los datos necesarios para crear un nuevo cliente
    public ClienteOtaku pedirDatosCliente() {
        System.out.println("\n--- Nuevo Cliente ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        LocalDate fechaRegistro = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Valida que la fecha esté en formato yyyy-MM-dd
        while (fechaRegistro == null) {
            System.out.print("Fecha de registro (yyyy-MM-dd): ");
            String fechaStr = scanner.nextLine();
            try {
                fechaRegistro = LocalDate.parse(fechaStr, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Por favor, ingresa la fecha con formato yyyy-MM-dd.");
            }
        }

        // Se devuelve un nuevo objeto ClienteOtaku con ID 0 (que debe asignar la base)
        return new ClienteOtaku(0, nombre, email, telefono, fechaRegistro);
    }

    // Solicita un ID de cliente válido (entero positivo)
    public int pedirIdCliente() {
        int id = -1;
        while (id < 0) {
            System.out.print("Introduce el ID del cliente: ");
            String input = scanner.nextLine();
            try {
                id = Integer.parseInt(input);
                if (id < 0) {
                    System.out.println("El ID debe ser un número positivo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingresa un número válido para el ID.");
            }
        }
        return id;
    }

    // Mensaje genérico para mostrar información al usuario
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    // Método privado para formatear la información de un producto en una sola línea
    private String formatearProducto(ProductoOtaku p) {
        return String.format("ID: %d | Nombre: %s | Categoría: %s | Precio: %.2f€ | Stock: %d",
                p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock());
    }

    // Método privado para formatear la información de un cliente en una sola línea
    private String formatearCliente(ClienteOtaku c) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaFormateada = (c.getFechaRegistro() != null) ? c.getFechaRegistro().format(formatter) : "N/A";
        return String.format("ID: %d | Nombre: %s | Email: %s | Teléfono: %s | Fecha de Registro: %s",
                c.getId(), c.getNombre(), c.getEmail(), c.getTelefono(), fechaFormateada);
    }
}
