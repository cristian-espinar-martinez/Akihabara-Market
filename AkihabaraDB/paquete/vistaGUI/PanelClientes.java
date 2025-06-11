package paquete.vistaGUI;

import paquete.modelo.ClienteDAO;
import paquete.modelo.ClienteOtaku;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class PanelClientes extends JPanel {
    // Tabla para mostrar los clientes
    private JTable tabla;
    // Modelo de la tabla para manipular datos
    private DefaultTableModel modeloTabla;
    // Campos de texto para los datos de cliente
    private JTextField campoId, campoNombre, campoEmail, campoTelefono;
    // DAO para manejar la lógica de datos de clientes
    private ClienteDAO clienteDAO;

    public PanelClientes() {
        clienteDAO = new ClienteDAO(); // Instanciamos el DAO para acceder a los datos

        setLayout(new BorderLayout()); // Layout principal del panel

        // Crear el modelo de tabla con columnas para los datos de cliente
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Email", "Teléfono", "Fecha Registro"}, 0);
        tabla = new JTable(modeloTabla); // Crear la tabla con ese modelo
        JScrollPane scroll = new JScrollPane(tabla); // Scroll para la tabla
        add(scroll, BorderLayout.CENTER); // Añadir el scroll al centro del panel

        // Crear panel para el formulario de datos con 6 filas y 2 columnas
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2));

        // Campos de entrada para el formulario
        campoId = new JTextField();
        campoNombre = new JTextField();
        campoEmail = new JTextField();
        campoTelefono = new JTextField();

        // Añadir etiquetas y campos al panel de formulario
        panelFormulario.add(new JLabel("ID (solo para actualizar/buscar):"));
        panelFormulario.add(campoId);
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(campoNombre);
        panelFormulario.add(new JLabel("Email:"));
        panelFormulario.add(campoEmail);
        panelFormulario.add(new JLabel("Teléfono:"));
        panelFormulario.add(campoTelefono);

        // Crear botones para las acciones CRUD
        JButton btnAgregar = new JButton("Agregar");
        JButton btnBuscar = new JButton("Buscar por ID");
        JButton btnVerTodos = new JButton("Ver Todos");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");

        // Añadir botones al formulario
        panelFormulario.add(btnAgregar);
        panelFormulario.add(btnBuscar);
        panelFormulario.add(btnVerTodos);
        panelFormulario.add(btnActualizar);
        panelFormulario.add(btnEliminar);

        // Añadir el panel del formulario en la parte inferior (sur)
        add(panelFormulario, BorderLayout.SOUTH);

        // Configurar eventos para los botones llamando a sus respectivos métodos
        btnAgregar.addActionListener(e -> agregarCliente());
        btnBuscar.addActionListener(e -> buscarClientePorId());
        btnVerTodos.addActionListener(e -> cargarClientes());
        btnActualizar.addActionListener(e -> actualizarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());

        cargarClientes(); // Cargar inicialmente todos los clientes en la tabla
    }

    // Método para cargar todos los clientes en la tabla
    private void cargarClientes() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        List<ClienteOtaku> clientes = clienteDAO.obtenerTodosLosClientes(); // Obtener lista de clientes
        for (ClienteOtaku c : clientes) {
            // Añadir cada cliente a la tabla
            modeloTabla.addRow(new Object[]{
                c.getId(),
                c.getNombre(),
                c.getEmail(),
                c.getTelefono(),
                c.getFechaRegistro()
            });
        }
    }

    // Método para agregar un cliente nuevo
    private void agregarCliente() {
        String nombre = campoNombre.getText().trim();
        String email = campoEmail.getText().trim();
        String telefono = campoTelefono.getText().trim();

        // Validar que no haya campos vacíos
        if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios para agregar.");
            return;
        }

        // Crear nuevo cliente con fecha actual
        ClienteOtaku cliente = new ClienteOtaku(0, nombre, email, telefono, LocalDate.now());
        clienteDAO.agregarCliente(cliente); // Guardar en la base de datos (o fuente de datos)
        cargarClientes(); // Refrescar tabla
        limpiarCampos(); // Limpiar formulario
    }

    // Buscar cliente por ID y mostrarlo en la tabla
    private void buscarClientePorId() {
        try {
            int id = Integer.parseInt(campoId.getText().trim());
            ClienteOtaku cliente = clienteDAO.obtenerClientePorId(id);
            if (cliente != null) {
                modeloTabla.setRowCount(0); // Limpiar tabla
                modeloTabla.addRow(new Object[]{
                    cliente.getId(), cliente.getNombre(), cliente.getEmail(), cliente.getTelefono(), cliente.getFechaRegistro()
                });
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado con ID: " + id);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido");
        }
    }

    // Actualizar datos de un cliente existente
    private void actualizarCliente() {
        try {
            int id = Integer.parseInt(campoId.getText().trim());
            String nombre = campoNombre.getText().trim();
            String email = campoEmail.getText().trim();
            String telefono = campoTelefono.getText().trim();

            // Validar que no falten datos
            if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios para actualizar.");
                return;
            }

            // Crear cliente actualizado
            ClienteOtaku cliente = new ClienteOtaku(id, nombre, email, telefono, LocalDate.now());
            clienteDAO.actualizarCliente(cliente); // Actualizar en el DAO
            cargarClientes(); // Refrescar tabla
            limpiarCampos(); // Limpiar formulario
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido");
        }
    }

    // Eliminar cliente por ID
    private void eliminarCliente() {
        try {
            int id = Integer.parseInt(campoId.getText().trim());
            clienteDAO.eliminarCliente(id); // Eliminar en DAO
            cargarClientes(); // Refrescar tabla
            limpiarCampos(); // Limpiar formulario
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido");
        }
    }

    // Limpiar campos del formulario
    private void limpiarCampos() {
        campoId.setText("");
        campoNombre.setText("");
        campoEmail.setText("");
        campoTelefono.setText("");
    }
}
