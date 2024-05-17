import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private List<Student> registeredStudents;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.registeredStudents = new ArrayList<>();
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public List<Student> getRegisteredStudents() {
        return registeredStudents;
    }

    public boolean registerStudent(Student student) {
        if (registeredStudents.size() < capacity) {
            registeredStudents.add(student);
            return true;
        }
        return false;
    }

    public boolean removeStudent(Student student) {
        return registeredStudents.remove(student);
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseCode='" + courseCode + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", capacity=" + capacity +
                ", schedule='" + schedule + '\'' +
                '}';
    }
}

class Student {
    private int studentID;
    private String name;
    private List<Course> registeredCourses;

    public Student(int studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public int getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public boolean registerCourse(Course course) {
        if (registeredCourses.contains(course)) {
            return false;
        }
        registeredCourses.add(course);
        return course.registerStudent(this);
    }

    public boolean dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            return course.removeStudent(this);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentID=" + studentID +
                ", name='" + name + '\'' +
                '}';
    }
}

public class CourseRegistrationSystem {
    private List<Course> courses;
    private List<Student> students;
    private Scanner scanner;

    public CourseRegistrationSystem() {
        this.courses = new ArrayList<>();
        this.students = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void displayCourses() {
        System.out.println("Available Courses:");
        for (Course course : courses) {
            int availableSlots = course.getCapacity() - course.getRegisteredStudents().size();
            System.out.println(course + " - Available Slots: " + availableSlots);
        }
    }

    public void displayStudents() {
        System.out.println("Registered Students:");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public void registerCourse() {
        System.out.print("Enter Student ID: ");
        int studentID = scanner.nextInt();
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.next();

        Student student = findStudent(studentID);
        Course course = findCourse(courseCode);

        if (student != null && course != null) {
            if (student.registerCourse(course)) {
                System.out.println("Course registration successful!");
            } else {
                System.out.println("Course is full or already registered.");
            }
        } else {
            System.out.println("Student or Course not found.");
        }
    }

    public void dropCourse() {
        System.out.print("Enter Student ID: ");
        int studentID = scanner.nextInt();
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.next();

        Student student = findStudent(studentID);
        Course course = findCourse(courseCode);

        if (student != null && course != null) {
            if (student.dropCourse(course)) {
                System.out.println("Course dropped successfully!");
            } else {
                System.out.println("Student is not registered in this course.");
            }
        } else {
            System.out.println("Student or Course not found.");
        }
    }

    private Student findStudent(int studentID) {
        for (Student student : students) {
            if (student.getStudentID() == studentID) {
                return student;
            }
        }
        return null;
    }

    private Course findCourse(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                return course;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        CourseRegistrationSystem system = new CourseRegistrationSystem();

        // Adding sample courses
        system.addCourse(new Course("CSC101", "Introduction to Programming", "Basic programming concepts", 50, "Mon/Wed/Fri 10:00-11:00"));
        system.addCourse(new Course("MAT201", "Calculus", "Mathematical analysis", 40, "Tue/Thu 13:00-14:30"));
        system.addCourse(new Course("ENG101", "English Composition", "Writing and communication skills", 60, "Mon/Wed 14:00-15:30"));

        // Adding sample students
        system.addStudent(new Student(1, "John Doe"));
        system.addStudent(new Student(2, "Jane Doe"));

        boolean exit = false;

        while (!exit) {
            System.out.println("\nCourse Registration System Menu:");
            System.out.println("1. Display Available Courses");
            System.out.println("2. Display Registered Students");
            System.out.println("3. Register for a Course");
            System.out.println("4. Drop a Course");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = system.scanner.nextInt();

            switch (choice) {
                case 1:
                    system.displayCourses();
                    break;
                case 2:
                    system.displayStudents();
                    break;
                case 3:
                    system.registerCourse();
                    break;
                case 4:
                    system.dropCourse();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
