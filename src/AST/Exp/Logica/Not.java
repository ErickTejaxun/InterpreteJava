/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Exp.Logica;

import AST.Ent.Entorno;
import AST.Ent.Tipo;
import static AST.Ent.Tipo.TypePrimitive.*;
import AST.Exp.Expresion;
import Utilidades.ErrorC;

/**
 *
 * @author erick
 */
public class Not implements Expresion
{
    int linea, columna;
    Tipo tipo;
    Expresion expresion;    
    public Not(Expresion i,  int l, int c)
    {
        this.expresion = i;        
        this.linea = l;
        this.columna = c;
    }
    
    
    @Override
    public Object getValor(Entorno ent) 
    {       
        Object derecha = expresion.getValor(ent);        
        tipo = new Tipo(BOOL);
        if(expresion.getTipo().typeprimitive!= tipo.typeprimitive)
        {
            Utilidades.Singlenton.registrarError("Relacional Not", "Operación válida sólo para tipo " + tipo , ErrorC.TipoError.SEMANTICO, linea, linea);            
            return false;            
        }        
        return !(Boolean)derecha;
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
