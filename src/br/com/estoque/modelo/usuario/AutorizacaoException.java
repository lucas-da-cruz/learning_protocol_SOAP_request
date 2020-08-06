package br.com.estoque.modelo.usuario;

import javax.xml.ws.WebFault;
import java.util.Date;

/**
 * Ex message:
 *  <S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
 *    <S:Body>
 *       <S:Fault xmlns:ns4="http://www.w3.org/2003/05/soap-envelope">
 *          <faultcode>S:Server</faultcode>
 *          <faultstring>Autorização falhou</faultstring>
 *          <detail>
 *             <ns2:AutorizandoFault xmlns:ns2="http://ws.estoque.com.br/">
 *                <dataErro>2020-08-06T09:28:08.425-03:00</dataErro>
 *                <mensagem>Token invalido</mensagem>
 *             </ns2:AutorizandoFault>
 *          </detail>
 *       </S:Fault>
 *    </S:Body>
 * </S:Envelope>
 */
@WebFault(name="AutorizandoFault")
public class AutorizacaoException extends Exception {

    public AutorizacaoException(String token_invalido) {
        //Passando para a classe mãe
        super(token_invalido);
    }

    public InfoFault getFaultInfo() {
        return new InfoFault("Token invalido" , new Date());
    }
}
