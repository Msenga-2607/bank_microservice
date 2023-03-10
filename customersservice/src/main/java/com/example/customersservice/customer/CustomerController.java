package com.example.customersservice.customer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


@Controller
public class CustomerController {
		String usernameforclass = "";
	private RestTemplate restTemplate;

	@Autowired
  public CustomerController(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

//check account service availability
  @GetMapping("/create/customer/account/{id}/{name}")
    public String checkAccountServiceStatus(@PathVariable int id, 
	@PathVariable String name) {
    try {
        // Send a request to the student service
        URL url = new URL("http://localhost:8001/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int statusCode = connection.getResponseCode();

        // If the response is 200 OK, the service is on
        if (statusCode == 200) {
			Random random = new Random();
        int number = (int) (random.nextDouble() * (int) (Math.pow(10, 13)));
        
			try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/accountservice","root","");
			Statement stmt = con.createStatement();
			
			PreparedStatement pst = con.prepareStatement("insert into accounts(customer_id,fullname,account_number,balance) values(?,?,?,?);");
			pst.setInt(1,id);
			pst.setString(2, name);
            pst.setInt(3, number);
			pst.setInt(4, 10000);
			int rowsAffected = pst.executeUpdate();

			if (rowsAffected > 0) {
				return "redirect:/home?status=1";
			} else {
				return "redirect:/home?status=0";
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

  //create customer accounts
  @GetMapping("/createww/customer/account/{id}/{name}")
  public String creatCustomerAccount(@PathVariable("id") Long id) {
    String url = "http://localhost:8001/api/create/customeraccount/" + id;
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    return response.getBody();
  }
	
    @GetMapping("/")
	public String loginUserPage(Model model){
		return "index";
	}

    @GetMapping("/home")
	public String index(Model model) {

		//check if user logged in
		if(usernameforclass.equalsIgnoreCase(""))
			return "index";
		else {
			model.addAttribute("username", usernameforclass);
			return "home";
		}
			
	}

    @RequestMapping(value = "userloginvalidate", method = RequestMethod.POST)
    public String userlogin(@RequestParam("username") String username,
    @RequestParam("password") String pass,Model model) {
        try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/staffservice","root","");
			Statement stmt = con.createStatement();
			ResultSet rst = stmt.executeQuery("select * from staffs where username = '"+username+"' and password = '"+pass+"' ;");
			if(rst.next()) {
				usernameforclass = rst.getString(2);
				return "redirect:/home";
				}
			else {
				model.addAttribute("message", "Invalid Username or Password");
				return "index";
			}
			
		}
		catch(Exception e){
			System.out.println("Exception:"+e);
		}
        return "index";
    }

    @GetMapping("/register")
	public String registerCustomer(){
		return "register";
	}

	@RequestMapping(value = "addcustomer", method=RequestMethod.POST)
	public String addCustomer(@RequestParam("name") String name,
	@RequestParam("sex") String sex,
	@RequestParam("dob") String dob,
	@RequestParam("phone_number") String phone_number,
	@RequestParam("email") String email){

		try
		{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/customerservice","root","");
			Statement stmt = con.createStatement();
			
			PreparedStatement pst = con.prepareStatement("insert into customers(fullname,sex,email,phone_number) values(?,?,?,?);");
			pst.setString(1,name);
			pst.setString(2, sex);
			pst.setString(3, email);
			pst.setString(4, phone_number);
			int rowsAffected = pst.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println("Exception:"+e);
		}

		return "redirect:/home";
		
	}

	@GetMapping("/customers")
	public String getCustomers(Model model) {
		return "home";
	}


	@GetMapping("/customer/update")
	public String updatecustomers(@RequestParam("customer_id") String idStr,Model model) {
		try {
			int id = Integer.parseInt(idStr);
			// rest of the code
		  } catch (NumberFormatException e) {
			model.addAttribute("errorMessage", "Invalid customer id. Please provide a valid integer value.");
			return "error";
		  } catch (Exception e) {
			System.out.println("Exception:" + e);
		  }
		String fullname,sex,email;
		int customer_id,phone_number;
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/customerservice","root","");
			Statement stmt = con.createStatement();
			ResultSet rst = stmt.executeQuery("select * from customers where id = "+idStr+";");
			
			if(rst.next())
			{
			customer_id = rst.getInt(1);
			fullname = rst.getString(2);
		sex = rst.getString(3);
				email =  rst.getString(4);
		phone_number = rst.getInt(5);
			model.addAttribute("customer_id",customer_id);
			model.addAttribute("name",fullname);
			model.addAttribute("gender",sex);
			model.addAttribute("phone_number",phone_number);
			model.addAttribute("email",email);
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception:"+e);
		}
		return "customerUpdate";
	}

	@RequestMapping(value = "/customer/updateData",method=RequestMethod.POST)
	public String updatecustomerstodb(@RequestParam("customer_id") String id,
	@RequestParam("name") String name,
	@RequestParam("dob") String dob,
	@RequestParam("phone_number") String phone_number,
	@RequestParam("email") String email) 
	
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/customer_managements","root","");
			
			PreparedStatement pst = con.prepareStatement("update customers set name= ?,dob = ?,phone_number = ?, email = ? where id = ?;");
			pst.setString(1, name);
			pst.setString(2, dob);
			pst.setString(3, phone_number);
			pst.setString(4, email);
			pst.setString(5, id);
			int i = pst.executeUpdate();			
		}
		catch(Exception e)
		{
			System.out.println("Exception:"+e);
		}
		return "redirect:/customers";
	}

	public String addCustomerAccounts(){
		return "redirect:/customers";
	}

	
}
