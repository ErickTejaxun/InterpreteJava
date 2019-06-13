/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion.Relacional;

import AST.Entorno.Entorno;
import AST.Entorno.Tipo;
import static AST.Entorno.Tipo.TypePrimitive.*;
import AST.Expresion.Expresion;
import Utilidades.ErrorC;

/**
 *
 * @author erick
 */
public class MenorIgual implements Expresion
{
    int linea, columna;
    Tipo tipo;
    Expresion opi, opd;
    
    public MenorIgual(Expresion i, Expresion d, int l, int c)
    {
        this.opi = i;
        this.opd = d;
        this.linea = l;
        this.columna = c;
    }
    
    
    @Override
    public Object getValor(Entorno ent) 
    {       
        Object izquierda = opi.getValor(ent);
        Object derecha = opd.getValor(ent);
        tipo = new Tipo(BOOL);
        switch(opi.getTipo().typeprimitive)
        {
            case INT:
                switch(opd.getTipo().typeprimitive)
                {
                    case INT:
                        return (int)izquierda <= (int)derecha;
                    case DOUBLE:
                        return (int)izquierda <= (Double)derecha;   
                    case CHAR:
                        return (int)izquierda <= derecha.toString().charAt(0);
                    default:
                        Utilidades.Singlenton.registrarError("Relacional <=", "Tipos no válidos: " + opi.getTipo() + " <= " +opd.getTipo() , ErrorC.TipoError.SEMANTICO, linea, columna);            
                        break;
                }
                break;  
            case DOUBLE:
                switch(opd.getTipo().typeprimitive)
                {
                    case INT:
                        return (Double)izquierda <= (int)derecha;
                    case DOUBLE:
                        return (Double)izquierda <= (Double)derecha;   
                    case CHAR:
                        return (Double)izquierda <= derecha.toString().charAt(0);
                    default:
                        Utilidades.Singlenton.registrarError("Relacional <=", "Tipos no válidos: " + opi.getTipo() + " <= " +opd.getTipo() , ErrorC.TipoError.SEMANTICO, linea, columna);            
                        break;
                }
                break;   
            case CHAR:
                switch(opd.getTipo().typeprimitive)
                {
                    case INT:
                        return izquierda.toString().charAt(0) <= (int)derecha;
                    case DOUBLE:
                        return izquierda.toString().charAt(0) <= (Double)derecha;   
                    case CHAR:
                        return izquierda.toString().charAt(0) <= derecha.toString().charAt(0);
                    default:
                        Utilidades.Singlenton.registrarError("Relacional <=", "Tipos no válidos: " + opi.getTipo() + " <= " +opd.getTipo() , ErrorC.TipoError.SEMANTICO, linea, columna);            
                        break;
                }
                break;                
        }
        return false;
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
