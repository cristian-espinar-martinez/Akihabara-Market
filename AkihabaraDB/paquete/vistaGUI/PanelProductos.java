package paquete.vistaGUI;

import paquete.modelo.ProductoDAO;
import paquete.modelo.ProductoOtaku;
import paquete.IA.IA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelProductos extends JPanel {
    // Componentes visuales para la tabla y campos de texto
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JTextField campoNombre, campoCategoria, campoPrecio, campoStock, campoBuscar, campoBuscarId;
    private JTextArea campoDescripcionIA;   // Área para mostrar descripción generada por IA
    private JTextField campoCategoriaIA;    // Campo para categoría generada por IA
    private ProductoDAO productoDAO;         // DAO para manejar datos de productos
    private IA ia;                          // Clase para manejar generación IA

    public PanelProductos() {
        productoDAO = new ProductoDAO(); // Inicializa acceso a datos
        ia = new IA();                   // Inicializa sistema IA

        setLayout(new BorderLayout());  // Layout principal

        // Configuración de la tabla con columnas específicas
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Categoría", "Precio", "Stock"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER); // Tabla al centro

        // Panel para formulario con 9 filas y 2 columnas
        JPanel panelFormulario = new JPanel(new GridLayout(9, 2));

        // Campos de entrada para producto
        campoNombre = new JTextField();
        campoCategoria = new JTextField();
        campoPrecio = new JTextField();
        campoStock = new JTextField();
        campoBuscar = new JTextField();
        campoBuscarId = new JTextField();

        // Área de texto para descripción generada por IA, con scroll y configuración
        campoDescripcionIA = new JTextArea(3, 20);
        campoDescripcionIA.setLineWrap(true);
        campoDescripcionIA.setWrapStyleWord(true);
        campoDescripcionIA.setEditable(false);
        JScrollPane scrollDescripcionIA = new JScrollPane(campoDescripcionIA);

        // Campo para categoría generada por IA, solo lectura
        campoCategoriaIA = new JTextField();
        campoCategoriaIA.setEditable(false);

        // Añadir etiquetas y campos al panel formulario
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(campoNombre);
        panelFormulario.add(new JLabel("Categoría:"));
        panelFormulario.add(campoCategoria);
        panelFormulario.add(new JLabel("Precio:"));
        panelFormulario.add(campoPrecio);
        panelFormulario.add(new JLabel("Stock:"));
        panelFormulario.add(campoStock);
        panelFormulario.add(new JLabel("Buscar por nombre:"));
        panelFormulario.add(campoBuscar);
        panelFormulario.add(new JLabel("Buscar por ID:"));
        panelFormulario.add(campoBuscarId);
        panelFormulario.add(new JLabel("Descripción IA sugerida:"));
        panelFormulario.add(scrollDescripcionIA);
        panelFormulario.add(new JLabel("Categoría IA sugerida:"));
        panelFormulario.add(campoCategoriaIA);

        // Crear botones para CRUD y búsqueda
        JButton botonAgregar = new JButton("Agregar");
        JButton botonActualizar = new JButton("Actualizar");
        JButton botonEliminar = new JButton("Eliminar");
        JButton botonBuscar = new JButton("Buscar");
        JButton botonBuscarPorId = new JButton("Buscar por ID");
        JButton botonGenerarIA = new JButton("Generar descripción IA");

        // Panel para los botones con layout Flow
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(botonAgregar);
        panelBotones.add(botonActualizar);
        panelBotones.add(botonEliminar);
        panelBotones.add(botonBuscar);
        panelBotones.add(botonBuscarPorId);
        panelBotones.add(botonGenerarIA);

        // Añadir panel formulario arriba y botones abajo
        add(panelFormulario, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.SOUTH);

        // Eventos para los botones: llaman a métodos específicos
        botonAgregar.addActionListener(e -> agregarProducto());
        botonActualizar.addActionListener(e -> actualizarProducto());
        botonEliminar.addActionListener(e -> eliminarProducto());
        botonBuscar.addActionListener(e -> buscarProducto());
        botonBuscarPorId.addActionListener(e -> buscarProductoPorId());
        botonGenerarIA.addActionListener(e -> generarDescripcionYCategoriaIA());

        // Evento para cuando seleccionan una fila de la tabla, cargar datos en formulario
        tabla.getSelectionModel().addListSelectionListener(e -> cargarProductoSeleccionado());

        cargarProductos(); // Carga inicial de productos en la tabla
    }

    // Carga todos los productos en la tabla
    private void cargarProductos() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        List<ProductoOtaku> productos = productoDAO.obtenerTodosLosProductos();
        for (ProductoOtaku p : productos) {
            modeloTabla.addRow(new Object[]{p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock()});
        }
    }

    // Método para agregar un nuevo producto (con validación de datos numéricos)
    private void agregarProducto() {
        try {
            String nombre = campoNombre.getText();
            String categoria = campoCategoria.getText();
            double precio = Double.parseDouble(campoPrecio.getText());
            int stock = Integer.parseInt(campoStock.getText());
            ProductoOtaku producto = new ProductoOtaku(0, nombre, categoria, precio, stock);
            productoDAO.agregarProducto(producto);
            cargarProductos();
            limpiarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio y Stock deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Actualiza el producto seleccionado
    private void actualizarProducto() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            try {
                int id = (int) modeloTabla.getValueAt(fila, 0);
                String nombre = campoNombre.getText();
                String categoria = campoCategoria.getText();
                double precio = Double.parseDouble(campoPrecio.getText());
                int stock = Integer.parseInt(campoStock.getText());
                ProductoOtaku producto = new ProductoOtaku(id, nombre, categoria, precio, stock);
                productoDAO.actualizarProducto(producto);
                cargarProductos();
                limpiarCampos();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Precio y Stock deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Elimina el producto seleccionado
    private void eliminarProducto() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            int id = (int) modeloTabla.getValueAt(fila, 0);
            productoDAO.eliminarProducto(id);
            cargarProductos();
            limpiarCampos();
        }
    }

    // Buscar productos por nombre
    private void buscarProducto() {
        String nombre = campoBuscar.getText();
        modeloTabla.setRowCount(0);
        List<ProductoOtaku> resultados = productoDAO.buscarProductosPorNombre(nombre);
        for (ProductoOtaku p : resultados) {
            modeloTabla.addRow(new Object[]{p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock()});
        }
    }

    // Buscar producto por ID
    private void buscarProductoPorId() {
        try {
            int id = Integer.parseInt(campoBuscarId.getText().trim());
            ProductoOtaku producto = productoDAO.obtenerProductoPorId(id);

            modeloTabla.setRowCount(0); // Limpiar la tabla

            if (producto != null) {
                modeloTabla.addRow(new Object[]{
                        producto.getId(), producto.getNombre(), producto.getCategoria(),
                        producto.getPrecio(), producto.getStock()
                });
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró producto con ID: " + id,
                        "No encontrado", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Cuando se selecciona una fila en la tabla, cargar los datos del producto en el formulario
    private void cargarProductoSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            campoNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
            campoCategoria.setText(modeloTabla.getValueAt(fila, 2).toString());
            campoPrecio.setText(modeloTabla.getValueAt(fila, 3).toString());
            campoStock.setText(modeloTabla.getValueAt(fila, 4).toString());
            campoDescripcionIA.setText(""); // Limpiar descripción IA
            campoCategoriaIA.setText("");    // Limpiar categoría IA
        }
    }

    // Limpiar todos los campos del formulario y de búsqueda
    private void limpiarCampos() {
        campoNombre.setText("");
        campoCategoria.setText("");
        campoPrecio.setText("");
        campoStock.setText("");
        campoBuscar.setText("");
        campoBuscarId.setText("");
        campoDescripcionIA.setText("");
        campoCategoriaIA.setText("");
    }

    // Genera la descripción y categoría del producto usando IA (simulada)
    private void generarDescripcionYCategoriaIA() {
        String nombreProducto = campoNombre.getText().trim();
        if (nombreProducto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa el nombre del producto para generar la descripción y categoría con IA.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Preguntas que se envían a la IA para generar respuestas
        String preguntaDescripcion = "Genera una descripción breve y atractiva para el producto: " + nombreProducto;
        String preguntaCategoria = "Sugiere una categoría adecuada para el producto: " + nombreProducto;

        // Obtener respuesta de IA
        String descripcionIA = ia.obtenerRespuesta(preguntaDescripcion);
        String categoriaIA = ia.obtenerRespuesta(preguntaCategoria);

        // Mostrar resultados en los campos de texto correspondientes
        campoDescripcionIA.setText(descripcionIA);
        campoCategoriaIA.setText(categoriaIA);
    }
}
