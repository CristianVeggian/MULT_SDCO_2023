package maquina;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Cliente {


    public static void main(String[] args) {
                
        try {
            URL url = new URL("http://localhost:8080/maquina?wsdl");
            QName qnameService = new QName("http://maquina/", "MaquinaResourceService");
            QName qnamePort = new QName("http://maquina/", "MaquinaResourcePort");
            Service service = Service.create(url, qnameService);
            MaquinaWebService maquina = service.getPort(qnamePort, MaquinaWebService.class);
            
            JFrame janela = new JFrame("Vitrine");

            String[] options = {"Coxinha de Jakarta", "Suco de Pneu", "Thread Frita", "Lucio-Cola 350ml"};
            String[] precos = {"R$3,50", "R$7,50", "R$2,75", "R$7,50"};
            int pos = 40;
            JLabel header1 = new JLabel("Preço");
            JLabel header2 = new JLabel("Produto");
            header1.setBounds(10,10,50,30);
            header2.setBounds(150,10,50,30);
            janela.add(header1);
            janela.add(header2);
            for(int i = 0; i < options.length; i++){
                JButton b = new JButton(options[i]);
                JLabel l = new JLabel(precos[i]);
                l.setBounds(10, pos, 50, 30);
                b.setBounds(60,pos,200,30);
                pos += 40;
                
                b.addActionListener((ActionEvent e) -> {
                    JButton clickedButton = (JButton) e.getSource();
                    String buttonLabel = clickedButton.getText();
                    double valor = Double.parseDouble(JOptionPane.showInputDialog("Insira o Dinheiro: R$"));
                    String json = maquina.pegaSnack(buttonLabel, valor);
                    try{
                        Produto meuProduto = new Produto(json);
                        if(meuProduto.getTipo().equals("comida"))
                            JOptionPane.showMessageDialog(null, "Vou comer essa " + meuProduto.getNome() + " deliciosa!");
                        else
                            JOptionPane.showMessageDialog(null, "Vou beber meu " + meuProduto.getNome() + " agora :D");
                        if(meuProduto.getValor() < valor){
                            JOptionPane.showMessageDialog(null, "Ah, sim... Meu troco é de R$" + Double.toString(valor - meuProduto.getValor()));
                        } 
                    }catch(Exception exception){
                        JOptionPane.showMessageDialog(null, exception);
                    }
                    
                    
                });
                janela.add(b);
                janela.add(l);
            }
            
            janela.setSize(330,300);  
            janela.setLayout(null);
            
            janela.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
            
            janela.setVisible(true);   
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a requisição: " + e.getMessage());
            System.out.println(e);
        }
    }
}
