package svimag6;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Ansvarar för kundens data och spara alla kundens konton i en ArrayList.
 * 
 * @author Svitri Magnusson, svimag-6
 */

public class Customer implements Serializable {

  private static final long  serialVersionUID = 11L;
  private String             name;
  private String             lastname;
  private String             ssn;
  private ArrayList<Account> Accounts         = new ArrayList<Account>();

  // En kund måste allitd konstrueras med namn, efternamn och personnummer.
  public Customer(String fname, String lname, String sNumber) {
    this.name = fname;
    this.lastname = lname;
    this.ssn = sNumber;
  }

  /**
   * Leter efter existerande kunder efter matchad accountId och returnerar.
   * returnerar null om inget hittades.
   * 
   * @param int
   *          accountId
   * @return SavingsAccount
   */
  public Account matchAccount(int accountId) {
    Account matchedAccount = null;

    for (int i = 0; Accounts.size() > i; i++) {
      if (Accounts.get(i).getAccountNumber() == accountId) {
        matchedAccount = Accounts.get(i);
      }
    }

    return matchedAccount;
  }

  /**
   * Byter namn på angiven kund.
   * 
   * @param String
   *          newNameme, newLastname
   * @return void
   */
  public void changeName(String newName, String newLastname) {
    this.name = newName;
    this.lastname = newLastname;
    System.out.println(this.name + " " + this.lastname);
  }

  /**
   * Skapar ett nytt konto objekt och spara den i kundobjektets ArrayList.
   *
   * @param void
   * @return int
   */

  public int addSavingsAccount() {
    System.out.println("Adding new savings account...");
    SavingsAccount account = new SavingsAccount();
    Accounts.add(account);
    return account.getAccountNumber();
  }

  /**
   * Skapar ett nytt konto objekt och spara den i kundobjektets ArrayList.
   *
   * @param void
   * @return int
   */

  public int addCreditAccount() {
    System.out.println("Adding new credit account...");
    CreditAccount account = new CreditAccount();
    Accounts.add(account);
    return account.getAccountNumber();
  }

  /**
   * Returnerar all info om kunden inclusive alla konton utan beräknad ränta.
   *
   * @param void
   * @return ArrayList<String>
   */

  public ArrayList<String> getAllCustomerAccountInfo() {

    ArrayList<String> results = new ArrayList<String>();
    String AccountInfo;

    for (int i = 0; Accounts.size() > i; i++) {
      AccountInfo = Accounts.get(i).getAccountInfo();
      results.add(AccountInfo);
    }

    return results;
  }

  /**
   * Returnerar all info om kundens konton inclusive alla konton med ränta
   * beräknad.
   *
   * @param void
   * @return Array<String>
   */
  public ArrayList<String> getFullCustomerAccountInfo() {

    ArrayList<String> results = new ArrayList<String>();
    String AccountInfo;

    for (int i = 0; Accounts.size() > i; i++) {
      AccountInfo = Accounts.get(i).getAccountInfo() + " " + Accounts.get(i).calculateInterest();
      results.add(AccountInfo);
    }

    return results;
  }

  /**
   * Returnerar info om ett konto utan beräknad ränta.
   *
   * @param int
   *          accountId
   * @return String
   */

  public String getCustomerAccountInfo(int accountID) {

    String AccountInfo = null;

    for (int i = 0; Accounts.size() > i; i++) {
      if (accountID == Accounts.get(i).getAccountNumber()) {
        AccountInfo = Accounts.get(i).getAccountInfo();
      }
    }
    return AccountInfo;
  }

  /**
   * Stänger av kundens konto med angivent kontonr. returnerar true/false beroende
   * på utfallet.
   * 
   * @param Int
   *          accountId
   * @return boolean
   */
  public boolean closeAccount(int accountId) {
    boolean result;
    try {
      Account Acc = matchAccount(accountId);
      Accounts.remove(Acc);
      result = true;
    } catch (IndexOutOfBoundsException e) {
      System.err.println("ERROR!");
      System.err.println(e);
      result = false;
    }

    return result;
  }

  /**
   * Returnerar kundens personnummer.
   *
   * @param void
   * @return String
   */
  public String getCustomerpNr() {
    return ssn;
  }

  /**
   * Returnerar kundens namn.
   *
   * @param void
   * @return String
   */
  public String getCustomerName() {
    return name;
  }

  /**
   * Returnerar personens efternamn
   *
   * @param void
   * @return String
   */
  public String getCustomerLastname() {
    return lastname;
  }

  /**
   * Returnerar all information om kunden, exclusive kontoinfo.
   *
   * @param void
   * @return String
   */
  public String getCustomerInfo() {
    String result;
    result = name + ' ' + lastname + ' ' + ssn;
    return result;
  }

  /**
   * Returnerar om kunden har ett eller flera konton. Används för GUI.
   *
   * @param void
   * @return boolean
   */
  public boolean hasOneOrMoreAccounts() {
    return !this.Accounts.isEmpty();
  }

}