/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memo_pad;

import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;

/**
 * The SubscriptAction Class
 */
 public class SubscriptAction extends StyledEditorKit.StyledTextAction {
    /**
     * SubscriptAction Constructor -
     * @param ImageIcon -
     */
    public SubscriptAction() {
        super(StyleConstants.Subscript.toString());

    }//end of the SubscriptAction Constructor
    /**
     * actionPerformed Method - 
     * @param ActionEvent 
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        JTextPane textPaneEditor = (JTextPane) getEditor(ae);
        if (textPaneEditor != null) {
            StyledEditorKit editorKit = getStyledEditorKit(textPaneEditor);
            MutableAttributeSet mutableAttributes = editorKit.getInputAttributes();
            boolean isSubscripted = (StyleConstants.isSubscript(mutableAttributes)) ? false : true;
            SimpleAttributeSet simpleAttributes = new SimpleAttributeSet();
            StyleConstants.setSubscript(simpleAttributes, isSubscripted);
            setCharacterAttributes(textPaneEditor, simpleAttributes, false);

        }
    }//end of the actionPerformed Method
}//end of the SubscriptAction Class