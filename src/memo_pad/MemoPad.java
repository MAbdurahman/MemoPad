package memo_pad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 * The MemoPad Class
 * @author:  MAbdurrahman
 * @date:  14 December 2016
 * @version:  1.0.1
 */
public class MemoPad extends JFrame {
    /** Instance Variables */
    private StyledDocument styledDocument;
    public static JTextPane TEXTPANE;
    private File currentFile;
    private PrinterJob printJob;
    private PageFormat pageFormat;
    private final Font menuFont = new Font("Bookman Old Style", Font.BOLD, 14);
    private final Font menuItemFont = new Font("Bookman Old Style", Font.PLAIN, 14);

    /** JMenuBar attributes for the textArea */
    private JMenuBar menuBar;

    /** Menus for the MenuBar */
    private JMenu fileMenu, editMenu, viewMenu;
    private JMenu formatMenu, helpMenu;

    /** MenuItems for the fileMenu */
    private static JMenuItem newItem, openItem, saveItem, saveAsItem;
    private JMenuItem pageSetupItem, printItem, previewPrintItem;
    private JMenuItem exitItem;
    private JMenu printerMenu;

    /** MenuItems for the editMenu */
    protected JMenuItem undoItem, redoItem, cutItem, copyItem;
    protected JMenuItem deleteItem, pasteItem, findItem;
    protected JMenuItem  replaceItem, selectAllItem, timeDateItem;

    /** MenuItems for the viewMenu */
    protected JCheckBoxMenuItem rulerCheckboxItem;
    protected JCheckBoxMenuItem statusBarCheckboxItem;
    protected JCheckBoxMenuItem wordWrapCheckboxItem;

    /** MenuItems for the formatMenu */
    private JMenu fontMenu, colorMenu, alignMenu;
    private JMenuItem fontItem, boldItem, italicItem, underlineItem;
    private JMenuItem subscriptItem, superscriptItem, strikeThroughItem;

    private JMenuItem colorBlackItem, colorBlueItem, colorRedItem;
    private JMenuItem moreColorsItem;

    private JMenuItem alignCenterItem, alignJustifyItem, alignLeftItem;
    private JMenuItem alignRightItem;

    /** MenuItem for the helpMenu */
    protected JMenuItem helpItem;
    protected JMenuItem aboutItem;
    
    /** UndoManager, UndoAction, RedoAction */
    protected UndoManager undoManager;
    protected UndoAction undoAction;
    protected RedoAction redoAction;
    protected CutAction cutAction;
    protected CopyAction copyAction;
    protected DeleteAction deleteAction;
    protected FindAction findAction;
    protected ReplaceAction replaceAction;
    protected SelectAllAction selectAllAction;
    
