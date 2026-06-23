package com.example.demo1.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.demo1.TaskRepository;
import com.example.demo1.BoardRepository;
import com.example.demo1.Board;
import com.example.demo1.Task;
import com.example.demo1.TaskStatus;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(BoardRepository boardRepository, TaskRepository taskRepository) {
        return args -> {
            // Check if database is already seeded
            if (boardRepository.count() == 0) {
                System.out.println("🌱 Seeding initial Kanban board database items...");

                // 1. Create and Save the Parent Board
                Board board = new Board();
                board.setName("Enterprise Kanban Workspace");
                board = boardRepository.save(board);

                // 2. Helper Method / Block to safely generate Tasks using Lombok setters
                createAndSaveTask(taskRepository, "Design system architecture", "Create the high-level architecture document for the new platform.", TaskStatus.TODO, board);
                createAndSaveTask(taskRepository, "Set up CI/CD pipeline", "Configure GitHub Actions for automated builds and deployments.", TaskStatus.TODO, board);
                
                createAndSaveTask(taskRepository, "Implement user authentication", "Add OAuth2 login flow with JWT token management.", TaskStatus.IN_PROGRESS, board);
                createAndSaveTask(taskRepository, "Build task API endpoints", "Create CRUD endpoints for task management.", TaskStatus.IN_PROGRESS, board);
                
                createAndSaveTask(taskRepository, "Database schema design", "Design the PostgreSQL schema for boards and tasks.", TaskStatus.DONE, board);
                createAndSaveTask(taskRepository, "Project scaffolding", "Initialize the monorepo with Vite, TypeScript, and ESLint.", TaskStatus.DONE, board);
                createAndSaveTask(taskRepository, "API contract definition", "Define OpenAPI spec for the backend API.", TaskStatus.DONE, board);

                System.out.println("✅ Database seeding complete!");
            }
        };
    }

    // Helper method to keep code clean and instantiate entities safely with relationships
    private void createAndSaveTask(TaskRepository taskRepository, String title, String description, TaskStatus status, Board board) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        task.setBoard(board); // Links the relationship required by @JoinColumn(nullable = false)
        taskRepository.save(task);
    }
}