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
        ServiceApi serviceApi = new ServiceApiDriver(my_provider_key);    // Create the API object

        ParameterMap params = new ParameterMap();
        params.add("user_key", user_key);
        params.add("service_id", user_key_service_id);
        
        ParameterMap usage = new ParameterMap();                          // Add a metric
        
	usage.add("hits", "2");
        usage.add("configuration_v33", "1");
        
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
