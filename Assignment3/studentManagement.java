import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


// ---------- ClassRoom ----------
class ClassRoom {
    private int id;
    private String name;

    public ClassRoom(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return "ClassRoom{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}

// ---------- Student ----------
class Student {
    private int id;
    private String name;
    private int classId;
    private int marks;
    private String gender;
    private int age;

    public Student(int id, String name, int classId, int marks, String gender, int age) {
        this.id = id;
        this.name = name;
        this.classId = classId;
        this.marks = marks;
        this.gender = gender;
        this.age = age;
    }

    // ---- Business Rule: Pass/Fail ----
    public String getStatus() {
        return marks < 50 ? "Failed" : "Passed";
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getClassId() { return classId; }
    public int getMarks() { return marks; }
    public String getGender() { return gender; }
    public int getAge() { return age; }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", classId=" + classId +
                ", marks=" + marks +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", status=" + getStatus() +
                '}';
    }
}

// ---------- Address ----------
class Address {
    private int id;
    private String pinCode;
    private String city;
    private int studentId;

    public Address(int id, String pinCode, String city, int studentId) {
        this.id = id;
        this.pinCode = pinCode;
        this.city = city;
        this.studentId = studentId;
    }

    public int getId() { return id; }
    public String getPinCode() { return pinCode; }
    public String getCity() { return city; }
    public int getStudentId() { return studentId; }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", pinCode='" + pinCode + '\'' +
                ", city='" + city + '\'' +
                ", studentId=" + studentId +
                '}';
    }
}

// ---------- Service Layer ----------
class StudentManagementService {
    private List<ClassRoom> classes = new ArrayList<>();
    private List<Student> students = new ArrayList<>();
    private List<Address> addresses = new ArrayList<>();

    // ---- Add Methods ----
    public void addClassRoom(ClassRoom cls) {
        classes.add(cls);
    }

    public void addStudent(Student student) {
        // Rule: age must be <= 20
        if (student.getAge() > 20) {
            System.out.println("Error: Age > 20, student not inserted -> " + student.getName());
            return;
        }
        students.add(student);
    }

    public void addAddress(Address addr) {
        addresses.add(addr);
    }

    // ---- Helper Methods ----
    private ClassRoom getClassById(int id) {
        for (ClassRoom cls : classes) {
            if (cls.getId() == id) return cls;
        }
        return null;
    }

    private List<Address> getAddressesByStudentId(int studentId) {
        return addresses.stream()
                .filter(addr -> addr.getStudentId() == studentId)
                .collect(Collectors.toList());
    }

