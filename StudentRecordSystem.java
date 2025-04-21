import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
class StudentRecordSystem {
    private List<Student> studentList;
    private final String FILE_NAME = "students.dat";

    public StudentRecordSystem() {
        studentList = loadFromFile();
    }

    public void addStudent(Student s) {
        for (Student std : studentList) {
            if (std.getRollNo() == s.getRollNo()) {
                System.out.println("Student with this roll number already exists!");
                return;
            }
        }
        studentList.add(s);
        System.out.println("✅ Student added successfully!");
    }

    public void displayAll() {
        if (studentList.isEmpty()) {
            System.out.println("❌ No records found.");
        } else {
            System.out.println("\n📋 Student Records:");
            for (Student s : studentList) {
                s.display();
            }
        }
    }

    public void sortByMarks() {
        studentList.sort(Comparator.comparingDouble(Student::getMarks).reversed());
        System.out.println("✅ Sorted by Marks (High to Low).\n");
    }

    public void sortByName() {
        studentList.sort(Comparator.comparing(Student::getName));
        System.out.println("✅ Sorted by Name (A-Z).\n");
    }

    public Student searchByRollNo(int roll) {
        for (Student s : studentList) {
            if (s.getRollNo() == roll) {
                return s;
            }
        }
        return null;
    }

    public void deleteByRollNo(int roll) {
        Iterator<Student> itr = studentList.iterator();
        while (itr.hasNext()) {
            if (itr.next().getRollNo() == roll) {
                itr.remove();
                System.out.println("✅ Student deleted.\n");
                return;
            }
        }
        System.out.println("❌ Student not found.\n");
    }

    public void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(studentList);
        } catch (IOException e) {
            System.out.println("❌ Error saving data.");
        }
    }

    private List<Student> loadFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Student>) in.readObject();
        } catch (Exception e) {
            return new LinkedList<>();
        }
    }

    public void exportToCSV(String filePath) {
        try (FileWriter fw = new FileWriter(filePath)) {
            fw.write("RollNo,Name,Marks\n");
            for (Student s : studentList) {
                fw.write(s.toCSV() + "\n");
            }
            System.out.println("✅ Exported to CSV successfully!");
        } catch (IOException e) {
            System.out.println("❌ Failed to export CSV.");
        }
    }
}
