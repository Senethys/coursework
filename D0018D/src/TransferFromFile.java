package svimag6;

import java.io.IOException;
import java.io.ObjectInputStream;
public class TransferFromFile extends ObjectInputStream {
  
  Object object = new Object; 
  
  public TransferFromFile(Object o) {
    this.object = o;
    
  }
}