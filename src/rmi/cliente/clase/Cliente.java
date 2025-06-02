package rmi.cliente.clase;


import rmi.servidor.clase.IServidor;
import rmi.servidor.clase.Persona;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class Cliente {
    private static final String HOST = "localhost";
    private static final int PORT = 1099;
    private static final String SERVICE_NAME = "ServidorEmpleados";
    private static IServidor servidor;

    static {
        try {
            Registry registry = LocateRegistry.getRegistry(HOST, PORT);
            servidor = (IServidor) registry.lookup(SERVICE_NAME);
        } catch (Exception e) {
            System.err.println("Error al conectar con el servidor: " + e.toString());
            e.printStackTrace();
        }
    }

    public static boolean crearEmpleado(String nombre, String correo, String cargo, double sueldo) throws Exception {
        Persona nuevoEmpleado = new Persona(0, nombre, correo, cargo, sueldo);
        return servidor.crear(nuevoEmpleado);
    }

    public static Persona leerEmpleado(int id) throws Exception {
        return servidor.leer(id);
    }

    public static List<Persona> leerTodosEmpleados() throws Exception {
        return servidor.leerTodos();
    }

    public static boolean actualizarEmpleado(int id, String nombre, String correo, String cargo, double sueldo) throws Exception {
        Persona empleado = new Persona(id, nombre, correo, cargo, sueldo);
        return servidor.actualizar(empleado);
    }

    public static boolean eliminarEmpleado(int id) throws Exception {
        return servidor.eliminar(id);
    }
}
