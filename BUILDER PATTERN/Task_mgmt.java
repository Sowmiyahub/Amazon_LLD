
import java.time.LocalDate;

enum Priority {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}

class Task {

    // 1. Fields
    private final String taskId;
    private final String title;
    private final String projectId;

    // Optional fields
    private final String description;
    private final String assignee;
    private final Priority priority;
    private final LocalDate dueDate;

    // 2. Private constructor
    private Task(Builder builder) {

        this.taskId = builder.taskId;
        this.title = builder.title;
        this.projectId = builder.projectId;

        this.description = builder.description;
        this.assignee = builder.assignee;
        this.priority = builder.priority;
        this.dueDate = builder.dueDate;
    }

    // 3. Getters
    public String getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getDescription() {
        return description;
    }

    public String getAssignee() {
        return assignee;
    }

    public Priority getPriority() {
        return priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    // 4. Static nested Builder
    public static class Builder {

        // Mandatory fields
        private final String taskId;
        private final String title;
        private final String projectId;

        // Optional fields
        private String description;
        private String assignee;
        private Priority priority;
        private LocalDate dueDate;

        // 5. Builder constructor
        public Builder(String taskId, String title, String projectId) {
            this.taskId = taskId;
            this.title = title;
            this.projectId = projectId;
        }

        // 6. Optional field methods
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder assignee(String assignee) {
            this.assignee = assignee;
            return this;
        }

        public Builder priority(Priority priority) {
            this.priority = priority;
            return this;
        }

        public Builder dueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        // 7. Build method
        public Task build() {

            if (taskId == null || taskId.isBlank()) {
                throw new IllegalArgumentException("Task ID is required");
            }

            if (title == null || title.isBlank()) {
                throw new IllegalArgumentException("Title is required");
            }

            if (projectId == null || projectId.isBlank()) {
                throw new IllegalArgumentException("Project ID is required");
            }

            return new Task(this);
        }
    }
}

// 8. Main class
public class Main {

    public static void main(String[] args) {

        // Task 1: Fully configured task
        Task task1 = new Task.Builder(
                "101",
                "Fix Login Bug",
                "P101"
        )
        .description("Fix authentication failure during login")
        .assignee("Sowmi")
        .priority(Priority.HIGH)
        .dueDate(LocalDate.of(2026, 11, 10))
        .build();

        // Task 2: Only mandatory fields
        Task task2 = new Task.Builder(
                "102",
                "Update Documentation",
                "P101"
        ).build();

        // Print Task 1
        System.out.println("----- TASK 1 -----");
        System.out.println("ID: " + task1.getTaskId());
        System.out.println("Title: " + task1.getTitle());
        System.out.println("Project: " + task1.getProjectId());
        System.out.println("Description: " + task1.getDescription());
        System.out.println("Assignee: " + task1.getAssignee());
        System.out.println("Priority: " + task1.getPriority());
        System.out.println("Due Date: " + task1.getDueDate());

        // Print Task 2
        System.out.println("\n----- TASK 2 -----");
        System.out.println("ID: " + task2.getTaskId());
        System.out.println("Title: " + task2.getTitle());
        System.out.println("Project: " + task2.getProjectId());
        System.out.println("Priority: " + task2.getPriority());
    }
}
