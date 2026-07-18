# Enterprise Kanban Workspace

An example Kanban project management application.
---

## 🛠️ Tech Stack & Architecture

* **Frontend:** React 19, TypeScript, Vite, Tailwind CSS (Slate-Dark UI)
* **Backend:** Spring Boot 4.1.0, JPA / Hibernate
* **Database:** H2 File-Based Storage (Persistent during runtime)
* **Data Layer:** OpenAPI / Swagger contract with auto-generated API client methods via **Orval**

---

## 🚀 Cross-Platform Installation & Setup

Follow these instructions to clone, configure, and spin up the full-stack workspace on **Windows, macOS, or Linux**.

### 📋 Prerequisites
Ensure you have the following installed on your machine:
* **Java Development Kit (JDK) 21**
* **Node.js** v22.23.1 & **npm**

---


### 1. Backend Setup (Spring Boot)

1. Open your terminal or command prompt and navigate to the backend directory:
```bash
git clone https://github.com/hvandamm/kanban-spring-backend.git
cd kanban-spring-backend
```

2. Run the application:

macOS/Linux:
```bash
chmod +x mvnw
./mvnw spring-boot:run
```

windows(powershell/cmd)
```bash
mvnw.cmd spring-boot:run
```

next you need to setup the frontend

### 2. Frontend Setup (react)

go to https://github.com/hvandamm/kanban-frontend for installation instructions.
