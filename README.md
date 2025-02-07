# CNote - Android Note-Taking App

CNote is a modern Android note-taking app built with **Clean Architecture**, **Jetpack Compose**, *
*Room Database**, and **Dagger Hilt** for dependency injection. It provides a clean and intuitive
user interface for creating, editing, and managing notes efficiently.

---

## Features

- **Create, Edit, and Delete Notes**: Easily manage your notes with a simple and intuitive UI.
- **Search Notes**: Quickly find notes using the search functionality.
- **Dark/Light Theme**: Supports both light and dark themes for better user experience.
- **Offline Storage**: Notes are stored locally using **Room Database**.
- **Clean Architecture**: Follows Clean Architecture principles for scalability and maintainability.
- **Jetpack Compose**: Built entirely with Jetpack Compose for a modern and declarative UI.
- **Dependency Injection**: Uses **Dagger Hilt** for managing dependencies.
- **Material Design**: Follows Material Design guidelines for a polished look and feel.

---

## Screenshots

| Notes                           | Add Note                              |
|---------------------------------|---------------------------------------|
| ![Notes](screenshots/notes.png) | ![Add Note](screenshots/add_note.png) |

| Tasks                           | Add Task                              |
|---------------------------------|---------------------------------------|
| ![Tasks](screenshots/tasks.png) | ![Add Task](screenshots/add_task.png) |

| Task Category                                   | Create Category                                     |
|-------------------------------------------------|-----------------------------------------------------|
| ![Task Category](screenshots/task_category.png) | ![Create Category](screenshots/create_category.png) |

| Task Priority                                   | Task Calendar                                   |
|-------------------------------------------------|-------------------------------------------------|
| ![Task Priority](screenshots/task_priority.png) | ![Task Calendar](screenshots/task_calendar.png) |

---

## Architecture

CNote follows the **Clean Architecture** principles, separating the app into three main layers:

1. **Presentation Layer**:
    - Built with **Jetpack Compose**.
    - Contains UI components and ViewModels.
    - Communicates with the domain layer to fetch and display data.

2. **Domain Layer**:
    - Contains business logic and use cases.
    - Defines repository interfaces.
    - Independent of frameworks and libraries.

3. **Data Layer**:
    - Implements repository interfaces.
    - Uses **Room Database** for local data storage.
    - Handles data operations and mapping.

---

## Technologies Used

- **Jetpack Compose**: Modern UI toolkit for building native Android UI.
- **Room Database**: Local SQLite database for storing notes.
- **Dagger Hilt**: Dependency injection library for managing dependencies.
- **Clean Architecture**: Separation of concerns for better maintainability.
- **Kotlin Coroutines**: For asynchronous programming.
- **Material Design**: For a polished and consistent UI.
- **StateFlow**: For reactive state management in Compose.

---

## Setup Instructions

Follow these steps to set up and run the project on your local machine.

### Prerequisites

- Android Studio (latest version recommended).
- Android SDK (API level 21 or higher).
- Kotlin plugin installed in Android Studio.

### Steps

1. **Clone the Repository**:
   ```bash
   https://github.com/Amirhosein-Baratali/CNote.git
   cd CNote