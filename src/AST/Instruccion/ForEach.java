/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Instruccion;

import AST.Entorno.Entorno;
import AST.Entorno.Tipo;
import AST.Expresion.Arreglo.Arreglo;
import AST.Expresion.Expresion;
import Utilidades.ErrorC;

/**
 *
 * @author erick
 */
public class ForEach implements Instruccion
{
    int linea, columna;
    Tipo tipo;
    String id;
    Expresion origen;
    
    public ForEach(Tipo t, String i, Expresion o, int l, int c)
    {
        this.tipo = t;
        this.id = i;
        this.origen = o;
        this.linea = l;
        this.columna = c;
    }
    
    @Override
    public Object ejectuar(Entorno entorno) 
    {
        Object resultado = origen.getValor(entorno);
        if(resultado instanceof Arreglo)
        {
            Arreglo arregloTmp = (Arreglo)resultado;
            
        }
        else
        {
            Utilidades.Singlenton.registrarError("Foreach", "Se esperaba un arreglo. " , ErrorC.TipoError.SEMANTICO, origen.linea(), origen.columna());    
        }                
        return null;
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
