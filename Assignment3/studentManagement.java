import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.*;

// ---------- Custom Exceptions ----------
class InvalidAgeException extends Exception {
    public InvalidAgeException(String msg) {
        super(msg);
    }
}

class InvalidMarksException extends Exception {
    public InvalidMarksException(String msg) {
        super(msg);
    }
}



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

    // ----  Rule: Pass/Fail ----
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
    private List<ClassRoom> classes;
    private List<Student> students;
    private List<Address> addresses;

    // File names
    private static final String CLASS_FILE = "classes.txt";
    private static final String STUDENT_FILE = "students.txt";
    private static final String ADDRESS_FILE = "addresses.txt";
    private static final String TOP5_FILE = "top5.txt";

    public StudentManagementService() {
        classes = loadClasses();
        students = loadStudents();
        addresses = loadAddresses();
    }

    // ---------- Load / Save Helpers ----------
    private List<ClassRoom> loadClasses() {
        List<ClassRoom> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CLASS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    list.add(new ClassRoom(id, name));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(CLASS_FILE + " not found, starting fresh.");
        } catch (IOException e) {
            System.out.println("Error reading " + CLASS_FILE + ": " + e.getMessage());
        }
        return list;
    }

    private List<Student> loadStudents() {
        List<Student> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(STUDENT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    int classId = Integer.parseInt(parts[2].trim());
                    int marks = Integer.parseInt(parts[3].trim());
                    String gender = parts[4].trim();
                    int age = Integer.parseInt(parts[5].trim());
                    list.add(new Student(id, name, classId, marks, gender, age));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(STUDENT_FILE + " not found, starting fresh.");
        } catch (IOException e) {
            System.out.println("Error reading " + STUDENT_FILE + ": " + e.getMessage());
        }
        return list;
    }

    private List<Address> loadAddresses() {
        List<Address> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ADDRESS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0].trim());
                    String pin = parts[1].trim();
                    String city = parts[2].trim();
                    int studentId = Integer.parseInt(parts[3].trim());
                    list.add(new Address(id, pin, city, studentId));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(ADDRESS_FILE + " not found, starting fresh.");
        } catch (IOException e) {
            System.out.println("Error reading " + ADDRESS_FILE + ": " + e.getMessage());
        }
        return list;
    }

    private void saveClasses() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CLASS_FILE))) {
            for (ClassRoom c : classes)
                bw.write(c.getId() + "," + c.getName() + "\n");
        } catch (IOException e) {
            System.out.println("Error writing " + CLASS_FILE + ": " + e.getMessage());
        }
    }

    private void saveStudents() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(STUDENT_FILE))) {
            for (Student s : students)
                bw.write(s.getId() + "," + s.getName() + "," + s.getClassId() + "," +
                         s.getMarks() + "," + s.getGender() + "," + s.getAge() + "\n");
        } catch (IOException e) {
            System.out.println("Error writing " + STUDENT_FILE + ": " + e.getMessage());
        }
    }

    private void saveAddresses() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ADDRESS_FILE))) {
            for (Address a : addresses)
                bw.write(a.getId() + "," + a.getPinCode() + "," + a.getCity() + "," + a.getStudentId() + "\n");
        } catch (IOException e) {
            System.out.println("Error writing " + ADDRESS_FILE + ": " + e.getMessage());
        }
    }

    // ---------- Add Methods ----------
    public void addClassRoom(ClassRoom cls) {
        classes.add(cls);
        saveClasses();
    }

    public void addStudent(Student student) throws InvalidAgeException, InvalidMarksException {
        if (student.getAge() > 20)
            throw new InvalidAgeException("Age > 20 not allowed: " + student.getName());
        if (student.getMarks() < 0 || student.getMarks() > 100)
            throw new InvalidMarksException("Marks should be 0–100: " + student.getName());

        students.add(student);
        saveStudents();
    }

    public void addAddress(Address addr) {
        addresses.add(addr);
        saveAddresses();
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

     // ---- Ranking Students ----
    public void rankStudentsWithTies() {
        List<Student> sorted = students.stream()
                .sorted(Comparator.comparingInt(Student::getMarks).reversed())
                .collect(Collectors.toList());

        int currentRank = 0;
        int lastMarks = -1;
        int count = 0;

        try (PrintWriter writer = new PrintWriter(new FileWriter(TOP5_FILE))) {
            for (Student s : sorted) {
                if (s.getMarks() != lastMarks) {
                    currentRank++;
                    lastMarks = s.getMarks();
                }
                String line = "Rank " + currentRank + ": " + s;
                System.out.println(line);

                if (count < 5) writer.println(line);
                count++;
            }
        } catch (IOException e) {
            System.out.println("Error writing Top 5 file: " + e.getMessage());
        }
    }

    // ---- Delete Student ----
    public void deleteStudent(int studentId) {
        Student stuToRemove = students.stream()
                .filter(s -> s.getId() == studentId)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Student not found"));

        students.remove(stuToRemove);
        addresses.removeIf(addr -> addr.getStudentId() == studentId);

        saveStudents();
        saveAddresses();

        System.out.println("Deleted student: " + stuToRemove.getName());
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
    public void displayStudents() {
        for (Student s : students) {
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
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Add Class");
            System.out.println("2. Add Student");
            System.out.println("3. Add Address");
            System.out.println("4. Display All Students");
            System.out.println("5. Rank Students");
            System.out.println("6. Delete Student");
            System.out.println("7. Find Students by Pincode");
            System.out.println("8. Find Students by City");
            System.out.println("9. Find Students by Class");
            System.out.println("10. Get Passed Students");
            System.out.println("11. Get Failed Students");
            System.out.println("12. Exit");
            System.out.print("Enter choice: ");

            try {
                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter Class ID: ");
                        int cid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Class Name: ");
                        String cname = sc.nextLine();
                        service.addClassRoom(new ClassRoom(cid, cname));
                    }
                    case 2 -> {
                        System.out.print("Enter Student ID: ");
                        int sid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Student Name: ");
                        String sname = sc.nextLine();
                        System.out.print("Enter Class ID: ");
                        int scid = sc.nextInt();
                        System.out.print("Enter Marks: ");
                        int marks = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Gender: ");
                        String gender = sc.nextLine();
                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();

                        service.addStudent(new Student(sid, sname, scid, marks, gender, age));
                    }
                    case 3 -> {
                        System.out.print("Enter Address ID: ");
                        int aid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter PinCode: ");
                        String pin = sc.nextLine();
                        System.out.print("Enter City: ");
                        String city = sc.nextLine();
                        System.out.print("Enter Student ID: ");
                        int sid = sc.nextInt();

                        service.addAddress(new Address(aid, pin, city, sid));
                    }
                    case 4 -> service.displayStudents();
                    case 5 -> service.rankStudentsWithTies();
                    case 6 -> {
                        System.out.print("Enter Student ID to delete: ");
                        int delId = sc.nextInt();
                        service.deleteStudent(delId);
                    }
                    case 7 -> {
                        System.out.print("Enter Pincode: ");
                        String pin = sc.nextLine();
                        System.out.print("Filter by Gender (or press Enter to skip): ");
                        String gender = sc.nextLine();
                        gender = gender.isEmpty() ? null : gender;
                        System.out.print("Filter by Age (or 0 to skip): ");
                        int age = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Filter by Class Name (or press Enter to skip): ");
                        String className = sc.nextLine();
                        className = className.isEmpty() ? null : className;

                        List<Student> results = service.findStudentsByPincode(pin, gender, age == 0 ? null : age, className);
                        results.forEach(System.out::println);
                    }
                    case 8 -> {
                        System.out.print("Enter City: ");
                        String city = sc.nextLine();
                        System.out.print("Filter by Gender (or press Enter to skip): ");
                        String gender = sc.nextLine();
                        gender = gender.isEmpty() ? null : gender;
                        System.out.print("Filter by Age (or 0 to skip): ");
                        int age = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Filter by Class Name (or press Enter to skip): ");
                        String className = sc.nextLine();
                        className = className.isEmpty() ? null : className;

                        List<Student> results = service.findStudentsByCity(city, gender, age == 0 ? null : age, className);
                        results.forEach(System.out::println);
                    }
                    case 9 -> {
                        System.out.print("Enter Class Name: ");
                        String className = sc.nextLine();
                        System.out.print("Filter by Gender (or press Enter to skip): ");
                        String gender = sc.nextLine();
                        gender = gender.isEmpty() ? null : gender;
                        System.out.print("Filter by Age (or 0 to skip): ");
                        int age = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Filter by City (or press Enter to skip): ");
                        String city = sc.nextLine();
                        city = city.isEmpty() ? null : city;
                        System.out.print("Filter by Pincode (or press Enter to skip): ");
                        String pinCode = sc.nextLine();
                        pinCode = pinCode.isEmpty() ? null : pinCode;

                        List<Student> results = service.findStudentsByClass(className, gender, age == 0 ? null : age, city, pinCode);
                        results.forEach(System.out::println);
                    }
                    case 10 -> {
                        System.out.print("Filter by Gender (or press Enter to skip): ");
                        String gender = sc.nextLine();
                        gender = gender.isEmpty() ? null : gender;
                        System.out.print("Filter by Age (or 0 to skip): ");
                        int age = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Filter by Class Name (or press Enter to skip): ");
                        String className = sc.nextLine();
                        className = className.isEmpty() ? null : className;
                        System.out.print("Filter by City (or press Enter to skip): ");
                        String city = sc.nextLine();
                        city = city.isEmpty() ? null : city;
                        System.out.print("Filter by Pincode (or press Enter to skip): ");
                        String pinCode = sc.nextLine();
                        pinCode = pinCode.isEmpty() ? null : pinCode;

                        List<Student> results = service.getPassedStudents(gender, age == 0 ? null : age, className, city, pinCode);
                        results.forEach(System.out::println);
                    }
                    case 11 -> {
                        System.out.print("Filter by Gender (or press Enter to skip): ");
                        String gender = sc.nextLine();
                        gender = gender.isEmpty() ? null : gender;
                        System.out.print("Filter by Age (or 0 to skip): ");
                        int age = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Filter by Class Name (or press Enter to skip): ");
                        String className = sc.nextLine();
                        className = className.isEmpty() ? null : className;
                        System.out.print("Filter by City (or press Enter to skip): ");
                        String city = sc.nextLine();
                        city = city.isEmpty() ? null : city;
                        System.out.print("Filter by Pincode (or press Enter to skip): ");
                        String pinCode = sc.nextLine();
                        pinCode = pinCode.isEmpty() ? null : pinCode;

                        List<Student> results = service.getFailedStudents(gender, age == 0 ? null : age, className, city, pinCode);
                        results.forEach(System.out::println);
                    }
                    case  12 ->{
                        
                        System.out.print("Enter Gender to filter (or leave blank for all): ");
                        String genderInput = sc.nextLine();
                        String gender = genderInput.isEmpty() ? null : genderInput;

                        System.out.print("Enter Start Index: ");
                        int start = sc.nextInt();
                        System.out.print("Enter End Index: ");
                        int end = sc.nextInt();
                        sc.nextLine(); // consume newline

                        System.out.print("Enter order by (name/marks): ");
                        String orderByInput = sc.nextLine();
                        String orderBy = orderByInput.isEmpty() ? null : orderByInput;

                        List<Student> paginated = service.paginateStudents(gender, start, end, orderBy);
                        System.out.println("Paginated Students:");
                        paginated.forEach(System.out::println);
                    }

                    
                    case 13 -> {
                        System.out.println("Exiting...");
                        sc.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice!");
                }

            } catch (InputMismatchException e) {
                System.out.println("⚠ Invalid input! Please enter numbers where required.");
                sc.nextLine(); // clear buffer
            } catch (InvalidAgeException | InvalidMarksException e) {
                System.out.println("⚠ Business Rule Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("⚠ Unexpected Error: " + e.getMessage());
            }
        }
    }
}
