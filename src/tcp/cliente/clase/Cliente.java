package tcp.cliente.clase;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Cliente {
    private static final int PUERTO = 5000;
    private static final String IP = "localhost";


    public static String enviarNombre(String nombre, String accion) throws Exception {
        Socket socket = new Socket(IP, PUERTO);
        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();

        DataOutputStream dos = new DataOutputStream(os);
        dos.writeUTF(nombre);
        dos.writeUTF(accion);

        DataInputStream dis = new DataInputStream(is);
        String respuesta = dis.readUTF();
        socket.close();
        return respuesta;
    }

    public static String leerRegistros(String nombre) {
        try (Socket socket = new Socket(IP, PUERTO);
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {

            dos.writeUTF("LEER");
            dos.writeUTF(nombre);

            String estado = dis.readUTF();
            if (estado.equals("OK")) {
                String registros = dis.readUTF();
                return registros;
            } else {
                return "Error: " + estado;
            }
        } catch (IOException e) {
            System.out.println("Error al conectar con el servidor: " + e.getMessage());
        }
        return "";
    }
}
