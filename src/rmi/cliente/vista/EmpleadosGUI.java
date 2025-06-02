package rmi.cliente.vista;

import rmi.cliente.clase.Cliente;
import rmi.servidor.clase.Persona;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmpleadosGUI {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField idField, nombreField, correoField, cargoField, sueldoField;
    private JButton crearBtn, leerBtn, actualizarBtn, eliminarBtn, refrescarBtn;

    public EmpleadosGUI() {
        initializeGUI();
        refrescarTabla();
    }

    private void initializeGUI() {
        frame = new JFrame("Gestión de Empleados");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        formPanel.add(nombreField);

        formPanel.add(new JLabel("Correo:"));
        correoField = new JTextField();
        formPanel.add(correoField);

        formPanel.add(new JLabel("Cargo:"));
        cargoField = new JTextField();
        formPanel.add(cargoField);

        formPanel.add(new JLabel("Sueldo:"));
        sueldoField = new JTextField();
        formPanel.add(sueldoField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        crearBtn = new JButton("Crear");
        leerBtn = new JButton("Leer");
        actualizarBtn = new JButton("Actualizar");
        eliminarBtn = new JButton("Eliminar");
        refrescarBtn = new JButton("Refrescar");

        buttonPanel.add(crearBtn);
        buttonPanel.add(leerBtn);
        buttonPanel.add(actualizarBtn);
        buttonPanel.add(eliminarBtn);
        buttonPanel.add(refrescarBtn);

        formPanel.add(buttonPanel);

        String[] columnNames = {"ID", "Nombre", "Correo", "Cargo", "Sueldo"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        frame.add(formPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        crearBtn.addActionListener(e -> handleCrear());
        leerBtn.addActionListener(e -> handleLeer());
        actualizarBtn.addActionListener(e -> handleActualizar());
        eliminarBtn.addActionListener(e -> handleEliminar());
        refrescarBtn.addActionListener(e -> refrescarTabla());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    idField.setText(table.getValueAt(row, 0).toString());
                    nombreField.setText(table.getValueAt(row, 1).toString());
                    correoField.setText(table.getValueAt(row, 2).toString());
                    cargoField.setText(table.getValueAt(row, 3).toString());
                    sueldoField.setText(table.getValueAt(row, 4).toString());
                }
            }
        });
    }

    private void refrescarTabla() {
        try {
            tableModel.setRowCount(0);
            List<Persona> empleados = Cliente.leerTodosEmpleados();
            for (Persona p : empleados) {
                tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getNombre(),
                    p.getCorreo(),
                    p.getCargo(),
                    p.getSueldo()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error al refrescar la tabla: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCrear() {
        try {
            String nombre = nombreField.getText();
            String correo = correoField.getText();
            String cargo = cargoField.getText();
            double sueldo = Double.parseDouble(sueldoField.getText());

            if (nombre.isEmpty() || correo.isEmpty() || cargo.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Por favor complete todos los campos.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean creado = Cliente.crearEmpleado(nombre, correo, cargo, sueldo);
            if (creado) {
                JOptionPane.showMessageDialog(frame, "Empleado creado exitosamente.");
                limpiarCampos();
                refrescarTabla();
            } else {
                JOptionPane.showMessageDialog(frame, "Error al crear el empleado.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Por favor ingrese un sueldo válido.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error al crear empleado: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleLeer() {
        try {
            int id = Integer.parseInt(idField.getText());
            Persona empleado = Cliente.leerEmpleado(id);
            if (empleado != null) {
                nombreField.setText(empleado.getNombre());
                correoField.setText(empleado.getCorreo());
                cargoField.setText(empleado.getCargo());
                sueldoField.setText(String.valueOf(empleado.getSueldo()));
            } else {
                JOptionPane.showMessageDialog(frame, "Empleado no encontrado.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Por favor ingrese un ID válido.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error al leer empleado: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleActualizar() {
        try {
            int id = Integer.parseInt(idField.getText());
            String nombre = nombreField.getText();
            String correo = correoField.getText();
            String cargo = cargoField.getText();
            double sueldo = Double.parseDouble(sueldoField.getText());

            if (nombre.isEmpty() || correo.isEmpty() || cargo.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Por favor complete todos los campos.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean actualizado = Cliente.actualizarEmpleado(id, nombre, correo, cargo, sueldo);
            if (actualizado) {
                JOptionPane.showMessageDialog(frame, "Empleado actualizado exitosamente.");
                refrescarTabla();
            } else {
                JOptionPane.showMessageDialog(frame, "Error al actualizar el empleado.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Por favor ingrese valores válidos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error al actualizar empleado: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleEliminar() {
        try {
            int id = Integer.parseInt(idField.getText());
            int confirmacion = JOptionPane.showConfirmDialog(frame,
                    "¿Está seguro de eliminar este empleado?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean eliminado = Cliente.eliminarEmpleado(id);
                if (eliminado) {
                    JOptionPane.showMessageDialog(frame, "Empleado eliminado exitosamente.");
                    limpiarCampos();
                    refrescarTabla();
                } else {
                    JOptionPane.showMessageDialog(frame, "Error al eliminar el empleado.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Por favor ingrese un ID válido.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error al eliminar empleado: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        idField.setText("");
        nombreField.setText("");
        correoField.setText("");
        cargoField.setText("");
        sueldoField.setText("");
    }

    public void mostrar() {
        frame.setVisible(true);
    }
} 