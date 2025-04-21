import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentRecordGUI extends JFrame {
    private DefaultTableModel model;
    private JTable table;
    private java.util.List<Student> studentList = new ArrayList<>();
    private static final String FILE_NAME = "students.dat";

    public StudentRecordGUI() {
        super("🎓 Student Record System");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        getContentPane().setBackground(new Color(240, 248, 255));
        setLayout(new BorderLayout(10, 10));

        model = new DefaultTableModel(new String[]{"Name", "Roll", "Marks"}, 0);
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(24);
        table.setGridColor(Color.LIGHT_GRAY);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(new Color(224, 255, 255));

        // Chatbot Button
        JButton chatbotButton = new JButton("Chat with Bot");
        chatbotButton.setBounds(50, 400, 150, 40);
        chatbotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChatbotAssistant();
            }
        });

        // Chart Button with chart type selection
        JButton chartButton = new JButton("Show Marks Chart");
        chartButton.setBounds(250, 400, 150, 40);
        chartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"Bar Chart", "Pie Chart", "Line Chart"};
                String choice = (String) JOptionPane.showInputDialog(
                        StudentRecordGUI.this,
                        "Choose Chart Type:",
                        "Chart Type",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                if (choice != null) {
                    switch (choice) {
                        case "Bar Chart":
                            ChartUtils.showBarChart(studentList);
                            break;
                        case "Pie Chart":
                            ChartUtils.showPieChart(studentList);
                            break;
                        case "Line Chart":
                            ChartUtils.showLineChart(studentList);
                            break;
                    }
                }
            }
        });

        // Add buttons to main layout
        add(chatbotButton, BorderLayout.WEST);
        add(chartButton, BorderLayout.EAST);

        // Other Buttons
        JButton addBtn = new JButton("Add");
        JButton delBtn = new JButton("Delete");
        JButton saveBtn = new JButton("Save to File");
        JButton loadBtn = new JButton("Load from File");
        JButton exportBtn = new JButton("Export to CSV");

        panel.add(addBtn);
        panel.add(delBtn);
        panel.add(saveBtn);
        panel.add(loadBtn);
        panel.add(exportBtn);

        add(panel, BorderLayout.SOUTH);

        addBtn.setToolTipText("Add a new student record");
        delBtn.setToolTipText("Delete selected student record");
        saveBtn.setToolTipText("Save records to file");
        loadBtn.setToolTipText("Load records from file");
        exportBtn.setToolTipText("Export records to CSV");

        addBtn.addActionListener(e -> {
            JTextField nameField = new JTextField();
            JTextField rollField = new JTextField();
            JTextField marksField = new JTextField();

            JPanel inputPanel = new JPanel(new GridLayout(3, 2));
            inputPanel.add(new JLabel("Name:"));
            inputPanel.add(nameField);
            inputPanel.add(new JLabel("Roll:"));
            inputPanel.add(rollField);
            inputPanel.add(new JLabel("Marks:"));
            inputPanel.add(marksField);

            int result = JOptionPane.showConfirmDialog(this, inputPanel, "Enter Student Info", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String name = nameField.getText();
                    int roll = Integer.parseInt(rollField.getText());
                    double marks = Double.parseDouble(marksField.getText());
                    studentList.add(new Student(roll, name, marks));
                    model.addRow(new Object[]{name, roll, marks});
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        delBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                studentList.remove(row);
                model.removeRow(row);
            }
        });

        saveBtn.addActionListener(e -> {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
                out.writeObject(studentList);
                JOptionPane.showMessageDialog(this, "Saved successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        loadBtn.addActionListener(e -> {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                studentList = (ArrayList<Student>) in.readObject();
                model.setRowCount(0);
                for (Student s : studentList) {
                    model.addRow(new Object[]{s.getName(), s.getRollNo(), s.getMarks()});
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        exportBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (FileWriter fw = new FileWriter(file)) {
                    fw.write("Name,Roll,Marks\n");
                    for (Student s : studentList) {
                        fw.write(s.getName() + "," + s.getRollNo() + "," + s.getMarks() + "\n");
                    }
                    JOptionPane.showMessageDialog(this, "CSV exported successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JOptionPane.showMessageDialog(this, "Welcome to the Student Record System 🎓", "Welcome", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentRecordGUI().setVisible(true));
    }
}