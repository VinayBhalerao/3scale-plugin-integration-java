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

    /**
     * Example code for calls on user key (API key) mode
     */
    private static void runUserKey() {
        ServiceApi serviceApi = new ServiceApiDriver(my_provider_key);    // Create the API object

        ParameterMap params = new ParameterMap();
        params.add("2a441e47aae6656ef244d3a6bfd298d0", user_key);                                 // Add keys for authrep or authorize
        params.add("2555417722022", user_key_service_id);

        ParameterMap usage = new ParameterMap();                          // Add a metric
        usage.add("hits", "1");

        params.add("configuration_v33", usage);

        AuthorizeResponse response = null;
        // the 'preferred way': authrep
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