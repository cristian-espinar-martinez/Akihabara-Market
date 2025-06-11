# Akihabara-Market
Configurar la base de datos MySQL
Primero tienes que crear la base de datos y las tablas para que la aplicación funcione. Yo uso MySQL Workbench para esto:
Abre MySQL Workbench y conéctate a tu servidor local.
Copia y pega este script y dale a ejecutar:

CREATE DATABASE IF NOT EXISTS akihabara_db;
USE akihabara_db;
CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    categoria VARCHAR(100),
    precio DECIMAL(10, 2),
    stock INT
);
CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    fecha_registro DATE DEFAULT (curdate())
);
Asegúrate de que tu servidor MySQL esté en funcionamiento (yo uso Unicontroller para levantarlo).

Configurar la API Key de OpenRouter
La aplicación utiliza OpenRouter para generar descripciones y categorías de productos con IA.
Crea un archivo llamado config.properties en la carpeta del proyecto.
Dentro coloca esta línea con tu API Key:
openrouter.api_key= y aqui dentro va tu apikey
La clase IA.java lee esta clave para conectarse a la API y obtener las respuestas.

 Cómo compilar y ejecutar la aplicación en Eclipse
 
Abre Eclipse y carga el proyecto.
Mis clases están organizadas dentro de paquetes.
Agrega las librerías externas (gson.jar y mysql-connector-java.jar) al build path:
Click derecho en el proyecto > Build Path > Configure Build Path > pestaña Libraries > Add External JARs > selecciona esos dos .jar.
Configura tu conexion a la base de datos en DatabaseConnection
Para ejecutar la parte de consola, ejecuta la clase paquete.Principal.java
Para ejecutar la interfaz gráfica con Swing, ejecuta la clase paquete.vistaGUI.MainFrame.java.

Funcionalidades:

Gestión de Clientes: Añadir, Consultar por id , ver todos, actualizar, eliminar.
Gestión de Productos: Añadir, Consultar por id, ver todos, actualizar, eliminar, generar descripcion con IA, sugerir categoria con IA.
Integración con IA: Generación automática de descripciones comerciales y sugerencias de categoría para productos.
Carga de datos de ejemplo: Inicialización de la base de datos con clientes y productos de muestra para facilitar pruebas.
Interfaz de Consola: Menús y mensajes interactivos para manejo rápido vía consola.
Interfaz Gráfica con Swing: Ventanas y formularios que ofrecen una experiencia visual para gestionar clientes y productos con botones, tablas y formularios.

Estructura de Clases

Controladores
ClientesController: Controla la lógica y flujo de clientes desde la consola.
ProductosController: Controla la lógica y flujo de productos desde la consola e integra IA.

Modelos y DAO
ClienteOtaku y ProductoOtaku: Modelos que representan los datos de clientes y productos.
ClienteDAO y ProductoDAO: Gestionan la persistencia y operaciones en la base de datos.
Setup: Clase para cargar datos de ejemplo en la base de datos.

Vistas
InterfazConsola: Interfaz de usuario para consola, con menús y solicitudes de datos.

VistaGui
MainFrame: ventana principal que contiene un JTabbedPane con pestañas para clientes y productos , Es donde ejecutamos el codigo si queremos verlo con la interfaz grafica de swing
PanelClientes: panel con formulario y tabla para gestionar clientes.
PanelProductos: panel con formulario, tabla y funcionalidades IA para gestionar productos.

IA
IA: Maneja la comunicación con la inteligencia artificial para generación de texto.

config.properties: Para guardar la apikey

Conexión a Base de Datos
DatabaseConnection: Maneja la conexión JDBC con la base de datos MySQL.

Paquete.Principal
Principal.java para ejecutar la aplicacion en consola


