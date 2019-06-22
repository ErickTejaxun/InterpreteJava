package Analisis.Grafica;
import java_cup.runtime.Symbol;
import java.util.ArrayList;
import Analisis.lexema;
import Utilidades.ErrorC;

%%
%{	    
    public ArrayList<ErrorC> listaErrores = new ArrayList(); // Lista para almacenar errores.
    public ArrayList<lexema> listaLexemas = new ArrayList(); // Lista para almacenar el flujo de palabras (tokens).
    public String cadena ="";
    public int linea=0,columna = 0;
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
%class scannerReporte /*Nombre de la clase a generar.%cupsym simbolos*/
%unicode /*Caracteres unicode*/
%public /*Se generará una clase pública.*/
%cup  /*Implementación con CUP*/
%full
%line   /*Almacenar el número de linea actual.*/
%char   /* Contador de caracteres.%ignorecase Indiferente entre mayusculas y minusculas*/
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
/*cadena = (("\"" [^*] ~"\"") | ("\“" [^*] ~"\”") | ("\"\""))*/
comilla = ("\"")
cadCaracter = ("'" [^*] ~"'")|("‘" [^*] ~"’")
direccionWindows= ("\"" ({letra}":"("\\"({id}|{espacio}|"_"|"-"|{numero})+)+"."{id}) "\"")

comentario = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"|"/**" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/*"+"*"+ [^/*] ~"*/"|"/**"+ [^/*] ~"*/"|"/**"+ [^/*] ~"**/"
si = ("if")
sino = ("else")
sinosi={sino}({comentario}|{espacio})*(si)
%state cad1, cad2

%%

<cad1>
{
    [^]
    {  
        switch(yytext())
        {
            case "\\":          
                yybegin(cad2);
            break;
            case "\"":      
                yybegin(YYINITIAL);
                return new Symbol(sym.cadena, columna, linea ,cadena);             
            default:
                cadena += yytext();
            break;                
        }                  
    } 
}

<cad2>
{
    [^]
    {
        switch(yytext())
        {
            case "\'":
                cadena += "\'";
            break;            
            case "\"":
                cadena += "\"";
            break;  
            case "?":
                cadena += "?";
            break;
            case "\\":
                cadena += "\\";
            break;  
            case "0":                
            break;  
            case "a":                
            break;   
            case "b":                
                cadena = cadena.substring(0, cadena.length()-1);
            break; 
            case "v":   
                String partes[] = cadena.split("\n");
                String subcadena = "";
                for(int i = 0 ; i < partes.length-1; i++)
                {
                    subcadena += partes[i];
                }         
                String tabulaciones[] = partes[partes.length].split("\t");
                for(int i = 0; i< tabulaciones.length; i++)
                {
                    subcadena += "\t";
                }
                cadena = subcadena;                                                       
            break;             
            case "n":                
                cadena = cadena + "\n";
            break; 
            case "t":                
                cadena = cadena + "\t";
            break;             
            case "r":       
                partes = cadena.split("\n");
                subcadena = "";
                for(int i = 0 ; i < partes.length-1; i++)
                {
                    subcadena += partes[i];
                }                                       
                cadena = subcadena;
            break;                                                                                                       
            default:
                Utilidades.Singlenton.registrarError("\\"+yytext(), "Carácter ilegal. Ha sido eliminado.", ErrorC.TipoError.SEMANTICO,yyline, yychar);
            break;                
        }
        yybegin(cad1);                  
    }
}

<YYINITIAL>
{
[\n] { yychar=0;}

{espacio}
{
    //Imprimir("Salto de linea");
}
{comentario} 
{
    //Imprimir(yytext());
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

"boolean"   {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.tbool, yychar, yyline, yytext());
            }        

"String"  {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.tstring, yychar, yyline, yytext());
            }   

"printable"  {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.printable, yychar, yyline, yytext());
            }
"print"  {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.print, yychar, yyline, yytext());
            }                            


"println"  {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.println, yychar, yyline, yytext());
            } 
"false"  {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.booleano, yychar, yyline, false);
            }     
"void"  {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.tvoid, yychar, yyline, yytext());
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
"toString"  {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.tostring, yychar, yyline, yytext());
            }
