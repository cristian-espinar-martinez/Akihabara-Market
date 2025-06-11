package paquete.vistaGUI;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JTabbedPane tabbedPane;

    public MainFrame() {
        // Configuración básica del JFrame principal
        setTitle("Akihabara Market - Inventario Otaku"); // Título de la ventana
        setSize(900, 600); // Tamaño inicial de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Termina la aplicación al cerrar la ventana
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        tabbedPane = new JTabbedPane(); // Componente para pestañas en la ventana

        // Creación del panel para la gestión de productos (con funciones CRUD y características IA)
        PanelProductos panelProductos = new PanelProductos();
        tabbedPane.addTab("Gestión de Productos", panelProductos); // Añade la pestaña de productos

        // Creación del panel para la gestión de clientes (CRUD)
        PanelClientes panelClientes = new PanelClientes();
        tabbedPane.addTab("Gestión de Clientes", panelClientes); // Añade la pestaña de clientes

        // Añade el tabbedPane (con ambas pestañas) al JFrame, en la región central
        add(tabbedPane, BorderLayout.CENTER);
    }

    // Método main para arrancar la aplicación de forma segura en el hilo de eventos Swing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(); // Crear la ventana principal
            frame.setVisible(true); // Mostrar la ventana
        });
    }
}
