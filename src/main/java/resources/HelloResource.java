package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import threescale.v3.api.AuthorizeResponse;
import threescale.v3.api.ParameterMap;
import threescale.v3.api.ServerError;
import threescale.v3.api.ServiceApi;
import threescale.v3.api.impl.ServiceApiDriver;

@Path("/hello")
public class HelloResource {

    @GET
    @Produces("text/plan")
    public String handleGreeting() {
        return "Hello World of API's";
    }

}
