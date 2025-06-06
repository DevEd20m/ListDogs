# 🐶 Dogs App - Clean Architecture + MVI + Compose

Este proyecto está desarrollado en **Kotlin**, utilizando **Gradle** como sistema de construcción.  
Sigue los principios de **Clean Architecture** con una separación rigurosa de las capas de **Dominio**, **Datos** y **Presentación**.

Además, se implementa el patrón de presentación **MVI (Model-View-Intent)** para una gestión robusta y predecible del estado de la UI.

La arquitectura y diseño del proyecto buscan facilitar:

✅ **Mantenibilidad**  
✅ **Escalabilidad**  
✅ **Testabilidad**  
✅ Posible evolución hacia un **SDK** modular

---

## 📚 Capas de la Arquitectura

### 🧩 Dominio
- Modelos de negocio: `DogsDomain`, `DomainError`
- Interfaces de casos de uso: `GetDogsUseCase`
- Lógica de negocio pura, independiente de frameworks

### 🗄️ Datos
- Repositorios y *Data Sources* (remotos y locales)
- Entidades de base de datos: `DogEntity`
- DAOs (Data Access Objects)
- Implementación de integración con APIs y persistencia local

### 🎨 Presentación
- Construida con **Jetpack Compose** para una interfaz de usuario moderna y declarativa
- Uso del patrón **MVI** para el manejo de estados y flujos de UI
- Componentes: `Image`, `ConstraintLayout`, entre otros

### ⚙️ Core (Módulo Reutilizable)
- Incluye componentes comunes
- Diseñado con el objetivo de **centralizar elementos reutilizables**
- Preparado para servir como base para una posible **SDK modularizable** en el futuro
- Facilita compartir lógica y componentes entre distintas features o incluso entre distintas apps

---

## 🚀 Librerías y Herramientas Utilizadas

- **Jetpack Compose** → UI declarativa moderna
- **Room** → Persistencia local de datos
- **Arrow** → Manejo funcional de errores y tipos como `Either`
- **MockK** → Mocking en pruebas unitarias
- **JUnit** → Framework de testing
- **Kotlin Coroutines** → Manejo de operaciones asíncronas
- **Coil** → Carga eficiente de imágenes en Compose (`rememberAsyncImagePainter`)
- **AndroidX** → Conjunto de librerías modernas para desarrollo Android
- **Hilt** → Inyección de dependencias
- **Retrofit** → Consumo de APIs REST

---

## 🧪 Pruebas Unitarias

El proyecto cuenta con un conjunto sólido de pruebas unitarias, enfocadas principalmente en las capas de **Dominio** y **Datos**, empleando **JUnit** y **MockK**.

Los tests cubren:

- ✅ Retorno y validación de datos desde fuentes locales y remotas
- ✅ Manejo y propagación de errores de red
- ✅ Verificación de inserciones y consultas en base de datos
- ✅ Mocking de repositorios y DAOs para pruebas aisladas

---

## 💎 Buenas Prácticas Adoptadas

- Uso de `Either` para un manejo explícito y funcional de errores
- Separación rigurosa de responsabilidades por capas
- Implementación del patrón **MVI** para una UI robusta y predecible
- Inyección de dependencias con **Hilt**
- Preparación del módulo `core` para componentes reutilizables y futura creación de SDK
- Uso de principios **SOLID** en la arquitectura de software
- Nombres de tests descriptivos y legibles

---

## 🗂️ Estructura del Proyecto

```plaintext
├── data
│   ├── database
│   │   ├── models
│   ├── network
│   │   ├── models
│   ├── repository
│   ├── di
│   ├── exception
│   ├── mapper
├── domain
│   ├── models
│   ├── repository
│   └── usecase
├── core
│   ├── common         # Componentes reutilizables, helpers, utilidades. Base para futura SDK.
├── app
│   ├── di
│   ├── feature
│   │   ├── dogscreen
│   ├── di
```
## 👨‍💻 Author / Contact
**José Edmundo Prado Astucuri**  
- Linkedin : https://www.linkedin.com/in/jose-edmundo-prado-astucuri-b76750182/
- Email: pradoulima@gmail.com



