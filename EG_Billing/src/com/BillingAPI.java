package com;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.google.gson.*;

import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

import model.Billing;

@Path("/Billing")
public class BillingAPI {
	Billing BillingObj = new Billing();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readBilling() {
		return BillingObj.readBilling();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBilling(
	 @FormParam("cusName") String cusName,		
	 @FormParam("cusAcc") String cusAcc,
	 @FormParam("cusDate") String cusDate,
	 @FormParam("units") String units,
	 @FormParam("totalPrice") String totalPrice)
	{
	 String output = BillingObj.insertBilling(cusName, cusAcc, cusDate, units, totalPrice);
	return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateBilling(String billingData)
	{
	//Convert the input string to a JSON object
	 JsonObject Object = new JsonParser().parse(billingData).getAsJsonObject();
	//Read the values from the JSON object
	 String billid = Object.get("billid").getAsString();
	 String cusName = Object.get("cusName").getAsString();
	 String cusAcc = Object.get("cusAcc").getAsString();
	 String cusDate = Object.get("cusDate").getAsString();
	 String units = Object.get("units").getAsString();
	 String totalPrice = Object.get("totalPrice").getAsString();
	 String output = BillingObj.updateBilling(billid, cusName, cusAcc, cusDate, units, totalPrice);
	return output;
	} 
	
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteBilling(String billingData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(billingData, "", Parser.xmlParser());

	//Read the value from the element 
	 String billid = doc.select("billid").text();
	 String output = BillingObj.deleteBilling(billid);
	return output;
	}
	
}
