package tcp.cliente.vista;

import tcp.cliente.clase.Cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroEmpleadosGUI {
    private JFrame frame;
    private JTextField nameField;
    private JComboBox<String> actionBox;
    private JTextArea respuestaArea;

    public RegistroEmpleadosGUI() {
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Registro de Empleados");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel nameLabel = new JLabel("Nombre:");
        nameLabel.setBounds(30, 30, 100, 25);
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(120, 30, 200, 25);
        frame.add(nameField);

        JLabel actionLabel = new JLabel("Registro:");
        actionLabel.setBounds(30, 70, 100, 25);
        frame.add(actionLabel);

        String[] acciones = {
                "Ingreso al trabajo",
                "Ingreso al almuerzo",
                "Salida del almuerzo",
                "Salida del trabajo"
        };

        actionBox = new JComboBox<>(acciones);
        actionBox.setBounds(120, 70, 200, 25);
        frame.add(actionBox);

        JButton enviarBtn = new JButton("Registrar");
        enviarBtn.setBounds(120, 110, 100, 30);
        frame.add(enviarBtn);

        JButton leerBtn = new JButton("Ver historial");
        leerBtn.setBounds(230, 110, 100, 30);
        frame.add(leerBtn);

        respuestaArea = new JTextArea();
        respuestaArea.setBounds(30, 150, 440, 200);
        respuestaArea.setEditable(false);
        respuestaArea.setLineWrap(true);
        respuestaArea.setWrapStyleWord(true);
        respuestaArea.setBackground(frame.getBackground());
        JScrollPane scrollPane = new JScrollPane(respuestaArea);
        scrollPane.setBounds(30, 150, 440, 200);
        frame.add(scrollPane);

        enviarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegistro();
            }
        });

        leerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleHistorial();
            }
        });
    }

    private void handleRegistro() {
        String nombre = nameField.getText();
        String accion = (String) actionBox.getSelectedItem();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Por favor, ingrese su nombre.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String respuesta = Cliente.enviarNombre(nombre, accion);
            respuestaArea.setText("Servidor: " + respuesta);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error al enviar datos al servidor.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void handleHistorial() {
        String nombre = nameField.getText();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Por favor, ingrese su nombre.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String respuesta = Cliente.leerRegistros(nombre);
            respuestaArea.setText("Servidor: " + respuesta);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error al enviar datos al servidor.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void mostrar() {
        frame.setVisible(true);
    }
} 