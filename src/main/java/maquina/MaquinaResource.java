package maquina;

import java.util.ArrayList;
import javax.jws.WebService;
import java.util.concurrent.locks.ReentrantLock;

@WebService(endpointInterface = "maquina.MaquinaWebService")
public class MaquinaResource implements MaquinaWebService {
    
    private ArrayList<Produto> produtos = new ArrayList();
    private ReentrantLock tranca = new ReentrantLock();
    
    public MaquinaResource(){
        //todo dia de manhã, a máquina começa com 3 de cada produto:
        for(int i = 0; i < 3; i++){
            this.produtos.add(new Produto("Suco de Pneu", 7.50f, "bebida"));
            this.produtos.add(new Produto("Lucio-Cola 350ml", 7.50f, "bebida"));
            this.produtos.add(new Produto("Coxinha de Jakarta", 3.50f, "comida"));
            this.produtos.add(new Produto("Thread Frita", 2.75f, "comida"));
        }
    }

    @Override
    public String pegaSnack(String nome, double dinheiro) {
        tranca.lock();
        try {
            for(Produto p: produtos){
                System.out.println(p);
                if(p.getNome().equals(nome)){
                    if(p.getValor() <= dinheiro){
                        produtos.remove(p);
                        return p.ProdutoToJson();
                    }else{
                        return "{'result':'pobre'}";
                    }
                }
            }
            return "{'result':'semProduto'}";
        }finally{
            tranca.unlock();
        }
    }
    
}
