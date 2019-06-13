/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion.Logica;


import AST.Entorno.Entorno;
import AST.Entorno.Tipo;
import static AST.Entorno.Tipo.TypePrimitive.*;
import AST.Expresion.Expresion;
import Utilidades.ErrorC;

/**
 *
 * @author erick
 */
public class And implements Expresion
{
    int linea, columna;
    Tipo tipo;
    Expresion opi, opd;
    
    public And(Expresion i, Expresion d, int l, int c)
    {
        this.opi = i;
        this.opd = d;
        this.linea = l;
        this.columna = c;
    }
    
    
    @Override
    public Object getValor(Entorno ent) 
    {       
        Object derecha = opi.getValor(ent);
        Object izquierda = opd.getValor(ent);
        tipo = new Tipo(BOOL);
        if(opi.getTipo().typeprimitive!= tipo.typeprimitive  || opd.getTipo().typeprimitive != tipo.typeprimitive)
        {
            Utilidades.Singlenton.registrarError("Relacional And", "Tipos no válidos " + opi.getTipo() + " and " +opd.getTipo() , ErrorC.TipoError.SEMANTICO, linea, columna);            
            return false;            
        }        
        
        if((Boolean)derecha && (Boolean) izquierda)
        {
            return true;
        }
        else
        {
            return false;
        }                
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
