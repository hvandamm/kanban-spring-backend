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
* **Java Development Kit (JDK) 17 or 21**
* **Node.js** (v18.x or higher) & **npm**

---


### 1. Backend Setup (Spring Boot)

1. Open your terminal or command prompt and navigate to the backend directory:
   ```bash
   git clone https://github.com/hvandamm/kanban-spring-backend.git
   cd kanban-spring-backend
```

2. Run the applications:

macOS/Linux:
```bash
chmod +x mvnw
./mvnw spring-boot:run
```

windows(powershell/cmd)
```bash
mvnw.cmd spring-boot:run
```

go to http://localhost:8080 to see your backend

### 2. Frontend Setup (react)

go to [https://github.com/hvandamm/kanban-frontend] for installation instructions.
