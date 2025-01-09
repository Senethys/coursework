package svimag6;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Denna abstracta klass har grunderna för olika kontotyper. Den har en eller
 * flera Transaction klasser.
 * 
 * @author Svitri Magnusson, svimag-6
 */

public abstract class Account implements Serializable {

  private static final long        serialVersionUID  = 1L;
  protected double                 balance           = 0.0;
  protected ArrayList<Transaction> TransactionList   = new ArrayList<Transaction>();
  protected Transaction            transaction;
  protected int                    accountNumber;
  protected static int             lastAccountNumber = 1001;

  public Account() {
    this.accountNumber = lastAccountNumber++;
  }

  /**
   * Lägger till amount i kontots saldo.
   *
   * @param double
   *          amount
   * @return void
   */
  public abstract void deposit(double amount);

  /**
   * Tar bort amount från kontot. Man kan inte ta bort mer än saldo. returnerar
   * true om uttaget gick igenom.
   * 
   * @param double
   *          amount
   * @return boolean.
   */
  public abstract boolean withdraw(double amount);

  /**
   * Returnerar kontots nummer
   *
   * @param void
   * @return String
   */
  public int getAccountNumber() {
    return this.accountNumber;
  };

  /**
   * Returnerar all information om kontot förutom räntan.
   *
   * @param void
   * @return String
   */
  public abstract String getAccountInfo();

  /**
   * Returnerar all information om kontots transaktioner i en ArrayList.
   *
   * @param void
   * @return ArrayList
   */
  public ArrayList<Transaction> getAccountTransactions() {
    return TransactionList;

  }

  /**
   * Beräknar kontots ränta.
   *
   * @param void
   * @return double
   */
  public abstract double calculateInterest();

  /**
   * Byter typen på kontot. Har ingen funktionell funktion.
   * 
   * @param String
   *          newType
   * @return void
   */
  public abstract void changeAccountType(String newType);

  /**
   * Returnerar senaste account nummer.
   * 
   * @return int
   */
  public static int getlastAccountNumber() {
    return lastAccountNumber;
  }

  /**
   * Sätter vars ifrån nästa account nummer ska starta.
   * 
   * @param int
   * @return void
   */
  public static void setlastAccountNumber(int startFromAccountNumber) {
    lastAccountNumber = startFromAccountNumber;
  }
}