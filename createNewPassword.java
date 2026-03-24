import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class createNewPassword extends JDialog {

    private final JTextField nameField;
    private final JTextField surnameField;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JPasswordField confirmPasswordField;

    public createNewPassword(Frame parent, boolean modal) {
        super(parent, modal);

        setTitle("Create New Account");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel titleLabel = new JLabel("Create New Account");
        titleLabel.setFont(titleLabel.getFont().deriveFont(24f));

        JLabel nameLabel = new JLabel("Name");
        JLabel surnameLabel = new JLabel("Surname");
        JLabel usernameLabel = new JLabel("New Username");
        JLabel passwordLabel = new JLabel("Password");
        JLabel confirmPasswordLabel = new JLabel("Confirm Password");

        nameField = new JTextField(20);
        surnameField = new JTextField(20);
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);

        JCheckBox showPasswordBox = new JCheckBox("Show password");
        showPasswordBox.addActionListener(evt -> {
            if (showPasswordBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        });

        JCheckBox showConfirmPasswordBox = new JCheckBox("Show confirm password");
        showConfirmPasswordBox.addActionListener(evt -> {
            if (showConfirmPasswordBox.isSelected()) {
                confirmPasswordField.setEchoChar((char) 0);
            } else {
                confirmPasswordField.setEchoChar('*');
            }
        });

        JButton createButton = new JButton("Create My Account");
        createButton.addActionListener(evt -> createAccount());

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(surnameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(surnameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        formPanel.add(showPasswordBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(confirmPasswordLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(confirmPasswordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        formPanel.add(showConfirmPasswordBox, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(createButton);

        root.add(formPanel, BorderLayout.CENTER);
        root.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setLocationRelativeTo(parent);
    }

    private void createAccount() {
        String name = nameField.getText().trim();
        String surname = surnameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter name");
            return;
        }

        if (surname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter surname");
            return;
        }

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username");
            return;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter password");
            return;
        }

        if (confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please confirm password");
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match");
            return;
        }

        boolean created = LoginPage.registerUser(username, password);
        if (!created) {
            JOptionPane.showMessageDialog(this, "Username already exists");
            return;
        }

        JOptionPane.showMessageDialog(this, "Account created successfully");
        dispose();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            createNewPassword dialog = new createNewPassword(null, true);
            dialog.setVisible(true);
        });
    }
}
