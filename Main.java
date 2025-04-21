import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Admin Login
        System.out.print("Username: ");
        String user = sc.next();
        System.out.print("Password: ");
        String pass = sc.next();

        if (!user.equals("admin") || !pass.equals("1234")) {
            System.out.println("❌ Access Denied!");
            return;
        }

        StudentRecordSystem srs = new StudentRecordSystem();
        int choice;

        do {
            System.out.println("\n===== 📚 Student Record System Menu =====");
            System.out.println("1️⃣ Add Student");
            System.out.println("2️⃣ Display All Students");
            System.out.println("3️⃣ Sort by Marks");
            System.out.println("4️⃣ Sort by Name");
            System.out.println("5️⃣ Search by Roll No");
            System.out.println("6️⃣ Delete by Roll No");
            System.out.println("7️⃣ Export to CSV");
            System.out.println("8️⃣ Exit");
            System.out.print("👉 Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("🆔 Enter Roll No: ");
                    int roll = sc.nextInt();
                    sc.nextLine();
                    System.out.print("👤 Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("📝 Enter Marks: ");
                    double marks = sc.nextDouble();
                    srs.addStudent(new Student(roll, name, marks));
                    break;
                case 2:
                    srs.displayAll();
                    break;
                case 3:
                    srs.sortByMarks();
                    break;
                case 4:
                    srs.sortByName();
                    break;
                case 5:
                    System.out.print("🔍 Enter Roll No to search: ");
                    int r = sc.nextInt();
                    Student found = srs.searchByRollNo(r);
                    if (found != null) found.display();
                    else System.out.println("❌ Student not found.");
                    break;
                case 6:
                    System.out.print("🗑️ Enter Roll No to delete: ");
                    int d = sc.nextInt();
                    srs.deleteByRollNo(d);
                    break;
                case 7:
                    System.out.print("📂 Enter file name (e.g., output.csv): ");
                    sc.nextLine();
                    String file = sc.nextLine();
                    srs.exportToCSV(file);
                    break;
                case 8:
                    srs.saveToFile();
                    System.out.println("👋 Exiting... Data saved successfully.");
                    break;
                default:
                    System.out.println("⚠️ Invalid choice.");
            }
        } while (choice != 8);

        sc.close();
    }
}