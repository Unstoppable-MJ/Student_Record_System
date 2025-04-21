import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChartUtils {

    public static void showMarksChart(List<Student> studentList) {
        String[] options = {"Bar Chart", "Pie Chart", "Line Chart"};
        int choice = JOptionPane.showOptionDialog(null, "Select Chart Type",
                "Chart Options", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        switch (choice) {
            case 0 -> showBarChart(studentList);
            case 1 -> showPieChart(studentList);
            case 2 -> showLineChart(studentList);
            default -> System.out.println("No chart selected");
        }
    }

    public static void showBarChart(List<Student> studentList) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Student s : studentList) {
            dataset.addValue(s.getMarks(), "Marks", s.getName());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Student Marks", "Student", "Marks", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        showChartFrame(chart, "Bar Chart");
    }

    public static void showPieChart(List<Student> studentList) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Student s : studentList) {
            dataset.setValue(s.getName(), s.getMarks());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Student Marks Distribution", dataset,
                true, true, false);

        showChartFrame(chart, "Pie Chart");
    }

    public static void showLineChart(List<Student> studentList) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Student s : studentList) {
            dataset.addValue(s.getMarks(), "Marks", s.getName());
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Student Marks Trend", "Student", "Marks", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        showChartFrame(chart, "Line Chart");
    }

    private static void showChartFrame(JFreeChart chart, String title) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        JFrame chartFrame = new JFrame(title);
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.add(chartPanel);
        chartFrame.pack();
        chartFrame.setVisible(true);
    }
}
