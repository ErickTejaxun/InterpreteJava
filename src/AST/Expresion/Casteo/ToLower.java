/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion.Casteo;

import AST.Entorno.Entorno;
import AST.Entorno.Tipo;
import AST.Expresion.Expresion;
import static AST.Entorno.Tipo.TypePrimitive.STRING;
import Utilidades.ErrorC;

/**
 *
 * @author erick
 */
public class ToLower implements Expresion
{
    int linea, columna;
    Tipo tipo;
    Expresion expresion;
    
    public ToLower(Expresion e, int l, int c)
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
            if(expresion.getTipo().isString())
            {                
                tipo = new Tipo(STRING);
                return valor.toString().toLowerCase();  
            }
            else
            {
                Utilidades.Singlenton.registrarError("ToLower()", "No se puede aplicar esta función a valores de tipo "+expresion.getTipo().nombreTipo() , ErrorC.TipoError.SEMANTICO, linea, columna);
            }
            
        }
        else
        {
            Utilidades.Singlenton.registrarError("ToLower()", "No es posible aplicar el método a un valor nulo." , ErrorC.TipoError.SEMANTICO, linea, columna);
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
