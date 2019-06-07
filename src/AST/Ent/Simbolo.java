/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Ent;

/**
 *
 * @author erick
 */
public class Simbolo 
{
    String id;        
    public Tipo tipo;
    public Object valor;
    String rol;
    public int linea, columna;            
    public Simbolo(Tipo t, String id, Object v )
    {
        this.id = id;
        this.tipo = t;
        this.valor = v;
        String c ="";       
        linea = 0 ; 
        columna = 0;
    }        
    
    public Simbolo(Tipo t, String id, Object v , int l, int col)
    {
        this.id = id;
        this.tipo = t;
        this.valor = v;
        String c ="";       
        this.linea = l;
        this.columna = col;
    }        
    
    public String getId() {
        return id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Object getValor() {
        return valor;
    }

    public String getRol() {
        return rol;
    }
    
    public String getMessage()
    {
        return  tipo.typeClass.equals("")?             
                id +"\t"+tipo.typeprimitive+"\t"+valor+"\t"+linea+"\t"+columna:
                id +"\t"+tipo.typeClass+"\t"+valor+"\t"+linea+"\t"+columna;
    }
    
    
    
    
}
