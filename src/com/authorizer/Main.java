package com.authorizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.authorizer.model.Operations;
import com.authorizer.model.Transactions;
import com.authorizer.utils.FlowProcess;
import com.authorizer.validations.Validations;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
	
	static boolean limitLessThenAmout;
	
	static boolean doubleTransaction;

	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws FileNotFoundException, IOException {

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			InputStream inputStream = new FileInputStream(new File("File/input/operations.json"));
			TypeReference<List<Operations>> typeReference = new TypeReference<List<Operations>>() {
			};
			List<Operations> operations = mapper.readValue(inputStream, typeReference);

			FlowProcess flowProcessCreation = FlowProcess.ACCOUNT_CREATION;
			FlowProcess flowProcessAuthorizer = FlowProcess.AUTHORIZER;
	

			logger.info("Read File input json...");
			
			for (Operations operationInputData : operations) {
				// verify o flowProcess type.
				String valueProcessRecive = operationInputData.getAccount().getFlowProcess().toString().toUpperCase();
				//verification about limit card
				int limitCard = operationInputData.getAccount().getAvaliableLimit();
				if(limitCard == 0) {
			       limitLessThenAmout = false;
				}
				List<Transactions> transactionsUnity= operationInputData.getAccount().getTransactions();
				List<Integer> listAmout = new ArrayList<Integer>();
				List<String> listTimeTransaction = new ArrayList<String>();
				for (Transactions index: transactionsUnity) {
					int amoutValue = index.getAmout();
					String timeTransaction = index.getTime();
					listAmout.add(amoutValue);	
					listTimeTransaction.add(timeTransaction);
					
				}//verification about time transactions and duplication

				for (int a = 0; a < listTimeTransaction.size(); a++) {
		          	String x = listTimeTransaction.get(a);
		          	String xDataInterval = listTimeTransaction.get(a).substring(0, 10);
		          	String xTimeInterval = listTimeTransaction.get(a).substring(11, 16).replaceAll("\\p{Punct}", "");
		          	int xTimeIntervalParseToInt = Integer.parseInt(xTimeInterval);
					for ( a += 1 ; a < listTimeTransaction.size(); a++) {
						if(a == listTimeTransaction.size()) {
							logger.debug("size array end, loop completed");
					      break;
						}
			
						String y = listTimeTransaction.get(a);
						String yDataInterval = listTimeTransaction.get(a).substring(0, 10);
						String yTimeInterval = listTimeTransaction.get(a).substring(11, 16).replaceAll("\\p{Punct}", "");
						int yTimeIntervalParseToInt = Integer.parseInt(yTimeInterval);

						
						int operationCalc = Math.abs(xTimeIntervalParseToInt - yTimeIntervalParseToInt);
						
						if (x.equals(y)) {
							logger.info("calling || authorizerTransactionDoubleTransaction");
							Validations.authorizerTransactionDoubleTransaction(operationInputData);
							Runtime.getRuntime().exit(0);
							break;
						}
						
						if ((!x.equals(y) && xDataInterval.contentEquals(yDataInterval)) && operationCalc <= 2 ) {
							logger.info("calling || authorizerTransactionHighFrequencyInterval");
							Validations.authorizerTransactionHighFrequencyInterval(operationInputData);
							Runtime.getRuntime().exit(0);
							break;
						}
							
					}
				}

				
				//logic about limit card low and different at 0
				for (int indexAmout : listAmout ) {	
					int valueOfAmout = indexAmout;
					if(valueProcessRecive.equals(flowProcessAuthorizer.getValor()) && limitCard < valueOfAmout) {// if limit less to amout, so set variable with = true;
						limitLessThenAmout = true;
						logger.info("the flow Type is Authorizer || LimitCard not is a limit valid");
						Validations.authorizerTransactionLimitCard(operationInputData, limitLessThenAmout);
						Runtime.getRuntime().exit(0);
					}
					if(limitCard > valueOfAmout && limitCard != 0) {
						limitLessThenAmout = false;
					}
				}
			
			
				if (valueProcessRecive.equals(flowProcessAuthorizer.getValor()) && !limitLessThenAmout) {
					logger.info("the flow Type is Authorizer");
					Validations.authorizerAccount(operationInputData);
					
				}
				
				if (valueProcessRecive.equals(flowProcessCreation.getValor())) {
					logger.info("the flow Type is Creation");
					logger.trace("Creation Account...");
			     	Validations.creationAccount(); 
			     	Runtime.getRuntime().exit(0);
				}
				if(valueProcessRecive.equals(flowProcessCreation.getValor())) {
					
				}
				if (!valueProcessRecive.equals(flowProcessCreation.getValor())
						&& !valueProcessRecive.equals(flowProcessAuthorizer.getValor())) {
					logger.error("No flow Types valid in input data, system shutdown.");
				}

			}
			inputStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		}

	}

}
