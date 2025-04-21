import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatbotAssistant {

    private JFrame frame;
    private JTextArea chatArea;
    private JTextField inputField;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ChatbotAssistant window = new ChatbotAssistant();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ChatbotAssistant() {
        frame = new JFrame("Student Record Chatbot");
        frame.setBounds(100, 100, 400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(chatArea);
        frame.getContentPane().add(scroll, BorderLayout.CENTER);

        inputField = new JTextField();
        frame.getContentPane().add(inputField, BorderLayout.SOUTH);
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputField.getText();
                chatArea.append("You: " + userInput + "\n");

                String response = getChatbotResponse(userInput);
                chatArea.append("Bot: " + response + "\n");
                inputField.setText("");
            }
        });
    }

    private String getChatbotResponse(String input) {
        input = input.toLowerCase();

        if (input.contains("marks") || input.contains("topper")) {
            return "I can show you the topper details!";
        } else if (input.contains("average")) {
            return "Let me fetch the average marks.";
        } else if (input.contains("fail")) {
            return "I can find out who is failing!";
        } else {
            return "Sorry, I didn't understand that. Try asking about marks or toppers.";
        }
    }
}
