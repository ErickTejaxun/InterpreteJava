/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Instruccion.Ciclos;

import AST.Entorno.Entorno;
import AST.Instruccion.Instruccion;

/**
 *
 * @author erick
 */
public class Continuar implements Instruccion
{
    int linea, columna;
    
    public Continuar(int l, int c)
    {
        this.linea  = l;
        this.columna = c;
    }
   
    
    @Override
    public Object ejectuar(Entorno entorno) 
    {
        return this;
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