"toLowerCase"  {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.tolower, yychar, yyline, yytext());
            }
"toUpperCase"  {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.touper, yychar, yyline, yytext());
            }                                                
"break"  {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.romper, yychar, yyline, yytext());
            }  
"continue"  {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.continuar, yychar, yyline, yytext());
            }    
"pow"  {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.potencia, yychar, yyline, yytext());
            } 
"null"  {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.nulo, yychar, yyline, yytext());
            }              
","  {
            addLexema("reservada", yytext(), yyline, yychar);
            return  new Symbol(sym.coma, yychar, yyline, yytext());
            }                                                
{si}  {
        addLexema("reservada", yytext(), yyline, yychar);
        return  new Symbol(sym.si, yychar, yyline, yytext());
      }                                         
{sino}  {
        addLexema("reservada", yytext(), yyline, yychar);
        return  new Symbol(sym.sino, yychar, yyline, yytext());
      } 
"."  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.punto, yychar, yyline, yytext());             
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
"abstract"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.abstracto, yychar, yyline, yytext());
        }   
"case"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.caso, yychar, yyline, yytext());
        }                         
"catch"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.cat, yychar, yyline, yytext());
        }  
"class"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.clase, yychar, yyline, yytext());
        }                 
"default"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.defecto, yychar, yyline, yytext());
        } 
"do"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.hacer, yychar, yyline, yytext());
        } 
"extends"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.extiende, yychar, yyline, yytext());
        }                           
"final"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.final_, yychar, yyline, yytext());
        }         
"graph"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.graph, yychar, yyline, yytext());
        }    
"import"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.importar, yychar, yyline, yytext());
        }  
"instanceof"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.instanceof_, yychar, yyline, yytext());
        }  
"new"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.nuevo, yychar, yyline, yytext());
        } 
"private"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.privado, yychar, yyline, yytext());
        }                                
"protected"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.protegido, yychar, yyline, yytext());
        }  
"public"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.publico, yychar, yyline, yytext());
        }   
"return"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.retorno, yychar, yyline, yytext());
        }     
"read_file"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.leerarchivo, yychar, yyline, yytext());
        }  
"static"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.estatico, yychar, yyline, yytext());
        }  
"str"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.str_, yychar, yyline, yytext());
        } 
"super"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.super_, yychar, yyline, yytext());
        } 
"switch"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.switch_, yychar, yyline, yytext());
        }  
"this"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.este, yychar, yyline, yytext());
        }
"@"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.arroba, yychar, yyline, yytext());
        }          
"Override"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.sobrescrito, yychar, yyline, yytext());
        }                  
"toChar"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.tochar, yychar, yyline, yytext());
        }   
"toDouble"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.todouble, yychar, yyline, yytext());
        } 
"toInt"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.toint, yychar, yyline, yytext());
        }  
"try"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.try_, yychar, yyline, yytext());
        } 
"write_file"  {
            addLexema("simbolo", yytext(), yyline, yychar);
            return new Symbol(sym.escribir, yychar, yyline, yytext());
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
"["  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.corchetei, yychar, yyline, yytext());             
        }  
"]"  {  
            addLexema("simbolo", yytext(), yyline, yychar);  	        
            return new Symbol(sym.corcheted, yychar, yyline, yytext());             
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
            return new Symbol(sym.xor, yychar, yyline, yytext());             
        }   
{comilla}  {  
            cadena = "";                    
            yybegin(cad1);
            linea = yyline;
            columna = yychar;
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
/*{cadena}  
        {  
            
            addLexema("Identificador", yytext(), yyline, yychar);  	        
            return new Symbol(sym.cadena, yychar, yyline,yytext().substring(1, yytext().length()-1));
        }                   
*/        
{numero}  {  
            
            addLexema("Identificador", yytext(), yyline, yychar);  	  
            if(Long.parseLong(yytext())> Utilidades.Singlenton.maxInt && Long.parseLong(yytext()) < Utilidades.Singlenton.minInt )
            {
                Utilidades.Singlenton.registrarError("Entero", "Número entero demasiado grande", ErrorC.TipoError.SEMANTICO,yyline, yychar);
                return new Symbol(sym.entero, yychar, yyline,0);             
            }   
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
}


		
