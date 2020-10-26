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
* The CutAction Class 
*/
 public class CutAction extends AbstractAction {
     MemoPad memoPad;
     /**
      * CutAction Constructor -
      * @param icon - the ImageIcon
      */
     @SuppressWarnings("OverridableMethodCallInConstructor")
     public CutAction(ImageIcon icon) {
         super("Cut", icon);
         setEnabled(false);

     }//end of the CutAction Constructor
         /**
          * actionPerformed Method -
          * @param ae - the ActionEvent
          */
         @Override
         public void actionPerformed(ActionEvent ae) {
         try {
             MemoPad.TEXTPANE.cut();

         } catch (Exception ex) {
             String message = ex.getMessage();
             JOptionPane.showMessageDialog(memoPad, message);

         }
         updateCutAction();

     }//end of the actionPerformed Method
     /**
      * updateCutAction Method -
      */
     public void updateCutAction() {
         if ((MemoPad.TEXTPANE.getSelectionEnd() - MemoPad.TEXTPANE.getSelectionStart()) > 0) {
             setEnabled(true);
             putValue(Action.NAME, "Cut");

         } else {
             setEnabled(false);
             putValue(Action.NAME, "Cut");
         }
     }//end of the updateCutAction Method
 }//end of the CutAction Class