package maquina;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@WebService
public interface MaquinaWebService {
    
    @WebMethod
    @GET
    @Path("/pegaSnack/{nome}/{dinheiro}")
    @Produces(MediaType.TEXT_PLAIN)
    String pegaSnack(@PathParam("nome") String nome, @PathParam("dinheiro") double dinheiro);
    
}
