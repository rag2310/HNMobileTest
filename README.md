# HNMobileTest

> Prueba para la Empresa Reign
> Asignado a Rodolfo Alfredo Gutierrez Ortega @mention
> Desarrollador Android - Ingeniero en Computacion
> fecha de asignacion de TAREA 2021-09-17 14:48
> Fecha de entrega 2021-09-20 14:00

## Version de IDE
```batch
    Android Studio Arctic Fox | 2020.3.1 Patch 2
    Build #AI-203.7717.56.2031.7678000, built on August 26, 2021
    Runtime version: 11.0.10+0-b96-7249189 amd64
    VM: OpenJDK 64-Bit Server VM by Oracle Corporation
    Windows 10 10.0
    GC: G1 Young Generation, G1 Old Generation
    Memory: 1280M
    Cores: 8
    Registry: external.system.auto.import.disabled=true
    Non-Bundled Plugins: Dart, org.jetbrains.kotlin - v1.5.30, io.flutter, org.intellij.plugins.markdown
```

## Api

* [Algolia](https://hn.algolia.com/api/v1/search_by_date?query=mobile)

## Tecnologias Usadas

* [Android JetPack](https://developer.android.com/jetpack?hl=es)
* [Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started)
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android?hl=es-419)
* [Retrofit](https://square.github.io/retrofit/)
* [Gson](https://github.com/google/gson)
* [Room](https://developer.android.com/jetpack/androidx/releases/room)
* [RecyclerView - DiffUtil](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/DiffUtil)
* [Data Binding](https://developer.android.com/topic/libraries/data-binding)

## Arquitectura del proyecto

* [MVVM](https://developer.android.com/jetpack/guide?gclid=Cj0KCQjwv5uKBhD6ARIsAGv9a-yPliH7SleIRC2r6FYnPjy3BIb7OurUlTrmo_l82Lb11-b9K_upsd8aAoDJEALw_wcB&gclsrc=aw.ds)

## Estructura del proyecto

```batch
📦 hnmobiletest
 ┣ 📂data                //Package con toda la capa de datos
 ┃ ┣ 📂db                // base de datos
 ┃ ┃ ┗ 📂dao             // daos para la base de datos en Room 
 ┃ ┣ 📂model             // Entidades para la generacion de tablas y manejo de json con GSON
 ┃ ┣ 📂network           // Interfaces para declarar las peticiones a la API con Retrofit
 ┃ ┗ 📂repository        // Repositorios para el manejo de datos, consulta, etc
 ┣ 📂di                  // Modulos de inyencion 
 ┣ 📂ui                  // Interfaz de Usuario
 ┃ ┣ 📂articlesdetails   // Detalle de los articulos de noticias
 ┃ ┗ 📂home              // Pantalla principal
 ┣ 📜 HNMobileTestApplication.kt
 ┗ 📜 MainActivity.kt
```

## Pantallas

![Home Screen](/screen/home.png)

> Home Screen pantalla de bienvenida del aplicativo,
> con la lista de articulos del api descrito arriba

### Funcionalidades para la pantalla principal

#### Actualizar Lista

![Refresh](/screen/refresh.png)

> Actualiza los datos de la lista realizando una consulta al API, tambien se valida si hay elementos
> de la lista que fueron eliminados con la siguiente funcion, para no mostrarlos otra vez cuando se refresque

#### Borrar Elemento

![Borrar Elemento](/screen/delete.png)

> Al deslizar desde la derecha asi la izquierda se elminara un elemento esto conlleva que se aguarde en base de datos
> y cuando se refresque la lista o se vuelva a entrar no aparezca dicho elemento eleminado

![Lista sin elemento](/screen/element_delete.png)

#### Cache

> Al momento de quedarnos sin internet podemos aun refrescar la lista de elementos con los elementos anteriores,
> antes de la desconexion

#### Click a un articulo de noticias

> Al darle click a cualquier tarjeta con una noticia vinculada se abrira la ventana descrita mas adelante.

![WebView Screen](/screen/webview.png)

> Home Screen pantalla de bienvenida del aplicativo,
> con la lista de articulos del api descrito arriba


