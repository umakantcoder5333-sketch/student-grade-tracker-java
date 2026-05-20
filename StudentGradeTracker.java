import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class StudentGradeTracker {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Student Grade Tracker - Umaknat");
        frame.setSize(900, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // COLORS
        Color bg = new Color(30,30,30);
        Color panelBg = new Color(45,45,45);
        Color accent = new Color(0,120,215);

        // TABLE
        String[] cols = {"ID","Roll","Name","Gender","M1","M2","M3","Total","Average","Grade"};
        DefaultTableModel model = new DefaultTableModel(cols,0);
        JTable table = new JTable(model);

        table.setBackground(bg);
        table.setForeground(Color.WHITE);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JTableHeader header = table.getTableHeader();
        header.setBackground(accent);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scroll = new JScrollPane(table);

        // GRADE COLOR RENDERER
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {

                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);

                String grade = t.getValueAt(row, 9).toString();

                if (grade.equals("A+")) c.setForeground(new Color(0,255,127));
                else if (grade.equals("A")) c.setForeground(new Color(50,205,50));
                else if (grade.equals("B")) c.setForeground(Color.CYAN);
                else if (grade.equals("C")) c.setForeground(Color.ORANGE);
                else if (grade.equals("D")) c.setForeground(Color.PINK);
                else c.setForeground(Color.RED);

                return c;
            }
        });

        // INPUTS
        JTextField name = new JTextField();
        JTextField roll = new JTextField();
        JTextField gender = new JTextField();
        JTextField m1 = new JTextField();
        JTextField m2 = new JTextField();
        JTextField m3 = new JTextField();

        JTextField[] fields = {name,roll,gender,m1,m2,m3};

        for(JTextField f:fields){
            f.setBackground(new Color(60,60,60));
            f.setForeground(Color.WHITE);
            f.setCaretColor(Color.WHITE);
        }

        JButton add = new JButton("Add Student");
        add.setBackground(accent);
        add.setForeground(Color.WHITE);
        add.setFocusPainted(false);

        JPanel panel = new JPanel(new GridLayout(7,2,10,10));
        panel.setBackground(panelBg);
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel[] labels = {
                new JLabel("Name:"), new JLabel("Roll No:"),
                new JLabel("Gender:"), new JLabel("Marks 1:"),
                new JLabel("Marks 2:"), new JLabel("Marks 3:")
        };

        for(JLabel l:labels){
            l.setForeground(Color.WHITE);
        }

        panel.add(labels[0]); panel.add(name);
        panel.add(labels[1]); panel.add(roll);
        panel.add(labels[2]); panel.add(gender);
        panel.add(labels[3]); panel.add(m1);
        panel.add(labels[4]); panel.add(m2);
        panel.add(labels[5]); panel.add(m3);

        panel.add(new JLabel(""));
        panel.add(add);

        frame.add(scroll,BorderLayout.CENTER);
        frame.add(panel,BorderLayout.SOUTH);

        //  ADD BUTTON LOGIC
        add.addActionListener(e -> {

            String n = name.getText().trim();
            String rollText = roll.getText().trim();
            String g = gender.getText().trim();
            String m1Text = m1.getText().trim();
            String m2Text = m2.getText().trim();
            String m3Text = m3.getText().trim();

            // EMPTY CHECK
            if(n.isEmpty() || rollText.isEmpty() || g.isEmpty() ||
               m1Text.isEmpty() || m2Text.isEmpty() || m3Text.isEmpty()) {

                JOptionPane.showMessageDialog(frame, "Please fill all fields!");
                return;
            }

            int r;
            double a,b,c;

            // ROLL CHECK
            try {
                r = Integer.parseInt(rollText);
            } catch(Exception ex){
                JOptionPane.showMessageDialog(frame, "Roll number must be a number!");
                return;
            }

            // MARKS CHECK
            try {
                a = Double.parseDouble(m1Text);
                b = Double.parseDouble(m2Text);
                c = Double.parseDouble(m3Text);
            } catch(Exception ex){
                JOptionPane.showMessageDialog(frame, "Marks must be valid numbers!");
                return;
            }

            // RANGE CHECK
            if(a<0 || a>100 || b<0 || b>100 || c<0 || c>100){
                JOptionPane.showMessageDialog(frame, "Marks must be between 0 and 100!");
                return;
            }

            int id = model.getRowCount()+1;

            double total = a+b+c;
            double avg = total/3;

            String grade;

            // 🇮🇳 INDIAN GRADING
            if (avg >= 90) grade = "A+";
            else if (avg >= 75) grade = "A";
            else if (avg >= 60) grade = "B";
            else if (avg >= 50) grade = "C";
            else if (avg >= 40) grade = "D";
            else grade = "Fail";

            model.addRow(new Object[]{
                    id,r,n,g,a,b,c,total,avg,grade
            });

            // CLEAR
            name.setText("");
            roll.setText("");
            gender.setText("");
            m1.setText("");
            m2.setText("");
            m3.setText("");
        });

        frame.setVisible(true);
    }
}