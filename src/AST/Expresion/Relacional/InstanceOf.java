/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion.Relacional;

import AST.Entorno.Entorno;
import AST.Entorno.Tipo;
import static AST.Entorno.Tipo.TypePrimitive.BOOL;
import AST.Expresion.Expresion;
import Utilidades.ErrorC;

/**
 *
 * @author erick
 */
public class InstanceOf implements Expresion
{
    int linea, columna;
    Tipo tipo = new Tipo(BOOL);
    Expresion valor;
    String tipoComparacion;
    
    public InstanceOf(Expresion v, String t, int l, int c)
    {
        this.linea = l;
        this.columna = c;
        this.valor = v;
        this.tipoComparacion=t;
    }
    
    
    
    @Override
    public Object getValor(Entorno ent) 
    {
        Object result = valor.getValor(ent);
        if(result==null && valor.getTipo()==null)
        {
            Utilidades.Singlenton.registrarError("Nulo", "El valor nulo no pertenece a ninguna clase." , ErrorC.TipoError.SEMANTICO, linea, columna);    
            return false;
        }
        switch(tipoComparacion.toLowerCase())
        {
            case "integer":
                return result instanceof Integer;                
            case "double":
                return result instanceof Double;                
            case "char":
                return result instanceof Character;
            case "boolean":
                return result instanceof Boolean;
            case "string":
                return result instanceof String;
            default:
                return valor.getTipo().nombreTipo().toLowerCase().equals(tipoComparacion);
        }        
    }
    

    @Override
    public Tipo getTipo() 
    {
        return tipo;
    }

    @Override
    public int linea() 
    {
        return linea;
    }

    @Override
    public int columna() {
        return columna;
    }
    
}
