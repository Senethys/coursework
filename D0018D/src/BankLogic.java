package svimag6;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Huvudclassen som innehåller alla klasser, Customer och Accountyper.
 * 
 * @author Svitri Magnusson, svimag-6
 */

public class BankLogic implements Serializable {

  private static final long   serialVersionUID = 231L;
  private ArrayList<Customer> CustomerList     = new ArrayList<Customer>();

  public BankLogic() {
  }

  /**
   * Letar bland kundlistan där pNr matchar objektets och returnerar den.
   * 
   * @param Personnummer.
   * @return Customer object.
   */
  public Customer matchCustomer(String pNr) {

    String matchedpNr;
    Customer MatchedCustomerObject = null;

    for (int i = 0; i < CustomerList.size(); i++) {
      matchedpNr = CustomerList.get(i).getCustomerpNr();
      {
        if (matchedpNr.equals(pNr)) {
          MatchedCustomerObject = CustomerList.get(i);
        }
      }
    }
    return MatchedCustomerObject;
  }

  /**
   * Returnerar en ArrayList<String> som innehåller en presentation av bankens
   * alla kunder.
   * 
   * @param void
   * @return void
   */
  public ArrayList<String> getAllCustomers() {

    ArrayList<String> results = new ArrayList<String>();
    String CustomerInfo;

    for (int i = 0; CustomerList.size() > i; i++) {
      CustomerInfo = CustomerList.get(i).getCustomerInfo();
      results.add(CustomerInfo);
    }
    return results;
  }

  /**
   * Returnerar antalet kunder på banken. alla kunder.
   * 
   * @param void
   * @return int
   */
  public int getAmountOfCustomers() {

    return this.CustomerList.size();
  }

  /**
   * Skapar ett Customer objekt och lägger den i en ArrayList
   * 
   * @param String
   *          name, surname, pNr
   * @return boolean
   */
  public boolean createCustomer(String name, String surname, String pNr) {

    boolean result = false;
    boolean exists = false;
    // If customers exists
    if (CustomerList.size() != 0) {
      // Scan after matching pNr
      for (int i = 0; i < CustomerList.size(); i++) {
        if (pNr.equals(CustomerList.get(i).getCustomerpNr())) {
          exists = true;
          break;
        }
      }
      if (!exists) {
        Customer NewCustomer = new Customer(name, surname, pNr);
        CustomerList.add(NewCustomer);
        result = true;
      }
    } else {
      Customer NewCustomer = new Customer(name, surname, pNr);
      CustomerList.add(NewCustomer);
      result = true;
    }
    return result;
  }

  /**
   * Lägger till ett customer objekt till ArrayList. Denna metod används för
   * serialisring.
   * 
   * @param Customer
   * @return boolean
   */
  public boolean addExistingCustomer(Customer customer) {

    boolean result = false;
    boolean exists = false;
    // If customers exists
    if (CustomerList.size() != 0) {
      // Scan after matching pNr
      for (int i = 0; i < CustomerList.size(); i++) {
        if (customer.getCustomerpNr().equals(CustomerList.get(i).getCustomerpNr())) {
          exists = true;
          break;
        }
      }
      if (!exists) {
        CustomerList.add(customer);
        result = true;
      }
    } else {
      CustomerList.add(customer);
      result = true;
    }
    return result;
  }

  /**
   * Returnerar en ArrayList<String> som innehåller informationen om kunden
   * inklusive dennes konton.
   * 
   * @param String
   *          pNr
   * @return ArrayList<String>
   */
  public ArrayList<String> getCustomer(String pNr) {

    ArrayList<String> results = new ArrayList<String>();
    String CustomerInfo;
    Customer MatchedCustomerObject;

    try {
      MatchedCustomerObject = matchCustomer(pNr);
      CustomerInfo = MatchedCustomerObject.getCustomerInfo();
      results.add(CustomerInfo);
      results.addAll(MatchedCustomerObject.getAllCustomerAccountInfo());
    } catch (NullPointerException e) {
      results = null;
    }

    return results;
  }

  /**
   * Byter namn på existerande kund.
   * 
   * @param String
   *          name, surname, pNr
   * @return void
   */
  public boolean changeCustomerName(String name, String surname, String pNr) {
    Customer customer;
    boolean result = false;

    try {
      customer = matchCustomer(pNr);
      customer.changeName(name, surname);
      result = true;
    } catch (NullPointerException e) {
      System.out.println("This person does not exist.");
      result = false;
    }
    return result;

  }

  /**
   * Tar bort och alla dess konton från banken. Allt som togs bort returneras som
   * strängar i ArrayList.
   * 
   * @param String
   *          pNr
   * @return ArrayList<String>
   */
  public ArrayList<String> deleteCustomer(String pNr) {
    Customer MatchedCustomerObject;
    ArrayList<String> results = new ArrayList<String>();
    String customerInfo;

    try {
      MatchedCustomerObject = matchCustomer(pNr);
      customerInfo = MatchedCustomerObject.getCustomerInfo();
      results.add(customerInfo);

      results.addAll(MatchedCustomerObject.getFullCustomerAccountInfo());
      CustomerList.remove(MatchedCustomerObject);
    } catch (NullPointerException e) {
      results = null;
    }

    return results;

  }

