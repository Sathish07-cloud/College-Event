package com.eventease;

import com.eventease.entity.Event;
import com.eventease.entity.Organizer;
import com.eventease.entity.Registration;
import com.eventease.entity.Student;
import com.eventease.repository.EventRepository;
import com.eventease.repository.OrganizerRepository;
import com.eventease.repository.RegistrationRepository;
import com.eventease.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// DataLoader class - automatically runs on application startup to insert sample data
// Implements CommandLineRunner so Spring Boot executes the run() method upon starting
@Component
public class DataLoader implements CommandLineRunner {

    // Repository dependencies injected by Spring
    @Autowired
    private OrganizerRepository organizerRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Override
    public void run(String... args) throws Exception {

        // Check if data already exists to prevent duplicate entries on application restarts
        if (organizerRepository.count() > 0 || studentRepository.count() > 0 || eventRepository.count() > 0) {
            System.out.println("DataLoader: Sample data already exists. Skipping data loading.");
            return;
        }

        System.out.println("DataLoader: Loading initial sample data into database...");

        // ==========================================
        // 1. POPULATE 10 ORGANIZERS
        // ==========================================
        List<Organizer> organizers = new ArrayList<>();

        organizers.add(createOrganizer("Computer Science Dept", "cs@eventease.com", "admin123"));
        organizers.add(createOrganizer("Electronics Dept", "ece@eventease.com", "admin123"));
        organizers.add(createOrganizer("Cultural Club", "cultural@eventease.com", "admin123"));
        organizers.add(createOrganizer("Coding Club", "coding@eventease.com", "admin123"));
        organizers.add(createOrganizer("Sports Committee", "sports@eventease.com", "admin123"));
        organizers.add(createOrganizer("Music Society", "music@eventease.com", "admin123"));
        organizers.add(createOrganizer("Robotics Club", "robotics@eventease.com", "admin123"));
        organizers.add(createOrganizer("IEEE Student Branch", "ieee@eventease.com", "admin123"));
        organizers.add(createOrganizer("ACM Student Chapter", "acm@eventease.com", "admin123"));
        organizers.add(createOrganizer("Literary Club", "literary@eventease.com", "admin123"));

        // Save all organizers to database
        organizers = organizerRepository.saveAll(organizers);
        System.out.println("DataLoader: Saved 10 organizers successfully.");

        // ==========================================
        // 2. POPULATE 50 STUDENTS
        // ==========================================
        List<Student> students = new ArrayList<>();

        students.add(createStudent("Arun Kumar", "arun.kumar@eventease.com", "password123", "CSE", 2));
        students.add(createStudent("Priya Sharma", "priya.sharma@eventease.com", "password123", "ECE", 3));
        students.add(createStudent("Rahul Verma", "rahul.verma@eventease.com", "password123", "MECH", 4));
        students.add(createStudent("Anitha Reddy", "anitha.reddy@eventease.com", "password123", "CSE", 1));
        students.add(createStudent("Sneha Patel", "sneha.patel@eventease.com", "password123", "IT", 2));
        students.add(createStudent("Karthik S", "karthik.s@eventease.com", "password123", "CSE", 3));
        students.add(createStudent("Pooja Nair", "pooja.nair@eventease.com", "password123", "ECE", 2));
        students.add(createStudent("Manoj Kumar", "manoj.kumar@eventease.com", "password123", "CIVIL", 4));
        students.add(createStudent("Divya Krishnan", "divya.krishnan@eventease.com", "password123", "IT", 1));
        students.add(createStudent("Vikram Singh", "vikram.singh@eventease.com", "password123", "MECH", 3));

        students.add(createStudent("Swathi Rao", "swathi.rao@eventease.com", "password123", "CSE", 4));
        students.add(createStudent("Sanjay Dutt", "sanjay.dutt@eventease.com", "password123", "ECE", 1));
        students.add(createStudent("Meera Nambiar", "meera.nambiar@eventease.com", "password123", "IT", 3));
        students.add(createStudent("Rajesh G", "rajesh.g@eventease.com", "password123", "CIVIL", 2));
        students.add(createStudent("Kavya Menon", "kavya.menon@eventease.com", "password123", "CSE", 2));
        students.add(createStudent("Suresh Babu", "suresh.babu@eventease.com", "password123", "MECH", 1));
        students.add(createStudent("Deepa Joshi", "deepa.joshi@eventease.com", "password123", "ECE", 4));
        students.add(createStudent("Ashwin Ramesh", "ashwin.ramesh@eventease.com", "password123", "IT", 2));
        students.add(createStudent("Nithya Sundar", "nithya.sundar@eventease.com", "password123", "CSE", 3));
        students.add(createStudent("Harish V", "harish.v@eventease.com", "password123", "CIVIL", 3));

        students.add(createStudent("Preeti Gupta", "preeti.gupta@eventease.com", "password123", "ECE", 2));
        students.add(createStudent("Deepak Sharma", "deepak.sharma@eventease.com", "password123", "MECH", 2));
        students.add(createStudent("Ananya Roy", "ananya.roy@eventease.com", "password123", "CSE", 1));
        students.add(createStudent("Rakesh Nair", "rakesh.nair@eventease.com", "password123", "IT", 4));
        students.add(createStudent("Shreya Deshmukh", "shreya.deshmukh@eventease.com", "password123", "ECE", 3));
        students.add(createStudent("Vignesh Balaji", "vignesh.balaji@eventease.com", "password123", "CSE", 4));
        students.add(createStudent("Sandhya Rani", "sandhya.rani@eventease.com", "password123", "CIVIL", 1));
        students.add(createStudent("Naveen Chandra", "naveen.chandra@eventease.com", "password123", "MECH", 3));
        students.add(createStudent("Bhavana Murthy", "bhavana.murthy@eventease.com", "password123", "IT", 2));
        students.add(createStudent("Gautam Iyer", "gautam.iyer@eventease.com", "password123", "CSE", 3));

        students.add(createStudent("Lavanya K", "lavanya.k@eventease.com", "password123", "ECE", 2));
        students.add(createStudent("Ajay Raj", "ajay.raj@eventease.com", "password123", "MECH", 4));
        students.add(createStudent("Monica Das", "monica.das@eventease.com", "password123", "CIVIL", 2));
        students.add(createStudent("Rohit Bhat", "rohit.bhat@eventease.com", "password123", "IT", 1));
        students.add(createStudent("Shilpa Hegde", "shilpa.hegde@eventease.com", "password123", "CSE", 2));
        students.add(createStudent("Tarun Joseph", "tarun.joseph@eventease.com", "password123", "ECE", 3));
        students.add(createStudent("Varsha Srinivas", "varsha.srinivas@eventease.com", "password123", "MECH", 1));
        students.add(createStudent("Akhil Pillai", "akhil.pillai@eventease.com", "password123", "IT", 3));
        students.add(createStudent("Keerthi Varma", "keerthi.varma@eventease.com", "password123", "CSE", 4));
        students.add(createStudent("Chetan Reddy", "chetan.reddy@eventease.com", "password123", "CIVIL", 4));

        students.add(createStudent("Archana Mishra", "archana.mishra@eventease.com", "password123", "ECE", 1));
        students.add(createStudent("Surya Prakash", "surya.prakash@eventease.com", "password123", "MECH", 2));
        students.add(createStudent("Tanvi Saxena", "tanvi.saxena@eventease.com", "password123", "IT", 2));
        students.add(createStudent("Abhinav Menon", "abhinav.menon@eventease.com", "password123", "CSE", 3));
        students.add(createStudent("Roshni Thomas", "roshni.thomas@eventease.com", "password123", "ECE", 4));
        students.add(createStudent("Vigneshwar R", "vigneshwar.r@eventease.com", "password123", "CIVIL", 3));
        students.add(createStudent("Ishwarya Mohan", "ishwarya.mohan@eventease.com", "password123", "CSE", 1));
        students.add(createStudent("Pradeep Kumar", "pradeep.kumar@eventease.com", "password123", "MECH", 3));
        students.add(createStudent("Yamini Sen", "yamini.sen@eventease.com", "password123", "IT", 4));
        students.add(createStudent("Siddharth Jain", "siddharth.jain@eventease.com", "password123", "CSE", 2));

        // Save all students to database
        students = studentRepository.saveAll(students);
        System.out.println("DataLoader: Saved 50 students successfully.");

        // ==========================================
        // 3. POPULATE 20 EVENTS ACROSS CATEGORIES
        // ==========================================
        List<Event> events = new ArrayList<>();

        // Event 1: Exact demo event required for testing seat capacity limits
        events.add(createEvent(
                "Java Workshop",
                "Hands-on workshop on Java 21, Spring Boot, and RESTful APIs for modern web application development.",
                LocalDate.now().plusDays(10),
                "Lab 3, CS Block",
                2,
                "Technical",
                organizers.get(0) // Computer Science Dept
        ));

        // Event 2
        events.add(createEvent(
                "Web Development Bootcamp",
                "Intensive training on HTML5, CSS3, modern JavaScript, and responsive UI design.",
                LocalDate.now().plusDays(5),
                "Seminar Hall 1",
                40,
                "Workshop",
                organizers.get(3) // Coding Club
        ));

        // Event 3
        events.add(createEvent(
                "AI and Machine Learning Seminar",
                "Explore the frontiers of Artificial Intelligence, Deep Neural Networks, and Generative AI.",
                LocalDate.now().plusDays(12),
                "Auditorium A",
                60,
                "Seminar",
                organizers.get(0) // Computer Science Dept
        ));

        // Event 4
        events.add(createEvent(
                "Annual College Hackathon 2026",
                "24-hour coding challenge to build innovative solutions for real-world campus problems.",
                LocalDate.now().plusDays(15),
                "Central Computing Lab",
                50,
                "Technical",
                organizers.get(8) // ACM Student Chapter
        ));

        // Event 5
        events.add(createEvent(
                "Robotics and IoT Expo",
                "Interactive exhibition and live demonstrations of autonomous robots and smart IoT prototypes.",
                LocalDate.now().plusDays(18),
                "Mechanical Workshop Block",
                45,
                "Technical",
                organizers.get(6) // Robotics Club
        ));

        // Event 6
        events.add(createEvent(
                "VLSI Circuit Design Workshop",
                "Comprehensive practical session covering digital circuit architecture and FPGA synthesis.",
                LocalDate.now().plusDays(8),
                "ECE Lab 2",
                30,
                "Workshop",
                organizers.get(1) // Electronics Dept
        ));

        // Event 7
        events.add(createEvent(
                "Cybersecurity and Ethical Hacking",
                "Introduction to security protocols, ethical hacking principles, and network defense strategies.",
                LocalDate.now().plusDays(14),
                "CS Conference Room",
                50,
                "Seminar",
                organizers.get(7) // IEEE Student Branch
        ));

        // Event 8
        events.add(createEvent(
                "Cloud Computing with AWS & Docker",
                "Practical guide to containerization, microservices deployment, and AWS cloud management.",
                LocalDate.now().plusDays(20),
                "IT Lab 1",
                35,
                "Workshop",
                organizers.get(3) // Coding Club
        ));

        // Event 9
        events.add(createEvent(
                "Inter-College Cricket Tournament",
                "Annual knock-out cricket championship featuring talented teams across all colleges.",
                LocalDate.now().plusDays(7),
                "Main Sports Ground",
                100,
                "Sports",
                organizers.get(4) // Sports Committee
        ));

        // Event 10
        events.add(createEvent(
                "Badminton Championship 2026",
                "Fast-paced singles and doubles badminton tournament for all registered students.",
                LocalDate.now().plusDays(11),
                "Indoor Sports Complex",
                32,
                "Sports",
                organizers.get(4) // Sports Committee
        ));

        // Event 11
        events.add(createEvent(
                "Annual Cultural Fest - Tarang",
                "The biggest cultural festival with music, dance, theatrical plays, and creative competitions.",
                LocalDate.now().plusDays(25),
                "Open Air Theatre",
                200,
                "Cultural",
                organizers.get(2) // Cultural Club
        ));

        // Event 12
        events.add(createEvent(
                "Battle of the Bands - Rock Night",
                "Live musical competition showcasing the best rock, pop, and acoustic college music groups.",
                LocalDate.now().plusDays(16),
                "Amphitheatre",
                150,
                "Cultural",
                organizers.get(5) // Music Society
        ));

        // Event 13
        events.add(createEvent(
                "Classical Music & Raga Evening",
                "A soulful musical evening featuring traditional Carnatic and Hindustani classical melodies.",
                LocalDate.now().plusDays(22),
                "Mini Auditorium",
                80,
                "Cultural",
                organizers.get(5) // Music Society
        ));

        // Event 14
        events.add(createEvent(
                "Debate & Elocution Competition",
                "Express thoughts and argue perspectives on contemporary national and international issues.",
                LocalDate.now().plusDays(9),
                "Library Seminar Hall",
                40,
                "Seminar",
                organizers.get(9) // Literary Club
        ));

        // Event 15
        events.add(createEvent(
                "Creative Writing Workshop",
                "Learn nuances of storytelling, poetic expressions, and creative writing techniques.",
                LocalDate.now().plusDays(13),
                "Literary Club Room",
                25,
                "Workshop",
                organizers.get(9) // Literary Club
        ));

        // Event 16
        events.add(createEvent(
                "Embedded Systems with Raspberry Pi",
                "Hands-on building of smart automation projects using Raspberry Pi and Python scripts.",
                LocalDate.now().plusDays(17),
                "Embedded Systems Lab",
                30,
                "Workshop",
                organizers.get(1) // Electronics Dept
        ));

        // Event 17
        events.add(createEvent(
                "IEEE International Tech Symposium",
                "Prestigious research symposium showcasing papers, posters, and industry expert talks.",
                LocalDate.now().plusDays(28),
                "Main Auditorium",
                75,
                "Seminar",
                organizers.get(7) // IEEE Student Branch
        ));

        // Event 18
        events.add(createEvent(
                "ACM Competitive Programming Contest",
                "Timed problem-solving battle emphasizing data structures, graph algorithms, and optimization.",
                LocalDate.now().plusDays(6),
                "Computer Center 2",
                60,
                "Technical",
                organizers.get(8) // ACM Student Chapter
        ));

        // Event 19
        events.add(createEvent(
                "Table Tennis & Chess Championship",
                "Dual indoor sports tournament testing speed, strategy, tactical skill, and concentration.",
                LocalDate.now().plusDays(19),
                "Recreation Center",
                40,
                "Sports",
                organizers.get(4) // Sports Committee
        ));

        // Event 20
        events.add(createEvent(
                "Autonomous Drone Building Seminar",
                "Comprehensive overview of drone aerodynamics, motor controllers, and autonomous navigation.",
                LocalDate.now().plusDays(24),
                "Robotics Innovation Lab",
                35,
                "Seminar",
                organizers.get(6) // Robotics Club
        ));

        // Save all events to database
        events = eventRepository.saveAll(events);
        System.out.println("DataLoader: Saved 20 events successfully.");

        // ==========================================
        // 4. POPULATE SAMPLE REGISTRATIONS
        // ==========================================
        List<Registration> registrations = new ArrayList<>();

        // Register several students for various events (leaving Java Workshop open for demo testing)
        // Web Development Bootcamp (Event index 1)
        registrations.add(createRegistration(students.get(0), events.get(1)));
        registrations.add(createRegistration(students.get(1), events.get(1)));
        registrations.add(createRegistration(students.get(3), events.get(1)));
        registrations.add(createRegistration(students.get(4), events.get(1)));

        // AI and Machine Learning Seminar (Event index 2)
        registrations.add(createRegistration(students.get(5), events.get(2)));
        registrations.add(createRegistration(students.get(6), events.get(2)));
        registrations.add(createRegistration(students.get(7), events.get(2)));

        // Annual College Hackathon 2026 (Event index 3)
        registrations.add(createRegistration(students.get(8), events.get(3)));
        registrations.add(createRegistration(students.get(9), events.get(3)));
        registrations.add(createRegistration(students.get(10), events.get(3)));

        // Inter-College Cricket Tournament (Event index 8)
        registrations.add(createRegistration(students.get(11), events.get(8)));
        registrations.add(createRegistration(students.get(12), events.get(8)));
        registrations.add(createRegistration(students.get(13), events.get(8)));

        // Annual Cultural Fest - Tarang (Event index 10)
        registrations.add(createRegistration(students.get(14), events.get(10)));
        registrations.add(createRegistration(students.get(15), events.get(10)));
        registrations.add(createRegistration(students.get(16), events.get(10)));

        // Cybersecurity Seminar (Event index 6)
        registrations.add(createRegistration(students.get(17), events.get(6)));
        registrations.add(createRegistration(students.get(18), events.get(6)));

        // Save all registrations to database
        registrationRepository.saveAll(registrations);
        System.out.println("DataLoader: Saved " + registrations.size() + " sample registrations successfully.");

        System.out.println("DataLoader: All sample data loaded successfully into EventEase!");
    }

