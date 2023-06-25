package maquina;

import javax.swing.JOptionPane;
import javax.xml.ws.Endpoint;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {
    public static void main(String[] args) {
        try {
            // Configuração do RMI
            Maquina maquinaRMI = new Rmi();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("Maquina", maquinaRMI);

            // Configuração do WebService
            String address = "http://localhost:8080/maquina";
            Endpoint.publish(address, new MaquinaResource());

            JOptionPane.showMessageDialog(null, "Servidor iniciado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao iniciar o servidor: " + e.getMessage());
            System.out.println(e);
        }
    }
}
