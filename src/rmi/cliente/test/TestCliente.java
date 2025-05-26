package rmi.cliente.test;

import rmi.cliente.clase.Cliente;

import javax.swing.*;

public class TestCliente {
    public static void main(String[] args) {
        while (true) {
            try {
                String input = JOptionPane.showInputDialog(null, "Ingrese el ID del empleado:", "Consulta de Empleado", JOptionPane.QUESTION_MESSAGE);

                if (input == null) {
                    break;
                }
                int id = Integer.parseInt(input);
                String resultado = Cliente.consultar(id);
                JOptionPane.showMessageDialog(null, resultado, "Resultado", JOptionPane.INFORMATION_MESSAGE);

                int opcion = JOptionPane.showConfirmDialog(null, "¿Desea consultar otro empleado?", "Continuar", JOptionPane.YES_NO_OPTION);
                if (opcion != JOptionPane.YES_OPTION) {
                    break;
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocurrió un error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        JOptionPane.showMessageDialog(null, "Gracias por usar el sistema.", "Finalizado", JOptionPane.INFORMATION_MESSAGE);
    }
}
