package maquina;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javax.jws.WebService;

@WebService
public class Rmi extends UnicastRemoteObject implements Maquina{
    public Rmi() throws RemoteException {
        super();
    }
    
    public String pegaSnack(String nome, double dinheiro) throws RemoteException {
        return "Opa";
    }
}
