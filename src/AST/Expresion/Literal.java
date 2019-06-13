/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion;
import AST.Entorno.Entorno;
import AST.Entorno.Tipo;
/**
 *
 * @author erick
 */
public class Literal implements Expresion
{
    Object valor;
    Tipo tipo;
    public int linea, columna;

    public Literal(Tipo t, Object v, int l, int c)
    {
        this.tipo = t;
        this.valor = v;
        this.linea = l;
        this.columna = c;        
    }        
    
    @Override
    public Object getValor(Entorno ent) 
    {
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
