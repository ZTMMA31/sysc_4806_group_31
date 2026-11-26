# Capstone Project Manager

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

**Deployment URL:** [https://4806-ztmma31-bdb7grbqemd6c5es.mexicocentral-01.azurewebsites.net/](https://4806-ztmma31-bdb7grbqemd6c5es.mexicocentral-01.azurewebsites.net/)

### IntelliJ IDEA Recommendations
- Set `Settings > Build, Execution, Deployment` > `Build Tools` > `Gradle` > `Distribution` to `Wrapper` instead of `Local installation`
- Set `Settings > Build, Execution, Deployment` > `Build Tools` > `Gradle` > `Gradle JVM` to a JVM/JDK that is Java 17 or higher
- Ensure the EditorConfig plugin is enabled

## Project Structure
```
ZTMMA31
├─ .github
│  └─ workflows
│     ├─ build_and_test.yml
│     └─ main_4806-ztmma31.yml
├─ CONTRIBUTING.md
├─ README.md
├─ app
│  ├─ build.gradle.kts
│  └─ src
│     ├─ main
│     │  ├─ java
|     |  |  └─ com.github.ztmma31.cpm.app
│     │  │     ├─ CpmApplication.java
│     │  │     ├─ config
│     │  │     │  ├─ DataSeeder.java
│     │  │     │  └─ SecurityConfig.java
│     │  │     ├─ controller
│     │  │     │  ├─ CapstoneProjectController.java
│     │  │     │  ├─ HomeController.java
│     │  │     │  ├─ LoginController.java
│     │  │     │  └─ RegistrationController.java
│     │  │     ├─ dto
│     │  │     │  └─ RegistrationForm.java
│     │  │     ├─ entities
│     │  │     │  ├─ CapstoneProject.java
│     │  │     │  ├─ CapstoneProjectRepository.java
│     │  │     │  ├─ Professor.java
│     │  │     │  ├─ ProfessorRepository.java
│     │  │     │  ├─ Program.java
│     │  │     │  ├─ Student.java
│     │  │     │  └─ StudentRepository.java
│     │  │     ├─ security
│     │  │     │  ├─ AppUserDetailsService.java
│     │  │     │  └─ CurrentUserService.java
│     │  │     └─ service
│     │  │        └─ CapstoneProjectService.java
│     │  └─ resources
│     │     ├─ application.yaml
│     │     └─ templates
│     │        ├─ home.html
│     │        ├─ login.html
│     │        ├─ professor
│     │        │  └─ projects
│     │        │     ├─ create.html
│     │        │     ├─ details.html
│     │        │     ├─ edit.html
│     │        │     └─ index.html
│     │        └─ registration.html
│     └─ test
│        ├─ java
│        │  └─ com.github.ztmma31.cpm.app
│        │     ├─ AppUserDetailsServiceTest.java
│        │     ├─ CpmApplicationTest.java
│        │     ├─ controller
│        │     │  └─ RegistrationControllerTest.java
│        │     ├─ entities
│        │     │  ├─ CapstoneProjectTest.java
│        │     │  ├─ ProfessorTest.java
│        │     │  └─ StudentTest.java
│        │     ├─ security
│        │     │  └─ CurrentUserServiceTest.java
│        │     └─ service
│        │        └─ CapstoneProjectServiceTest.java
│        └─ resources
│           └─ application.yaml
├─ architecture
│  ├─ class_diagram.mmd
│  └─ database_schema.png
├─ gradle.properties
├─ gradle
│  ├─ libs.versions.toml
│  └─ wrapper
│     ├─ gradle-wrapper.jar
│     └─ gradle-wrapper.properties
├─ gradlew
├─ gradlew.bat
└─ settings.gradle.kts
```
## Releases / Milestones

## Authors
- Amber Skinner
- Edward Kurita
- Matthew Ross
- Max Bui
- Zane Labonté-Hagar
