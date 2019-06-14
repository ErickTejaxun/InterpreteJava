/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Instruccion;

import AST.Entorno.Entorno;
import AST.Expresion.Expresion;
import AST.Expresion.Relacional.Igual;
import AST.Instruccion.Ciclos.Break;
import java.util.ArrayList;

/**
 *
 * @author erick
 */
public class Switch implements Instruccion
{
    public int linea, columna;
    public Expresion condicion;
    public ArrayList<Caso> listaCasos;
    
    public Switch(Expresion cond, ArrayList<Caso> lista, int l, int c)
    {
        this.condicion = cond;
        this.listaCasos = lista;
        this.linea = l;
        this.columna = c;
    }
            
    @Override
    public Object ejectuar(Entorno entorno) 
    {

        Object resultadoCondicion = condicion.getValor(entorno);
        if(resultadoCondicion!=null)
        {
            for(Caso caso: listaCasos)
            {
                if(caso.condicion!=null)
                /*Caso normal*/
                {
                    Object resultadoCaso = caso.condicion.getValor(entorno);
                    if(caso.condicion.getTipo().nombreTipo().toLowerCase().equals(condicion.getTipo().nombreTipo().toLowerCase()))
                    {
                        Igual comparacion =  new Igual(condicion,caso.condicion,caso.linea,caso.columna);
                        Object resultadoCompracion = comparacion.getValor(entorno);
                        if(resultadoCompracion!=null)
                        {
                            if(comparacion.getTipo().isBoolean())
                            {
                                if((Boolean)resultadoCompracion)
                                {
                                    Object result = caso.ejectuar(entorno);
                                    if(result instanceof Break)
                                    {
                                        return null;
                                    }                                    
                                }                                
                            }
                        }
                    }                    
                }
                else
                {                
                    Object resultf = caso.ejectuar(entorno);
                    if(resultf instanceof Break)
                    {
                        return null;
                    }                     
                }
            }
        }
        else
        {
            
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