    // ---- 1. Find students by pincode ----
    public List<Student> findStudentsByPincode(String pin, String gender, Integer age, String className) {
        return students.stream()
                .filter(stu -> {
                    // match addresses
                    boolean hasPin = addresses.stream()
                            .anyMatch(addr -> addr.getStudentId() == stu.getId() && addr.getPinCode().equals(pin));
                    if (!hasPin) return false;

                    if (gender != null && !stu.getGender().equalsIgnoreCase(gender)) return false;
                    if (age != null && stu.getAge() != age) return false;
                    if (className != null) {
                        ClassRoom cls = getClassById(stu.getClassId());
                        if (cls == null || !cls.getName().equalsIgnoreCase(className)) return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    // ---- 2. Find students by city ----
    public List<Student> findStudentsByCity(String city, String gender, Integer age, String className) {
        return students.stream()
                .filter(stu -> {
                    boolean hasCity = addresses.stream()
                            .anyMatch(addr -> addr.getStudentId() == stu.getId() && addr.getCity().equalsIgnoreCase(city));
                    if (!hasCity) return false;

                    if (gender != null && !stu.getGender().equalsIgnoreCase(gender)) return false;
                    if (age != null && stu.getAge() != age) return false;
                    if (className != null) {
                        ClassRoom cls = getClassById(stu.getClassId());
                        if (cls == null || !cls.getName().equalsIgnoreCase(className)) return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    // ---- 3. Find students by class ----
    public List<Student> findStudentsByClass(String className, String gender, Integer age, String city, String pinCode) {
        return students.stream()
                .filter(stu -> {
                    ClassRoom cls = getClassById(stu.getClassId());
                    if (cls == null || !cls.getName().equalsIgnoreCase(className)) return false;

                    if (gender != null && !stu.getGender().equalsIgnoreCase(gender)) return false;
                    if (age != null && stu.getAge() != age) return false;

                    if (city != null || pinCode != null) {
                        List<Address> stuAddrs = getAddressesByStudentId(stu.getId());
                        boolean match = stuAddrs.stream().anyMatch(addr ->
                                (city == null || addr.getCity().equalsIgnoreCase(city)) &&
                                        (pinCode == null || addr.getPinCode().equals(pinCode)));
                        return match;
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    // ---- 4. Get Passed Students ----
    public List<Student> getPassedStudents(String gender, Integer age, String className, String city, String pinCode) {
        return students.stream()
                .filter(stu -> stu.getMarks() >= 50)
                .filter(stu -> {
                    if (gender != null && !stu.getGender().equalsIgnoreCase(gender)) return false;
                    if (age != null && stu.getAge() != age) return false;

                    ClassRoom cls = getClassById(stu.getClassId());
                    if (className != null && (cls == null || !cls.getName().equalsIgnoreCase(className))) return false;

                    if (city != null || pinCode != null) {
                        List<Address> stuAddrs = getAddressesByStudentId(stu.getId());
                        return stuAddrs.stream().anyMatch(addr ->
                                (city == null || addr.getCity().equalsIgnoreCase(city)) &&
                                        (pinCode == null || addr.getPinCode().equals(pinCode)));
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    // ---- 5. Get Failed Students ----
    public List<Student> getFailedStudents(String gender, Integer age, String className, String city, String pinCode) {
        return students.stream()
                .filter(stu -> stu.getMarks() < 50)
                .filter(stu -> {
                    if (gender != null && !stu.getGender().equalsIgnoreCase(gender)) return false;
                    if (age != null && stu.getAge() != age) return false;

                    ClassRoom cls = getClassById(stu.getClassId());
                    if (className != null && (cls == null || !cls.getName().equalsIgnoreCase(className))) return false;

                    if (city != null || pinCode != null) {
                        List<Address> stuAddrs = getAddressesByStudentId(stu.getId());
                        return stuAddrs.stream().anyMatch(addr ->
                                (city == null || addr.getCity().equalsIgnoreCase(city)) &&
                                        (pinCode == null || addr.getPinCode().equals(pinCode)));
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    // ---- 6. Ranking Students ----
    public void rankStudentsWithTies() {
    // Sort students by marks (descending)
    List<Student> sorted = students.stream()
            .sorted(Comparator.comparingInt(Student::getMarks).reversed())
            .collect(Collectors.toList());

    int currentRank = 0;
    int lastMarks = -1;

    for (Student s : sorted) {
        if (s.getMarks() != lastMarks) {
            currentRank++;          // increase rank only when marks change
            lastMarks = s.getMarks();
        }
        System.out.println("Rank " + currentRank + ": " + s);
    }
}


    // ---- 7. Delete Student + Cascade ----
    public void deleteStudent(int studentId) {
    // Remove addresses for that student
    addresses.removeIf(addr -> addr.getStudentId() == studentId);

    // Find the student to remove
    Student stuToRemove = null;
    for (Student s : students) {
        if (s.getId() == studentId) {
            stuToRemove = s;
            break;
        }
    }

    if (stuToRemove != null) {
        // Remove the student
        students.remove(stuToRemove);
        System.out.println("Deleted student: " + stuToRemove.getName());

        // Capture the classId in a final/effectively-final variable so lambdas can use it
        final int removedClassId = stuToRemove.getClassId();

        // Check if any students remain in that class
        boolean anyLeft = students.stream()
                                  .anyMatch(s -> s.getClassId() == removedClassId);

        if (!anyLeft) {
            // Remove the class if empty
            classes.removeIf(c -> c.getId() == removedClassId);
            System.out.println("Class deleted as no students left in it.");
        }
    } else {
        System.out.println("Student not found.");
    }
}

    // ---- 8. Pagination ----
    
public List<Student> paginateStudents(String gender, int start, int end, String orderBy) {
    // Start with all students
    Stream<Student> stream = students.stream();

    // Filter by gender (if not null)
    if (gender != null) {
        stream = stream.filter(s -> s.getGender().equalsIgnoreCase(gender));
    }

    // Sort order
    if ("name".equalsIgnoreCase(orderBy)) {
        stream = stream.sorted(Comparator.comparing(Student::getName));
    } else if ("marks".equalsIgnoreCase(orderBy)) {
        stream = stream.sorted(Comparator.comparingInt(Student::getMarks).reversed());
    }

    // Apply pagination
    return stream
            .skip(start - 1)                // skip first (start-1) records
            .limit(end - start + 1)         // take only (end-start+1) records
            .collect(Collectors.toList());
}


    // ---- Display Helper ----
    public void displayStudents(List<Student> list) {
        for (Student s : list) {
            ClassRoom cls = getClassById(s.getClassId());
            System.out.println(s + " | Class: " + (cls != null ? cls.getName() : "Unknown"));
            System.out.println("  Addresses: " + getAddressesByStudentId(s.getId()));
        }
    }
}

// ---------- Main ----------
class StudentManagementApp {
    public static void main(String[] args) {
        StudentManagementService service = new StudentManagementService();

        // Sample Classes
        service.addClassRoom(new ClassRoom(1, "A"));
        service.addClassRoom(new ClassRoom(2, "B"));
        service.addClassRoom(new ClassRoom(3, "C"));
        service.addClassRoom(new ClassRoom(4, "D"));

        // Sample Students
        service.addStudent(new Student(1, "stud1", 1, 88, "F", 10));
        service.addStudent(new Student(2, "stud2", 1, 70, "F", 11));
        service.addStudent(new Student(3, "stud3", 2, 88, "M", 22)); // won't insert (age > 20)
        service.addStudent(new Student(4, "stud4", 2, 55, "M", 19));
        service.addStudent(new Student(5, "stud5", 1, 30, "F", 12));
        service.addStudent(new Student(6, "stud6", 3, 30, "F", 13));
        service.addStudent(new Student(7, "stud7", 3, 10, "F", 14));
        service.addStudent(new Student(8, "stud8", 3, 0, "M", 15));

        // Sample Addresses
        service.addAddress(new Address(1, "452002", "Indore", 1));
        service.addAddress(new Address(2, "422002", "Delhi", 1));
        service.addAddress(new Address(3, "442002", "Indore", 2));
        service.addAddress(new Address(4, "462002", "Delhi", 4));
        service.addAddress(new Address(5, "472002", "Indore", 4));
        service.addAddress(new Address(6, "452002", "Indore", 5));
        service.addAddress(new Address(7, "452002", "Delhi", 5));
        service.addAddress(new Address(8, "482002", "Mumbai", 6));
        service.addAddress(new Address(9, "482002", "Bhopal", 7));
        service.addAddress(new Address(10, "482002", "Indore", 8));

        // ---- Example Queries ----
        System.out.println("Students by Pincode 482002:");
        service.displayStudents(service.findStudentsByPincode("482002", null, null, null));

        System.out.println("\nStudents by City Indore:");
        service.displayStudents(service.findStudentsByCity("Indore", null, null, null));

        System.out.println("\nPassed Students:");
        service.displayStudents(service.getPassedStudents(null, null, null, null, null));

        System.out.println("\nRanking Students (with ties):");
        service.rankStudentsWithTies();


        List<Student> femaleByName = service.paginateStudents("F", 7, 8, "name");
        System.out.println("Female students (7–8) ordered by name:");
        femaleByName.forEach(System.out::println);

        List<Student> femaleByMarks = service.paginateStudents("F", 1, 5, "marks");
        System.out.println("\nFemale students (1–5) ordered by marks:");
        femaleByMarks.forEach(System.out::println);


        System.out.println("\nDeleting student id=6");
        service.deleteStudent(6);
    }
}
