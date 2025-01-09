package svimag6;

import java.io.Serializable;

/**
 * Denna klass tillhör en kund. Har sin egen ränta. Får inte bli mindre än
 * -5000;
 * 
 * @author Svitri Magnusson, svimag-6
 */

public class CreditAccount extends Account implements Serializable {

  private static final long serialVersionUID = 15L;
  private String            type             = "Kreditkonto";
  private double            creditLimit      = -5000.0;
  private double            interestRate     = 0.5;

  public CreditAccount() {
  }

  /**
   * Efterssom det inte går att att lägga pengar i kredit konton så tas funktionen
   * bort.
   *
   * @param double
   *          amount
   * @return void
   */
  @Override
  public void deposit(double amount) {
    this.balance += amount;
    this.interestRate = (this.balance > 0.0) ? 0.5 : 7.0;
    transaction = new Transaction(amount, this.balance);
    TransactionList.add(transaction);

  }

  /**
   * Tar bort amount från kontot. Man kan inte ta bort mer än saldo. returnerar
   * true om uttaget gick igenom.
   * 
   * @param double
   *          amount
   * @return boolean.
   */
  @Override
  public boolean withdraw(double amount) {
    boolean result = false;
    if (creditLimit < (this.balance - Math.abs(amount))) {
      this.balance = this.balance - Math.abs(amount);
      result = true;
      this.interestRate = (this.balance > 0.0) ? 0.5 : 7.0;
      transaction = new Transaction(amount * -1, this.balance);
      TransactionList.add(transaction);
    }

    else if (balance < creditLimit) {
      System.out.println("Credit card will overdrawn.");

    }
    return result;
  }

  /**
   * Beräknar kontots ränta.
   *
   * @param void
   * @return double
   */
  public double calculateInterest() {
    this.interestRate = (this.balance > 0.0) ? 0.5 : 7.0;
    return (this.balance * this.interestRate / 100.0);

  }

  /**
   * Återger basal data om kontot.
   *
   * @param void
   * @return String
   */

  public String getAccountInfo() {
    return accountNumber + " " + balance + ' ' + type + ' ' + interestRate;
  }

  /**
   * Byter namnet på kontotypen. Påverkar inte kontofunktionen.
   *
   * @param String
   * @return void
   */
  @Override
  public void changeAccountType(String newType) {
    this.type = newType;

  }

}
