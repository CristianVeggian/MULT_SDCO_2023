package maquina;

import javax.swing.JOptionPane;

public class Produto {
    
    private String nome;
    private double valor;
    private String tipo;
    
    public Produto(){
        
    }
    
    public Produto(String json)throws Exception{
        json = json.substring(1, json.length()-1);
        String[] params = json.split(",");
        for(String a: params){
            String[] temp = a.split(":");
            String type = temp[0].substring(1, temp[0].length()-1);
            String param = temp[1].substring(1, temp[1].length()-1);
            switch(type){
                case "result":
                    System.out.println(param);
                    if(param.equals("pobre")){
                        throw new Exception("Dinheiro insuficiente");
                    }else{
                        throw new Exception("Produto insuficiente");
                    }
                case "nome":
                    this.nome = param;
                    break;
                case "valor":
                    this.valor = Double.parseDouble(param);
                    break;
                case "tipo":
                    this.tipo = param;
                    break;
            }
        }
    }
    
    public Produto(String nome, double valor, String tipo){
        this.nome = nome;
        this.valor = valor;
        this.tipo = tipo;
    }
    
    public String ProdutoToJson(){
        String json = "{'nome':'"+ this.nome +"','valor':'"+ Double.toString(valor) + "','tipo':'" +this.tipo+ "'}";
        return json;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public double getValor() {
        return this.valor;
    }
    
    public String getTipo(){
        return this.tipo;
    }
    
}
