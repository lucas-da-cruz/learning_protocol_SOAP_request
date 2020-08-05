package br.com.estoque.ws;

import javax.xml.ws.Endpoint;

public class PublicaWS {
    public static void main(String[] args){
        EstoqueWS service = new EstoqueWS();
        String url = "http://localhost:8081/estoques";
        //Url para o contrato de serviço
        System.out.println("Service rodando " + url + "?wsdl");

        Endpoint.publish(url, service);
    }
}
