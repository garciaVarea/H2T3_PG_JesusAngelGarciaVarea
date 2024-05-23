# Proyecto CRUD con JavaFX y MongoDB Atlas
Jesús Ángel García Varea

---------------------------------------------------------------------------------------------------
Descripción del Proyecto

Este proyecto desarrolla una aplicación de escritorio CRUD (Crear, Leer, Actualizar, Eliminar) para gestionar el inventario de una tienda, conectada a una base de datos en la nube MongoDB Atlas. La aplicación está implementada en Java utilizando JavaFX para la interfaz gráfica de usuario (GUI) y Scene Builder para su diseño.

---------------------------------------------------------------------------------------------------
Características

Operaciones CRUD: Gestión completa de productos y categorías con operaciones de creación, lectura, actualización y eliminación.
Interfaz Gráfica: Diseño intuitivo y estético, siguiendo los estándares UX/UI, implementado con JavaFX y estilizado con CSS.
Conexión Segura a MongoDB Atlas: Configuración de conexión a una base de datos NoSQL en la nube.
Validación y Manejo de Errores: Validaciones de datos y gestión de errores para asegurar la consistencia y funcionalidad de la aplicación.
Persistencia de Datos: Almacenamiento seguro y eficiente de datos en MongoDB Atlas.

---------------------------------------------------------------------------------------------------
Requisitos

JDK 22: El proyecto está desarrollado con la versión JDK 22 de Java. Se recomienda descargarla desde este enlace.
IDE IntelliJ IDEA: Se recomienda utilizar IntelliJ IDEA para el desarrollo y despliegue del proyecto.
MongoDB Atlas Account: Cuenta en MongoDB Atlas para gestionar la base de datos en la nube.
Instalación y Despliegue
Descargar el JDK: Descargar y descomprimir el JDK 22 en la ruta “C:\Program Files\Java”.
Clonar el Repositorio: Clonar el proyecto desde GitHub:
https://github.com/garciaVarea/H2T3_PG_JesusAngelGarciaVarea.git
Abrir el Proyecto en IntelliJ IDEA:
Descargar el proyecto como archivo zip y abrirlo.
O clonar directamente desde IntelliJ IDEA accediendo a File > New > Project from Version Control....
Configurar Maven: Asegurarse de que el proyecto se reconozca como un proyecto Maven.
Click derecho en pom.xml y seleccionar Add as Maven Project si no está ya configurado.
Construir el Proyecto:
Seleccionar Build > Build Project para descargar todos los recursos necesarios.
Ejecutar la Aplicación:
Ejecutar la clase LoginApplication desde IntelliJ IDEA (LoginApplication.main()).
Utilizar las credenciales usuario: carmelo y contraseña: carmelo para iniciar sesión.

---------------------------------------------------------------------------------------------------

Estructura del Proyecto

Colecciones de MongoDB:
Productos: Almacena id, nombre, precio, cantidad y categoría de los productos.
Categorías: Almacena las categorías disponibles para los productos.

---------------------------------------------------------------------------------------------------

Webgrafía

Learn MongoDB. (s.f.). Using MongoDB with Java. [23 de mayo de 2024]. Disponible en: https://learn.mongodb.com/learning-paths/using-mongodb-with-java
GeeksforGeeks. (s.f.). Difference between PreparedStatement and CallableStatement. [23 de mayo de 2024]. Disponible en: https://www.geeksforgeeks.org/difference-between-preparedstatement-and-callablestatement/
Chuidiang. (2022). Statement, PreparedStatement y CallableStatement. [23 de mayo de 2024]. Disponible en: https://blog.chuidiang.org/2022/06/19/statement-preparedstatement-y-callablestatement/

---------------------------------------------------------------------------------------------------

Este README proporciona una guía básica para entender, instalar y desplegar el proyecto de gestión de inventarios utilizando JavaFX y MongoDB Atlas. Para más detalles, consulte la documentación completa del proyecto.