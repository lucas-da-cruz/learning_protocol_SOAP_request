package br.com.estoque.ws;

import br.com.estoque.modelo.item.*;
import br.com.estoque.modelo.usuario.TokenDao;
import br.com.estoque.modelo.usuario.TokenUsuario;
import br.com.estoque.modelo.usuario.AutorizacaoException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.List;

@WebService
//Literal = Não vêm o DataType no xml / Encoded = Vêm o DataType, não é recomendado seu uso
//WRAPPED encapsula a mensagem
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL, parameterStyle = ParameterStyle.WRAPPED)
public class EstoqueWS {
    private ItemDao dao = new ItemDao();

    //Defino o nome da minha mensagem no wsdl e dentro do body do xml response
    //Ex: <ns2:todosOsItensResponse>
    //define meu encapsulador com itens
    //Defino da list de itens, ou seja, o nome do meu XML externo,
    // aonde conterá todos os itens de retorno
    //Ex? <itens> <item></item><item></item> </itens>
    @WebMethod(operationName = "todosOsItens")
    @ResponseWrapper(localName="itens")
    @RequestWrapper(localName = "listaItens")
    @WebResult(name="item")
    public List<Item> getItem(@WebParam(name="filtros") Filtros filtros){
        System.out.println("Chamado getItem()");
        List<Filtro> lista = filtros.getLista();
        List<Item> itensResultado = dao.todosItens(lista);
        return itensResultado;
    }

    //action define o nome da ação para ser chamada
    //Passada via o header da requisição
    @WebMethod(action = "cadastrarItemNovo",operationName = "cadastrarItem")
    @WebResult(name="item")
    public Item cadastrarItem(@WebParam(name = "token", header = true) TokenUsuario token, @WebParam(name="item") Item item) throws AutorizacaoException {
        System.out.println("Cadastrando item" +  item.getNome() + "token: " + token.getToken());

        boolean valido = new TokenDao().ehValido(token);

        if(!valido){
            //Lançando exceção pois o token é invalido
            //Esse tipo de exception é do tipo modeled, pois foi prevista acontecer
            //O tipo ummodeled é um runtimeException por exemplo, erros de lógica
            //As exception modeled são previstas no portType no wsdl
            throw new AutorizacaoException("Autorização falhou");
        }

        //Gerara uma fault(exception) unchecked
        new ItemValidador(item).validate();

        dao.cadastrar(item);
        return item;
    }

}
