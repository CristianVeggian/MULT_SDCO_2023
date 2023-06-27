package maquina;

//Imports de WebService
import javax.xml.ws.Endpoint;

//Imports de RMI
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import javax.jws.WebService;

@WebService(endpointInterface = "maquina.MaquinaWebService")
public class Servidor implements MaquinaInterfaceRMI, MaquinaWebService {

    private final ArrayList<Produto> produtos = new ArrayList();
    private final ReentrantLock tranca = new ReentrantLock();

    public Servidor() {
        for (int i = 0; i < 3; i++) {
            this.produtos.add(new Produto("Suco de Pneu", 7.50f, "bebida"));
            this.produtos.add(new Produto("Lucio-Cola 350ml", 7.50f, "bebida"));
            this.produtos.add(new Produto("Coxinha de Jakarta", 3.50f, "comida"));
            this.produtos.add(new Produto("Thread Frita", 2.75f, "comida"));
        }
    }

    // MÉTODO IMPLEMENTADO DE WEBSERVICE
    @Override
    public String pegaSnack(String nome, double dinheiro, String usuario) {
        tranca.lock();
        try {
            if (usuario.equals("espera")) {
                Thread.sleep(10000);
            }
            for (Produto p : produtos) {
                System.out.println(p);
                if (p.getNome().equals(nome)) {
                    if (p.getValor() <= dinheiro) {
                        produtos.remove(p);
                        return p.ProdutoToJson();
                    } else {
                        return "{'result':'pobre'}";
                    }
                }
            }
            return "{'result':'semProduto'}";
        } catch (InterruptedException ex) {
            return "{'result':'erroEspera'}";
        } finally {
            tranca.unlock();
        }
    }

    //MÉTODO IMPLEMENTADO DE RMI
    @Override
    public String estocaProduto(String json) throws RemoteException {
        tranca.lock();
        try {
            this.produtos.add(new Produto(json));
            return "Produto estocado com sucesso";
        } catch (Exception ex) {
            return "Falha ao estocar o produto";
        } finally {
            tranca.unlock();
        }
    }

    public static void main(String[] args) {
        try {
            // Configuração do WebService
            Servidor obj = new Servidor();

            String address = "http://localhost:8080/maquina";
            Endpoint.publish(address, obj);

            // Configuração RMI
            MaquinaInterfaceRMI stub = (MaquinaInterfaceRMI) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("MaquinaInterfaceRMI", stub);

            System.out.println("O Servidor está online");
        } catch (Exception e) {
            System.out.println("Erro ao iniciar o servidor: " + e.getMessage());
        }
    }

}