    /** Print */
    
    
    /**
     * MemoPad Constructor - Creates an instance of the MemoPad
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public MemoPad() {
        super();
        this.setSize(1000, 500);
        this.setLocation(200, 100);
        this.setTitle("Untitled Document - MemoPad");
        this.addWindowListener(new WindowAdapter() {
            /**
             * windowClosing Method - Closes the frame for the NotePad
             * @param WindowEvent - the event of closing the frame
             */
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);

            }//end of the windowClosing Method for the Anonymous WindowAdapter
        });//end of the Anonymous WindowAdapter Class

        initComponents();

    }//end of the MemoPad Constructor

    /**
     * initComponents Method - Initializes the components for the MemoPad
     * @param Void
     */
    @SuppressWarnings("Convert2Lambda")
    private void initComponents() {
        TEXTPANE = new JTextPane();
        TEXTPANE.setFont(new Font("Bookman Old Style", Font.PLAIN, 14));
        this.add(TEXTPANE);
        ScrollPane scrollPane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        scrollPane.add(TEXTPANE);
        this.add(scrollPane);
        this.setVisible(true);
        
        /** The following two lines of code creates and sets a new icon for the frame */
        Image icon = Toolkit.getDefaultToolkit().getImage(MemoPad.class.getResource("/images/memoPad.png"));
        setIconImage(icon);
        
        /** Get the Look and Feel of the system */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
        } catch (ClassNotFoundException |
                 InstantiationException |
                 IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            String message = ex.getMessage();
            JOptionPane.showConfirmDialog(rootPane, message);
        }
        
        /** ImageIcon for the JMenus and JMenuItems */
        ImageIcon aboutIcon = new ImageIcon(getClass().getResource("/images/about.png"));
        ImageIcon alignIcon = new ImageIcon(getClass().getResource("/images/align.png"));
        ImageIcon alignCenterIcon = new ImageIcon(getClass().getResource("/images/alignCenter.png"));
        ImageIcon alignJustifyIcon = new ImageIcon(getClass().getResource("/images/alignJustify.png"));
        ImageIcon alignLeftIcon = new ImageIcon(getClass().getResource("/images/alignLeft.png"));
        ImageIcon alignRightIcon = new ImageIcon(getClass().getResource("/images/alignRight.png"));
        ImageIcon boldIcon = new ImageIcon(getClass().getResource("/images/boldIcon.png"));
        ImageIcon colorBlackIcon = new ImageIcon(getClass().getResource("/images/colorBlack.png"));
        ImageIcon colorBlueIcon = new ImageIcon(getClass().getResource("/images/colorBlue.png"));
        ImageIcon colorRedIcon = new ImageIcon(getClass().getResource("/images/colorRed.png"));
        ImageIcon colorsIcon = new ImageIcon(getClass().getResource("/images/colors.png"));
        ImageIcon copyIcon = new ImageIcon(getClass().getResource("/images/copy.png"));
        ImageIcon cutIcon = new ImageIcon(getClass().getResource("/images/cut.png"));
        ImageIcon timeDateIcon = new ImageIcon(getClass().getResource("/images/dateTime.png"));
        ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/images/delete.png"));
        ImageIcon exitIcon = new ImageIcon(getClass().getResource("/images/exit.png"));
        ImageIcon findIcon = new ImageIcon(getClass().getResource("/images/find.png"));
        ImageIcon fontIcon = new ImageIcon(getClass().getResource("/images/font.png"));
        ImageIcon fontsIcon = new ImageIcon(getClass().getResource("/images/fonts.png"));
        ImageIcon helpIcon = new ImageIcon(getClass().getResource("/images/help.png"));
        ImageIcon italicIcon = new ImageIcon(getClass().getResource("/images/italic.png"));
        ImageIcon moreColorsIcon = new ImageIcon(getClass().getResource("/images/moreColors.png"));
        ImageIcon newFileIcon = new ImageIcon(getClass().getResource("/images/newFile.png"));
        ImageIcon openFileIcon = new ImageIcon(getClass().getResource("/images/openFile.png"));
        ImageIcon pageSetupIcon = new ImageIcon(getClass().getResource("/images/pageSetup.png"));
        ImageIcon pasteIcon = new ImageIcon(getClass().getResource("/images/paste.png"));
        ImageIcon printIcon = new ImageIcon(getClass().getResource("/images/print.png"));
        ImageIcon printPreviewIcon = new ImageIcon(getClass().getResource("/images/printPreview.png"));
        ImageIcon printerIcon = new ImageIcon(getClass().getResource("/images/printer.png"));
        ImageIcon redoIcon = new ImageIcon(getClass().getResource("/images/redo.png"));
        ImageIcon replaceIcon = new ImageIcon(getClass().getResource("/images/replace.png"));
        ImageIcon rulerIcon = new ImageIcon(getClass().getResource("/images/ruler.png"));
        ImageIcon saveFileIcon = new ImageIcon(getClass().getResource("/images/save.png"));
        ImageIcon saveAsFileIcon = new ImageIcon(getClass().getResource("/images/saveAs.png"));
        ImageIcon selectAllIcon = new ImageIcon(getClass().getResource("/images/selectAll.png"));
        ImageIcon statusBarIcon = new ImageIcon(getClass().getResource("/images/statusBar.png"));
        ImageIcon strikeThroughIcon = new ImageIcon(getClass().getResource("/images/strikethrough.png"));
        ImageIcon subscriptIcon = new ImageIcon(getClass().getResource("/images/subscript.png"));
        ImageIcon superscriptIcon = new ImageIcon(getClass().getResource("/images/superscript.png"));
        ImageIcon underlineIcon = new ImageIcon(getClass().getResource("/images/underline.png"));
        ImageIcon undoIcon = new ImageIcon(getClass().getResource("/images/undo.png"));
        ImageIcon wordWrapIcon = new ImageIcon(getClass().getResource("/images/wordWrap.png"));
        
        /** Initialization of UndoManager, undoAction, and redoAction */
        undoManager = new UndoManager();
        undoAction = new UndoAction(undoIcon);
        redoAction = new RedoAction(redoIcon);
        cutAction = new CutAction(cutIcon);
        copyAction = new CopyAction(copyIcon);
        deleteAction = new DeleteAction(deleteIcon);
        findAction = new FindAction(findIcon);
        replaceAction = new ReplaceAction(replaceIcon);
        selectAllAction = new SelectAllAction(selectAllIcon);
        
        TEXTPANE.getStyledDocument().addUndoableEditListener(new UndoableEditListener() {
            /**
             * undoableEditHappened Method -
             * @param UndoableEditEvent -
             */
            @Override
            public void undoableEditHappened(UndoableEditEvent ue) {
                undoManager.addEdit(ue.getEdit());
                undoAction.updateUndoAction();
                redoAction.updateRedoAction();
                selectAllAction.updateSelectAllAction();
                
            }//end of the undoableEditHappened Method
        });//end of the Anonymous UndoableEditListener Class
        
        TEXTPANE.getStyledDocument().addDocumentListener(new DocumentListener() {
            /**
             * changedUpdate Method - 
             * @param DocumentEvent
             */
            @Override
            public void changedUpdate(DocumentEvent de) {
               TEXTPANE.getDocument().getLength();
                
            }//end of the changedUpdate Method
            /**
             * removeUpdate Method -
             * @param DocumentEvent -
             */
            @Override
            public void removeUpdate(DocumentEvent de) {
                
            }//end of the removeUpdate Method
            /**
             * insertUpdate Method -
             * @param DocumentEvent  
             */
            @Override
            public void insertUpdate(DocumentEvent de) {
            
            }//end of the insertUpdate Method
        });//end of the Anonymous DocumentListener
        
        TEXTPANE.addCaretListener(new CaretListener() {
            /**
             * caretUpdate Method -
             * @param CaretEvent -
             */
            @Override
            public void caretUpdate(CaretEvent ce) {
               // TEXTPANE.setCaretPosition(TEXTPANE.getDocument().getLength());
                TEXTPANE.getCaretPosition();
                
            }//end of the caretUpdate Method
        });//end of the Anonymous Caret Listener
        
        /** Create the MenuBar and its attributes */
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        /** Create the Menus, their attributes, and add them to the MenuBar */
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        viewMenu = new JMenu("View");
        formatMenu = new JMenu("Format");
        helpMenu = new JMenu("Help");
        
        fileMenu.setFont(menuFont);
        editMenu.setFont(menuFont);
        viewMenu.setFont(menuFont);
        formatMenu.setFont(menuFont);
        helpMenu.setFont(menuFont);
        
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(formatMenu);
        menuBar.add(helpMenu);

        /** Create the Menu and MenuItem for the fileMenu */
        printerMenu = new JMenu("Printer...");
        printerMenu.setIcon(printerIcon);
        
        newItem = new JMenuItem("New", newFileIcon);
        openItem = new JMenuItem("Open", openFileIcon);
        saveItem = new JMenuItem("Save", saveFileIcon);
        saveAsItem = new JMenuItem("Save As...", saveAsFileIcon);
        pageSetupItem = new JMenuItem("Page Setup", pageSetupIcon);
        printItem = new JMenuItem("Print", printIcon);
        previewPrintItem = new JMenuItem("Preview Print", printPreviewIcon);
        exitItem = new JMenuItem("Exit", exitIcon);
        
        printerMenu.setFont(menuItemFont);
        newItem.setFont(menuItemFont);
        openItem.setFont(menuItemFont);
        saveItem.setFont(menuItemFont);
        saveAsItem.setFont(menuItemFont);
        pageSetupItem.setFont(menuItemFont);
        printItem.setFont(menuItemFont);
        previewPrintItem.setFont(menuItemFont);
        exitItem.setFont(menuItemFont);
        
        /** Shortcut KeyStrokes for JMenuItems for the fileMenu */
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        printItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        
        pageSetupItem.addActionListener(new ActionListener() {
            /**
             * actionPerformed Method -
             * @param ActionEvent 
             */
            @Override
            public void actionPerformed(ActionEvent ae) {
                printJob = PrinterJob.getPrinterJob();
                pageFormat = new PageFormat();
            
                pageFormat = printJob.pageDialog(printJob.defaultPage());
                
            }//end of the actionPerformed Method
        });//end of the Anonymous ActionListener for the pageSetupItem
        
        printItem.addActionListener(new ActionListener() {
           /**
            * actionPerformed Method -
            * @param ActionEvent
            */ 
            @Override
            public void actionPerformed(ActionEvent ae) {
                printJob = PrinterJob.getPrinterJob();
                if (printJob.printDialog()) {
                    try {
                        printJob.print();
                        
                    } catch (PrinterException ex) {
                        String message = ex.getMessage();
                        JOptionPane.showMessageDialog(rootPane, message);
                    }
                }
            }
        });
         
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.addSeparator();
        fileMenu.add(pageSetupItem);
        fileMenu.add(printerMenu);
        fileMenu.addSeparator();
        printerMenu.add(printItem);
        printerMenu.addSeparator();
        printerMenu.add(previewPrintItem);
        fileMenu.add(exitItem);

        /** Create MenuItems for the editMenu */
        undoItem = new JMenuItem(undoAction);
        redoItem = new JMenuItem(redoAction);
        //redoItem = new JMenuItem("Redo", redoIcon);
        //cutItem = new JMenuItem("Cut", cutIcon);
        cutItem = new JMenuItem(cutAction);
       // copyItem = new JMenuItem("Copy", copyIcon);
        copyItem = new JMenuItem(copyAction);
        pasteItem = new JMenuItem(new StyledEditorKit.PasteAction());
       // pasteItem = new JMenuItem("Paste", pasteIcon);
        //deleteItem = new JMenuItem("Delete", deleteIcon);
        deleteItem = new JMenuItem(deleteAction);
        //findItem = new JMenuItem("Find", findIcon);
        findItem = new JMenuItem(findAction);
        replaceItem = new JMenuItem(replaceAction);
        //replaceItem = new JMenuItem("Replace", replaceIcon);
        selectAllItem = new JMenuItem(selectAllAction);
        //selectAllItem = new JMenuItem("Select All", selectAllIcon);
        timeDateItem = new JMenuItem("Time/Date", timeDateIcon);
        
        undoItem.setFont(menuItemFont);
        redoItem.setFont(menuItemFont);
        cutItem.setFont(menuItemFont);
        copyItem.setFont(menuItemFont);
        pasteItem.setFont(menuItemFont);
        deleteItem.setFont(menuItemFont);
        findItem.setFont(menuItemFont);
        replaceItem.setFont(menuItemFont);
        selectAllItem.setFont(menuItemFont);
        timeDateItem.setFont(menuItemFont);
        
        pasteItem.setText("Paste");
        pasteItem.setIcon(pasteIcon);
        
        /** Shortcut keystrokes for the editMenu JMenuItems */
        undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, ActionEvent.ALT_MASK));
        selectAllItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        
        editMenu.add(undoItem);
        editMenu.add(redoItem);
        editMenu.addSeparator();
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(deleteItem);
        editMenu.addSeparator();
        editMenu.add(findItem);
        editMenu.add(replaceItem);
        editMenu.addSeparator();
        editMenu.add(selectAllItem);
        editMenu.add(timeDateItem);

        /** Create the CheckboxMenuItems for the viewMenu */
        rulerCheckboxItem = new JCheckBoxMenuItem("Ruler", rulerIcon);
        statusBarCheckboxItem = new JCheckBoxMenuItem("Status Bar", statusBarIcon);
        wordWrapCheckboxItem = new JCheckBoxMenuItem("Word Wrap", wordWrapIcon);
        
        rulerCheckboxItem.setFont(menuItemFont);
        statusBarCheckboxItem.setFont(menuItemFont);
        wordWrapCheckboxItem.setFont(menuItemFont);

        viewMenu.add(rulerCheckboxItem);
        viewMenu.addSeparator();
        viewMenu.add(statusBarCheckboxItem);
        viewMenu.addSeparator();
        viewMenu.add(wordWrapCheckboxItem);

        /** Create the Menus and MenuItems for the formatMenu */
        fontMenu = new JMenu("Fonts...");
        colorMenu = new JMenu("Colors...");
        alignMenu = new JMenu("Alignments...");
        
        fontMenu.setIcon(fontIcon);
        colorMenu.setIcon(colorsIcon);
        alignMenu.setIcon(alignIcon);
        
        fontMenu.setFont(menuItemFont);
        colorMenu.setFont(menuItemFont);
        alignMenu.setFont(menuItemFont);

        formatMenu.add(fontMenu);
        formatMenu.addSeparator();
        formatMenu.add(colorMenu);
        formatMenu.addSeparator();
        formatMenu.add(alignMenu);

        fontItem = new JMenuItem(new MemoPadFontChooserAction());
        boldItem = new JMenuItem(new StyledEditorKit.BoldAction());
        italicItem = new JMenuItem(new StyledEditorKit.ItalicAction());
        underlineItem = new JMenuItem(new StyledEditorKit.UnderlineAction());
        subscriptItem = new JMenuItem(new SubscriptAction());
        superscriptItem = new JMenuItem(new SuperscriptAction());
        strikeThroughItem = new JMenuItem(new StrikeThroughAction());
        
        fontItem.setFont(menuItemFont);
        boldItem.setFont(menuItemFont);
        italicItem.setFont(menuItemFont);
        underlineItem.setFont(menuItemFont);
        subscriptItem.setFont(menuItemFont);
        superscriptItem.setFont(menuItemFont);
        strikeThroughItem.setFont(menuItemFont);
        
        fontItem.setText("Fonts");
        boldItem.setText("Bold");
        italicItem.setText("Italic");
        underlineItem.setText("Underline");
        subscriptItem.setText("Subscript");
        superscriptItem.setText("Superscript");
        strikeThroughItem.setText("Strikethrough");
        
        fontItem.setIcon(fontsIcon);
        boldItem.setIcon(boldIcon);
        italicItem.setIcon(italicIcon);
        underlineItem.setIcon(underlineIcon);
        subscriptItem.setIcon(subscriptIcon);
        superscriptItem.setIcon(superscriptIcon);
        strikeThroughItem.setIcon(strikeThroughIcon);
        
        /** Shortcut KeyStroke for the JMenuItems in formatMenu */
        fontItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.CTRL_MASK));

        fontMenu.add(fontItem);
        fontMenu.addSeparator();
        fontMenu.add(boldItem);
        fontMenu.addSeparator();
        fontMenu.add(italicItem);
        fontMenu.addSeparator();
        fontMenu.add(underlineItem);
        fontMenu.addSeparator();
        fontMenu.add(subscriptItem);
        fontMenu.addSeparator();
        fontMenu.add(superscriptItem);
        fontMenu.addSeparator();
        fontMenu.add(strikeThroughItem);

        colorBlackItem = new JMenuItem(new StyledEditorKit.ForegroundAction("Black", Color.BLACK));
        colorBlueItem = new JMenuItem(new StyledEditorKit.ForegroundAction("Blue", Color.BLUE));
        colorRedItem = new JMenuItem(new StyledEditorKit.ForegroundAction("Red", Color.red));
        moreColorsItem = new JMenuItem("More Colors...", moreColorsIcon);
       
        colorBlackItem.setIcon(colorBlackIcon);
        colorBlueItem.setIcon(colorBlueIcon);
        colorRedItem.setIcon(colorRedIcon);
    
        colorBlackItem.setForeground(Color.BLACK);
        colorBlackItem.setFont(menuItemFont);
        colorBlueItem.setForeground(Color.BLUE);
        colorBlueItem.setFont(menuItemFont);
        colorRedItem.setForeground(Color.red);
        colorRedItem.setFont(menuItemFont);
        moreColorsItem.setFont(menuItemFont);
        
        moreColorsItem.addActionListener(new MemoPadColorChooser());
       
        colorMenu.add(colorBlackItem);
        colorMenu.addSeparator();
        colorMenu.add(colorBlueItem);
        colorMenu.addSeparator();
        colorMenu.add(colorRedItem);
        colorMenu.addSeparator();
        colorMenu.add(moreColorsItem);
       
       /** AlignMenu and its JMenuItems; and their attributes */ 
        alignLeftItem = new JMenuItem(new StyledEditorKit.AlignmentAction("Left Alignment", StyleConstants.ALIGN_LEFT));
        alignCenterItem = new JMenuItem(new StyledEditorKit.AlignmentAction("Center Alignment", StyleConstants.ALIGN_CENTER));
        alignRightItem = new JMenuItem(new StyledEditorKit.AlignmentAction("Right Alignment", StyleConstants.ALIGN_RIGHT));
        alignJustifyItem = new JMenuItem(new StyledEditorKit.AlignmentAction("Justify Alignment", StyleConstants.ALIGN_JUSTIFIED));
       
        alignLeftItem.setIcon(alignLeftIcon);
        alignCenterItem.setIcon(alignCenterIcon);
        alignRightItem.setIcon(alignRightIcon);
        alignJustifyItem.setIcon(alignJustifyIcon);
        
        alignLeftItem.setFont(menuItemFont);
        alignCenterItem.setFont(menuItemFont);
        alignRightItem.setFont(menuItemFont);
        alignJustifyItem.setFont(menuItemFont);

        alignMenu.add(alignLeftItem);
        alignMenu.addSeparator();
        alignMenu.add(alignCenterItem);
        alignMenu.addSeparator();
        alignMenu.add(alignRightItem);
        alignMenu.addSeparator();
        alignMenu.add(alignJustifyItem);

        //Create the MenuItem for the helpMenu
        helpItem = new JMenuItem("View Help", helpIcon);
        aboutItem = new JMenuItem("About MemoPad", aboutIcon);
        
        
        //helpMenu.add(new AboutDialogAction(this, "About", true));
        helpItem.setFont(menuItemFont);
        aboutItem.setFont(menuItemFont);
        
        /** Shortcut KeyStroke for the JMenuItem in the helpMenu */
        helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.CTRL_MASK));
        
        helpItem.addActionListener(new HelpDialog(this, "View Help", true));
        aboutItem.addActionListener(new AboutDialog(this, "About MemoPad", true));
       
        helpMenu.add(helpItem);
        helpMenu.addSeparator();
        helpMenu.add(aboutItem);

    }//end of the initComponents Method
 
    /**
     * AboutDialog Class -
     */
    public class AboutDialog extends JDialog implements ActionListener {
        /** Instance Variable */
        private final JButton okayButton;
        private final JTextArea aboutTextArea;
        private final JPanel textPanel, buttonPanel;


        /**
         * AboutDialog Constructor -
         * @param JFrame - the parent frame
         * @param String - the title
         * @param Boolean - the modal
         */
        @SuppressWarnings({"OverridableMethodCallInConstructor", "LeakingThisInConstructor"})
        public AboutDialog(JFrame frame, String title, Boolean modal) {
            super(frame, title, modal);

           /** The following 2 lines of code creates a null icon for the JDialog */
            Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
            setIconImage(icon);

            setBounds(500, 150, 500, 250);
            StringBuilder text = new StringBuilder();
            text.append("MemoPad is a stand-alone application that allows users to create\n");
            text.append("and manipulate computer files with the extensions, .cpp, .css, .html,\n");
            text.append(".java, .js, .rtf, and .txt.  This application not only allows for the \n");
            text.append("creation and storage of these text files, but users can manipulate text\n");
            text.append("fonts, colors, and alignments.  In addition to allowing basic functions\n");
            text.append("like cut, copy, and paste, users are allowed to find and replace desired\n");
            text.append("specified text. \n\n");
            text.append("@author:  Mahdi Abdurrahman\n");
            text.append("@date:  14 December 2016\n");
            text.append("@version:  1.0.1");

            aboutTextArea = new JTextArea(30, 1);
            aboutTextArea.setFont(new Font("Bookman Old Style", Font.PLAIN, 12));
            aboutTextArea.setText(text.toString());
            aboutTextArea.setEditable(false);

            okayButton = new JButton(" OK ");
            okayButton.addActionListener(this);

            addWindowListener(new WindowAdapter() {
                /**
                 * windowClosing Method -
                 * @param WindowEvent -
                 */
                @Override
                public void windowClosing(WindowEvent we) {
                    Window window = we.getWindow();
                    window.dispose();

                }//end of the windowClosing Method for the Anonymous WindowAdapter
            });//end of the Anonymous WindowAdapter 

            textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            textPanel.add(aboutTextArea);
            //textPanel.setBackground(Color.BLUE);

            buttonPanel.add(okayButton);
            //buttonPanel.setBackground(Color.BLUE);

            getContentPane().add(textPanel, BorderLayout.CENTER);
            getContentPane().add(buttonPanel, BorderLayout.SOUTH);
            //setVisible(true);

        }//end of the AboutDialog Constructor
        /**
         * actionPerformed Method - 
         * @param ActionEvent -
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == aboutItem) {
                this.setVisible(true);
            }
            if (ae.getSource() == okayButton) {
                this.dispose();
            }
        }//end of the actionPerformed Method
    }//end of the AboutDialog Class
     /**
     * HelpDialog Class -
     */
    public class HelpDialog extends JDialog implements ActionListener {
        /** Instance Variable */
        private final JButton okayButton;
        private final JTextArea helpTextArea;
        private final JPanel textPanel, buttonPanel;

        /**
         * HelpDialog Constructor -
         * @param JFrame - the parent frame
         * @param String - the title
         * @param Boolean - the modal
         */
        @SuppressWarnings({"OverridableMethodCallInConstructor", "LeakingThisInConstructor"})
        public HelpDialog(JFrame frame, String title, Boolean modal) {
            super(frame, title, modal);

           /** The following 2 lines of code creates a null icon for the JDialog */
            Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
            setIconImage(icon);

            setBounds(500, 150, 450, 250);
            StringBuilder text = new StringBuilder();
            text.append("This program is free software.  You can distribute  it and/or \n");
            text.append("modify it under the terms of the GNU General Public License \n");
            text.append("as published by the Free Software Foundation; either version \n");
            text.append("1.0.1 of the License, or (at your option) any later rendition.\n\n");
            text.append("This program is distributed in hope that it will be useful, but \n");
            text.append("WITHOUT ANY WARRANTY; without even the implied warranty of \n");
            text.append("MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.\n");

            helpTextArea = new JTextArea(20, 1);
            helpTextArea.setFont(new Font("Bookman Old Style", Font.PLAIN, 12));
            helpTextArea.setText(text.toString());
            helpTextArea.setEditable(false);

            okayButton = new JButton(" OK ");
            okayButton.addActionListener(this);

            addWindowListener(new WindowAdapter() {
                /**
                 * windowClosing Method -
                 * @param WindowEvent -
                 */
                @Override
                public void windowClosing(WindowEvent we) {
                    Window window = we.getWindow();
                    window.dispose();

                }//end of the windowClosing Method for the Anonymous WindowAdapter
            });//end of the Anonymous WindowAdapter 

            textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            textPanel.add(helpTextArea);
            buttonPanel.add(okayButton);

            getContentPane().add(textPanel, BorderLayout.CENTER);
            getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        }//end of the HelpDialog Constructor
        /**
         * actionPerformed Method - 
         * @param ActionEvent -
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == helpItem) {
                this.setVisible(true);

            }
            if (ae.getSource() == okayButton) {
                this.dispose();

            }
        }//end of the actionPerformed Method
    }//end of the HelpDialog Class




      
    /**
     * UndoAction Class
     */
    class UndoAction extends AbstractAction {
        /**
         * UndoAction Constructor -
         * @param ImageIcon -
         */
        @SuppressWarnings("OverridableMethodCallInConstructor")
        public UndoAction(ImageIcon icon) {
            super("Undo", icon);
            setEnabled(false);
            
        }//end of the UndoAction Constructor
        /**
         * actionPerformed Method -
         * @param ActionEvent -
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                undoManager.undo();
                
            } catch (CannotUndoException ex) {
                String message = ex.getMessage();
                JOptionPane.showMessageDialog(rootPane, message);
                
            }
            updateUndoAction();
            redoAction.updateRedoAction();
            
        }//end of the actionPerformed Method
        /**
         * updateUndoAction Method - 
         * @param Void
         */
        protected void updateUndoAction() {
            if (undoManager.canUndo()) {
                setEnabled(true);
                putValue(Action.NAME, "Undo");
                if (TEXTPANE.getDocument().getLength() > 0) {
                    selectAllItem.setEnabled(true);
                    findItem.setEnabled(true);
                    replaceItem.setEnabled(true);
                
                } else {
                    selectAllItem.setEnabled(false);
                    cutItem.setEnabled(false);
                    copyItem.setEnabled(false);
                    deleteItem.setEnabled(false);
                    findItem.setEnabled(false);
                    replaceItem.setEnabled(false);
                    
                }
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Undo");
                selectAllItem.setEnabled(false);
                cutItem.setEnabled(false);
                copyItem.setEnabled(false);
                deleteItem.setEnabled(false);
                findItem.setEnabled(false);
                replaceItem.setEnabled(false);
                
            }
            
        }//end of the updateUndoAction Method
    }//end of the UndoAction Class
    /**
     * RedoAction Class - 
     */
    class RedoAction extends AbstractAction {
        /**
         * RedoAction Constructor -
         * @param ImageIcon
         */
        @SuppressWarnings("OverridableMethodCallInConstructor")
        public RedoAction(ImageIcon icon) {
            super("Redo", icon);
            setEnabled(false);
            
        }//end of the RedoAction Constructor
        /**
         * actionPerformed Method - 
         * @param ActionEvent -
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                undoManager.redo();
                
            } catch (CannotRedoException ex) {
                String message = ex.getMessage();
                JOptionPane.showMessageDialog(rootPane, message);
                
            }
            updateRedoAction();
            undoAction.updateUndoAction();
            
        }//end of the actionPerformed Method
        /**
         * updateRedoAction Method -
         * @param Void
         */
        protected void updateRedoAction() {
            if (undoManager.canRedo()) {
                setEnabled(true);
                putValue(Action.NAME, "Redo"); 
                if (TEXTPANE.getDocument().getLength() > 0) {
                    selectAllItem.setEnabled(true);
                    findItem.setEnabled(true);
                    replaceItem.setEnabled(true);
                    
                } else {
                    selectAllItem.setEnabled(false);
                    cutItem.setEnabled(false);
                    copyItem.setEnabled(false);
                    deleteItem.setEnabled(false);
                    findItem.setEnabled(false);
                    replaceItem.setEnabled(false);
                }
                
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Redo");
            }
        }//end of the updateRedoAction Method
    }//end of the RedoAction Class
    /**
     * getTextPane Method - 
     * @param Void
     * @return JTextPane
     */
    public static JTextPane getTextPane() {
        return  TEXTPANE;
    }//end of the getTextPane Method
      /**
     * main Method - Contains the command line arguments
     * @param String[] - the command line arguments
     */
    @SuppressWarnings("Convert2Lambda")
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            /**
             * run Method -
             * @param Void
             */
            @Override
            public void run() {
                MemoPad pad = new MemoPad();
                
            }//end of the run Method for the Anonymous Runnable
        });//end of the Anonymous Runnable
    }//end of the main Method
}//end of the MemoPad Class