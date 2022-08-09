import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator implements ActionListener {

    JFrame frame;
    JPanel panel;
    JTextField textField;
    JButton[] numberButtons = new JButton[10];
    JButton[] functionButtons = new JButton[8];
    JButton addButton,
            subButton,
            mulButton,
            divButton,
            decButton,
            equButton,
            delButton,
            clrButton;

    Font myFont = new Font("Arial", Font.BOLD, 30);

    BigDecimal num1 = new BigDecimal(0);
    BigDecimal num2 = new BigDecimal(0);
    BigDecimal result = new BigDecimal(0);

    Color fieldColor = new Color(34,37,45),
          btnColor = new Color(39,43,51),
          panelColor = new Color(41, 45, 54),
          textColor = new Color(254,254,254),
          redTextColor = new Color(187,87,90),
          clickColor = new Color(48, 49, 54);

    char operator;

    Calculator() {

        UIManager.put("Button.select", clickColor);

        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 530);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(fieldColor);
        frame.setResizable(false);

        textField = new JTextField();
        textField.setBounds(50,20,300,50);
        textField.setFont(myFont);
        textField.setEditable(false);
        textField.setText("0");
        textField.setBackground(fieldColor);
        textField.setForeground(textColor);
        textField.setBorder(BorderFactory.createEmptyBorder());

        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        decButton = new JButton(".");
        equButton = new JButton("=");
        delButton = new JButton("Delete");
        clrButton = new JButton("Clear");

        functionButtons[0] = addButton;
        functionButtons[1] = subButton;
        functionButtons[2] = mulButton;
        functionButtons[3] = divButton;
        functionButtons[4] = decButton;
        functionButtons[5] = equButton;
        functionButtons[6] = delButton;
        functionButtons[7] = clrButton;

        for(int i=0 ; i<8 ; i++) {
            functionButtons[i].addActionListener(this);
            functionButtons[i].setFont(myFont);
            functionButtons[i].setFocusable(false);
            functionButtons[i].setBackground(btnColor);
            functionButtons[i].setForeground(redTextColor);
            functionButtons[i].setBorder(BorderFactory.createEmptyBorder());
        }

        for(int i=0 ; i<10 ; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(myFont);
            numberButtons[i].setFocusable(false);
            numberButtons[i].setBackground(btnColor);
            numberButtons[i].setForeground(textColor);
            numberButtons[i].setBorder(BorderFactory.createEmptyBorder());
        }

        delButton.setBounds(40, 430,145,50);
        clrButton.setBounds(195, 430,145,50);

        panel = new JPanel();
        panel.setBounds(0,80,385,430);
        panel.setLayout(new GridLayout(4,4,10,10));
        panel.setBackground(panelColor);
        panel.setBorder(new EmptyBorder(10, 30, 90, 30));

        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);

        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);

        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(mulButton);

        panel.add(decButton);
        panel.add(numberButtons[0]);
        panel.add(equButton);
        panel.add(divButton);

        frame.add(delButton);
        frame.add(clrButton);
        frame.add(panel);
        frame.add(textField);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        Calculator calc = new Calculator();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=0 ; i<10 ; i++) {
            if(e.getSource() == numberButtons[i]) {
                if(textField.getText().contains("+")        ||
                   textField.getText().contains("-")        ||
                   textField.getText().contains("*")        ||
                   textField.getText().contains("/")        ||
                   textField.getText().startsWith("Error")  ||
                   textField.getText().startsWith("Infinity")) {
                        textField.setText("");
                }
                textField.setText(textField.getText().concat(String.valueOf(i)));
            }
        }
        if(e.getSource() == decButton
                && !textField.getText().contains("+")
                && !textField.getText().contains("-")
                && !textField.getText().contains("*")
                && !textField.getText().contains("/")
                && !textField.getText().startsWith("Error")
                && !textField.getText().startsWith("Infinity")
                && !textField.getText().contains(".")) {
            textField.setText(textField.getText().concat("."));
        }
        try {
            if (e.getSource() == addButton && !textField.getText().contains("+")) {
                num1 = new BigDecimal(textField.getText());
                operator = '+';
                textField.setText("+");
            }
            if (e.getSource() == subButton && !textField.getText().contains("-")) {
                num1 = new BigDecimal(textField.getText());
                operator = '-';
                textField.setText("-");
            }
            if (e.getSource() == mulButton && !textField.getText().contains("*")) {
                num1 = new BigDecimal(textField.getText());
                operator = '*';
                textField.setText("*");
            }
            if (e.getSource() == divButton && !textField.getText().contains("/")) {
                num1 = new BigDecimal(textField.getText());
                operator = '/';
                textField.setText("/");
            }
            if(e.getSource() == equButton && !textField.getText().contains("=")) {
                num2 = new BigDecimal(textField.getText());

                switch (operator) {
                    case '+' -> result = num1.add(num2).stripTrailingZeros();
                    case '-' -> result = num1.subtract(num2).stripTrailingZeros();
                    case '*' -> result = num1.multiply(num2).stripTrailingZeros();
                    case '/' -> result = num1.divide(num2, 50, RoundingMode.HALF_UP).stripTrailingZeros();
                }
                textField.setText(String.valueOf(String.format("%.7s",result.toPlainString())));
                num1 = result;
            }
        }
        catch(NumberFormatException | ArithmeticException ex) {
            textField.setText("Error");
        }

        if(e.getSource() == clrButton) {
            textField.setText("0");
        }
        if(e.getSource() == delButton) {
            String string = textField.getText();
            textField.setText("");
            for(int i=0 ; i<string.length()-1 ; i++) {
                textField.setText(textField.getText()+string.charAt(i));
            }
        }
    }

}