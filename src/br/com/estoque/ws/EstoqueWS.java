package br.com.estoque.ws;

import br.com.estoque.modelo.item.*;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.List;

@WebService
public class EstoqueWS {
    private ItemDao dao = new ItemDao();

    //Defino o nome da minha mensagem no wsdl e dentro do body do xml response
    //Ex: <ns2:todosOsItensResponse>
    @WebMethod(operationName = "todosOsItens")
    @ResponseWrapper(localName="itens")
    //define meu encapsulador com itens
    @RequestWrapper(localName = "listaItens")
    //Defino da list de itens, ou seja, o nome do meu XML externo,
    // aonde conterá todos os itens de retorno
    //Ex? <itens> <item></item><item></item> </itens>
    @WebResult(name="item")
    public List<Item> getItem(@WebParam(name="filtros") Filtros filtros){
        System.out.println("Chamado getItem()");
        List<Filtro> lista = filtros.getLista();
        List<Item> itensResultado = dao.todosItens(lista);
        return itensResultado;
    }

}
