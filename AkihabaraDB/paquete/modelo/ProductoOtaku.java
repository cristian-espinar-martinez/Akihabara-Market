package paquete.modelo;

public class ProductoOtaku {
    private int id;              // Identificador único del producto
    private String nombre;       // Nombre del producto
    private String categoria;    // Categoría a la que pertenece el producto
    private double precio;       // Precio del producto
    private int stock;           // Cantidad disponible en inventario

    // Constructor vacío para crear un objeto sin inicializar campos
    public ProductoOtaku() {
    }

    // Constructor con parámetros para inicializar un producto completo
    public ProductoOtaku(int id, String nombre, String categoria, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters y setters para acceder y modificar los campos privados
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
