/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion.Casteo;

import AST.Entorno.Entorno;
import AST.Entorno.Tipo;
import AST.Expresion.Arreglo.Arreglo;
import AST.Expresion.Expresion;
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
            if(valor instanceof Arreglo)
            {
                return ((Arreglo)valor).getCadena();
            }
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
