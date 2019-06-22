/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion;
import AST.Entorno.Entorno;
import AST.Entorno.Simbolo;
import AST.Entorno.Tipo;
import Utilidades.ErrorC;
/**
 *
 * @author erick
 */
public class Escribir implements Expresion
{
    public String id;
    public int linea, columna;
    public Tipo tipo;
    public Escribir(String id, int l, int c)
    {
        this.id = id;
        this.linea = l;
        this.columna = c;
    }
    
    @Override
    public Object getValor(Entorno ent) 
    {
        Utilidades.Singlenton.setVariable(id);
        if(this.id.equals("this"))
        {
            
        }
        
        Simbolo s = ent.obtener(this.id);
        if(s!=null)
        {            
            this.tipo = s.tipo;
            return s.valor;
        }
        Utilidades.Singlenton.registrarError(this.id, "La variable no existe en la tabla de símbolos. ", ErrorC.TipoError.SEMANTICO, linea, columna);
        return null;
    }
    
    

    @Override
    public Tipo getTipo() {
        return this.tipo;
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
