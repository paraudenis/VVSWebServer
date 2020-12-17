import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class WebServerGUI extends Thread {
    private JButton startWebServerButton;
    private JPanel panel;
    private JButton maintenanceModeButton;
    private JButton stopWebServerButton;
    private JTextField homeFolderTextbox;
    private JButton setHomeFolderButton;
    private JTextField portTextbox;
    private JButton setPortButton;
    private JTextArea outputTextArea;
    private JLabel serverStatusLabel;

    private String webServerStatus = "Stopped";
    private int webServerPort;
    private String webServerHome;
    private String outputText = "";
    private WebServer server;

    public WebServerGUI() {
        startWebServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (webServerStatus.equals("Stopped") || webServerStatus.equals("Maintenance")) {
                    if (homeFolderTextbox.getText().isEmpty() == false && Files.exists(Paths.get(homeFolderTextbox.getText()))) {
                        if (portTextbox.getText().isEmpty() == false) {
                            try {
                                int port = Integer.parseInt(portTextbox.getText());
                                if (port >= 0 && port <= 65535) {
                                    webServerPort = port;
                                    server.setPort(webServerPort);
                                    webServerHome = homeFolderTextbox.getText();
                                    server.setHome(webServerHome);
                                    webServerStatus = "Running";
                                    server.setStatus("Running");
                                    serverStatusLabel.setText("WebServer status: Running");
                                    writeInOutputTextbox("Server running.");
                                } else {
                                    writeInOutputTextbox("Please enter an integer in the port textbox.");
                                }
                            } catch (NumberFormatException numberFormatException) {
                                writeInOutputTextbox("Please enter an integer in the port textbox.");
                            }
                        } else {
                            writeInOutputTextbox("Please enter a port.");
                        }

                    } else {
                        writeInOutputTextbox("Please enter a correct path for home address.");
                    }
                } else {
                    writeInOutputTextbox("Server is already running.");
                }
            }
        });
        maintenanceModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (webServerStatus.equals("Stopped") || webServerStatus.equals("Running")) {
                    if (homeFolderTextbox.getText().isEmpty() == false && Files.exists(Paths.get(homeFolderTextbox.getText()))) {
                        if (portTextbox.getText().isEmpty() == false) {
                            try {
                                int port = Integer.parseInt(portTextbox.getText());
                                if (port >= 0 && port <= 65535) {
                                    webServerPort = port;
                                    server.setPort(webServerPort);
                                    webServerHome = homeFolderTextbox.getText();
                                    server.setHome(webServerHome);
                                    webServerStatus = "Maintenance";
                                    server.setStatus("Maintenance");
                                    serverStatusLabel.setText("WebServer status: Maintenance mode");
                                    writeInOutputTextbox("Server running in maintenance mode.");
                                } else {
                                    writeInOutputTextbox("Please enter an integer between 0 and 65535 in the port textbox.");
                                }
                            } catch (NumberFormatException numberFormatException) {
                                writeInOutputTextbox("Please enter an integer in the port textbox.");
                            }
                        } else {
                            writeInOutputTextbox("Please enter a port.");
                        }

                    } else {
                        writeInOutputTextbox("Please enter a correct path for home address.");
                    }
                } else {
                    writeInOutputTextbox("Server is already in maintenance mode.");
                }
            }
        });
        stopWebServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (webServerStatus.equals("Running") || webServerStatus.equals("Maintenance")) {
                    if (homeFolderTextbox.getText().isEmpty() == false && Files.exists(Paths.get(homeFolderTextbox.getText()))) {
                        if (portTextbox.getText().isEmpty() == false) {
                            try {
                                int port = Integer.parseInt(portTextbox.getText());
                                if (port >= 0 && port <= 65535) {
                                    webServerPort = port;
                                    server.setPort(webServerPort);
                                    webServerHome = homeFolderTextbox.getText();
                                    server.setHome(webServerHome);
                                    webServerStatus = "Stopped";
                                    server.setStatus("Stopped");
                                    serverStatusLabel.setText("WebServer status: Stopped");
                                    writeInOutputTextbox("Server stopped.");
                                } else {
                                    writeInOutputTextbox("Please enter an integer in the port textbox.");
                                }
                            } catch (NumberFormatException numberFormatException) {
                                writeInOutputTextbox("Please enter an integer in the port textbox.");
                            }
                        } else {
                            writeInOutputTextbox("Please enter a port.");
                        }

                    } else {
                        writeInOutputTextbox("Please enter a correct path for home address.");
                    }
                } else {
                    writeInOutputTextbox("Server is already stopped.");
                }
            }
        });
        setHomeFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (homeFolderTextbox.getText().isEmpty() == false && Files.exists(Paths.get(homeFolderTextbox.getText()))) {
                    webServerHome = homeFolderTextbox.getText();
                    server.setHome(webServerHome);
                    writeInOutputTextbox("Home path modified to: \"" + webServerHome + "\".");
                } else {
                    writeInOutputTextbox("Please enter a correct path for home address.");
                }
            }
        });
        setPortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (portTextbox.getText().isEmpty() == false) {
                    try {
                        int port = Integer.parseInt(portTextbox.getText());
                        if (port >= 0 && port <= 65535) {
                            webServerPort = port;
                            server.setPort(webServerPort);
                            writeInOutputTextbox("Port switched to: " + webServerPort);
                        } else {
                            writeInOutputTextbox("Please enter an integer between 0 and 65535 in the port textbox.");
                        }
                    } catch (NumberFormatException numberFormatException) {
                        writeInOutputTextbox("Please enter an integer in the port textbox.");
                    }
                } else {
                    writeInOutputTextbox("Please enter a port.");
                }
            }
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                runWebServer();
            }
        });
        thread.start();
    }

    public void writeInOutputTextbox(String s) {
        outputText += s + "\n";
        outputTextArea.setText(outputText);
    }

    public void runWebServer() {
        server = new WebServer(8080, "E:/VVSWebServer/TestFiles", "Stopped");
        webServerStatus = "Stopped";
        serverStatusLabel.setText("WebServer status: Stopped");
        webServerHome = "E:/VVSWebServer/TestFiles";
        homeFolderTextbox.setText(webServerHome);
        webServerPort = 8080;
        portTextbox.setText("8080");
        for (; ; ) {
            try {
                server.handleRequest();
            } catch (Exception e) {
                writeInOutputTextbox("Exception: " + e);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("WebServerGUI");
        frame.setContentPane(new WebServerGUI().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(7, 4, new Insets(10, 10, 10, 10), -1, -1));
        panel.setEnabled(true);
        startWebServerButton = new JButton();
        startWebServerButton.setText("Start WebServer");
        panel.add(startWebServerButton, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        maintenanceModeButton = new JButton();
        maintenanceModeButton.setText("Maintenance Mode");
        panel.add(maintenanceModeButton, new com.intellij.uiDesigner.core.GridConstraints(2, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        homeFolderTextbox = new JTextField();
        homeFolderTextbox.setText("");
        panel.add(homeFolderTextbox, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(300, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Home folder:");
        panel.add(label1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        setHomeFolderButton = new JButton();
        setHomeFolderButton.setText("Set Home Folder");
        panel.add(setHomeFolderButton, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Port:");
        panel.add(label2, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        setPortButton = new JButton();
        setPortButton.setText("Set Port");
        panel.add(setPortButton, new com.intellij.uiDesigner.core.GridConstraints(4, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        portTextbox = new JTextField();
        portTextbox.setText("");
        panel.add(portTextbox, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        stopWebServerButton = new JButton();
        stopWebServerButton.setText("Stop WebServer");
        panel.add(stopWebServerButton, new com.intellij.uiDesigner.core.GridConstraints(3, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Text Output:");
        panel.add(label3, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        outputTextArea = new JTextArea();
        outputTextArea.setText("");
        scrollPane1.setViewportView(outputTextArea);
        serverStatusLabel = new JLabel();
        serverStatusLabel.setText("Server Status:");
        panel.add(serverStatusLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

}
