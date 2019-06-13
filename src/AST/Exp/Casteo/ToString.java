/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Exp.Casteo;

import AST.Ent.Entorno;
import AST.Ent.Tipo;
import AST.Exp.Expresion;
import Utilidades.ErrorC;

/**
 *
 * @author erick
 */
public class ToString implements Expresion
{
    int linea, columna;
    Tipo tipo;
    Expresion expresion;
    
    public ToString(Expresion e, int l, int c)
    {
        this.columna = c;
        this.linea = l;
        this.expresion = e;
    }
    
    @Override
    public Object getValor(Entorno ent) 
    {
        Object valor = expresion.getValor(ent);
        if(valor !=null)
        {
            tipo = new Tipo(Tipo.TypePrimitive.STRING);
            return valor.toString();
        }
        else
        {
            Utilidades.Singlenton.registrarError("ToString()", "No es posible aplicar el método a un valor nulo." , ErrorC.TipoError.SEMANTICO, linea, columna);
        }        
        return null;
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
