# Foro-Hub API

Foro-Hub es una API diseñada para gestionar un foro. Permite a los usuarios crear, leer, actualizar y eliminar publicaciones y comentarios, así como gestionar usuarios y temas del foro.

## Tecnologías Utilizadas

La API Foro-Hub se desarrolló utilizando las siguientes tecnologías y herramientas principales:

- **Spring Framework:** Plataforma de desarrollo de aplicaciones Java que proporciona soporte integral para el desarrollo de aplicaciones web y servicios RESTful.
  
- **Spring Boot:** Framework de Spring que facilita la creación de aplicaciones Java basadas en Spring con una configuración mínima.

- **Java 17:** La versión más reciente de Java en el momento del desarrollo, ofreciendo mejoras en rendimiento, seguridad y funcionalidad.

- **Spring Data JPA:** Parte del proyecto Spring Data, que facilita el acceso y manejo de datos relacionales en aplicaciones Java basadas en Spring mediante el uso de Hibernate como implementación JPA.

- **MySQL:** Sistema de gestión de bases de datos relacional utilizado para almacenar datos persistentes de la aplicación.

- **JSON Web Tokens (JWT):** Utilizado para la autenticación y la seguridad de las API RESTful, proporcionando tokens seguros para la comunicación entre cliente y servidor.

- **Maven:** Herramienta de gestión de proyectos y construcción de software utilizada para la gestión de dependencias y la compilación del proyecto.

- **Git:** Sistema de control de versiones distribuido utilizado para el seguimiento de cambios en el código fuente y la colaboración en equipo.

Estas tecnologías se combinan para proporcionar una API robusta y eficiente para la gestión de un foro de discusión, asegurando un desarrollo ágil y mantenible.

## Tabla de Contenidos

- [Instalación](#instalación)
- [Uso](#uso)
- [Endpoints](#endpoints)
  - [Usuarios](#usuarios)
  - [Publicaciones](#publicaciones)
  - [Comentarios](#comentarios)
  - [Temas](#temas)
- [Autenticación](#autenticación)
- [Contribuciones](#contribuciones)
- [Licencia](#licencia)

## Instalación

Para instalar y ejecutar la API Foro-Hub, sigue estos pasos:

1. Clona el repositorio:
    ```sh
    git clone https://github.com/tu_usuario/foro-hub.git
    ```
2. Navega al directorio del proyecto:
    ```sh
    cd foro-hub
    ```
3. Instala las dependencias:
    ```sh
    npm install
    ```
4. Configura las variables de entorno en un archivo `.env`:
    ```env
    PORT=3000
    DB_HOST=localhost
    DB_USER=tu_usuario
    DB_PASS=tu_contraseña
    DB_NAME=forohub
    ```

5. Inicia el servidor:
    ```sh
    npm start
    ```

## Uso
La JWT depende de cada usuario registrado por lo tanto estan limitados eliminar y actualizar solo lo que sea de dicho usuario autenticado 

## Endpoints

### Usuarios

- **Registrar un nuevo usuario**
  - `POST /api/users`
  - Body: 
    ```json
    {
      "name": "string",
      "mail": "string",
      "password": "string"
    }
    ```

- **Obtener información de un usuario**
- Solo el usuario puede tener su informacion mas detallada
  - `GET /api/users/:id`

- **Actualizar un usuario**
- Se actualiza dependiendo del id del usurio que se obtenga den JWT
  - `PUT /api/users`
  - Body: 
    ```json
    {
      "nombre": "string",
      "password": "string"
    }
    ```

- **Eliminar un usuario**
- El ususrio se podra eliminar a si mismo si asi lo desea
  - `DELETE /api/users/`

### Topicos

- **Crear un nuevo topico**
  - `POST /api/topics`
  - Body: 
    ```json
    {
      "title": "string",
      "message": "string",
      "courseId": "number"
    }
    ```

- **Obtener todas los topicos**
  - `GET /api/topics`

- **Obtener un topico por ID**
  - `GET /api/topics/:id`

- **Actualizar un topico**
  - `PUT /api/topics/:id`
  - Body:
    ```json
    {
      "title": "string",
      "message": "string"
    }
    ```

- **Eliminar un topico**
  - `DELETE /api/topics/:id`

### Respuestas

- **Agregar una respuesta a un topico**
  - `POST /api/topics/:idTopico/answers`
  - Body: 
    ```json
    {
      "message": "string"
    }
    ```

- **Obtener todas las respuesta**
  - `GET /api/topics/answers`


- **Actualizar una respuesta**
  - `PUT /api/topics/answers/:id`
  - Body:
    ```json
    {
      "message": "string"
    }
    ```

- **Eliminar una respuesta**
  - `DELETE /api/topics/answers/:id`

### Cursos

- **Crear un nuevo curso**
  - `POST /api/courses`
  - Body: 
    ```json
    {
      "name": "string",
      "category":"string"
    }
    ```

- **Obtener todos los cursos**
  - `GET /api/courses`

## Autenticación

La API utiliza JSON Web Tokens (JWT) para la autenticación. Al registrarse o iniciar sesión, el usuario recibe un token que debe incluir en el encabezado de autorización de las solicitudes protegidas.


## Contribuciones

Las contribuciones son bienvenidas. Por favor, sigue los pasos a continuación para contribuir:

1. Haz un fork del proyecto.
2. Crea una nueva rama (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza los cambios necesarios y realiza commits (`git commit -m 'Agrega nueva funcionalidad'`).
4. Empuja tus cambios a la rama (`git push origin feature/nueva-funcionalidad`).
5. Abre un Pull Request.

## Licencia

Foro-Hub está licenciado bajo la [MIT License](LICENSE).
