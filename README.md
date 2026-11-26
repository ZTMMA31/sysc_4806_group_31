# Capstone Project Manager
Deployed at: https://4806-ztmma31-bdb7grbqemd6c5es.mexicocentral-01.azurewebsites.net/login

## Building, Testing, Deployment

### Build Requirements
- Java 21. You should not have to download this yourself. Gradle will download an appropriate JDK automatically with the foojay-resolver plugin (see `settings.gradle.kts`).
- Gradle is used for building. You do not need to install Gradle on your local machine. The Gradle wrapper is commited to this repository and can be run with `./gradlew` (Unix) or `gradlew.bat` (Windows).
- The remaining instructions assume a Unix environment. You can use Git Bash or WSL on Windows if you want to follow the instructions exactly.

### Building
Build with `./gradlew clean assemble`.

### Testing
Run tests with `./gradlew check`.

Testing is done automatically on PRs and the main branch with `.github/workflows/build_and_test.yml` workflow.

### Deployment
The system is deployed automatically from the main branch to Azure with the `.github/workflows/main_4806-ztmma31.yml` workflow.

### IntelliJ IDEA Recommendations
- Set `Settings > Build, Execution, Deployment` > `Build Tools` > `Gradle` > `Distribution` to `Wrapper` instead of `Local installation`
- Set `Settings > Build, Execution, Deployment` > `Build Tools` > `Gradle` > `Gradle JVM` to a JVM/JDK that is Java 17 or higher
- Ensure the EditorConfig plugin is enabled

## Project Structure

The application follows a layered Spring Boot architecture. The Java source code is located under `app/src/main/java/com/github/ztmma31/cpm/app/` and is organized into the following packages:

| Package | Description |
|---|---|
| `config` | Application configuration, security configuration, and initial database seeding. |
| `controller` | Handles HTTP requests and renders Thymeleaf views. |
| `dto` | Data transfer objects used to transfer data between application components. |
| `entities` | JPA entities and repositories used for database persistence. |
| `security` | User authentication and authenticated user retrieval. |
| `service` | Application business logic, including capstone project management. |

### Other Directories

- `app/src/main/resources/templates/` — Thymeleaf HTML templates for the user interface.
- `app/src/main/resources/application.yaml` — Spring Boot application configuration.
- `app/src/test/java/` — Automated tests for application components.

### Gradle Configuration

The project uses Gradle with the Kotlin DSL for build configuration and dependency management.

| File | Description |
|---|---|
| `settings.gradle.kts` | Defines the Gradle project structure and included modules. |
| `app/build.gradle.kts` | Configures application dependencies, plugins, and build tasks. |
| `gradle/libs.versions.toml` | Centralizes dependency and plugin version declarations. |
The database schema is shown in `architecture/database_schema.png`. Hibernate with the community SQLiteDialect is used to generate the schema for SQLite.
This combination can result in oddities like foreign key relationships not being defined properly in the dumped schema.
However, this doesn't negatively effect on the application, because the application uses JPA/Hibernate for all database interactions.

## Releases / Milestones

### Milestone 1 – Initial Release (November 12, 2026)

The first milestone focused on establishing the application architecture and development infrastructure. The initial Spring Boot application was configured with Gradle, SQLite, Spring Data JPA, Thymeleaf, and Spring Security.

- Implemented student, professor, and capstone project entities.
- Implemented user registration and authentication.
- Developed the initial login, registration, and home pages.
- Configured automated builds and testing through GitHub Actions.
- Configured the Azure App Service deployment workflow.
- Created the initial UML class diagram and database schema.

### Milestone 2 – Alpha Release (November 26, 2026)

The second milestone focused on expanding application functionality and improving the existing architecture. Professors can now create, view, update, and delete their capstone projects through the web interface.

- Implemented the capstone project repository, service, and controller.
- Developed professor project management views.
- Implemented project ownership validation and access control.
- Extended the database schema to support student project applications.
- Implemented a service for retrieving authenticated students and professors.
- Added unit tests for project management and authenticated user retrieval.
- Improved application logging and corrected database entity relationships.
- Introduced Lombok to reduce boilerplate code.

## Authors
- Amber Skinner
- Edward Kurita
- Matthew Ross
- Max Bui
- Zane Labonté-Hagar
