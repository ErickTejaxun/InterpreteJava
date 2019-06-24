/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interprete;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 *
 * @author erick
 */
public class Position implements CaretListener{

    Interfaz ventana;
    public Position(Interfaz v)
    {
        this.ventana = v;
    }
    @Override
    public void caretUpdate(CaretEvent e)
    {
        if(e.getSource() instanceof RSyntaxTextArea)
        {
            try 
            {
                RSyntaxTextArea actual = (RSyntaxTextArea) e.getSource();
                    int caretpos = actual.getCaretPosition();
                    int linenum = actual.getLineOfOffset(caretpos);

                    // We subtract the offset of where our line starts from the overall caret position.
                    // So lets say that we are on line 5 and that line starts at caret position 100, if our caret position is currently 106
                    // we know that we must be on column 6 of line 5.
                    int columnnum = caretpos - actual.getLineStartOffset(linenum);
                    ventana.setPosicion(linenum, columnnum);
                //System.out.printf("Offset %d on line %d%n", columna, line);
            } catch (BadLocationException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }          
    } 
    
}
