/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memo_pad;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


/**
  * DeleteAction Class
  */
  public class DeleteAction extends AbstractAction {
      MemoPad memoPad;
      
     /**
      * DeleteAction Constructor -
      * @param ImageIcon -
      */
     @SuppressWarnings("OverridableMethodCallInConstructor")
     public DeleteAction(ImageIcon icon) {
         super("Delete", icon);
         setEnabled(false);

     }//end of the DeleteAction Constructor
     /**
      * actionPerformed Method -
      * @param ActionEvent 
      */
     @Override
     public void actionPerformed(ActionEvent ae) {
         try {
             MemoPad.TEXTPANE.setText("");

         } catch (Exception ex) {
             String message = ex.getMessage();
             JOptionPane.showMessageDialog(memoPad, message);

         }
         updateDeleteAction();

     }//end of the actionPerformed Method
     /**
      * updateDeleteAction Method -
      * @param Void
      */
     protected void updateDeleteAction() {
         if ((MemoPad.TEXTPANE.getSelectionEnd() - MemoPad.TEXTPANE.getSelectionStart()) > 0) {
             setEnabled(true);
             putValue(Action.NAME, "Delete");

         } else {
             setEnabled(false);
             putValue(Action.NAME, "Delete");
         }

     }//end of the updateDeleteAction Method
 }//end of the DeleteAction Class