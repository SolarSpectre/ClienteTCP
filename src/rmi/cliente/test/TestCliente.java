package rmi.cliente.test;

import rmi.cliente.vista.EmpleadosGUI;

public class TestCliente {
    public static void main(String[] args) {
        try {
            EmpleadosGUI gui = new EmpleadosGUI();
            gui.mostrar();
        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación: " + e.toString());
            e.printStackTrace();
        }
    }
}
