/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Instruccion;

import AST.Entorno.Entorno;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author erick
 */
public class ImprimirT implements Instruccion
{
    int linea, columna;

    public ImprimirT(int l, int c) 
    {
        this.linea = l;
        this.columna = c;
    }
    
    

    @Override
    public Object ejectuar(Entorno entorno) 
    {
        entorno.ventana.Imprimir("\nID\tTIPO\tVALOR\tROL\tDIMENSIONES\tLINEA\tCOLUMNA");
        try 
        {
            entorno.ventana.Imprimir(entorno.Tabla());
        } catch (IOException ex) {
            Logger.getLogger(ImprimirT.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int linea() 
    {
        return linea;
    }

    @Override
    public int columna() 
    {
        return columna;
    }
    
}
