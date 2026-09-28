# EventEase — Comprehensive College Viva Preparation Guide

> A complete, conversational, line-by-line concept guide and viva examination question bank for the **EventEase: College Event Registration System** project. Designed specifically for university practical exams, project reviews, and technical viva voice assessments.

---

# Table of Contents

1. [Part I: The 52 Core Concepts (Line-by-Line Conversational Guide)](#part-i-the-52-core-concepts)
   - [Category 1: Java & Modern Spring Boot Fundamentals (Concepts 1–6)](#category-1-java--modern-spring-boot-fundamentals)
   - [Category 2: Spring MVC & Web Request Flow (Concepts 7–15)](#category-2-spring-mvc--web-request-flow)
   - [Category 3: Thymeleaf Template Engine & Frontend Binding (Concepts 16–21)](#category-3-thymeleaf-template-engine--frontend-binding)
   - [Category 4: Database Design, JPA & Hibernate ORM (Concepts 22–32)](#category-4-database-design-jpa--hibernate-orm)
   - [Category 5: Service Layer, Dependency Injection & IoC (Concepts 33–36)](#category-5-service-layer-dependency-injection--ioc)
   - [Category 6: Validation, Error Handling & Business Rules (Concepts 37–43)](#category-6-validation-error-handling--business-rules)
   - [Category 7: Data Initialization & REST API Architecture (Concepts 44–48)](#category-7-data-initialization--rest-api-architecture)
   - [Category 8: Build Automation, Styling & Testing (Concepts 49–52)](#category-8-build-automation-styling--testing)
2. [Part II: Top 35 Viva Questions & Accurate Answers](#part-ii-top-35-viva-questions--accurate-answers)
   - [General Spring Boot & Architecture (Q1 – Q8)](#1-general-spring-boot--architecture)
   - [Database, JPA & Hibernate (Q9 – Q15)](#2-database-jpa--hibernate)
   - [Thymeleaf & Frontend UI (Q16 – Q21)](#3-thymeleaf--frontend-ui)
   - [Business Logic, Exceptions & Seat Allocation (Q22 – Q27)](#4-business-logic-exceptions--seat-allocation)
   - [REST APIs & HTTP Protocols (Q28 – Q32)](#5-rest-apis--http-protocols)
   - [Project Demonstration & Troubleshooting (Q33 – Q35)](#6-project-demonstration--troubleshooting)

---

# Part I: The 52 Core Concepts

---

### Category 1: Java & Modern Spring Boot Fundamentals

#### Concept 1: Java 21 LTS Features & Benefits in Spring Boot
- **What it is**: Java 21 is the modern Long-Term Support (LTS) release of the Java platform.
- **Why we use it**: It brings high-performance runtime enhancements, virtual threads, sealed classes, pattern matching, and cleaner record syntax.
- **In our project**: In `pom.xml`, `<java.version>21</java.version>` ensures our application compiles on the fastest, most stable, and secure modern Java standard.

#### Concept 2: Spring Framework Overview & Philosophy
- **What it is**: Spring is an open-source enterprise Java application framework designed to make Java development simpler and less tightly coupled.
- **Philosophy**: It emphasizes Inversion of Control (IoC) and Dependency Injection (DI), allowing developers to write plain Java objects (POJOs) while the framework manages creation, configuration, and object lifecycle.

#### Concept 3: Spring Boot Overview & Why Spring Boot over Standard Spring MVC
- **What it is**: Spring Boot is an opinionated extension of Spring that eliminates boilerplate XML configuration and complex setup.
- **Why we use it**: In traditional Spring MVC, you had to configure `web.xml`, `dispatcher-servlet.xml`, configure view resolvers manually, and install an external Tomcat server. Spring Boot provides an **embedded Tomcat server**, starter dependencies, and auto-configuration so the application runs out of the box with `java -jar`.

#### Concept 4: Spring Boot Starter Dependencies
- **What it is**: Starters are convenient dependency descriptors you can include in your build file (`pom.xml`).
- **In our project**:
  - `spring-boot-starter-web`: Pulls in Spring MVC, REST support, Jackson JSON, and Embedded Tomcat.
  - `spring-boot-starter-thymeleaf`: Provides the Thymeleaf view engine for HTML rendering.
  - `spring-boot-starter-data-jpa`: Pulls in Hibernate, JPA API, Spring Data, and HikariCP connection pool.
  - `spring-boot-starter-validation`: Brings Hibernate Validator for bean validation annotations.

#### Concept 5: Spring Boot Auto-Configuration
- **What it is**: When the application starts, Spring Boot looks at the jar dependencies on the classpath and automatically configures beans for you.
- **Example**: Because `mysql-connector-j` is on the classpath and `application.properties` specifies `jdbc:mysql://...`, Spring Boot automatically creates a `DataSource` bean, an `EntityManagerFactory`, and a transaction manager without writing a single line of Java configuration code.

#### Concept 6: Main Entry Point (`@SpringBootApplication` & `SpringApplication.run`)
- **What it is**: The main entry class `EventEaseApplication.java` has the `main()` method.
- **Line explanation**:
  - `@SpringBootApplication`: A composite annotation combining `@Configuration`, `@EnableAutoConfiguration`, and `@ComponentScan`.
  - `SpringApplication.run(EventEaseApplication.class, args);`: Bootstraps the application context, creates the Spring container, launches the embedded Tomcat server on port 8080, and starts listening for HTTP traffic.

---

### Category 2: Spring MVC & Web Request Flow

#### Concept 7: Model-View-Controller (MVC) Architectural Pattern
- **Model**: Represents the data and business entities (`Student`, `Event`, `Organizer`, `Registration`).
- **View**: The HTML user interface that displays data to the end-user (Thymeleaf templates).
- **Controller**: The intermediary coordinator (`EventController`, `RegistrationController`) that receives user requests, invokes service logic, and returns the view name.

#### Concept 8: `DispatcherServlet` & Spring MVC Request Lifecycle
- **What it is**: `DispatcherServlet` is the front-controller of Spring MVC.
- **Request Flow**:
  1. Browser sends HTTP request (e.g., `GET /events`).
  2. `DispatcherServlet` intercepts the request and queries `HandlerMapping` to find the matching controller method (`EventController.showEvents`).
  3. Controller calls `EventService` to fetch data and populates the `Model`.
  4. Controller returns the logical view name `"events"`.
  5. `ViewResolver` resolves `"events"` to `classpath:/templates/events.html`.
  6. Thymeleaf merges the HTML template with the Model data and renders pure HTML back to the browser.

#### Concept 9: `@Controller` vs `@RestController`
- **`@Controller`**: Used for server-side HTML rendering. The returned `String` from methods represents the template name (e.g., `return "events";` loads `events.html`).
- **`@RestController`**: Used for RESTful web services. It combines `@Controller` and `@ResponseBody`. The returned object is serialized directly into JSON format and written into the HTTP response body.

#### Concept 10: HTTP Methods (`GET`, `POST`, `PUT`, `DELETE`)
- **`GET`**: Used to retrieve or read data without modifying server state (e.g., viewing events).
- **`POST`**: Used to create a new resource or submit form data (e.g., creating an event, registering).
- **`PUT`**: Used to update or replace an existing resource.
- **`DELETE`**: Used to remove a resource from the server.

#### Concept 11: Mapping Annotations (`@GetMapping`, `@PostMapping`, etc.)
- **Shortcut annotations**: `@GetMapping("/events")` is a shortcut for `@RequestMapping(value = "/events", method = RequestMethod.GET)`.
- **Purpose**: Improves readability and specifies exact HTTP verb routing.

#### Concept 12: Request Parameter Annotations
- **`@PathVariable`**: Extracts values from the URI path (e.g., `/events/{id}` $\rightarrow$ `@PathVariable Long id`).
- **`@RequestParam`**: Extracts query parameters or form fields (e.g., `/events?category=Technical` $\rightarrow$ `@RequestParam String category`).
- **`@RequestBody`**: Binds incoming JSON payload from an HTTP request body into a Java object.
- **`@ModelAttribute`**: Binds HTML form fields directly to a Java model object (e.g., `@ModelAttribute("event") Event event`).

#### Concept 13: Spring MVC `Model` Interface
- **What it is**: A container object supplied by Spring MVC to pass data from the Java Controller to the Thymeleaf template.
- **Example**: `model.addAttribute("events", eventsList);` allows `events.html` to access the list using `${events}`.

#### Concept 14: Flash Attributes and `RedirectAttributes`
- **What it is**: Flash attributes store temporary data in the HTTP session across a redirect.
- **Why we use it**: When a user creates an event via POST, we redirect to `/events`. `redirectAttributes.addFlashAttribute("successMessage", "Event created!");` preserves the alert message across the redirect and removes it immediately after display.

#### Concept 15: Post-Redirect-Get (PRG) Pattern
- **What it is**: After a successful POST request (e.g., creating an event), the server responds with a redirect (`return "redirect:/events";`) instead of rendering the page directly.
- **Why it is critical**: It prevents duplicate form submissions if the user refreshes their browser.

---

### Category 3: Thymeleaf Template Engine & Frontend Binding

#### Concept 16: Thymeleaf Template Engine Overview & Natural Templating
- **What it is**: An XML/XHTML/HTML5 template engine for Java web applications.
- **Natural Templating**: Thymeleaf templates are valid HTML files that can be opened directly in a browser even without running the Spring server. Prototype attributes like `th:text` are ignored in static mode and processed dynamically at runtime.

#### Concept 17: Thymeleaf Expression Syntaxes
- **`${...}`**: Variable expressions to access model attributes (e.g., `${event.title}`).
- **`@{...}`**: Link URL expressions for context-aware URLs (e.g., `th:href="@{/events}"` or `th:href="@{'/events/' + ${event.id}}"`).
- **`*{...}`**: Selection expressions used inside `th:object` forms (e.g., `th:field="*{title}"`).
- **`#{...}`**: Internationalization and message bundle expressions.

#### Concept 18: Thymeleaf Conditional Logic (`th:if`, `th:unless`)
- **`th:if`**: Renders the HTML element only if the condition evaluates to `true` (e.g., `th:if="${event.availableSeats > 0}"`).
- **`th:unless`**: Renders the element if the condition is `false` (equivalent to `if not`).

#### Concept 19: Thymeleaf Iteration (`th:each`)
- **What it is**: Loops through a Java collection (`List`, `Set`) to render repetitive HTML elements like table rows or cards.
- **Example**: `<tr th:each="event : ${events}">` repeats the `<tr>` block for every `Event` object in the list.

#### Concept 20: Thymeleaf Form Binding (`th:action`, `th:object`, `th:field`)
- **`th:object`**: Specifies the backing bean for the HTML form (e.g., `th:object="${event}"`).
- **`th:field`**: Binds an input element to a property of the bean (e.g., `th:field="*{venue}"`), generating `id="venue"`, `name="venue"`, and `value="..."` automatically.

#### Concept 21: Dynamic Class Manipulation (`th:classappend`)
- **What it is**: Dynamically adds CSS classes based on conditional logic without removing base classes.
- **Example**: `th:classappend="${selectedCategory == 'Technical'} ? 'active' : ''"` highlights the active category filter button.

---

### Category 4: Database Design, JPA & Hibernate ORM

#### Concept 22: Relational Database Management Systems (RDBMS) & MySQL
- **What it is**: An RDBMS organizes structured data into tables of rows and columns with enforced relationships, primary keys, and ACID transaction guarantees.
- **Why MySQL**: Fast, highly reliable, widely used in enterprise production, and perfectly integrated with Spring Data JPA.

#### Concept 23: Database Normalization (1NF, 2NF, 3NF in EventEase)
- **1NF (First Normal Form)**: All column values are atomic; no repeating groups.
- **2NF (Second Normal Form)**: In 1NF and all non-key columns depend fully on the primary key.
- **3NF (Third Normal Form)**: In 2NF and there are no transitive dependencies (e.g., `Organizer` data is stored in `organizers`, referenced by `organizer_id` in `events`, avoiding data duplication).

#### Concept 24: Primary Keys vs Foreign Keys
- **Primary Key (PK)**: A unique identifier for each record in a table (e.g., `id` in `students`). It cannot be null and must be unique.
- **Foreign Key (FK)**: A column in one table that references the primary key of another table (e.g., `organizer_id` in `events` table references `id` in `organizers` table) to maintain referential integrity.

#### Concept 25: JPA (Java Persistence API / Jakarta Persistence)
- **What it is**: The standard specification in Java for object-relational mapping (ORM). It defines interfaces and annotations (`@Entity`, `@Table`, `@ManyToOne`) without containing implementation code.

#### Concept 26: Hibernate ORM
- **What it is**: The actual underlying implementation of the JPA specification provided by Spring Boot.
- **How it works**: Translates Java object state into SQL `INSERT`, `UPDATE`, `SELECT`, and `DELETE` queries, eliminating manual JDBC code.

#### Concept 27: JPA Entity Annotations
- **`@Entity`**: Marks a Java class as a persistent database entity mapped to a database table.
- **`@Table(name = "events")`**: Specifies the name of the database table.
- **`@Id`**: Marks the primary key field.
- **`@GeneratedValue(strategy = GenerationType.IDENTITY)`**: Instructs MySQL to use its native `AUTO_INCREMENT` column mechanism for generating IDs.

#### Concept 28: Entity Relationship Annotations (`@ManyToOne`, `@JoinColumn`)
- **`@ManyToOne`**: Declares that many child rows map to one parent row (e.g., many `Event` entities belong to one `Organizer`).
- **`@JoinColumn(name = "organizer_id")`**: Specifies the exact foreign key column name in the database table.

#### Concept 29: Hibernate DDL-Auto Modes
- **`update`** (Used in our project): Automatically creates tables if they do not exist and updates schema if new columns are added without dropping existing data.
- **`create`**: Drops existing tables and recreates them on every application startup (data is lost).
- **`create-drop`**: Creates tables on startup and drops them when `SessionFactory` closes.
- **`validate`**: Only checks whether existing database schema matches entity mappings, making no changes.

#### Concept 30: Spring Data JPA & `JpaRepository` Interface
- **What it is**: Spring Data JPA provides generic repository interfaces that eliminate boilerplate DAO implementation classes.
- **In our project**: `public interface EventRepository extends JpaRepository<Event, Long>` provides methods like `findAll()`, `findById()`, `save()`, and `delete()` without writing a single line of SQL.

#### Concept 31: Derived Query Methods
- **What it is**: Spring Data parses method names based on entity properties and automatically generates SQL queries behind the scenes.
- **Examples from EventEase**:
  - `findByOrganizerId(Long organizerId)` $\rightarrow$ `SELECT * FROM events WHERE organizer_id = ?`
  - `findByCategoryIgnoreCase(String category)` $\rightarrow$ `SELECT * FROM events WHERE UPPER(category) = UPPER(?)`
  - `findByStudentIdAndEventIdAndStatus(Long sId, Long eId, String st)` $\rightarrow$ `SELECT * FROM registrations WHERE student_id = ? AND event_id = ? AND status = ?`

#### Concept 32: `Optional<T>` Container in Java
- **What it is**: A container object that may or may not contain a non-null value.
- **Why we use it**: `findById(id)` returns `Optional<Event>`. Calling `.orElseThrow(() -> new ResourceNotFoundException(...))` prevents `NullPointerException` and handles missing data gracefully.

---

### Category 5: Service Layer, Dependency Injection & IoC

#### Concept 33: Service Layer Architecture & `@Service`
- **What it is**: The Service layer holds the business logic of the application.
- **Why it matters**: It keeps Controllers thin (focused only on HTTP routing and view presentation) and Repositories clean (focused only on database access).
- **In EventEase**: `RegistrationService` contains all business checks: verifying seat capacity, checking duplicates, and verifying cancellation deadlines.

#### Concept 34: Inversion of Control (IoC) Principle
- **What it is**: Traditional programming creates dependencies with `new Service()`. In Spring, the control of object creation and lifecycle is inverted to the **Spring IoC Container** (ApplicationContext).

#### Concept 35: Dependency Injection (DI) & `@Autowired`
- **What it is**: The design pattern where the IoC container injects required dependent objects into a class rather than the class creating them itself.
- **Example**: In `EventController`, `@Autowired private EventService eventService;` instructs Spring to automatically provide the initialized `EventService` instance.

#### Concept 36: Spring Bean Lifecycle & Component Scanning
- **What it is**: Any class annotated with `@Component`, `@Service`, `@Repository`, or `@Controller` is discovered during component scanning and registered as a **Spring Bean** (singleton by default). Spring creates it, injects dependencies, and destroys it when the application shuts down.

---

### Category 6: Validation, Error Handling & Business Rules

#### Concept 37: Jakarta Bean Validation Annotations
- **`@NotBlank`**: Ensures a String is not null and trimmed length is greater than 0 (e.g., student name, venue).
- **`@Email`**: Validates standard RFC email syntax (e.g., `user@domain.com`).
- **`@Positive`**: Ensures numeric values like `maxSeats` and `yearOfStudy` are strictly $> 0$.
- **`@NotNull`**: Ensures an object reference (such as `LocalDate eventDate`) is not null.

#### Concept 38: `BindingResult` & Form Validation Flow
- **What it is**: In Spring MVC, `@Valid @ModelAttribute("event") Event event, BindingResult result` captures any validation errors during form submission.
- **How it works**: If `result.hasErrors()` is true, the controller returns the user back to the form with error messages instead of proceeding to save invalid data.

#### Concept 39: Custom Runtime Exceptions
- **In EventEase**:
  - `ResourceNotFoundException`: Thrown when a requested entity ID does not exist in the database (e.g., Event ID 999).
  - `BusinessException`: Thrown when a business rule is violated (e.g., event full, duplicate registration).

#### Concept 40: `@ControllerAdvice` & Global Exception Handling
- **What it is**: A centralized interceptor for exceptions thrown across all controllers. It allows returning user-friendly error views or structured JSON error responses instead of default white-label error pages.

#### Concept 41: Business Invariant Enforcement (Seat Limits & Duplicates)
- **Invariant**: The system state must always satisfy $0 \le \text{Occupied Seats} \le \text{maxSeats}$.
- **How EventEase enforces it**: Before inserting a new `Registration`, `RegistrationService.registerStudent` checks:
  1. Is the student already registered? If yes $\rightarrow$ throw `BusinessException`.
  2. Is `registeredCount >= event.getMaxSeats()`? If yes $\rightarrow$ throw `BusinessException`.

#### Concept 42: Soft Cancellation vs Hard Delete
- **Hard Delete**: `DELETE FROM registrations WHERE id = ?` removes the row forever. (Bad practice: loses audit trail).
- **Soft Cancellation** (Used in EventEase): `registration.setStatus("CANCELLED");` updates the status column. The historical record is preserved, but because seat counters filter by `status = 'REGISTERED'`, the seat is immediately freed up for other students.

#### Concept 43: Dynamic Seat Calculation vs Stored Counter Fields
- **Why dynamic calculation is superior**: If you store a column `int availableSeats` in the `events` table, simultaneous requests could cause race conditions and data corruption. By calculating `Available Seats = maxSeats - count(REGISTERED)` on the fly, the count is always 100% consistent with the actual registration table.

---

### Category 7: Data Initialization & REST API Architecture

#### Concept 44: `CommandLineRunner` & Startup Data Seeding (`DataLoader`)
- **What it is**: A Spring Boot interface with a `run(String... args)` method that executes automatically once the Spring Application Context is fully loaded.
- **In EventEase**: `DataLoader.java` checks if `organizerRepository.count() == 0`. If empty, it automatically populates 10 Organizers, 50 Students, and 20 Events so the examiner immediately sees a fully functional system.

#### Concept 45: REST Architecture & Statelessness
- **What it is**: Representational State Transfer (REST) is an architectural style for networked applications using standard HTTP verbs.
- **Statelessness**: Every REST API request contains all information needed to process it; the server stores no client session context between API calls.

#### Concept 46: JSON Serialization & Jackson ObjectMapper
- **What it is**: Jackson is the default JSON library in Spring Boot.
- **How it works**: It automatically converts Java objects into JSON strings when returning from `@RestController`, and parses incoming JSON strings into Java objects for `@RequestBody`.

#### Concept 47: HTTP Status Codes
- **`200 OK`**: Request succeeded (e.g., retrieving events, successful registration).
- **`201 Created`**: Resource successfully created.
- **`400 Bad Request`**: Client sent invalid data or violated a business constraint.
- **`404 Not Found`**: Requested resource (event/student) does not exist.
- **`500 Internal Server Error`**: Unexpected server-side crash.

#### Concept 48: `ResponseEntity<T>` in Spring REST
- **What it is**: Represents the entire HTTP response including status code, headers, and body.
- **Example**: `return ResponseEntity.ok(eventService.getEventById(id));` returns HTTP 200 with the event JSON payload.

---

### Category 8: Build Automation, Styling & Testing

#### Concept 49: Apache Maven Build Lifecycle & `pom.xml`
- **What it is**: Maven manages project dependencies, compilation, testing, packaging, and execution.
- **Core phases**: `clean` (deletes target directory), `compile` (compiles source code), `test` (executes unit tests), `package` (bundles into executable `.jar`), `install` (copies jar to local repository).

#### Concept 50: Pure CSS3 Architecture (No Heavy Frameworks)
- **Why Pure CSS**: Many student projects break due to un-cached external CDN links (Bootstrap/Tailwind). EventEase uses pure, self-contained CSS3 with CSS variables (`--primary-color`, `--success-color`), Flexbox, CSS Grid, and media queries for responsive mobile/desktop layouts.

#### Concept 51: Web Application Security Fundamentals
- **Input Validation**: Enforcing constraints prevents SQL injection and buffer exploits.
- **ORMs as Security**: Hibernate uses prepared statements with parameterized queries (`?`), completely preventing SQL injection attacks.
- **CORS (Cross-Origin Resource Sharing)**: Controls which domains can access the REST API endpoints.

#### Concept 52: Software Testing & Demonstration Scenarios
- **Unit Testing**: Testing individual service methods in isolation.
- **Integration Testing**: Verifying that Controller, Service, and Repository layers interact seamlessly with the live MySQL database.
- **Walkthrough Demonstration**: Proving business rules using the 2-Seat Java Workshop test case.

---

# Part II: Top 35 Viva Questions & Accurate Answers

---

### 1. General Spring Boot & Architecture

#### Q1: What is Spring Boot and why did you choose it for EventEase?
**Answer**: Spring Boot is an opinionated framework built on top of the Spring ecosystem. We chose it because it provides an embedded Tomcat server, starter dependencies, and auto-configuration. It eliminates complex XML configurations, allowing us to build a production-ready web application rapidly.

#### Q2: What is the purpose of `@SpringBootApplication`?
**Answer**: `@SpringBootApplication` is a convenience annotation that bundles three key annotations:
1. `@Configuration`: Marks the class as a source of bean definitions.
2. `@EnableAutoConfiguration`: Tells Spring Boot to configure beans based on classpath dependencies.
3. `@ComponentScan`: Enables automatic scanning of components (`@Controller`, `@Service`, `@Repository`) in the base package and sub-packages.

#### Q3: Explain the MVC architecture in EventEase.
**Answer**: 
- **Model**: JPA entities (`Student`, `Event`, `Organizer`, `Registration`) representing the data.
- **View**: Thymeleaf HTML templates that render the interface in the browser.
- **Controller**: `@Controller` classes that receive HTTP requests, invoke business services, and return view names.

#### Q4: What is the difference between `@Controller` and `@RestController`?
**Answer**: 
- `@Controller` is used for traditional web applications where methods return the name of a view template (HTML) to be rendered.
- `@RestController` is used for RESTful web services. It automatically applies `@ResponseBody` to all methods, serializing return values directly into JSON format.

#### Q5: What is Inversion of Control (IoC) and Dependency Injection (DI)?
**Answer**: Inversion of Control is a software principle where the control of object creation and lifecycle is delegated to a container (Spring IoC Container). Dependency Injection is the mechanism used to implement IoC, where dependencies are provided (injected) into a class via annotations like `@Autowired` rather than instantiated using `new`.

#### Q6: How does Spring Boot connect to MySQL?
**Answer**: We specify connection parameters in `application.properties` (`spring.datasource.url`, `username`, `password`, and `driver-class-name`). Spring Boot uses the `mysql-connector-j` driver and creates a `HikariCP` connection pool bean automatically.

#### Q7: What is the role of `pom.xml` in this project?
**Answer**: `pom.xml` (Project Object Model) is the central configuration file for Maven. It defines project metadata, Java version (21), starter dependencies (Web, Thymeleaf, Data JPA, Validation, MySQL), and build plugins (`spring-boot-maven-plugin`).

#### Q8: What happens when you run `mvn spring-boot:run`?
**Answer**: Maven compiles the Java source code, packages the resources, creates the Spring ApplicationContext, starts the embedded Tomcat server on port 8080, establishes the MySQL database connection, runs `DataLoader`, and begins accepting HTTP requests.

---

### 2. Database, JPA & Hibernate

#### Q9: What is the difference between JPA and Hibernate?
**Answer**: JPA (Jakarta Persistence API) is a standard specification defining interfaces and rules for object-relational mapping in Java. Hibernate is the actual ORM library that implements the JPA specification and executes SQL queries against the database.

#### Q10: Explain the entity relationships in EventEase.
**Answer**:
1. **Organizer $\rightarrow$ Event (`1 : N`)**: One organizer creates multiple events.
2. **Student $\rightarrow$ Registration (`1 : N`)**: One student can have multiple registrations.
3. **Event $\rightarrow$ Registration (`1 : N`)**: One event can have multiple registrations.
4. **Student $\rightarrow$ Event (`M : N`)**: Modeled via the `Registration` join entity containing extra attributes (`registrationDate`, `status`).

#### Q11: Why do we use `@GeneratedValue(strategy = GenerationType.IDENTITY)`?
**Answer**: `GenerationType.IDENTITY` tells Hibernate to rely on the underlying MySQL database's native `AUTO_INCREMENT` feature to generate unique primary key values for new records.

#### Q12: What does `spring.jpa.hibernate.ddl-auto=update` do?
**Answer**: `update` checks the existing database tables against JPA entity definitions on application startup. If tables or columns are missing, Hibernate creates or alters them automatically without dropping existing data.

#### Q13: What is `JpaRepository` and what advantages does it provide?
**Answer**: `JpaRepository` is a Spring Data JPA interface that provides complete CRUD operations (`save`, `findById`, `findAll`, `deleteById`) and pagination support out of the box without requiring manual SQL or boilerplate DAO classes.

#### Q14: How do Derived Query Methods work in Spring Data JPA?
**Answer**: Spring Data JPA inspects the method name in the repository interface. Based on naming conventions (e.g., `findByCategoryIgnoreCase(String category)`), it automatically parses the method and generates the corresponding SQL `SELECT` statement with `WHERE UPPER(category) = UPPER(?)`.

#### Q15: Why is `Optional<T>` used as the return type for repository search methods?
**Answer**: `Optional<T>` is a container that may or may not hold a value. It prevents `NullPointerException` by forcing developers to handle empty states explicitly using methods like `.orElseThrow(() -> new ResourceNotFoundException(...))`.

---

### 3. Thymeleaf & Frontend UI

#### Q16: What is Thymeleaf and how does it differ from JSP?
**Answer**: Thymeleaf is a modern server-side Java template engine designed for HTML5. Unlike JSP (JavaServer Pages), which requires a servlet container to compile, Thymeleaf supports "Natural Templating"—templates can be opened directly as static prototypes in any web browser without running a server.

#### Q17: What are the primary Thymeleaf expression types?
**Answer**:
- `${...}`: Variable expressions (e.g., `${event.title}`) to fetch model attributes.
- `@{...}`: Link URL expressions (e.g., `@{/events}`) to create context-relative URLs.
- `*{...}`: Selection expressions for form fields within `th:object`.
- `#{...}`: Message / localization expressions.

#### Q18: How does `th:each` work in `events.html`?
**Answer**: `<tr th:each="event : ${events}">` iterates through the collection of `Event` objects passed from the controller, creating a new `<tr>` table row for every event in the list.

#### Q19: How do you display conditional elements using Thymeleaf?
**Answer**: Using `th:if` and `th:unless`. For example, `th:if="${event.availableSeats > 0}"` renders the green "Register" button, while `th:if="${event.availableSeats <= 0}"` displays the red "Registration Closed" badge.

#### Q20: How are form submissions bound in Thymeleaf?
**Answer**: A form uses `th:action="@{/events/create}"` to define the target URL and `th:object="${event}"` to bind the form to a model object. Input fields use `th:field="*{title}"` to bind directly to the entity's properties.

#### Q21: What is the Post-Redirect-Get (PRG) pattern used in controllers?
**Answer**: After processing a form submission (POST), the controller returns a redirect (`return "redirect:/events";`) instead of directly rendering the HTML template. This instructs the browser to issue a GET request, preventing duplicate submissions if the user refreshes the page.

---

### 4. Business Logic, Exceptions & Seat Allocation

#### Q22: How is real-time available seat capacity calculated in EventEase?
**Answer**: Available seats are calculated dynamically:
$$\text{Available Seats} = \text{event.getMaxSeats()} - \text{COUNT}(\text{Registrations with status 'REGISTERED'})$$
We do not store `availableSeats` as a static column to avoid data synchronization bugs and race conditions.

#### Q23: What happens when an event reaches maximum capacity?
**Answer**: When `registeredCount >= event.getMaxSeats()`, `RegistrationService` throws a `BusinessException("Registration failed: No seats are available for this event.")`. The controller catches this and displays a user-friendly error alert on the registration page.

#### Q24: How does EventEase prevent duplicate registrations for the same student?
**Answer**: Before creating a registration, `RegistrationService` queries `registrationRepository.findByStudentIdAndEventIdAndStatus(studentId, eventId, "REGISTERED")`. If a record exists, it throws a `BusinessException("You are already registered for this event.")`.

#### Q25: What is "Soft Cancellation" and why is it used?
**Answer**: When a student cancels their registration, the database row is **not deleted**. Instead, its status is updated from `REGISTERED` to `CANCELLED`. This preserves historical registration logs for college audits while instantly freeing up the seat for other students.

#### Q26: Can a student re-register for an event after cancelling?
**Answer**: Yes. Because the duplicate check only searches for registrations with `status = 'REGISTERED'`, a student whose previous registration is `CANCELLED` is allowed to register again if seats are available.

#### Q27: What validation prevents cancellation after an event has passed?
**Answer**: In `RegistrationService.cancelRegistration()`, the code compares today's date with the event date:
```java
if (LocalDate.now().isAfter(eventDate)) {
    throw new BusinessException("Registration cannot be cancelled after the event date.");
}
```

---

### 5. REST APIs & HTTP Protocols

#### Q28: What is a RESTful API?
**Answer**: A RESTful API is an architectural style for web services that uses standard HTTP methods (`GET`, `POST`, `PUT`, `DELETE`), operates statelessly, and exchanges data typically in JSON format.

#### Q29: What is the difference between `@RequestParam` and `@PathVariable`?
**Answer**:
- `@PathVariable` extracts values directly from the URI path (e.g., `/api/events/5` $\rightarrow$ `5`).
- `@RequestParam` extracts query parameters from the query string or form data (e.g., `/api/events?category=Technical` $\rightarrow$ `Technical`).

#### Q30: What is `@RequestBody` and how does it work?
**Answer**: `@RequestBody` tells Spring to read the raw HTTP request body (JSON payload) and deserialize it into a Java object using the Jackson `ObjectMapper` library.

#### Q31: What is `ResponseEntity<T>`?
**Answer**: `ResponseEntity<T>` represents the complete HTTP response in Spring. It allows developers to specify the HTTP status code (e.g., `200 OK`, `404 Not Found`), custom headers, and the response body.

#### Q32: What standard HTTP status codes are returned by EventEase REST endpoints?
**Answer**:
- `200 OK`: Successful retrieval, update, or cancellation.
- `201 Created`: Successful creation of a new entity.
- `400 Bad Request`: Validation failure or business rule violation.
- `404 Not Found`: Entity not found in database.

---

### 6. Project Demonstration & Troubleshooting

#### Q33: How does the `DataLoader` class work in this project?
**Answer**: `DataLoader` implements `CommandLineRunner`. When the Spring Boot application starts, its `run()` method executes. It checks if the database is empty; if so, it seeds 10 Organizers, 50 Students, 20 Events, and sample registrations, providing instant demo data.

#### Q34: How would you demonstrate the seat capacity limit to an examiner?
**Answer**:
1. Open the **Java Workshop** event (configured with `maxSeats = 2`).
2. Register **Student 1 (Arun Kumar)** $\rightarrow$ Available seats drop to `1`.
3. Register **Student 2 (Priya Sharma)** $\rightarrow$ Available seats drop to `0`, badge switches to **FULL**.
4. Attempt to register **Student 3 (Rahul Verma)** $\rightarrow$ System rejects with error *"No seats are available"*.
5. Cancel Student 1's registration in `/my-registrations` $\rightarrow$ Available seats return to `1`.
6. Register Student 3 $\rightarrow$ Registration succeeds!

#### Q35: If the database connection fails with "Access denied for user 'root'", how do you fix it?
**Answer**: Open `src/main/resources/application.properties` and verify that `spring.datasource.password` matches your local MySQL `root` password, and ensure MySQL service is running on port 3306.

---

*End of Viva Preparation Guide — EventEase College Event Registration System.*
