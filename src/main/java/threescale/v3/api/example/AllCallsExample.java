package threescale.v3.api.example;

import threescale.v3.api.*;
import threescale.v3.api.impl.*;


/**
 * Simple Example of using the API
 */
public class AllCallsExample implements TestKeys {

    public static void main(String[] args) {

        runUserKey();
      }

    private static void runUserKey() {
        ServiceApi serviceApi = new ServiceApiDriver("9062f7afd5a594f2e9882c34206eea67");    // Create the API object

        ParameterMap params = new ParameterMap();
        //params.add("727440b359299cd369edb8f2e1371e1b", user_key);
        String user_key = "a9171e63c10b987f9df7c86e268ec562";
        //params.add("2555417722022", service_id);
        params.add("service_id", "2555417722022"); 
        ParameterMap usage = new ParameterMap();                          // Add a metric
        
	usage.add("Points", "2");
        
        params.add("usage", usage);

        AuthorizeResponse response = null;
        try {
            response = serviceApi.authrep(params);
            System.out.println("AuthRep on User Key Success: " + response.success());
            if (response.success() == false) {
                System.out.println("Error: " + response.getErrorCode());
                System.out.println("Reason: " + response.getReason());
            }
            System.out.println("Plan: " + response.getPlan());
        } catch (ServerError serverError) {
            serverError.printStackTrace();
        }

        // the '2 steps way': authorize + report
        try {
            response = serviceApi.authorize(params);
            System.out.println("Authorize on User Key Success: " + response.success());
            if (response.success() == false) {
                System.out.println("Error: " + response.getErrorCode());
                System.out.println("Reason: " + response.getReason());
            } else {

              // the API call got authorized, let's do a report
              ParameterMap transaction = new ParameterMap();
              transaction.add("user_key", user_key);

              ParameterMap transaction_usage = new ParameterMap();
              transaction_usage.add("hits", "1");
              transaction.add("usage", transaction_usage);

              try {
                  final ReportResponse report_response = serviceApi.report(user_key_service_id, transaction);

                  if (report_response.success()) {
                      System.out.println("Report on User Key was successful");
                  } else {
                      System.out.println("Report on User Key failed");
                  }
              } catch (ServerError serverError) {
                  serverError.printStackTrace();
              }

            }
            System.out.println("Plan: " + response.getPlan());

        } catch (ServerError serverError) {
            serverError.printStackTrace();
        }
    }

    
}
