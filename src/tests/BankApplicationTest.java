package tests;

import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Admin on 27.03.2014.
 */
public class BankApplicationTest {
	Client client1;
	Client client2;
	Client client3;

	@Before
	public void setUp() throws Exception {
		client1 = new Client(500);
		client1.setName("Ivanov");
		client1.setGender(Gender.MALE);
		client1.getAccounts().add(new SavingAccount(500));
		client1.getAccounts().add(new CheckingAccount(1000));
		((CheckingAccount) client1.getAccounts().get(1)).setOverdraft(300f);

		client2 = new Client(500);
		client2.setName("Ivanov");
		client2.setGender(Gender.MALE);
		client2.getAccounts().add(new SavingAccount(500));
		client2.getAccounts().add(new CheckingAccount(1000));
		((CheckingAccount) client2.getAccounts().get(1)).setOverdraft(300f);

		client3 = new Client(500);
		client3.setName("Ivanov");
		client3.setGender(Gender.FEMALE);
		client3.getAccounts().add(new SavingAccount(600));
		client3.getAccounts().add(new CheckingAccount(1000));
		((CheckingAccount) client3.getAccounts().get(1)).setOverdraft(100f);

	}

	@Test
	public void testAccountTypesEnum() {
		AccountType accType1 = AccountType.valueOf("c");
		AccountType accType2 = AccountType.valueOf("Saving Account");

		System.out.println(accType1.fullName);
		System.out.println(accType2.shortName);


	}
}
