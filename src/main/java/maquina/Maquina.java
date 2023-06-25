package maquina;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Maquina extends Remote {
   
    public String pegaSnack(String nome, double dinheiro)throws RemoteException;
        
}