  /**
   * Skapar ett unikt konto till kund med personnummer pNr. Returnerar -1 om den
   * inte skapades.
   * 
   * @param String
   *          pNr
   * @return int
   */
  public int createSavingsAccount(String pNr) {
    Customer MatchedCustomerObject;
    int accountNumber;

    try {
      MatchedCustomerObject = matchCustomer(pNr);
      accountNumber = MatchedCustomerObject.addSavingsAccount();
    } catch (NullPointerException e) {
      return -1;
    }
    return accountNumber;
  }

  /**
   * Skapar ett unikt kreditkonto till kund med personnummer pNr. Returnerar -1 om
   * den inte skapades.
   * 
   * @param String
   *          pNr
   * @return int
   */
  public int createCreditAccount(String pNr) {
    Customer MatchedCustomerObject;
    int accountNumber;

    try {
      MatchedCustomerObject = matchCustomer(pNr);
      accountNumber = MatchedCustomerObject.addCreditAccount();
    } catch (NullPointerException e) {
      return -1;
    }
    return accountNumber;
  }

  /**
   * Returnerar en String som innehåller presentation av kontot med kontonnummer
   * accountId som tillhör kunden pNr
   * 
   * @param String
   *          pNr, int account Id
   * @return String
   */
  public String getAccount(String pNr, int accountId) {
    String result;
    Customer customer;

    try {
      customer = matchCustomer(pNr);
      result = customer.getCustomerAccountInfo(accountId);
    } catch (NullPointerException e) {
      result = null;
    }
    return result;

  }

  /**
   * Hämtar en lista som innehåller presentation av konto samt alla transaktioner
   * som gjorts på kontot, ex: [2017-01-30 09:17:06 -500.0 -500.0, 2017-01-30
   * 09:17:11 -4000.0 -4500.0]
   * 
   * @param String
   *          pNr, int accountId
   * @return ArrayList<String>
   */
  public ArrayList<String> getTransactions(String pNr, int accountId) {
    ArrayList<String> results = new ArrayList<String>();
    ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    Account account;
    Customer customer;

    try {
      customer = matchCustomer(pNr);
      account = customer.matchAccount(accountId);
      transactions = account.getAccountTransactions();

      for (Transaction transaction : transactions) {
        results.add(transaction.getTransacionDetails());
      }

    } catch (NullPointerException e) {
      results = null;
    }
    return results;
  }

  /**
   * Gör en insättning på konto med kontonnummer accountId som tillhör kunden pNr.
   * Returnerar true om det gick bra annars false
   * 
   * @param String
   *          pNr, int accountId, double amount.
   * @return boolean
   */
  public boolean deposit(String pNr, int accountId, double amount) {
    Account account;
    Customer customer;
    boolean result = false;

    try {
      customer = matchCustomer(pNr);
      account = customer.matchAccount(accountId);
      account.deposit(amount);
      result = true;
    } catch (NullPointerException e) {
      result = false;
    }

    return result;

  }

  /**
   * Gör ett uttag på konto med kontonnummer accountId som tillhör kunden pNr.
   * Returnerar true om det gick bra annars false
   * 
   * @param String
   *          pNr, int accountId, double amount
   * @return boolean
   */
  public boolean withdraw(String pNr, int accountId, double amount) {
    Account account;
    Customer customer;
    boolean result;

    try {
      customer = matchCustomer(pNr);
      account = customer.matchAccount(accountId);
      result = account.withdraw(amount);
    } catch (NullPointerException e) {
      result = false;
    }
    return result;

  }

  /**
   * Avslutar ett konto med kontonnummer accountId som tillhör kunden pNr.
   * Returnerar null om inget konto togs bort
   * 
   * @param String
   *          pNr, int accountId
   * @return String
   */
  public String closeAccount(String pNr, int accountId) {
    Customer customer;
    Account account;
    String result;

    try {
      customer = matchCustomer(pNr);
      account = customer.matchAccount(accountId);
      result = customer.getCustomerAccountInfo(accountId);
      result += " " + account.calculateInterest();
      customer.closeAccount(accountId);
    } catch (NullPointerException e) {
      result = null;
    }
    return result;
  }

  public String getNameForPersonAt(int position) {
    return (CustomerList.get(position)).getCustomerName();
  }

  public String getLastNameForPersonAt(int position) {
    return (CustomerList.get(position)).getCustomerLastname();
  }

  public String getpNrAt(int position) {
    return (CustomerList.get(position)).getCustomerpNr();
  }
}
