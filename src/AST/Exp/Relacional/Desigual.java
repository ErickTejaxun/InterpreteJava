/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Exp.Relacional;

import AST.Ent.Entorno;
import AST.Ent.Tipo;
import static AST.Ent.Tipo.TypePrimitive.BOOL;
import AST.Exp.Expresion;
import Utilidades.ErrorC;

/**
 *
 * @author erick
 */
public class Desigual implements Expresion
{
    int linea, columna;
    Tipo tipo;
    Expresion opi, opd;
    
    public Desigual(Expresion i, Expresion d, int l, int c)
    {
        this.opi = i;
        this.opd = d;
        this.linea = l;
        this.columna = c;
    }
    
    
    @Override
    public Object getValor(Entorno ent) 
    {       
        Object derecha = opi.getValor(ent);
        Object izquierda = opd.getValor(ent);
        tipo = new Tipo("");
        tipo.typeprimitive = BOOL;
        if( opi.getTipo().typeprimitive != opd.getTipo().typeprimitive)
        {
            Utilidades.Singlenton.registrarError("Relacional Desigual", "No se puede operar entre tipos distintos. " + opi.getTipo() + " == " +opd.getTipo() , ErrorC.TipoError.SEMANTICO, linea, columna);            
            return false;            
        }        
        
        if(derecha != izquierda)
        {
            return true;
        }
        else
        {
            return false;
        }                
    }
    
    public boolean isNumeric(Expresion e)
    {
        switch(e.getTipo().typeprimitive)
        {
            case INT:
            case DOUBLE:
            case CHAR:
                return true;               
        }
        return false;
    }
    
    
    @Override
    public Tipo getTipo() {
        return tipo;
    }

    @Override
    public int linea() {
        return linea;
    }

    @Override
    public int columna() {
        return columna;
    }
    
}
