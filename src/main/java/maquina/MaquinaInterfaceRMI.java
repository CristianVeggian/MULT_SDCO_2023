package maquina;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MaquinaInterfaceRMI extends Remote {
    
    public String estocaProduto(String json) throws RemoteException;
}
