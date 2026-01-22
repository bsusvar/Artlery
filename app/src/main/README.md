# ARTLERY

## Qué es Artlery

Artlery es una red social que permite a los usuarios conectar con otros amantes del arte, colaborar en la difusión del conocimiento y participar en la construcción de la comunidad.

## Funcionalidades

La aplicación, desarrollada en Android Studio con Kotlin y Jetpack Compose, comprende las siguientes funcionalidades:

- **Autenticación** de usuario con correo y contraseña.
- **Exploración de arte** mediante un **listado dinámico de obras** y un **buscador integrado**.
- **Fichas técnicas** con información detallada (autor, año, movimiento artístico, localización de la obra...). 
- **Gestión de favoritos** a través de sistemas de guardado con confirmación de eliminación.
- **Publicación de comentarios** sobre la obra desde la sección de favoritos.
- Gestión de la **sesión del usuario**.
- Detalles sobre el **propósito de la aplicación**.
- **Adaptación de la interfaz** según **idioma** (español/inglés) y **dispositivo** (móvil/tablet).

## Diagrama de casos de uso

<p align="center">
  <img src="images/diagrama_artlery.jpg" alt="Diagrama de casos de uso de Artlery" width="700">
</p>

- **Precondiciones**: el acceso a las funcionalidades de listados, favoritos, perfil o información sobre la aplicación requiere que el usuario haya iniciado sesión de manera previa.
- **Extensiones**:
  - **Ver detalles de la obra**: se trata de una extensión opcional del listado de obras y del listado de favoritos.
  - **Comentarios**: funcionalidad disponible exclusivamente en la ficha técnica a la que se accede desde el listado de favoritos.
  - **Cerrar sesión**: caso de uso independiente que representa el objetivo final del usuario al finalizar su actividad.
