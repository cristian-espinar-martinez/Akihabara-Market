package paquete.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import paquete.conexion.DatabaseConnection;

public class ProductoDAO {
    // Agrega un producto nuevo a la base de datos
    public void agregarProducto(ProductoOtaku producto) {
        String sql = "INSERT INTO productos (nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());

            stmt.executeUpdate();
            System.out.println("Producto agregado!");

        } catch (SQLException e) {
            System.out.println("No pude agregar el producto :(");
            e.printStackTrace();
        }
    }

    // Busca un producto por su ID
    public ProductoOtaku obtenerProductoPorId(int id) {
        String sql = "SELECT * FROM productos WHERE id = ?";
        ProductoOtaku producto = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                producto = new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error buscando producto por ID");
            e.printStackTrace();
        }

        return producto;
    }

    // Obtiene todos los productos
    public List<ProductoOtaku> obtenerTodosLosProductos() {
        String sql = "SELECT * FROM productos";
        List<ProductoOtaku> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ProductoOtaku p = new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error sacando todos los productos");
            e.printStackTrace();
        }

        return lista;
    }

    // Actualiza un producto existente
    public boolean actualizarProducto(ProductoOtaku producto) {
        String sql = "UPDATE productos SET nombre = ?, categoria = ?, precio = ?, stock = ? WHERE id = ?";
        boolean ok = false;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setInt(5, producto.getId());

            int filas = stmt.executeUpdate();
            ok = filas > 0;

        } catch (SQLException e) {
            System.out.println("No se pudo actualizar el producto");
            e.printStackTrace();
        }

        return ok;
    }

    // Borra un producto por ID
    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";
        boolean ok = false;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();
            ok = filas > 0;

        } catch (SQLException e) {
            System.out.println("No pude borrar el producto");
            e.printStackTrace();
        }

        return ok;
    }

    // Busca productos que contengan un nombre similar
    public List<ProductoOtaku> buscarProductosPorNombre(String nombre) {
        String sql = "SELECT * FROM productos WHERE nombre LIKE ?";
        List<ProductoOtaku> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProductoOtaku p = new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error buscando productos por nombre");
            e.printStackTrace();
        }

        return lista;
    }
}
