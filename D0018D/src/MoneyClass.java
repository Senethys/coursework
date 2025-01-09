package svimag6;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * En liten klass som skapar ett nytt fönster för att skriva in summa pengar som
 * ska föras över.
 * 
 * @author svimag-6
 */

public class MoneyClass extends JFrame implements ActionListener {

  private static final long serialVersionUID = 16L;
  private Account           account;
  private JPanel            moneyPanel;
  private JTextField        moneyField;
  private JButton           withdrawButton   = new JButton("Withdraw");
  private JButton           depositButton    = new JButton("Deposit");
  protected double          withdrawAmount   = 0;
  protected double          depositAmount    = 0;
  DefaultTableModel         transactionModel;
  DefaultTableModel         accountModel;

  /**
   * Konstruktor som tar emot konto för att kunna manipulera hur mycket pengar som
   * för över eller från kontot.
   * 
   * @param Account,
   *          DefaultTableModel, DefaultTableModel
   * @return void
   */
  public MoneyClass(Account account, DefaultTableModel transactionModel, DefaultTableModel accountModel) {

    this.accountModel = accountModel;
    this.transactionModel = transactionModel;
    this.account = account;
    initiateVariables();

  }

  /**
   * Instansierar variabler för fönstret och definierar typ av layout.
   * 
   * @return void
   */
  public void initiateVariables() {
    setTitle("Money to transfer");
    setSize(300, 300);
    setLayout(new GridLayout(3, 1));
    moneyPanel = new JPanel(new GridLayout(1, 1));
    moneyField = new JTextField();
    moneyPanel.add(moneyField);
    moneyField.setBorder(BorderFactory.createTitledBorder("Amount to Transfer"));
    withdrawButton.addActionListener(this);
    depositButton.addActionListener(this);

    add(moneyPanel);
    add(withdrawButton);
    add(depositButton);
  }

  /**
   * Aktiveras när användaren väljer att föra över pengar. Allt sparas i samma
   * variabel men används beroende på vilken knapp användaren klickar.
   * 
   * @param ActionEvent
   * @return void
   */
  @Override
  public void actionPerformed(ActionEvent event) {
    String buttonText = event.getActionCommand();
    String amount;

    if (buttonText.equals("Withdraw")) {
      // Rensa transaktioner
      transactionModel.setRowCount(0);
      // Hämta talet från input fältet.
      amount = moneyField.getText();
      // Omvandla till int.
      try {
        withdrawAmount = Double.parseDouble(amount);
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Invalid amount.");

      }
      // Spara i objektet.
      if (withdrawAmount != 0) {
        account.withdraw(withdrawAmount);
      }
      this.setVisible(false);
      // Skriver in de nya förändringarna.
      updateTransactionTables();
      // Stänger av fönstret.
      dispose();
    }

    if (buttonText.equals("Deposit")) {
      transactionModel.setRowCount(0);
      amount = moneyField.getText();
      try {

        if (Integer.parseInt(amount) <= 0) {
          JOptionPane.showMessageDialog(null, "You can't deposit 0 or a negative amount");
        } else {

          depositAmount = Double.parseDouble(amount);
          account.deposit(depositAmount);
          this.setVisible(false);
        }
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Invalid amount.");
      }
      updateTransactionTables();
      dispose();
    }
  }

  /**
   * Uppdaterar transaktionsListorna efter att någon förändring hände.
   * 
   * @return void
   */
  public void updateTransactionTables() {
    String[] transactionDetails;
    ArrayList<Transaction> transactions = account.getAccountTransactions();
    for (Transaction t : transactions) {
      transactionDetails = t.getTransacionDetails().split(" ");
      System.out.println(transactionDetails);
      transactionModel.addRow(
          new String[] { transactionDetails[0], transactionDetails[1], transactionDetails[2], transactionDetails[3] });
      System.out.println(transactionDetails);
    }

  }
}