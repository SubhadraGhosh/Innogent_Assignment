import java.util.*;

// Employee class implementing Comparable for natural sorting
class Employee implements Comparable<Employee> {
    int id;
    String name;
    String department;
    int salary;

    // Constructor
    public Employee(int id, String name, String department, int salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    // Comparable: Sorting by Department → Name → Salary
    @Override
    public int compareTo(Employee other) {
        // First compare by department
        int deptCompare = this.department.compareTo(other.department);
        if (deptCompare != 0) return deptCompare;

        // If department is same, compare by name
        int nameCompare = this.name.compareTo(other.name);
        if (nameCompare != 0) return nameCompare;

        // If both department and name are same, compare by salary
        return Integer.compare(this.salary, other.salary);
    }

    // To display employee details
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                '}';
    }
}

// Comparator for Salary in descending order
class SalaryDescComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee e1, Employee e2) {
        // For descending, reverse the natural order of salary
        return Integer.compare(e2.salary, e1.salary);
    }
}

class Main {
    public static void main(String[] args) {
        // Creating Employee objects
        Employee e1 = new Employee(0, "Ram", "IT", 100000);
        Employee e2 = new Employee(1, "Shyam", "E", 50000);
        Employee e3 = new Employee(2, "Krishna", "Math", 40000);
        Employee e4 = new Employee(3, "Hariom", "Bank", 80000);
        Employee e5 = new Employee(4, "Bharat", "IT", 60000);

        // Adding to list
        ArrayList<Employee> list = new ArrayList<>();
        list.add(e1);
        list.add(e2);
        list.add(e3);
        list.add(e4);
        list.add(e5);

        // Sorting using Comparable (Department → Name → Salary)
        System.out.println("Sorting by Department → Name → Salary:");
        Collections.sort(list);
        Iterator<Employee> itr1 = list.iterator();
        while (itr1.hasNext()) {
            System.out.println(itr1.next());
        }

        System.out.println();

        // Sorting using Comparator (Salary descending)
        System.out.println("Sorting by Salary (Descending):");
        Collections.sort(list, new SalaryDescComparator());
        Iterator<Employee> itr2 = list.iterator();
        while (itr2.hasNext()) {
            System.out.println(itr2.next());
        }
    }
}