    // Helper method to instantiate and set up an Organizer
    private Organizer createOrganizer(String name, String email, String password) {
        Organizer organizer = new Organizer();
        organizer.setName(name);
        organizer.setEmail(email);
        organizer.setPassword(password);
        return organizer;
    }

    // Helper method to instantiate and set up a Student
    private Student createStudent(String name, String email, String password, String department, Integer yearOfStudy) {
        Student student = new Student();
        student.setName(name);
        student.setEmail(email);
        student.setPassword(password);
        student.setDepartment(department);
        student.setYearOfStudy(yearOfStudy);
        return student;
    }

    // Helper method to instantiate and set up an Event
    private Event createEvent(String title, String description, LocalDate eventDate,
                             String venue, Integer maxSeats, String category, Organizer organizer) {
        Event event = new Event();
        event.setTitle(title);
        event.setDescription(description);
        event.setEventDate(eventDate);
        event.setVenue(venue);
        event.setMaxSeats(maxSeats);
        event.setCategory(category);
        event.setOrganizer(organizer);
        return event;
    }

    // Helper method to instantiate and set up a Registration
    private Registration createRegistration(Student student, Event event) {
        Registration registration = new Registration();
        registration.setStudent(student);
        registration.setEvent(event);
        registration.setRegistrationDate(LocalDate.now());
        registration.setStatus("REGISTERED");
        return registration;
    }
}
