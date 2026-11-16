# PizzeriaApp

## 1. Descripción del proyecto

**PizzeriaApp** es una aplicación Android desarrollada en Kotlin mediante Android Studio y Jetpack Compose.

El proyecto corresponde a la actividad formativa de la **Experiencia 2, Semana 4**, de la asignatura Desarrollo de Aplicaciones Móviles I (PMY2201), de Duoc UC.

Su objetivo es configurar un entorno de desarrollo Android funcional e integrar la lógica de negocio desarrollada durante las semanas anteriores en una aplicación móvil.

La aplicación permite procesar un pedido de demostración, verificar el inventario, calcular descuentos e impuestos, asignar un repartidor y generar una notificación y un reporte de pedidos.

## 2. Tecnologías utilizadas

- **Lenguaje:** Kotlin 2.2.10.
- **Entorno de desarrollo:** Android Studio.
- **Interfaz gráfica:** Jetpack Compose y Material 3.
- **Sistema de construcción:** Gradle 9.6 mediante Gradle Wrapper.
- **Android Gradle Plugin:** 9.5.1.
- **Configuración de Gradle:** Kotlin DSL.
- **Android SDK de compilación:** API 37.
- **Minimum SDK:** API 23 (Android 6.0).
- **Java:** compatibilidad de código fuente y destino configurada en Java 11.
- **Compatibilidad de bibliotecas:** Core Library Desugaring.

Para realizar las pruebas se utilizó un dispositivo virtual **Pixel 4 con Android API 37**.

## 3. Funcionalidades implementadas

### 3.1. Gestión de clientes y productos

La aplicación utiliza clases Kotlin para representar clientes, usuarios, productos y combos.

Estas clases incorporan conceptos de programación orientada a objetos, como herencia, encapsulamiento y sobrescritura de métodos.

### 3.2. Procesamiento de pedidos

La clase `Pedido` permite:

- Asociar un cliente con los productos solicitados.
- Calcular el subtotal del pedido.
- Aplicar descuentos según la cantidad de productos.
- Calcular descuentos promocionales.
- Calcular impuestos.
- Obtener el total del pedido.
- Administrar el estado del pedido.

### 3.3. Control de inventario

Antes de confirmar un pedido, la aplicación verifica que existan suficientes ingredientes disponibles.

Si el inventario es suficiente, descuenta los ingredientes utilizados y cambia el estado del pedido a **En preparación**.

Si los ingredientes no son suficientes, el pedido no se confirma.

### 3.4. Promociones y descuentos

El proyecto incorpora dos descuentos:

- **10 % por cantidad:** aplicable a pedidos confirmados de más de tres pizzas.
- **5 % promocional:** aplicable a productos elegibles durante el período comprendido entre el 1 y el 30 de noviembre de 2026.

La identificación de productos promocionables utiliza una anotación personalizada y Reflection.

### 3.5. Asignación de repartidores

La aplicación utiliza la clase `Repartidor` para seleccionar automáticamente al repartidor disponible más cercano.

La demostración considera dos repartidores con distintas distancias.

La asignación se realiza cuando el pedido se encuentra confirmado.

### 3.6. Notificaciones simuladas

El sistema genera un mensaje de notificación que informa al cliente sobre el estado de su pedido.

**Importante:** esta funcionalidad es una simulación académica. No realiza envíos reales de mensajes SMS ni notificaciones externas.

### 3.7. Reporte diario de pedidos

La aplicación genera un reporte que contiene:

- Identificador y fecha del pedido.
- Información del cliente.
- Productos y cantidades solicitadas.
- Subtotal, descuentos, impuestos y total.
- Estado del pedido.
- Resumen de pedidos completados, pendientes y no confirmados.

En la demostración actual, el reporte contiene el pedido procesado durante la ejecución.

### 3.8. Interfaz Android

La interfaz está desarrollada mediante Jetpack Compose.

Incluye:

- Mensaje de bienvenida.
- Botón **Procesar pedido**.
- Visualización de resultados.
- Desplazamiento vertical para consultar toda la información.

El mensaje de bienvenida solicitado para la actividad es:

**Bienvenidos a la mejor pizzería de la ciudad**

## 4. Organización del proyecto

El código fuente principal se encuentra en:

`app/src/main/java/com/example/pizzeriaapp/`

Su organización es la siguiente:

```text
com.example.pizzeriaapp/
│
├── MainActivity.kt
│
├── model/
│   ├── Usuario.kt
│   ├── Cliente.kt
│   ├── Repartidor.kt
│   ├── Producto.kt
│   ├── Combo.kt
│   ├── Pedido.kt
│   └── Promocionable.kt
│
├── logic/
│   ├── Inventario.kt
│   ├── Promociones.kt
│   ├── Reflexion.kt
│   ├── DemostracionPedido.kt
│   ├── Notificaciones.kt
│   └── Reporte.kt
│
└── ui/
    └── theme/
```

### Responsabilidades

**model:** contiene las entidades y sus comportamientos relacionados con el negocio.

**logic:** contiene las funciones responsables del procesamiento de pedidos, inventario, promociones, repartidores, notificaciones y reportes.

**ui:** contiene los componentes de configuración visual de Jetpack Compose.

**MainActivity.kt:** constituye el punto de entrada de la aplicación Android y conecta la interfaz con la lógica del proyecto.

## 5. Requisitos para ejecutar el proyecto

