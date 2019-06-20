/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion.Funcion;

import AST.Entorno.Entorno;
import AST.Entorno.Simbolo;
import static AST.Entorno.Simbolo.Rol.CONSTRUCTOR;
import AST.Entorno.Tipo;
import static AST.Entorno.Tipo.TypePrimitive.CHAR;
import static AST.Entorno.Tipo.TypePrimitive.DOUBLE;
import static AST.Entorno.Tipo.TypePrimitive.INT;
import static AST.Entorno.Tipo.TypePrimitive.STRING;
import AST.Instruccion.Instruccion;
import java.util.ArrayList;

/**
 *
 * @author erick
 */
public class Constructor extends Funcion
{
    
    public Constructor(ArrayList<String> mod, String id, ArrayList<ParametroFormal> lpf, Instruccion bloque, int l, int c) 
    {        
        super(mod, new Tipo(id), id, lpf, l, c);
        this.id = this.id.substring(0, this.id.length());
        this.instrucciones = bloque;
        this.rol = CONSTRUCTOR;
    }
    
    public Constructor(ArrayList<String> mod, String id, ArrayList<ParametroFormal> lpf, int l, int c) {
        super(mod, new Tipo(id), id, lpf, l, c);        
        this.id = this.id.substring(0, this.id.length()-1);
        this.rol = CONSTRUCTOR;
    }  
    
    @Override
    public Object getValor(Entorno entorno) 
    {                
        /*Creamos un nuevo entorno enlazado al entorno global.*/
        //Entorno local = new Entorno(ent.ventana.entornoGlobal, ent.ventana);                
        /*Declarar los parametros formales
        y asignarle el valor de los parametros actuales.*/
        //System.out.print(id+"(");
        for(ParametroFormal p: this.parametrosFormales)
        {            
            ParametroFormal nuevo = new ParametroFormal(p.tipo, p.id,false, p.dimensiones,p.linea, p.columna);
            if(entorno.insertar(nuevo))
            {                
                nuevo.valor = p.valor;
            }            
        }       
        Object result = instrucciones.ejectuar(entorno);  
        Tipo tipoResultado = new Tipo("");
        if(result instanceof Integer)
        {
            tipoResultado = new Tipo(INT);
        }
        if(result instanceof Double)
        {
            tipoResultado = new Tipo(DOUBLE);
        }        
        if(result instanceof Character)
        {
            tipoResultado = new Tipo(CHAR);
        }        
        if(result instanceof String)
        {
            tipoResultado = new Tipo(STRING);
        }        
        if(result instanceof Simbolo)
        {
            tipoResultado = ((Simbolo)result).tipo;
        }
        tipo = tipoResultado;
        //System.out.print(result);
        //System.out.print(")");
        return result;
    }    
    
}
