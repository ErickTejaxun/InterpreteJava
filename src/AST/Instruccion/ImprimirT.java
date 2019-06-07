/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Instruccion;

import AST.Ent.Entorno;

/**
 *
 * @author erick
 */
public class ImprimirT implements Instruccion
{
    int linea, columna;

    public ImprimirT(int l, int c) 
    {
        this.linea = l;
        this.columna = c;
    }
    
    

    @Override
    public Object ejectuar(Entorno entorno) 
    {
        entorno.ventana.Imprimir("ID\tTIPO\tVALOR\tLINEA\tCOLUMNA");
        entorno.ventana.Imprimir(entorno.Tabla());
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
