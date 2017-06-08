
package memo_pad;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static memo_pad.MemoPad.TEXTPANE;


    /**
     * SelectAllAction Class
     */
     public class SelectAllAction extends AbstractAction {
         
        MemoPad memoPad;
       /**
        * SelectionAllAction Constructor -
        */  
         @SuppressWarnings("OverridableMethodCallInConstructor")
         public SelectAllAction(ImageIcon icon) {
             super("Select All", icon);
             setEnabled(false);
             
         }//end of the SelectionAllAction Constructor
         /**
          * actionPerformed Method -
          * @param ActionEvent -
          */
         @Override
         public void actionPerformed(ActionEvent ae) {
             try {
                 TEXTPANE.selectAll();
                 if ((TEXTPANE.getSelectionEnd() - TEXTPANE.getSelectionStart()) > 0) {
                     memoPad.cutItem.setEnabled(true);
                     memoPad.copyItem.setEnabled(true);
                     memoPad.deleteItem.setEnabled(true);
                     
                 } else {
                     memoPad.cutItem.setEnabled(false);
                     memoPad.copyItem.setEnabled(false);
                     memoPad.deleteItem.setEnabled(false);
                 }
                 
             } catch (Exception ex) {
                 String message = ex.getMessage();
                 JOptionPane.showMessageDialog(memoPad, message);
             }
             updateSelectAllAction();
         }//end actionPerformed Method
         /**
          * updateSelectAllAction Method -
          * @param Void
          */
         protected void updateSelectAllAction() {
             if ((TEXTPANE.getText().length() == 0) || ((TEXTPANE.getCaretPosition() == 0))) {
                 setEnabled(false);
                 putValue(Action.NAME, "SelectAll");
                 
             } else {
                 setEnabled(true);
                 putValue(Action.NAME, "Select All");
                 
             }
             
         }//end of the updateSelectAllAction Method
     }//end of the SelectAllAction Class