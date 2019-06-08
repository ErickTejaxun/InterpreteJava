package Analisis;
import java_cup.runtime.Symbol;
import java.util.ArrayList;
import Analisis.lexema;
import Utilidades.ErrorC;

%%
%{	    
    public ArrayList<ErrorC> listaErrores = new ArrayList(); // Lista para almacenar errores.
    public ArrayList<lexema> listaLexemas = new ArrayList(); // Lista para almacenar el flujo de palabras (tokens).

    public void adderror(int linea, int columna, String valor)
    {        
        listaErrores.add(new ErrorC(ErrorC.TipoError.LEXICO,valor, linea, columna));
    }

    public void addLexema(String tipo, String valor, int linea, int columna)
    {        
        listaLexemas.add(new lexema(tipo, valor, linea, columna));	            
    } 
    public void Imprimir(String cadena)
    {
        System.out.println(cadena);
    }   
 

%}
%class scanner /*Nombre de la clase a generar.%cupsym simbolos*/
%unicode /*Caracteres unicode*/
%public /*Se generará una clase pública.*/
%cup  /*Implementación con CUP*/
%full
%line   /*Almacenar el número de linea actual.*/
%char   /* Contador de caracteres.*/
%ignorecase /*Indiferente entre mayusculas y minusculas*/
%eofval{ // Genera el simbolo de cierre.
	return new Symbol(sym.EOF);    
%eofval}

espacio = \t|\f|" "|\r|\n    // ER para capturar espacios, salto de línea, tabulaciones.
numero = ([0-9][0-9]*)       // ER para capturar números.
decimal= {numero}"."{numero} // Expresión 
letra = ([a-zA-Z]|"ñ"|"á"|"é"|"í"|"ó"|"ú")
InputCharacter = [^\r\n]
LineTerminator = \r|\n|\r\n
id = (({letra}|"_")({letra}|{numero}|"_")*)
cadena = (("\"" [^*] ~"\"") | ("\“" [^*] ~"\”"))
cadCaracter = ("'" [^*] ~"'")
direccionWindows= ("\"" ({letra}":"("\\"({id}|{espacio}|"_"|"-"|{numero})+)+"."{id}) "\"")

comentario = {TraditionalComment} | {EndOfLineComment} | 
          {DocumentationComment}

TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/*" "*"+ [^/*] ~"*/"
si = ("if")
sino = ("else")
sinosi={sino}({comentario}|{espacio})*(si)
%state comentarioSimple, comentarioMulti

%%
<YYINITIAL>
{
[\n] { yychar=0;}
{espacio}
{
    //Imprimir("Salto de linea");
}
{comentario} 
{
    Imprimir(yytext());
}

"int"   {
            addLexema("reservada", yytext(), yyline, yychar);            
            return  new Symbol(sym.tint, yychar, yyline, yytext());
        }
"double"   {
            addLexema("reservada", yytext(), yyline, yychar);            
            return  new Symbol(sym.tdouble, yychar, yyline, yytext());
        }        

"char"  {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.tchar, yychar, yyline, yytext());
            }

"bool"   {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.tbool, yychar, yyline, yytext());
            }        

"string"  {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.tstring, yychar, yyline, yytext());
            }   

"printable"  {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.printable, yychar, yyline, yytext());
            }

"println"  {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.print, yychar, yyline, yytext());
            }                
"false"  {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.booleano, yychar, yyline, false);
            }     
"true"  {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.booleano, yychar, yyline, true);
            }  
"while"  {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.mientras, yychar, yyline, yytext());
            } 
"for"  {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.para, yychar, yyline, yytext());
            }              
"break"  {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.romper, yychar, yyline, yytext());
            }  
"continue"  {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.continuar, yychar, yyline, yytext());
            }                             
{si}  {
        addLexema("reservada", yytext(), yyline, yychar);
        return  new Symbol(sym.si, yychar, yyline, yytext());
      }                                         
{sino}  {
        addLexema("reservada", yytext(), yyline, yychar);
        return  new Symbol(sym.sino, yychar, yyline, yytext());
      }                   
"++"  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.aumento, yychar, yyline, yytext());             
        }                    
"--"  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.decremento, yychar, yyline, yytext());             
        }                            
"!="  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.desigual, yychar, yyline, yytext());             
        }             
"=="  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.igualigual, yychar, yyline, yytext());             
        } 
">="  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.mayorigual, yychar, yyline, yytext());             
        }
"<="  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.menorigual, yychar, yyline, yytext());             
        }         
">"  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.mayor, yychar, yyline, yytext());             
        } 
"<"  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.menor, yychar, yyline, yytext());             
        }         
"!"  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.not, yychar, yyline, yytext());             
        }
"||"  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.or, yychar, yyline, yytext());             
        }
"&&"  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.and, yychar, yyline, yytext());             
        }

";"     {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.puntocoma, yychar, yyline, yytext());             
        } 
"("  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.pari, yychar, yyline, yytext());             
        }

")"  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.pard, yychar, yyline, yytext());             
    }        
"?"  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.interrogante, yychar, yyline, yytext());             
    } 

"{"  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.llavei, yychar, yyline, yytext());             
        }           
"="  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.igual, yychar, yyline, yytext());             
        } 
":"  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.dospuntos, yychar, yyline, yytext());             
        }                 
"}"  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.llaved, yychar, yyline, yytext());             
        }
"%"  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.modulo, yychar, yyline, yytext());             
        }             
"+"  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.suma, yychar, yyline, yytext());             
        }           
 "-"  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.menos, yychar, yyline, yytext());             
        }   
"*"  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.multi, yychar, yyline, yytext());             
        }   
"/"  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.div, yychar, yyline, yytext());             
        }     
"^"  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.potencia, yychar, yyline, yytext());             
        }                                            
{id}  {  
            
            addLexema("Identificador", yytext(), yyline, yychar);  	        
            return new Symbol(sym.id, yychar, yyline, yytext().toLowerCase());             
        }         
{cadCaracter}  
        {  
            
            addLexema("Identificador", yytext(), yyline, yychar);  	        
            return new Symbol(sym.caracter, yychar, yyline,yytext().substring(1, yytext().length()-1).charAt(0));             
        }
{cadena}  
        {  
            
            addLexema("Identificador", yytext(), yyline, yychar);  	        
            return new Symbol(sym.cadena, yychar, yyline,yytext().substring(1, yytext().length()-1));             
        }                   
{numero}  {  
            
            addLexema("Identificador", yytext(), yyline, yychar);  	        
            return new Symbol(sym.entero, yychar, yyline,Integer.parseInt(yytext()));             
        }  
{decimal}  {  
            
            addLexema("Identificador", yytext(), yyline, yychar);  	        
            return new Symbol(sym.decimal, yychar, yyline,Double.parseDouble(yytext()));             
        }                       
.		{
            System.out.println("Caracter ilegal: " + yytext()+" Linea : "+yyline +" Columna: "+yychar); 
            adderror(yyline, yychar, yytext());
        }
<comentarioMulti> "*/"  
                {yybegin(YYINITIAL);} 
<comentarioMulti> . 
                { /**/}
<comentarioSimple> "\n"
                {yybegin(YYINITIAL);}
<comentarioSimple> .
                    {/**/}            	
}


		
