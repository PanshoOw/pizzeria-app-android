# 🍕 PizzeriaApp

**PizzeriaApp** es una aplicación Android desarrollada con **Kotlin y Jetpack Compose**, cuyo propósito es gestionar el registro de clientes y pedidos de una pizzería mediante una interfaz sencilla e interactiva.

El proyecto fue desarrollado como parte de las actividades académicas de la asignatura **Desarrollo de Aplicaciones Móviles I (PMY2201)**.

Su implementación permite aplicar conceptos de programación orientada a objetos, modularización con Gradle, gestión de estados y desarrollo de interfaces modernas para Android.

## 1. Funcionalidades

La aplicación incorpora las siguientes funcionalidades:

### Gestión de clientes

- Registro de clientes mediante un formulario.
- Validación de nombres y apellidos.
- Compatibilidad con caracteres Unicode, incluyendo tildes y la letra ñ.
- Validación de números telefónicos móviles chilenos.
- Validación del formato de correo electrónico.
- Verificación de correos electrónicos duplicados.
- Actualización automática del resumen de clientes.

### Gestión de pedidos

- Registro de pedidos asociados a clientes existentes.
- Identificación del cliente mediante su correo electrónico.
- Validación de cantidades ingresadas.
- Creación automática de pedidos.
- Verificación de disponibilidad de ingredientes.
- Descuento de existencias al registrar pedidos válidos.
- Rechazo de pedidos cuando el inventario es insuficiente.

### Resumen dinámico

La pantalla principal muestra:

- Cantidad total de clientes registrados.
- Cantidad total de pedidos realizados.
- Nombre del último cliente registrado.

La información se actualiza automáticamente después de cada operación exitosa.

### Notificaciones y validaciones

- Confirmaciones mediante componentes Snackbar de Material 3.
- Desaparición automática de las confirmaciones después de 3,5 segundos.
- Mensajes de error para entradas inválidas.
- Teclados adaptados al tipo de información solicitada.

---

## 2. Tecnologías utilizadas

| Tecnología | Aplicación en el proyecto |
|---|---|
| Kotlin | Lenguaje principal de programación. |
| Android SDK | Desarrollo y ejecución de la aplicación Android. |
| Jetpack Compose | Construcción declarativa de la interfaz gráfica. |
| Material 3 | Componentes visuales y experiencia de usuario. |
| ViewModel | Gestión del estado y coordinación de la lógica de presentación. |
| LiveData | Observación y actualización dinámica del resumen. |
| Kotlin Coroutines | Ejecución de temporizadores sin bloquear la interfaz. |
| Gradle | Gestión de dependencias y configuración modular. |
| Android Studio | Entorno de desarrollo y ejecución. |

---

## 3. Arquitectura del proyecto

PizzeriaApp utiliza una estructura multimódulo organizada mediante Gradle.

### Módulos principales

**`app`**

Módulo principal de la aplicación Android.

Contiene el punto de entrada de la aplicación y la configuración necesaria para su ejecución.

**`ui`**

Módulo encargado de la interfaz y la lógica de presentación.

Incluye:

- `PantallaPrincipal.kt`
- `PizzeriaViewModel.kt`

Utiliza Jetpack Compose, Material 3, ViewModel y LiveData.

**`data`**

Módulo responsable de la lógica de negocio y la gestión de datos en memoria.

Incluye las clases del dominio, la gestión de clientes, los pedidos y las operaciones relacionadas con el inventario.

Entre sus componentes se encuentra:

- `RegistroPizzeria.kt`

**`utils`**

Módulo que centraliza funciones auxiliares reutilizables.

Incluye:

- `Validaciones.kt`

Este archivo contiene las validaciones utilizadas durante el registro de clientes y pedidos.

### Dependencias entre módulos

La estructura principal de dependencias es:

```text
app
 |
 v
 ui
 |
 v
data
 |
 v
utils
```

Actualmente, `app` también mantiene una dependencia directa con `data` para conservar compatibilidad con componentes de demostración desarrollados durante etapas anteriores.

La modularización permite distribuir responsabilidades y facilitar el mantenimiento y la evolución del proyecto.

---

## 4. Requisitos para ejecutar la aplicación

Para trabajar con el código fuente se necesita:

- Android Studio.
- Android SDK.
- Un emulador Android o dispositivo físico compatible.
- Conexión a Internet para descargar las dependencias durante la configuración inicial.

La aplicación tiene configurada una versión mínima de Android correspondiente a **API 23 (Android 6.0)**.

---

## 5. Instalación y ejecución

### Paso 1. Obtener el proyecto

Clonar el repositorio:

```bash
git clone https://github.com/PanshoOw/pizzeria-app-android.git
```

También es posible descargar el proyecto como archivo ZIP desde GitHub.

### Paso 2. Abrir en Android Studio

1. Iniciar Android Studio.
2. Seleccionar **Open**.
3. Buscar la carpeta `PizzeriaApp`.
4. Abrir el proyecto.
5. Esperar a que Gradle sincronice las dependencias.

### Paso 3. Seleccionar un dispositivo

Se puede utilizar un emulador Android configurado mediante el administrador de dispositivos de Android Studio o un dispositivo físico compatible.

Durante el desarrollo se realizaron pruebas utilizando un emulador Pixel 4.

