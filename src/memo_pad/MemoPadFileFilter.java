package memo_pad;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * The MemoPadFileFilter Class
 * @author MAbdurrahman
 * @date:  19 January 2017
 * @version:  1.0.0
 */
public class MemoPadFileFilter extends FileFilter {
    /**
     * accept Method
     * @param File
     * @return Boolean
     */
    @Override
    public boolean accept(File file) {
        return (file.getAbsolutePath().endsWith(".txt")) ||
               (file.getAbsolutePath().endsWith(".rtf")) ||
               (file.getAbsolutePath().endsWith(".html"))||
               (file.getAbsolutePath().endsWith(".css")) ||
               (file.getAbsolutePath().endsWith(".js"))  ||
               (file.getAbsolutePath().endsWith(".java"))||
               (file.getAbsolutePath().endsWith(".cpp"))  ? true : false;
           
    }//end of accept Method
    /**
     * getDescription Method -
     * @param Void
     * @return String
     */
    @Override
    public String getDescription() {
        return "Text Files";
        
    }//en of the getDescription Method
}//end of the TextFilter Class
    

