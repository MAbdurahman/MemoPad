package memo_pad;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
* FindAction Class -
*/
public class FindAction extends AbstractAction {
    MemoPad memoPad;
 /**
  * FindAction Constructor -
  * @param icon - an ImageIcon
  */
 @SuppressWarnings("OverridableMethodCallInConstructor")
 public FindAction(ImageIcon icon) {
     super("Find", icon);
     setEnabled(false);

 }//end of the FindAction Constructor
 /**
  * actionPerformed Method -
  * @param ae - the ActionEvent listener
  */
 @Override
 @SuppressWarnings("ResultOfObjectAllocationIgnored")
 public void actionPerformed(ActionEvent ae) {
     try {
         new FindAndReplace(null, false);

     } catch (Exception ex) {
         String message = ex.getMessage();
         JOptionPane.showMessageDialog(memoPad, message);

     }
     updateFindAction();

 }//end of the actionPerformed Method
 /**
  * updateFindAction Method
  */
 protected void updateFindAction() {
     if (MemoPad.TEXTPANE.getText().length() > 0) {
         setEnabled(true);
         putValue(Action.NAME, "Find");

     } else {
         setEnabled(false);
         putValue(Action.NAME, "Find");
     }

 }//end of the updateFindAction Method
}//end of the FindAction Class