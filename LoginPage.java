import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class LoginPage extends JDialog {

    private static final Map<String, String> USER_STORE = new HashMap<>();

    static {
        USER_STORE.put("admin", "1234");
    }

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JCheckBox showPasswordBox;
    private final JButton loginButton;
    private final JButton createAccountButton;

    public LoginPage(Frame parent, boolean modal) {
        super(parent, modal);

        setTitle("Login Page");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel titleLabel = new JLabel("LOGIN PAGE");
        titleLabel.setFont(titleLabel.getFont().deriveFont(28f));

        JLabel usernameLabel = new JLabel("Username");
        JLabel passwordLabel = new JLabel("Password");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        showPasswordBox = new JCheckBox("Show Password");
        showPasswordBox.addActionListener(evt -> {
            if (showPasswordBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        });

        loginButton = new JButton("Login");
        loginButton.addActionListener(evt -> attemptLogin());

        createAccountButton = new JButton("Create New Account");
        createAccountButton.addActionListener(evt -> openCreateAccountDialog());

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
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(showPasswordBox, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(loginButton);
        buttonPanel.add(createAccountButton);

        root.add(formPanel, BorderLayout.CENTER);
        root.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setLocationRelativeTo(parent);
    }

    public static synchronized boolean registerUser(String username, String password) {
        if (username == null || password == null) {
            return false;
        }

        String normalizedUsername = username.trim();
        if (normalizedUsername.isEmpty() || password.isEmpty()) {
            return false;
        }

        if (USER_STORE.containsKey(normalizedUsername)) {
            return false;
        }

        USER_STORE.put(normalizedUsername, password);
        return true;
    }

    private static synchronized boolean isValidCredentials(String username, String password) {
        String savedPassword = USER_STORE.get(username);
        return savedPassword != null && savedPassword.equals(password);
    }

    private void attemptLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username");
            return;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter password");
            return;
        }

        if (!isValidCredentials(username, password)) {
            JOptionPane.showMessageDialog(this, "Wrong username or password");
            return;
        }

        JOptionPane.showMessageDialog(this, "Login successfully");
    }

    private void openCreateAccountDialog() {
        createNewPassword dialog = new createNewPassword((Frame) SwingUtilities.getWindowAncestor(this), true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
            // Keep default look and feel if Nimbus is unavailable.
        }

        java.awt.EventQueue.invokeLater(() -> {
            LoginPage dialog = new LoginPage(new JFrame(), true);
            dialog.setVisible(true);
        });
    }
}
