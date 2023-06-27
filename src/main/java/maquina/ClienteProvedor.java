package maquina;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ClienteProvedor {

    private ClienteProvedor() {
    }

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            MaquinaInterfaceRMI stub = (MaquinaInterfaceRMI) registry.lookup("MaquinaInterfaceRMI");
            JFrame janela = new JFrame("Adicionar Produto");

            String[] options = {"Coxinha de Jakarta", "Suco de Pneu", "Thread Frita", "Lucio-Cola 350ml"};
            double[] precos = {3.5f, 7.5f, 2.75f, 3.5f};
            String[] tipos = {"comida", "bebida", "comida", "bebida"};
            int pos = 40;

            JLabel header2 = new JLabel("Adicionar o produto:");
            header2.setBounds(10, 10, 50, 30);
            janela.add(header2);

            for (int i = 0; i < options.length; i++) {
                String json = "{'nome':'"+options[i]+"','valor':'"+Double.toString(precos[i])+"','tipo':'"+tipos[i]+"'}";
                MeuBotao b = new MeuBotao(options[i], json);
                b.setBounds(10, pos, 200, 30);
                pos += 40;

                b.addActionListener((ActionEvent e) -> {
                    MeuBotao clickedButton = (MeuBotao) e.getSource();
                    try {
                        String a = stub.estocaProduto(clickedButton.getKey());
                        JOptionPane.showMessageDialog(null,a);
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage());
                    }

                });
                janela.add(b);
            }

            janela.setSize(330, 300);
            janela.setLayout(null);

            janela.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });

            janela.setVisible(true);

        } catch (NotBoundException | RemoteException e) {
            System.out.println("Client exception: " + e.toString());
        }
    }
}