Para abrir y ejecutar la aplicación se necesita:

1. Android Studio instalado.
2. Android SDK configurado.
3. Una plataforma Android compatible con la configuración del proyecto.
4. Acceso a las dependencias necesarias para sincronizar Gradle.
5. Un emulador Android o dispositivo físico compatible.

El proyecto incluye **Gradle Wrapper**, por lo que no es necesario instalar manualmente la versión de Gradle utilizada.

Se recomienda utilizar el JDK configurado por Android Studio para la ejecución de Gradle.

## 6. Instrucciones de ejecución

### Paso 1. Abrir el proyecto

1. Iniciar Android Studio.
2. Seleccionar **Open**.
3. Buscar la carpeta `PizzeriaApp`.
4. Abrir el proyecto.

### Paso 2. Sincronizar Gradle

Esperar a que Android Studio complete la sincronización del proyecto.

Si es necesario, utilizar la opción **Sync Project with Gradle Files**.

Comprobar que no existan errores de configuración.

### Paso 3. Configurar el dispositivo virtual

1. Abrir **Tools → Device Manager**.
2. Crear o seleccionar un dispositivo virtual.
3. Utilizar una imagen de Android API 31 o superior.
4. Iniciar el emulador.

Para las pruebas del proyecto se utilizó un **Pixel 4 con Android API 37**.

### Paso 4. Compilar el proyecto

Desde Android Studio, seleccionar:

**Build → Make Project**

Comprobar que la compilación finalice correctamente.

### Paso 5. Ejecutar la aplicación

1. Seleccionar la configuración de ejecución `app`.
2. Seleccionar el dispositivo virtual disponible.
3. Presionar **Run**.
4. Verificar que la aplicación se abra en el emulador.

### Paso 6. Probar las funcionalidades

Al iniciar la aplicación, se mostrará el mensaje:

**Bienvenidos a la mejor pizzería de la ciudad**

Debajo aparecerá el botón **Procesar pedido**.

Presionarlo para ejecutar la demostración.

La aplicación mostrará los resultados del procesamiento. Para visualizar todas las secciones, desplazarse verticalmente por la pantalla.

## 7. Prueba funcional y resultados esperados

La aplicación utiliza los siguientes datos de demostración:

| Parámetro | Valor |
|---|---|
| Cliente | Cliente de prueba |
| Producto | Pepperoni con bordes de queso |
| Precio unitario | $10.000 CLP |
| Cantidad | 4 pizzas |
| Fecha | 15-11-2026 |
| Repartidores disponibles | Carlos González y Pedro Ramírez |

### Resultado esperado del pedido

| Concepto | Valor |
|---|---:|
| Subtotal | $40.000 |
| Descuento por cantidad (10 %) | -$4.000 |
| Descuento promocional (5 %) | -$1.800 |
| Base imponible | $34.200 |
| Impuestos (19 %) | $6.498 |
| **Total del pedido** | **$40.698** |

El pedido debe quedar en estado **En preparación**, siempre que exista inventario suficiente.

### Resultado esperado de la asignación

El sistema debe seleccionar a **Carlos González**, ya que se encuentra a 2 km y está disponible.

### Resultado esperado de la notificación

Debe mostrarse un mensaje simulado que informe al cliente sobre el estado de su pedido.

### Resultado esperado del reporte

El reporte diario debe mostrar:

- Total de pedidos: 1.
- Pedidos completados: 0.
- Pedidos pendientes: 1.
- Pedidos no confirmados: 0.

Estos resultados corresponden al escenario de demostración implementado.

## 8. Consideraciones técnicas

La aplicación reutiliza parte de la lógica desarrollada anteriormente en un proyecto Kotlin de consola.

Para su integración con Android, las funcionalidades de presentación de información fueron adaptadas para devolver cadenas de texto que pueden visualizarse mediante Jetpack Compose.

Se utiliza Core Library Desugaring para permitir el uso de determinadas API modernas de Java, como `LocalDate`, manteniendo la compatibilidad con el SDK mínimo configurado.

Los datos de demostración se crean durante la ejecución de la función `procesarPedidoEjemplo()`.

El inventario se inicializa nuevamente cada vez que se procesa el ejemplo.

La aplicación no utiliza actualmente:

- Base de datos.
- Almacenamiento persistente de pedidos.
- Integración con servicios externos.
- Envío real de notificaciones.
- Registro de pedidos mediante formularios de usuario.

Estas características quedan fuera del alcance de la demostración académica actual.

## 9. Evidencias de funcionamiento

La entrega incorpora capturas de pantalla destinadas a demostrar:

1. **Instalación de Android Studio:** entorno de desarrollo correctamente instalado.
2. **Configuración del AVD:** dispositivo virtual Android funcionando.
3. **Ejecución de PizzeriaApp:** aplicación ejecutándose en el emulador, con el mensaje de bienvenida solicitado.

Estas evidencias permiten verificar la configuración del entorno y el funcionamiento de la aplicación.

## 10. Conclusión

PizzeriaApp demuestra la configuración de un entorno de desarrollo Android y la integración de funcionalidades Kotlin previamente desarrolladas.

Mediante Jetpack Compose, las operaciones del sistema de pedidos pueden ejecutarse y visualizarse desde una aplicación móvil.

El proyecto aplica principios de programación orientada a objetos, reutilización de código y separación de responsabilidades, constituyendo una base para futuras ampliaciones.