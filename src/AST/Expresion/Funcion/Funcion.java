
package AST.Expresion.Funcion;

import AST.Entorno.Entorno;
import AST.Entorno.Simbolo;
import AST.Entorno.Tipo;
import static AST.Entorno.Tipo.TypePrimitive.*;
import AST.Expresion.Expresion;
import AST.Instruccion.Instruccion;
import java.util.ArrayList;

/**
 *
 * @author erick
 */
public class Funcion extends Simbolo implements Instruccion, Expresion
{
    public ArrayList<ParametroFormal> parametrosFormales;
    public ArrayList<String> modificadores;
    public String nombre;
    public Instruccion instrucciones;
    public boolean sobreescito = false;
    
    
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
        this.parametrosFormales = new ArrayList<ParametroFormal>();
        this.parametrosFormales = lpf;
        for(ParametroFormal i:parametrosFormales)
        {   
            this.id+="$"+i.tipo.nombreTipo().toLowerCase();
        }    
        this.id +="$";
        this.rol = Simbolo.Rol.FUNCION; 
        this.modificadores = mod;
        this.tipo = t;                
        this.linea =l;
        this.columna = c;
    }
    
    public void setSobreescrito(boolean f)
    {
        this.sobreescito = f;
    }
    
    public void setInstrucciones(Instruccion ins)
    {
        this.instrucciones = ins;
    }

    @ Override
    public Object ejectuar(Entorno entorno) 
    {        
        return entorno.insertar(this);        
    }

    @Override
    public int linea() {
        return linea;
    }

    @Override
    public int columna() {
        return columna;
    }
    
    
    
    /*
    Para determinar que es un metodo principal tiene que ser
    *Modificadores: estatico publico
    *Nombre: Main
    *Tipo: void
    */
    public boolean isPrincipal()
    {   
        /*Verificamos que tenga modificadores*/
        if(modificadores==null)
        {            
            return false;
        }
        /*Verificamos que tenga los dos modificadores: static y public*/
        if(modificadores.size()!=2)
        {
            return false;
        }
        String modificador1 = modificadores.get(0);
        String modificador2 = modificadores.get(1);
        if(!(modificador1.toLowerCase().equals("public") && modificador2.toLowerCase().equals("static")))
        {
            return false;
        }
        /*Verificar que sea de tipo void*/        
        if(!(tipo.nombreTipo().toLowerCase().equals("void")))
        {
            return false;
        }
        /*Verificamos que tenga id main*/
        if(!(id.toLowerCase().equals("main$")))
        {
            return false;
        }        
        return true;
    }

    
    /*Recibimos un entorno pero sólo se utiliza para poder obtener 
    el entorno global.*/
    
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
    
    public boolean estaSobreescrito()
    {
        return sobreescito;
    }
    
}
