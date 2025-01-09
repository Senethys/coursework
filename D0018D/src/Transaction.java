package svimag6;

import java.io.Serializable;

/**
 * Sparar data om en trasaktion, som sedan behålls in en ArrayList.
 * 
 * @author Svitri Magnusson, svimag-6
 */

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction implements Serializable {

  private static final long serialVersionUID = 21L;
  private double            balance          = 0.0;
  private String            pattern          = "yyyy-MM-dd HH:mm:ss";
  private double            amount;
  private String            TransactionDetails;

  public Transaction(double amount, double balance) {
    this.balance = balance;
    this.amount = amount;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String TransactionDetails = simpleDateFormat.format(new Date());
    this.TransactionDetails = TransactionDetails + " " + this.amount + " " + this.balance;

  }

  /**
   * Returnen fältet med transationens datum, pengar och saldo.
   *
   * @param void
   * @return String
   */

  public String getTransacionDetails() {
    return this.TransactionDetails;
  }

}
