package paquete.modelo;

import java.time.LocalDate;

public class ClienteOtaku {
    private int id;               // ID del cliente
    private String nombre;        // Nombre del cliente
    private String email;         // Email del cliente
    private String telefono;      // Teléfono del cliente
    private LocalDate fechaRegistro; // Fecha de registro del cliente

    public ClienteOtaku() {}     // Constructor vacío

    // Constructor con todos los campos
    public ClienteOtaku(int id, String nombre, String email, String telefono, LocalDate fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters y setters para cada atributo
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
