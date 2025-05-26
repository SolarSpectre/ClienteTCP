package rmi.cliente.clase;

import rmi.servidor.clase.Servidor;

import java.rmi.Naming;

public class Cliente {
    public static String consultar(int id) throws Exception{
        String resultado = null;
        String rmiObjectName = "rmi://localhost/Datos";
        Servidor servicio = (Servidor) Naming.lookup(rmiObjectName);
        resultado = servicio.consultar(id);
        return resultado;
    }
}
