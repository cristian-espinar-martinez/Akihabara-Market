package paquete.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import paquete.conexion.DatabaseConnection;

public class ClienteDAO {

    // Añade un cliente nuevo a la base de datos
    public void agregarCliente(ClienteOtaku cliente) {
        String sql = "INSERT INTO clientes (nombre, email, telefono, fecha_registro) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefono());
            stmt.setDate(4, Date.valueOf(cliente.getFechaRegistro())); // Convierte LocalDate a SQL Date

            stmt.executeUpdate();
            System.out.println("Cliente agregado!");

        } catch (SQLException e) {
            System.out.println("No pude agregar el cliente :(");
            e.printStackTrace();
        }
    }

    // Busca un cliente por su ID
    public ClienteOtaku obtenerClientePorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        ClienteOtaku cliente = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new ClienteOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("email"),
                    rs.getString("telefono"),
                    rs.getDate("fecha_registro").toLocalDate() // Convierte SQL Date a LocalDate
                );
            }
        } catch (SQLException e) {
            System.out.println("Error buscando cliente por ID");
            e.printStackTrace();
        }

        return cliente;
    }

    // Obtiene todos los clientes de la base de datos
    public List<ClienteOtaku> obtenerTodosLosClientes() {
        String sql = "SELECT * FROM clientes";
        List<ClienteOtaku> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ClienteOtaku c = new ClienteOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("email"),
                    rs.getString("telefono"),
                    rs.getDate("fecha_registro").toLocalDate()
                );
                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error sacando todos los clientes");
            e.printStackTrace();
        }

        return lista;
    }

    // Actualiza un cliente existente, devuelve true si se pudo
    public boolean actualizarCliente(ClienteOtaku cliente) {
        String sql = "UPDATE clientes SET nombre = ?, email = ?, telefono = ?, fecha_registro = ? WHERE id = ?";
        boolean ok = false;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefono());
            stmt.setDate(4, Date.valueOf(cliente.getFechaRegistro()));
            stmt.setInt(5, cliente.getId());

            int filas = stmt.executeUpdate();
            ok = filas > 0;

        } catch (SQLException e) {
            System.out.println("No se pudo actualizar el cliente");
            e.printStackTrace();
        }

        return ok;
    }

    // Borra un cliente por ID, devuelve true si se pudo borrar
    public boolean eliminarCliente(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        boolean ok = false;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();
            ok = filas > 0;

        } catch (SQLException e) {
            System.out.println("No pude borrar el cliente");
            e.printStackTrace();
        }

        return ok;
    }

    // Busca un cliente por su email
    public ClienteOtaku buscarPorEmail(String email) {
        String sql = "SELECT * FROM clientes WHERE email = ?";
        ClienteOtaku cliente = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new ClienteOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("email"),
                    rs.getString("telefono"),
                    rs.getDate("fecha_registro").toLocalDate()
                );
            }

        } catch (SQLException e) {
            System.out.println("Error buscando cliente por email");
            e.printStackTrace();
        }

        return cliente;
    }

    // Busca clientes cuyo nombre contenga un texto (LIKE %nombre%)
    public List<ClienteOtaku> buscarClientesPorNombre(String nombre) {
        String sql = "SELECT * FROM clientes WHERE nombre LIKE ?";
        List<ClienteOtaku> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ClienteOtaku c = new ClienteOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("email"),
                    rs.getString("telefono"),
                    rs.getDate("fecha_registro").toLocalDate() // Convierte SQL Date a LocalDate
                );
                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error buscando clientes por nombre");
            e.printStackTrace();
        }

        return lista;
    }

}
