# ✍️ Perihelion Expanse
> **⚠️ Status: Work in Progress (WIP)**  
> Actualmente en fase de desarrollo activo.

**Perihelion Expanse** es una aplicación de gestión inteligente para tareas y notas diseñada bajo estándares exigentes de la industria móvil. Combina una arquitectura robusta, inyección de dependencias e integración con Inteligencia Artificial para ofrecer una experiencia de productividad.
## 🏗️ Arquitectura y Patrones
El proyecto utiliza una arquitectura limpia (**Clean Architecture**) dividida en capas (Data, Domain, UI, DI, etc.) para garantizar la escalabilidad y mantenibilidad.
- **MVVM (Model-View-ViewModel):** Gestión centralizada de la lógica de negocio y estados.
  <img width="430" height="430" alt="image" src="https://github.com/user-attachments/assets/664118e9-cedb-429f-8797-88480c2c34ce" />

- **MVI / Unidirectional Data Flow (UDF):** Implementación de estados inmutables mediante `StateFlow`, asegurando una interfaz de usuario predecible y fácil de depurar.

  <img width="428" height="300" alt="image" src="https://github.com/user-attachments/assets/b8bc330a-6ada-4ce9-8288-bea0599bacd4" />

- **Inyección de Dependencias:** Uso de **Koin** como framework ligero y eficiente para desacoplar componentes y facilitar el testing.

## 🚀 Desafíos Técnicos y Soluciones

- **IA Generativa (DeepSeek):** Integración de modelos de lenguaje mediante **Retrofit** para asistir al usuario en la creación de tarea, con el botón autogenerar.
  | Pre-Generación | Post-Generación |
  | :--- | :--- |
  |<img width="180" height="400" alt="image" src="https://github.com/user-attachments/assets/1b452e95-de5a-400c-bccc-f9b604b59cc3" /> |<img width="180" height="400" alt="image" src="https://github.com/user-attachments/assets/ace02c70-79c3-49f3-930d-cfde926b5679" />|
 
- **Seguridad:** Implementación de **Google Credential Manager API** para un flujo de autenticación.
  |Vista Presentación|Google|
  | :--- | :--- |
  | <img width="180" height="400" alt="image" src ="https://github.com/user-attachments/assets/7ad868e1-8128-4c97-8a45-4e91b544ebcd"/> | <img width="180" height="400" alt="image" src="https://github.com/user-attachments/assets/2f45f513-8688-41c7-95b5-d43e916f37b7"/> |
  
- **Integridad de Datos:** Navegación basada exclusivamente en Identificadores (IDs) para garantizar que la vista de detalle siempre consulte la **Única Fuente de Verdad**, evitando datos obsoletos.
- **Notificaciones PUSH (FCM):** Gestión de notificaciones push mediante Data Payloads, con soporte para **Deep Linking** y manejo de permisos dinámicos (Android 13+).
  | Notificación | Descripción de la notificación |
  | :--- | :--- |
  | <img width="180" height="400" alt="image" src="https://github.com/user-attachments/assets/adf9528a-3fe6-476f-83b3-f795aeeb7130" /> |<img width="500" height="200" alt="image" src="https://github.com/user-attachments/assets/77f0a2d4-cecf-4a3c-82c7-2fd82d89e248" />|

 
## ✨ Componentes de Interfaz (Jetpack Compose)
- **Material 3:** Implementación de las últimas guías de diseño de Google.
- **Expandable FAB (Speed Dial):** Menú de acciones rápidas animado para optimizar el espacio en pantalla.
- **Modal BottomSheets:** Despliegue de opciones contextuales y formularios de forma elegante y nativa.
- **Lazy Layouts:** Gestión eficiente de listas de notas con estados de carga reactivos.
## 🛠️ Tech Stack
- **Lenguaje:** Kotlin + Coroutines + Flows.
- **UI:** Jetpack Compose.
- **DI:** Koin.
- **Networking:** Retrofit + Kotlinx Serialization.
- **Backend:** Firebase (Auth, Firestore, Messaging).
- **Testing:** Unit Testing con **MockK** y testeo de flujos reactivos con **Turbine**.

## 📋 Cómo empezar
### Prerrequisitos
- Android Studio.
- JDK 11
- Firebase project configurado

### Instalación
1. Clona el repositorio
   `git clone https://github.com/Derkpy/Perihelion-Expanse.git`
2. Copia tu `google-services.json` en `/app`
3. Añade tus API Keys en `secrets.properties`
4. Sync & Run

---

## 🗺️ Roadmap / Próximos Pasos
- [ ] Finalización del Módulo de Autenticación: Pulido de flujos alternativos, manejo de excepciones complejas y flujos de recuperación de sesión.
- [ ] Persistencia Local y Modo Offline 100%: Implementación de una base de datos Room como caché local para garantizar la disponibilidad absoluta de las tareas y notas sin conexión.
- [ ] Optimización de Interfaz (UI/UX): Refinamiento de micro-animaciones, transiciones de pantalla y pulido de detalles visuales para una experiencia más fluida.
- [ ] Ampliación de la Cobertura de Tests: Implementación de pruebas integrales para ViewModels y casos de uso, asegurando la robustez de la lógica de negocio.

## 👤 Autor
**Derek Cerecedo García**
*Ingeniero en Sistemas Computacionales - Especialidad Desarrollo de Software*
[LinkedIn](https://www.linkedin.com/in/derek-cerecedo-469a91345?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app) | [Portfolio](https://github.com/Derkpy)
