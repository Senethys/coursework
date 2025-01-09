package svimag6;

import java.io.Serializable;

/**
 * Denna klass tillhör en kund. Har sin egen ränta. Får inte bli mindre än 0.
 * 
 * @author Svitri Magnusson, svimag-6
 */

public class SavingsAccount extends Account implements Serializable {

  private static final long serialVersionUID = 41L;
  private String            type             = "Sparkonto";
  private boolean           usedFreeWithdraw = false;
  private double            interestRate     = 1.0;

  public SavingsAccount() {
  }

  /**
   * Lägger till amount i kontots saldo.
   *
   * @param double
   *          amount
   * @return void
   */
  @Override
  public void deposit(double amount) {

    this.balance += amount;
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
    double difference = 0.0;
    double withdrawFee = 0.0;
    double withdrawAmount = 0.0;

    if (!this.usedFreeWithdraw) {
      difference = balance - amount;
    }

    else if (this.usedFreeWithdraw) {
      withdrawFee = amount * 0.02;
      difference = balance - (amount + withdrawFee);
    }

    withdrawAmount = amount + withdrawFee;

    if (!(difference < 0.0)) {
      this.balance = balance - withdrawAmount;
      transaction = new Transaction(amount * -1, this.balance);
      TransactionList.add(transaction);
      result = true;
      if (this.usedFreeWithdraw == false) {
        this.usedFreeWithdraw = true;
      }
    }
    return result;
  }

  /**
   * Returnerar basal information om kontot.
   *
   * @param void
   * @return String
   */
  @Override
  public String getAccountInfo() {
    return accountNumber + " " + balance + ' ' + type + ' ' + interestRate;
  }

  /**
   * Byter typen på kontot. Har ingen funktionell funktion.
   * 
   * @param String
   *          newType
   * @return void
   */
  @Override
  public void changeAccountType(String newType) {
    this.type = newType;
  }

  /**
   * Beräknar kontots ränta.
   *
   * @param void
   * @return double
   */

  @Override
  public double calculateInterest() {
    return (this.balance * this.interestRate / 100.0);
  }
}