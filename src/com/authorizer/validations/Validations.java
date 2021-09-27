package com.authorizer.validations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.authorizer.Main;
import com.authorizer.model.Account;
import com.authorizer.model.AccountCreated;
import com.authorizer.model.Operations;
import com.authorizer.model.OperationsConsumed;
import com.authorizer.model.Transactions;
import com.authorizer.model.Violations;
import com.authorizer.utils.FlowProcess;
import com.authorizer.utils.ViolationsTypes;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Validations {
	
	
	private static final Logger logger = LoggerFactory.getLogger(Validations.class);
	
	//FlowProcessTypesEnum
	static FlowProcess flowProcessCreation = FlowProcess.ACCOUNT_CREATION;
	static FlowProcess flowProcessAuthorizer = FlowProcess.AUTHORIZER;
	
	//ViolationsTypesEnum
	static ViolationsTypes violationsTypesAccountNotInittialized = ViolationsTypes.ACCOUNT_NOTINITIALIZED;
	static ViolationsTypes violationsTypesAccountAlreadyInitialized = ViolationsTypes.ACCOUNTALREADY_INITIALIZED;
	static ViolationsTypes violationsTypesCardNotActive= ViolationsTypes.CARD_NOT_ACTIVE;
	static ViolationsTypes violationsTypesDoubleTransaction= ViolationsTypes.DOUBLE_TRANSACTION;
	static ViolationsTypes violationsTypesHighFrequencyInterval= ViolationsTypes.HIGHFREQUENCY_SMALL_INTERVAL;
	static ViolationsTypes violationsTypesInsufficientLimit= ViolationsTypes.INSUFFICIENT_LIMIT;

	public static void creationAccount() throws JsonGenerationException, JsonMappingException, IOException {
		//method do CreationAccount

		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		AccountCreated accountCreated = new AccountCreated();

		Account newAccount = new Account();
		newAccount.setFlowProcess(flowProcessAuthorizer.getValor());
		newAccount.setActiveCard(false);
		newAccount.setAvaliableLimit(750);
		newAccount.setTransactions(null);
		accountCreated.setAccount(newAccount);
		

		mapper.writeValue(new File("File/output/accountCreated.json"), accountCreated);
		logger.info("Account Created success File output json in File Folder...");

	}

	public static void authorizerAccount(Operations dataSave)
			throws JsonGenerationException, JsonMappingException, IOException {
		//method Autorization Account, 1 - no violations || 2 - violation card-no-active
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		OperationsConsumed operationsConsumed = new OperationsConsumed();
		Account newAuthorization = new Account();

		for (Transactions transactionIndividuality : dataSave.getAccount().getTransactions()) {
			if (transactionIndividuality.getMerchant() != null && transactionIndividuality.getMerchant() != null
					&& transactionIndividuality.getTime() != null) {
				if (dataSave.getAccount().isActiveCard() == true) {
					newAuthorization.setActiveCard(true);
					newAuthorization.setAvaliableLimit(dataSave.getAccount().getAvaliableLimit());
					newAuthorization.setTransactions(dataSave.getAccount().getTransactions());
					operationsConsumed.setAccount(newAuthorization);
					List<Violations> violationsList = new ArrayList<Violations>();
					operationsConsumed.setViolations(violationsList); // setando as violacões, neste caso, Não há violacões.

					mapper.writeValue(new File("File/output/authorizationTransactions.json"), operationsConsumed);
		            //1 - no violations
					logger.info("Account with Card Active");
					logger.info("Account Authorizer success, no violations - File output json in File Folder path...");
					Runtime.getRuntime().exit(0);
				

				} else if (dataSave.getAccount().isActiveCard() == false) {
					newAuthorization.setFlowProcess(flowProcessAuthorizer.getValor());
					newAuthorization.setActiveCard(false);
					newAuthorization.setAvaliableLimit(dataSave.getAccount().getAvaliableLimit());
					newAuthorization.setTransactions(dataSave.getAccount().getTransactions());
					operationsConsumed.setAccount(newAuthorization);
					List<Violations> violationsList = new ArrayList<Violations>();
					Violations violations = new Violations();
					violations.setViolationType(violationsTypesCardNotActive.getValor());
					
					violationsList.add(violations); // DONE
				    
					operationsConsumed.setViolations(violationsList); // setando as violacões, neste caso, há uma violacao: card-no-active.

					mapper.writeValue(new File("File/output/authorizationTransactions.json"), operationsConsumed);
					 //2 - violation card-no-active
					logger.info("Account with Card Not Active");
					logger.info("Account Authorizer, with violations: card-not-active - File output json in File Folder path...");
					Runtime.getRuntime().exit(0);

				}

			}
		}

	}

	public static void authorizerTransactionLimitCard(Operations dataSave, boolean limitCardLow)
			throws JsonGenerationException, JsonMappingException, IOException {
		//method what verify the Limit Card, if is low or equals to zero.
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		OperationsConsumed operationsConsumed = new OperationsConsumed();
		Account newAuthorization = new Account();


			if (limitCardLow == true || dataSave.getAccount().getAvaliableLimit() == 0) {
				newAuthorization.setFlowProcess(flowProcessAuthorizer.getValor());
				newAuthorization.setActiveCard(true);
				newAuthorization.setAvaliableLimit(dataSave.getAccount().getAvaliableLimit());
				newAuthorization.setTransactions(null);
				operationsConsumed.setAccount(newAuthorization);
				List<Violations> violationsList = new ArrayList<Violations>();
				Violations violations = new Violations();
				violations.setViolationType(violationsTypesInsufficientLimit.getValor());
				violationsList.add(violations); // set violations, in this case, there are one: insufficient-limit.

				operationsConsumed.setViolations(violationsList);
				mapper.writeValue(new File("File/output/authorizationTransactions.json"), operationsConsumed);
				logger.info("Account with Limit Card insufficient");
				logger.info("Account Authorizer, with violations: insufficient-limit - File output json in File Folder path...");

			}
		

	}

	public static void authorizerTransactionDoubleTransaction(Operations dataSave) 
			throws JsonGenerationException, JsonMappingException, IOException {
		//method which verify the quantity of operations per time, if there are some duplicate.
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		OperationsConsumed operationsConsumed = new OperationsConsumed();
		Account newAuthorization = new Account();
		
		newAuthorization.setFlowProcess(flowProcessAuthorizer.getValor());
		newAuthorization.setActiveCard(true);
		newAuthorization.setAvaliableLimit(dataSave.getAccount().getAvaliableLimit());
		newAuthorization.setTransactions(null);
		operationsConsumed.setAccount(newAuthorization);
		List<Violations> violationsList = new ArrayList<Violations>();
		Violations violations = new Violations();
		violations.setViolationType(violationsTypesDoubleTransaction.getValor());
		violationsList.add(violations); //set violations, in this case, there are one: double-transaction.

		operationsConsumed.setViolations(violationsList);
		mapper.writeValue(new File("File/output/authorizationTransactions.json"), operationsConsumed);
		logger.info("Account with Transactions Duplicate");
		logger.info("Account Authorizer, with violations: double-transaction - File output json in File Folder path...");

	}
	
	public static void authorizerTransactionHighFrequencyInterval(Operations dataSave) 
			throws JsonGenerationException, JsonMappingException, IOException {
		//method which verify the quantity of operations per time, if there are some duplicate.
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		OperationsConsumed operationsConsumed = new OperationsConsumed();
		Account newAuthorization = new Account();
		
		newAuthorization.setFlowProcess(flowProcessAuthorizer.getValor());
		newAuthorization.setActiveCard(true);
		newAuthorization.setAvaliableLimit(dataSave.getAccount().getAvaliableLimit());
		newAuthorization.setTransactions(null);
		operationsConsumed.setAccount(newAuthorization);
		List<Violations> violationsList = new ArrayList<Violations>();
		Violations violations = new Violations();
		violations.setViolationType(violationsTypesHighFrequencyInterval.getValor());
		violationsList.add(violations); //set violations, in this case, there are one: highfrequency-small-interval.

		operationsConsumed.setViolations(violationsList);
		mapper.writeValue(new File("File/output/authorizationTransactions.json"), operationsConsumed);
		logger.info("Account with Transactions Duplicate");
		logger.info("Account Authorizer, with violations: highfrequency-small-interval - File output json in File Folder path...");

	}

 }
	
