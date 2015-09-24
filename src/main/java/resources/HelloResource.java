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

    private String my_provider_key = "YOUR_PROVIDER_KEY";
    //Note that the api key is hardcoded. so need to send the user_key in params
    static String user_key            = "YOUR_USER_KEY";
    static String user_key_service_id = "SERVICE_ID";


    @GET
    @Produces("text/plain")
    public String handleGreeting() {
        ServiceApi serviceApi = new ServiceApiDriver(my_provider_key);
        ParameterMap params = new ParameterMap();              // the parameters of your call
        params.add("user_key", user_key);               // Add the user key of your application for authorization
        params.add("service_id", user_key_service_id);  // Add the service id of your application

        ParameterMap usage = new ParameterMap(); // Add a metric to the call
	//usage.add("hits", "1");
        //usage.add("API-A", "1");
        usage.add("hits", "1");
        params.add("usage", usage);              // metrics belong inside the usage parameter

        AuthorizeResponse response = null;
        // the 'preferred way' of calling the backend: authrep
        try {
          response = serviceApi.authrep(params);
          String returnString1 = "AuthRep on User Key Success: " + response.success();
          System.out.println(returnString1);
          if (response.success() == true) {
            // your api access got authorized and the  traffic added to 3scale backend
            String returnString2 = "Plan: " + response.getPlan();
            System.out.println(returnString2);
            return "Hello World of API's";
          } else {
            // your api access did not authorized, check why
            System.out.println("Error: " + response.getErrorCode());
            System.out.println("Reason: " + response.getReason());
            return "Error  " + response.getErrorCode() + response.getReason();
          }
        } catch (ServerError serverError) {
          serverError.printStackTrace();
        }
        return "";
    }
    
}
