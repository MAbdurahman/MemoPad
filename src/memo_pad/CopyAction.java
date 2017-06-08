package memo_pad;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

 /**
  * CopyAction Class
 */
 public class CopyAction extends AbstractAction {
     MemoPad memoPad;
     /**
      * CopyAction Constructor -
      * @param ImageIcon
      */
     @SuppressWarnings("OverridableMethodCallInConstructor")
     public CopyAction(ImageIcon icon) {
         super("Copy", icon);
         setEnabled(false);

     }//end of the CopyAction Constructor
     /**
      * actionPerformed Method - 
      * @param ActionEvent -
      */
     @Override
     public void actionPerformed(ActionEvent ae) {
         try {
             MemoPad.TEXTPANE.copy();

         } catch (Exception ex) {
             String message = ex.getMessage();
             JOptionPane.showMessageDialog(memoPad, message);

         }
         updateCopyAction();

     }//end of the actionPerformed Method
     /**
      * updateCopyAction Method - 
      * @param Void
      */
     public void updateCopyAction() {
         if ((MemoPad.TEXTPANE.getSelectionEnd() - MemoPad.TEXTPANE.getSelectionStart()) > 0) {
             setEnabled(true);
             putValue(Action.NAME, "Copy");

         } else {
             setEnabled(false);
             putValue(Action.NAME, "Copy");
         }
     }//end of the updateCopyAction Method
 }//end of the CopyAction Class