package com.example.accountservice.accountservice.account;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
//@RequestMapping("/api/accounts")
public class AccountController {
    // Integer balance = 10000;

    private RestTemplate restTemplate;

	@Autowired
  public AccountController(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

    @GetMapping("/")//working
   public String homePage(){
    return "home";
   }


   @GetMapping("/addAccount")
    public String addAccountPage(){
        return "addAccount";
    }

    //to be accessed from customer service
  //  @GetMapping("/api/create/customeraccount/{id}")
  //  @ResponseBody
  //   public String home(@PathVariable("id") String idString){
  //       try {
  //           long customer_id = Long.parseLong(idString);
  //           // Use the id value to retrieve customer information and return it in the response
  //       } catch (NumberFormatException e) {
  //           return "Invalid customer ID format. ID must be a long value.";
  //       }

  //       Random random = new Random();
  //       int number = (int) (random.nextDouble() * (int) (Math.pow(10, 13)));
  //       System.out.println(number);

  //       try
	// 	{
  //           Class.forName("com.mysql.cj.jdbc.Driver");
	// 		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/accountservice","root","");
	// 		Statement stmt = con.createStatement();
			
	// 		PreparedStatement pst = con.prepareStatement("insert into accounts(customer_id,account_number,balance) values(?,?,?);");
	// 		pst.setString(1, idString);
	// 		pst.setInt(2, number);
	// 		pst.setInt(3, balance);
	// 		int rowsAffected = pst.executeUpdate();
	// 	}
	// 	catch(Exception e)
	// 	{
	// 		System.out.println("Exception:"+e);
	// 	}
  //       return "home";
  //   }

  //   @GetMapping("/appyloan/{id}/{name}")
  //   public String applyLoanPage(@PathVariable int id, 
  //   @PathVariable String name){
  //       return "applyLoan";
  //   }

    //add loan to loan service
  @GetMapping("/apply/customer/loan/{id}")
  public String applyCustomerLoan(@PathVariable("id") Long id) {
    String url = "http://localhost:8001/api/create/loanapplication/" + id;
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    return response.getBody();
  }


  @RequestMapping(value = "applyloanform", method=RequestMethod.POST)
	public String addCustomer(@RequestParam("customer_id") String customer_id,
	@RequestParam("name") String name,
	@RequestParam("amount") String amount,
  @RequestParam("purpose") String purpose,
  @RequestParam("credit_history") String credit_history,
  @RequestParam("status") String status){

		try {
      // Send a request to the loan service
      URL url = new URL("http://localhost:8003/");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      int statusCode = connection.getResponseCode();

      // If the response is 200 OK, the service is on
      if (statusCode == 200) {
  
    try
  {
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/loanservice","root","");
			Statement stmt = con.createStatement();
			
			PreparedStatement pst = con.prepareStatement("insert into loans(customer_id,name,amount,purpose,credit_history,status) values(?,?,?,?,?,?);");
			pst.setString(1,customer_id);
      pst.setString(2,name);
			pst.setString(3, amount);
			pst.setString(4, purpose);
			pst.setString(5, credit_history);
      pst.setString(6, "pending");
			int rowsAffected = pst.executeUpdate();

    if (rowsAffected > 0) {
      return "redirect:/?status=1";
    } else {
      return "redirect:/?status=0";
    }
  }
  catch(Exception e)
  {
    System.out.println("Exception:"+e);
  }
          return "redirect:redirect:/home?status=1";
      } else {
          return "serviceoff";
      }
  } catch (IOException e) {
      // If an exception is thrown, the service is off
      return "serviceoff";
  }
   
}
}
