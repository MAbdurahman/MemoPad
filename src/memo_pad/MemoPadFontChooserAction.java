package memo_pad;

import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;

/**
 * The MemoPadFontChooserAction Class
 */
public class MemoPadFontChooserAction extends StyledEditorKit.StyledTextAction {
    /** Instance Variables */
    MemoPadFontChooser fontChooser;
    MemoPad memoPad;

    /**
     * MemoPadFontChooserAction Constructor -
     */
    public MemoPadFontChooserAction() {
        super(StyleConstants.FontFamily.toString());

    }//end of the MemoPadFontChooserAction Constructor
    /**
     * actionPerformed Method - 
     * @param ActionEvent -
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        JTextPane textPaneEditor = (JTextPane) getEditor(ae);
        if (textPaneEditor == null) {
            String message = "Select the TextPad before changing the font.";
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR);
            return;
        }
        fontChooser = new MemoPadFontChooser();
        int option = fontChooser.showDialog(memoPad);
        if (option == MemoPadFontChooser.APPROVE_OPTION) {

            Font font = fontChooser.getSelectedFont();
            StyledEditorKit editorKit = getStyledEditorKit(textPaneEditor);
            MutableAttributeSet mutableAttributes = editorKit.getInputAttributes();

            StyleConstants.setFontFamily(mutableAttributes, font.getFamily());
            StyleConstants.setFontSize(mutableAttributes, font.getSize());
            boolean isSubscripted = (StyleConstants.isSubscript(mutableAttributes)) ? false : true;
            boolean isSuperscripted = (StyleConstants.isSuperscript(mutableAttributes)) ? false : true;
            boolean isStrikeThroughed = (StyleConstants.isStrikeThrough(mutableAttributes)) ? false : true;

            SimpleAttributeSet simpleAttributes = new SimpleAttributeSet();
            StyleConstants.setSubscript(simpleAttributes, isSubscripted);
            StyleConstants.setSuperscript(simpleAttributes, isSuperscripted);
            StyleConstants.setStrikeThrough(simpleAttributes, isStrikeThroughed);

            int startingPoint = textPaneEditor.getSelectionStart();
            int endingPoint = textPaneEditor.getSelectionEnd();

            StyledDocument document = getStyledDocument(textPaneEditor);
            document.setCharacterAttributes(startingPoint, endingPoint, mutableAttributes, false);
            //Element characterElement = document.getCharacterElement(startingPoint);
            //AttributeSet attributes = characterElement.getAttributes

        }
    }//end of the actionPerformed Method
}//end of the MemoPadFontAction Class