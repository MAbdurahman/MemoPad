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
 * ReplaceAction Class
 */
public class ReplaceAction extends AbstractAction {
    MemoPad memoPad;
     /**
      * ReplaceAction Constructor -
      * @param ImageIcon
      */
     @SuppressWarnings("OverridableMethodCallInConstructor")
     public ReplaceAction(ImageIcon icon) {
         super("Replace", icon);
         setEnabled(false);

     }//end of the FindAndReplaceAction Constructor
     /**
      * actionPerformed Method -
      * @param ActionEvent 
      */
     @Override
     @SuppressWarnings("ResultOfObjectAllocationIgnored")
     public void actionPerformed(ActionEvent ae) {
         try {
             new FindAndReplace(null, true);

         } catch (Exception ex) {
             String message = ex.getMessage();
             JOptionPane.showMessageDialog(memoPad, message);

         }
         updateReplaceAction();

     }//end of the actionPerformed Method
     /**
      * updateReplaceAction Method -
      * @param Void
      */
     protected void updateReplaceAction() {
         if (MemoPad.TEXTPANE.getText().length() > 0) {
             setEnabled(true);
             putValue(Action.NAME, "Replace");

         } else {
             setEnabled(false);
             putValue(Action.NAME, "Replace");

     }
     }//end of the updateReplaceAction Method
 }//end of the FindAndReplaceAction Class