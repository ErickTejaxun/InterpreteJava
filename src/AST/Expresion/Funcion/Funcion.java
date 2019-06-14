
package AST.Expresion.Funcion;

import AST.Entorno.Entorno;
import AST.Entorno.Simbolo;
import AST.Entorno.Tipo;
import AST.Instruccion.Instruccion;
import java.util.ArrayList;

/**
 *
 * @author erick
 */
public class Funcion extends Simbolo implements Instruccion
{
    ArrayList<ParametroFormal> parametrosFormales;
    ArrayList<String> modificadores;
    public String nombre;
    public Instruccion instrucciones;
    
    
    public Funcion(Tipo t, String id, int dim, int l, int c) 
    {
        super(t, id, dim, l, c);
    }
    
    public Funcion(ArrayList<String> mod, Tipo t, String id, ArrayList<ParametroFormal> lpf , int l, int c)
    {
        super();
        this.id = id;
        this.nombre =id;        
        /*]Generamos la firma del metodo*/        
        for(ParametroFormal i:parametrosFormales)
        {   
            this.id+="$"+i.tipo.nombreTipo();
        }        
        this.rol = Simbolo.Rol.FUNCION; 
        this.modificadores = mod;
        this.tipo = t;        
        this.parametrosFormales = lpf;
        this.linea =l;
        this.columna = c;
    }
    
    
    public void setInstrucciones(Instruccion ins)
    {
        this.instrucciones = ins;
    }

    @Override
    public Object ejectuar(Entorno entorno) 
    {
        entorno.insertar(this);
        return this;
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
