/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Instruccion.Ciclos;

import AST.Entorno.Entorno;
import AST.Entorno.Simbolo;
import AST.Entorno.Tipo;
import AST.Expresion.Arreglo.Arreglo;
import AST.Expresion.Arreglo.NodoNario;
import AST.Expresion.Expresion;
import AST.Instruccion.Bloque;
import AST.Instruccion.Declaracion;
import AST.Instruccion.Instruccion;
import Utilidades.ErrorC;
import java.util.ArrayList;

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
    public Bloque instrucciones;
    
    public ForEach(Tipo t, String i, Expresion o, Bloque b, int l, int c)
    {
        this.tipo = t;
        this.id = i;
        this.origen = o;
        this.linea = l;
        this.columna = c;
        this.instrucciones = b;
    }
    
    @Override
    public Object ejectuar(Entorno entorno) 
    {
        Object resultado = origen.getValor(entorno);
        if(resultado instanceof Arreglo)
        {
            Arreglo arregloTmp = (Arreglo)resultado;
            ArrayList<NodoNario> arrayLineal = arregloTmp.getArrayLinealizado();            
            for(NodoNario item : arrayLineal)
            {
                Entorno local = new Entorno(entorno,entorno.ventana);
                Simbolo s  = new Simbolo(tipo, id, item.valor, linea, columna);
                local.insertar(s);
                instrucciones.ejectuar(local);                
                //Declaracion(Tipo t, String id, int d, Expresion e, int l,int c)
                //Declaracion dec = new Declaracion(this.tipo,id,item.valor,linea,columna);
            }            
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