### Paso 4. Ejecutar

Seleccionar el módulo `app` y presionar **Run**.

También es posible compilar la aplicación desde la terminal de Windows:

```powershell
.\gradlew.bat :app:assembleDebug
```

Una compilación correcta debería finalizar con el mensaje:

```text
BUILD SUCCESSFUL
```

---

## 6. Guía de uso

### 6.1. Registrar un cliente

Al iniciar PizzeriaApp, se presenta una pantalla con el resumen de la pizzería y los formularios disponibles.

Para registrar un cliente:

1. Ubicar la sección **Registrar cliente**.
2. Introducir el nombre completo.
3. Ingresar un número telefónico móvil chileno.
4. Escribir la dirección del cliente.
5. Ingresar un correo electrónico válido.
6. Presionar **Registrar cliente**.

Si los datos son correctos, aparecerá una confirmación temporal.

El resumen de la aplicación se actualizará automáticamente.

**Consideraciones:**

- Se debe ingresar al menos un nombre y un apellido.
- Se aceptan nombres con tildes y otros caracteres Unicode válidos.
- Los números telefónicos deben cumplir con el formato móvil chileno admitido.
- No se permite registrar dos clientes con el mismo correo electrónico, independientemente de las diferencias entre mayúsculas y minúsculas.

### 6.2. Registrar un pedido

Para registrar un pedido:

1. Ubicar la sección **Registrar pedido**.
2. Introducir el correo electrónico de un cliente registrado.
3. Especificar la cantidad de pizzas solicitadas.
4. Presionar **Registrar pedido**.

El sistema comprobará:

- Que el cliente exista.
- Que la cantidad ingresada sea válida.
- Que el pedido no supere el máximo permitido.
- Que existan ingredientes suficientes para prepararlo.

Si todas las comprobaciones son satisfactorias, el pedido será registrado y aparecerá una confirmación temporal.

El contador de pedidos aumentará automáticamente.

**Importante:** en esta versión, los pedidos se generan utilizando un producto de pizza predefinido. Todavía no existe un catálogo interactivo para seleccionar distintos productos.

### 6.3. Consultar el resumen

La sección **Resumen de la pizzería** muestra información actualizada sobre los registros realizados durante la sesión.

No es necesario actualizar manualmente la pantalla para consultar los nuevos valores.

### 6.4. Interpretar los mensajes

La aplicación utiliza dos tipos de mensajes:

**Confirmaciones**

Indican que una operación se completó correctamente.

Se muestran mediante un Snackbar y desaparecen automáticamente después de 3,5 segundos.

**Errores**

Informan sobre datos incorrectos o situaciones que impiden completar una operación.

Permanecen visibles junto al formulario correspondiente para facilitar su corrección.

---

## 7. Validaciones implementadas

| Campo u operación | Validación |
|---|---|
| Nombre completo | Obligatorio, mínimo dos palabras y caracteres permitidos. |
| Teléfono | Formato de número móvil chileno. |
| Dirección | Campo obligatorio. |
| Correo electrónico | Formato válido y ausencia de duplicados. |
| Cliente del pedido | Debe existir previamente. |
| Cantidad de pizzas | Debe ser un número entero mayor que cero. |
| Límite por pedido | Máximo de 10 pizzas. |
| Inventario | Disponibilidad suficiente de ingredientes. |

Las validaciones permiten prevenir registros incorrectos y mantener la coherencia de las operaciones realizadas.

---

## 8. Limitaciones actuales

PizzeriaApp es un proyecto académico en desarrollo.

Por esta razón, existen funcionalidades que todavía no se encuentran implementadas:

- Persistencia permanente de clientes y pedidos.
- Conexión con una base de datos.
- Autenticación de usuarios.
- Selección interactiva de diferentes pizzas.
- Procesamiento de pagos.
- Seguimiento real de entregas.
- Sincronización con servicios externos.

**Los datos y el inventario se mantienen en memoria durante la ejecución del proceso de la aplicación.**

Si el proceso de la aplicación finaliza, los registros realizados pueden perderse y el inventario vuelve a su estado inicial cuando se crea una nueva instancia del sistema de registro.

---

## 9. Posibles mejoras futuras

Entre las posibles extensiones del proyecto se encuentran:

- Incorporar persistencia local mediante Room.
- Implementar operaciones de consulta mediante Coroutines.
- Permitir seleccionar diferentes productos.
- Incorporar un carrito de compras.
- Mostrar el historial de pedidos.
- Permitir consultar el inventario disponible.
- Implementar pruebas automatizadas.
- Mejorar la navegación entre pantallas.

Estas funcionalidades no forman parte de la versión actual.

---

## 10. Propósito académico

El proyecto permite demostrar conocimientos relacionados con:

- Programación orientada a objetos en Kotlin.
- Desarrollo de aplicaciones Android.
- Arquitectura modular mediante Gradle.
- Separación de responsabilidades.
- Desarrollo de interfaces con Jetpack Compose.
- Gestión del estado con ViewModel.
- Observación de datos mediante LiveData.
- Programación asíncrona con Kotlin Coroutines.
- Validación de datos y control básico de operaciones.

---

## 11. Repositorio

Código fuente disponible en:

**https://github.com/PanshoOw/pizzeria-app-android**

Proyecto desarrollado con fines educativos.