/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion.Aritmetica;

import AST.Entorno.Entorno;
import AST.Entorno.Tipo;
import static AST.Entorno.Tipo.TypePrimitive.*;
import AST.Expresion.Expresion;
import Utilidades.ErrorC;

/**
 *
 * @author erick
 */
public class Menos implements Expresion
{
    int linea, columna;
    Tipo tipo;
    Expresion expresion;
    
    public Menos(Expresion e, int l, int c)
    {
        this.expresion = e;
        this.linea = l;
        this.columna = c;
    }
    
    
    @Override
    public Object getValor(Entorno ent) 
    {
        Object valor = expresion.getValor(ent);
        tipo = expresion.getTipo();
        switch(tipo.typeprimitive)
        {
            case INT:
                valor = (int)valor * -1;                
                break;
            case DOUBLE:
                valor = (Double)valor * -1;                
                break;            
            case CHAR:
                valor = valor.toString().charAt(0) * -1;
                tipo.typeprimitive = INT;
                break;
            default:
                Utilidades.Singlenton.registrarError("Operación menos (-)", "No se puede operar tipo " + tipo.typeprimitive + " tipo " , ErrorC.TipoError.SEMANTICO, linea, columna);
                break;
            
        }        
        return valor;
    }

    @Override
    public Tipo getTipo() 
    {
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
