//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "Gramatica.y"
package com.tp.compiladores;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
//#line 31 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short IF=257;
public final static short THEN=258;
public final static short ELSE=259;
public final static short END_IF=260;
public final static short OUT=261;
public final static short FUN=262;
public final static short RETURN=263;
public final static short I8=264;
public final static short F32=265;
public final static short WHEN=266;
public final static short CTE_ENTERA=267;
public final static short CTE_FLOTANTE=268;
public final static short ID=269;
public final static short CONTINUE=270;
public final static short CADENA_MULT=271;
public final static short ASIGNACION=272;
public final static short MAYOR_IGUAL=273;
public final static short MENOR_IGUAL=274;
public final static short DISTINTO=275;
public final static short CONST=276;
public final static short TOF32=277;
public final static short BREAK=278;
public final static short FOR=279;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    1,    2,    2,    2,    2,    2,
    2,    4,    4,    4,    4,    4,    4,    4,    9,    9,
   11,   11,   11,   11,   11,   11,   11,    6,    6,    7,
    7,    7,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   15,    8,    8,    8,    8,   16,   16,   17,   17,
   17,    5,    5,    5,   20,   20,   20,   20,   20,   23,
   23,   23,   10,   10,   21,   21,   25,   25,   25,   22,
   22,   22,   22,   22,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   22,   22,   22,   27,   27,   27,   26,
   26,   12,   12,   13,   13,   18,   18,   18,   18,   18,
   18,   18,   28,   28,   28,   28,   30,   30,   31,   31,
   32,   33,   33,   19,   19,   19,   19,   19,   24,   24,
   24,   35,   35,   35,   35,   35,   35,   34,   34,   34,
   34,   34,   34,   34,   34,   34,   34,   34,   34,   34,
   34,   34,   34,   36,   36,   36,   36,   36,   36,   37,
   37,   40,   38,   38,   41,   41,    3,    3,    3,    3,
   39,   39,   39,   39,   29,   29,   29,   29,   29,
};
final static short yylen[] = {                            2,
    4,    3,    2,    2,    1,    2,    2,    2,    1,    1,
    1,    3,    3,    1,    3,    1,    2,    2,    1,    3,
    3,    4,    3,    4,    2,    2,    2,    1,    1,    3,
    1,    2,    8,    6,    5,    8,    6,    5,    7,    5,
    4,    2,    4,    3,    3,    4,    2,    1,    2,    2,
    1,    1,    1,    2,    4,    4,    4,    5,    3,    3,
    2,    2,    4,    2,    4,    3,    1,    2,    1,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    1,    3,    3,    3,    2,    2,    3,    5,    4,
    3,    1,    1,    1,    1,    5,    5,    5,    4,    4,
    4,    2,    2,    2,    1,    1,    3,    2,    1,    3,
    1,    3,    3,    3,    3,    3,    2,    2,    3,    2,
    2,    1,    1,    1,    1,    1,    1,    3,    3,    4,
    3,    4,    3,    3,    3,    4,    3,    4,    3,    3,
    2,    2,    2,    2,    2,    2,    1,    1,    1,    9,
    8,    3,    3,    3,    3,    3,    2,    4,    5,    6,
    3,    3,    3,    4,    1,    2,    3,    4,    4,
};
final static short yydefred[] = {                         0,
    0,    5,    0,    0,    4,    0,    0,    0,    0,   28,
   29,    0,   95,   94,    0,  124,  125,  127,    0,    0,
    0,   92,   93,    0,    0,  122,  123,  126,    0,    0,
    0,    0,    0,    0,   14,   16,    0,    0,    0,    0,
   52,   53,    0,    0,    0,    0,   82,    0,    0,    0,
    0,    0,  108,  117,  118,  142,   64,  143,    0,    0,
    0,    0,    0,   42,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   32,    0,   86,    0,    0,    0,
    0,   19,    0,    0,    0,    0,    0,    0,    0,    0,
   51,    0,    0,    2,    6,    7,    8,    0,   18,    0,
   17,    0,    0,    0,    0,    0,    0,    0,   54,    0,
    0,    0,    0,    0,   61,    0,    0,    0,    0,  109,
  111,    0,  121,  141,    0,  157,    0,  107,    0,   48,
    0,    0,    0,   60,   59,    0,    0,    0,    0,  152,
   74,   76,   75,   77,   30,   69,   87,    0,   67,    0,
    0,   70,   71,    0,    0,    0,    0,    0,   27,   25,
   15,    0,    0,   91,    0,    0,    0,    0,  102,    0,
    0,    0,    0,    1,   50,   49,   45,   66,  119,   13,
   12,    0,   84,   85,   83,    0,    0,    0,   44,    0,
    0,    0,    0,   78,   80,   79,   81,   72,   73,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  165,    0,
    0,  116,    0,  114,  115,    0,    0,  147,  148,  149,
    0,    0,   65,    0,   47,    0,    0,   56,    0,   57,
   55,   68,    0,   88,  158,  138,   21,   23,    0,   20,
   90,    0,    0,    0,    0,    0,    0,    0,   46,   43,
    0,    0,    0,  130,   63,    0,  166,    0,    0,  103,
  110,  104,    0,  156,  155,  145,  144,  146,    0,    0,
   38,    0,    0,   58,    0,   22,   24,  153,  154,    0,
    0,    0,    0,    0,   35,    0,    0,    0,    0,  167,
  113,  112,    0,  159,    0,   37,   89,    0,    0,   96,
   97,   98,    0,   34,  169,  168,  160,    0,    0,    0,
    0,    0,    0,    0,    0,   36,  164,  151,    0,   33,
  150,
};
final static short yydgoto[] = {                          3,
    4,   96,   30,   31,   32,   33,   34,   35,   81,   36,
   82,   37,   38,   39,   40,  133,   90,   91,   41,   42,
   43,   44,   45,   46,  150,   47,   77,  210,  211,   48,
  121,  122,  215,   49,   50,  221,   51,  168,  281,   52,
  126,
};
final static short yysindex[] = {                      -209,
 -106,    0,    0,   -5,    0,  371,  344,   30,  -35,    0,
    0,  344,    0,    0,  470,    0,    0,    0, -152,   32,
   51,    0,    0,   42,  429,    0,    0,    0,  -25,  280,
  280,  280,  255,   36,    0,    0, -184,  121,  -18,   67,
    0,    0,   50,  529,   -4, -133,    0,  353,  106, -123,
   26,   26,    0,    0,    0,    0,    0,    0,  185,   54,
  490,  -89,  -75,    0,  -19,  -10,  190,  -34,  230,  216,
  334,    8,   65,  -26,    0,  368,    0,  293,  -74, -184,
  -16,    0,   24,   19,    9,  403,  171,   42,   42,  177,
    0,  281,  285,    0,    0,    0,    0,  271,    0,  291,
    0,  373,  101,  103,  105, -216,   92,   25,    0,  384,
  394,  414,  300,  280,    0,  455, -245,  225,  115,    0,
    0, -101,    0,    0,  122,    0,  117,    0,  356,    0,
  351,  133,  136,    0,    0,   33, -184,  428,  536,    0,
    0,    0,    0,    0,    0,    0,    0, -184,    0,  303,
   26,    0,    0,  688, -184,  763,  769,  -30,    0,    0,
    0, -152,  688,    0,  763,  727,  143,  358,    0,  -93,
  478,  403,  403,    0,    0,    0,    0,    0,    0,    0,
    0,  113,    0,    0,    0,  688,  763,  769,    0,  297,
  302,  389,  372,    0,    0,    0,    0,    0,    0,  688,
 -184,  763,  769,  325,  319,  421,  -41,  225,    0,  375,
  225,    0,  353,    0,    0,  -51,  402,    0,    0,    0,
  151,  237,    0,  -86,    0,  -45,  444,    0,  457,    0,
    0,    0,  169,    0,    0,    0,    0,    0,  377,    0,
    0, -219,  250,  678,  741,  749,  688,  769,    0,    0,
  -86,  -45,  469,    0,    0,  260,    0, -184,  475,    0,
    0,    0, -167,    0,    0,    0,    0,    0, -184,  484,
    0,  517,  -86,    0,  523,    0,    0,    0,    0,  373,
  514,  521,  526,  534,    0,  541,  -86,  549,  561,    0,
    0,    0,  565,    0,  567,    0,    0,  330,  -12,    0,
    0,    0,  575,    0,    0,    0,    0,  -86,    0,  688,
  308,  769,  601,  388,  -86,    0,    0,    0,  617,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  614,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  674,    3,
    6,   20,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  564,    0,  614,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  559,  569,    0,
    0,    0, -114,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -33,    0,   23,   81,   15,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  614,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   88,   93,  336,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  357,
    0,  369,  370,    0,    0,    0,    0,  570,    0,    0,
  574,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  383,  385,    0,    0,
  578,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  162,  188,  199,    0,    0,  582,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  665,  631,
    0,  633,    0,    0,  588,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,  602, -109,  477,  568,  -29,   -1,    0,    0,    0,
  555,  -15,  130,    0,    0,  -66,  516,  -81,    0,    0,
    0,  -44,    0,   14,  489,    0,    0,  231, -108,    0,
    0,  519,    0,   16,  -14,    0,   12,    0,    0,    0,
  -40,
};
final static int YYTABLESIZE=816;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         76,
   78,   22,   10,   23,   65,   11,   74,  139,  120,   72,
   67,  127,   22,   75,   23,  218,  220,  257,    5,    9,
   62,  131,  105,  106,  139,   66,  205,  162,  112,  113,
   22,  100,   23,  157,   25,  132,  209,   22,  166,   23,
   93,  193,  161,  209,   76,   78,    1,  278,  170,  279,
   13,   14,  186,  137,   26,   28,   27,  148,   26,    2,
   20,  188,  155,  137,  164,  192,   75,  169,  203,   63,
  145,   83,   73,   26,   70,   22,   93,   23,  132,   71,
  140,   25,   13,   14,   22,  151,   23,  182,  291,  139,
   84,  228,  292,   73,  101,   70,   22,  201,   23,   94,
   71,   26,   28,   27,  107,  147,  108,   22,  109,   23,
  235,  266,  268,   26,   28,   27,   79,   24,  114,   80,
   76,  134,  105,  112,  115,  246,  209,   10,  135,  209,
   11,   25,  124,  140,   22,   58,   23,  248,   76,  209,
  105,  112,  239,  120,    9,  137,  123,   76,  125,  105,
  112,   26,   28,   27,  212,   76,   78,  213,  214,  272,
  139,  119,  103,   22,   86,   23,  102,  104,  128,   75,
   76,  105,  112,   13,   14,  244,  227,   10,   11,  226,
   26,   28,   27,   20,   76,  286,  105,  112,   58,  137,
  119,  258,   13,   14,  271,  129,  132,  158,  138,  142,
  144,   99,  149,  134,   67,  153,  269,  156,  159,  160,
  135,   22,  165,   23,   86,  140,  189,  148,   10,   11,
   69,  285,  132,  130,  139,   13,   14,  100,   76,  105,
  112,   76,  112,   64,   68,  187,  237,  238,  101,  195,
  197,  199,  202,  296,   10,   11,  217,  134,  135,  130,
    6,    7,   21,  312,  313,    8,    9,  304,   10,   11,
   12,   13,   14,   15,  119,  298,  229,   16,   17,   18,
   19,   20,   22,   21,   23,  265,   68,  232,  316,   22,
  137,   23,  311,  314,  236,  320,   99,  167,   10,   11,
   13,   14,  163,  130,   76,  174,  112,    6,    7,  245,
   20,  177,    8,    9,   85,   10,   11,   12,   13,   14,
   15,  165,  100,   99,   16,   17,   18,   19,   20,   25,
   21,  178,   22,  101,   23,  179,   16,   17,   18,  180,
  254,   13,   14,  146,  138,   22,  259,   23,  134,   26,
   28,   27,   22,  234,   23,  135,  233,    6,    7,  181,
  140,  270,    8,    9,   85,   10,   11,   12,   13,   14,
   15,   22,  149,   23,   16,   17,   18,   19,   20,  183,
   21,  184,   22,  185,   23,  222,  131,  116,    7,   13,
   14,  247,    8,   61,   85,   92,   22,  289,   23,   20,
  216,  206,  119,   16,   17,   18,  223,  133,  293,  207,
   21,  225,   86,   26,   28,   27,  116,    7,  224,  129,
  128,    8,  253,   85,  242,  252,  243,   99,   99,  216,
  206,  249,   99,  136,   99,  132,  250,  165,  207,   21,
   99,   99,   26,   28,   27,   13,   14,  146,  260,   99,
   99,  262,   25,  100,  100,   22,  251,   23,  100,  255,
  100,   13,   14,   56,  101,  101,  100,  100,  131,  101,
  264,  101,   26,   28,   27,  100,  100,  101,  101,  103,
   22,   22,   23,   23,  104,  118,  101,  101,  256,  133,
  116,    7,   13,   14,  141,    8,  230,   85,   26,   28,
   27,  129,  128,  117,  206,   57,   13,   14,  136,  261,
   88,  273,  207,   13,   14,  136,   20,  132,   21,   73,
   98,   70,   22,   72,   23,  274,   71,   73,  280,   70,
   22,   72,   23,   68,   71,   86,  287,   74,  288,   26,
   28,   27,   22,  290,   23,    6,    7,   26,   28,   27,
    8,    9,  294,   10,   11,   12,   13,   14,   15,   26,
   28,   27,   16,   17,   18,   19,   20,  295,   21,   13,
   14,  154,  172,  297,   88,   88,   13,   14,  200,   20,
  110,   22,  299,   23,  317,  111,   20,  110,   22,  300,
   23,  303,  111,   88,  301,   13,   14,  136,   26,   28,
   27,   89,  302,  131,  231,   20,  309,   14,  310,   59,
   13,   14,  143,  175,  176,   29,   20,  305,  116,    7,
   13,   14,   60,    8,  133,  120,   16,   17,   18,  306,
   20,  117,  191,  307,  308,   87,  129,  128,   53,   54,
   55,   95,  315,   97,   13,   14,  152,   13,   14,   56,
  136,  318,  132,  276,  277,   16,   17,   18,  172,  172,
   13,   14,  194,  173,  319,   89,   89,  321,    6,    7,
   13,   14,  196,    8,    9,   85,   10,   11,   12,   13,
   14,  171,   31,    3,   89,   16,   17,   18,   19,   20,
   13,   14,  198,   11,   59,  208,   62,  175,  176,  161,
   97,  162,  219,    9,  105,   13,   14,   60,  106,   92,
   41,   16,   17,   18,   40,   20,   95,   95,  190,   95,
   39,   95,   53,   54,   55,  204,  240,   73,  282,   70,
   22,  275,   23,  163,   71,   67,    0,   73,    0,   70,
   22,  263,   23,   67,   71,    0,    0,    0,   68,  173,
  173,   69,   16,   17,   18,   59,   68,    0,    0,  205,
   16,   17,   18,    0,    0,    0,   13,   14,   60,    0,
    0,    0,   16,   17,   18,    0,   20,  241,  110,   22,
    0,   23,    0,  111,    0,  208,    0,    0,  208,    0,
  120,  283,  103,   22,    0,   23,    0,  104,  267,  284,
  110,   22,    0,   23,    0,  111,    0,    0,    0,    0,
    0,   16,   17,   18,  103,   22,    0,   23,    0,  104,
  110,   22,    0,   23,    0,  111,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         15,
   15,   43,    0,   45,   40,    0,   58,   41,  123,   44,
  256,   52,   43,   15,   45,  125,  125,   59,  125,    0,
    7,   41,   38,   38,   69,   12,  272,   44,   44,   44,
   43,   33,   45,   78,   40,   65,  118,   43,   83,   45,
   25,  108,   59,  125,   60,   60,  256,  267,   40,  269,
  267,  268,  269,   69,   60,   61,   62,   73,   44,  269,
  277,  106,   78,   41,   41,   41,   68,   59,  113,   40,
   72,   40,   40,   59,   42,   43,   61,   45,  108,   47,
   69,   40,  267,  268,   43,   74,   45,  102,  256,  123,
   40,   59,  260,   40,   59,   42,   43,  113,   45,  125,
   47,   60,   61,   62,  123,   41,   40,   43,   59,   45,
  151,  221,  221,   60,   61,   62,  269,  123,  123,  272,
  136,   41,  138,  139,  258,  170,  208,  125,   41,  211,
  125,   40,  256,   41,   43,    6,   45,  182,  154,  221,
  156,  157,  158,  258,  125,  123,   41,  163,  123,  165,
  166,   60,   61,   62,  256,  171,  171,  259,  260,  226,
  205,   40,   42,   43,  123,   45,   37,   47,  258,  171,
  186,  187,  188,  267,  268,  269,   41,  264,  265,   44,
   60,   61,   62,  277,  200,  252,  202,  203,   59,  205,
   40,  207,  267,  268,  224,  271,  226,  272,   69,   70,
   71,   40,   73,  123,  256,   76,  222,   78,   79,   80,
  123,   43,   83,   45,  123,  123,  125,  233,  264,  265,
  272,  251,  252,  269,  258,  267,  268,   40,  244,  245,
  246,  247,  248,  269,  269,  106,  267,  268,   40,  110,
  111,  112,  113,  273,  264,  265,  125,  258,   59,  269,
  256,  257,  279,  298,  267,  261,  262,  287,  264,  265,
  266,  267,  268,  269,   40,  280,  137,  273,  274,  275,
  276,  277,   43,  279,   45,  125,  269,  148,  308,   43,
  258,   45,  298,  299,  155,  315,  125,  269,  264,  265,
  267,  268,  269,  269,  310,  125,  312,  256,  257,  170,
  277,  125,  261,  262,  263,  264,  265,  266,  267,  268,
  269,  182,  125,   59,  273,  274,  275,  276,  277,   40,
  279,   41,   43,  125,   45,   41,  273,  274,  275,   59,
  201,  267,  268,  269,  205,   43,  207,   45,  258,   60,
   61,   62,   43,   41,   45,  258,   44,  256,  257,   59,
  258,  222,  261,  262,  263,  264,  265,  266,  267,  268,
  269,   43,  233,   45,  273,  274,  275,  276,  277,  269,
  279,  269,   43,  269,   45,  259,   41,  256,  257,  267,
  268,  269,  261,   40,  263,  271,   43,  258,   45,  277,
  269,  270,   40,  273,  274,  275,   41,   41,  269,  278,
  279,  269,  123,   60,   61,   62,  256,  257,   58,   41,
   41,  261,   41,  263,  272,   44,   59,  256,  257,  269,
  270,  125,  261,   41,  263,   41,  125,  298,  278,  279,
  269,  270,   60,   61,   62,  267,  268,  269,  208,  278,
  279,  211,   40,  256,  257,   43,   58,   45,  261,  125,
  263,  267,  268,  269,  256,  257,  269,  270,  123,  261,
   59,  263,   60,   61,   62,  278,  279,  269,  270,   42,
   43,   43,   45,   45,   47,  123,  278,  279,   58,  123,
  256,  257,  267,  268,  269,  261,   59,  263,   60,   61,
   62,  123,  123,  269,  270,  125,  267,  268,  269,  125,
   24,   58,  278,  267,  268,  123,  277,  123,  279,   40,
  256,   42,   43,   44,   45,   59,   47,   40,  269,   42,
   43,   44,   45,  269,   47,  123,   58,   58,  269,   60,
   61,   62,   43,   59,   45,  256,  257,   60,   61,   62,
  261,  262,   59,  264,  265,  266,  267,  268,  269,   60,
   61,   62,  273,  274,  275,  276,  277,   41,  279,  267,
  268,  269,   86,   41,   88,   89,  267,  268,  269,  277,
   42,   43,   59,   45,  267,   47,  277,   42,   43,   59,
   45,   41,   47,  107,   59,  267,  268,  269,   60,   61,
   62,   24,   59,  258,   59,  277,  267,  268,  269,  256,
  267,  268,  269,   88,   89,    4,  277,   59,  256,  257,
  267,  268,  269,  261,  258,   48,  273,  274,  275,   59,
  277,  269,  107,   59,   58,   24,  258,  258,  258,  259,
  260,   30,   58,   32,  267,  268,  269,  267,  268,  269,
  258,   41,  258,  267,  268,  273,  274,  275,  172,  173,
  267,  268,  269,   86,  267,   88,   89,   41,  256,  257,
  267,  268,  269,  261,  262,  263,  264,  265,  266,  267,
  268,  269,   59,    0,  107,  273,  274,  275,  276,  277,
  267,  268,  269,  125,  256,  118,  123,  172,  173,   59,
   89,   59,  125,  125,  125,  267,  268,  269,  125,  271,
  123,  273,  274,  275,  123,  277,   42,   43,  107,   45,
  123,   47,  258,  259,  260,  114,  162,   40,   41,   42,
   43,  233,   45,   59,   47,  256,   -1,   40,   -1,   42,
   43,  213,   45,  256,   47,   -1,   -1,   -1,  269,  172,
  173,  272,  273,  274,  275,  256,  269,   -1,   -1,  272,
  273,  274,  275,   -1,   -1,   -1,  267,  268,  269,   -1,
   -1,   -1,  273,  274,  275,   -1,  277,   41,   42,   43,
   -1,   45,   -1,   47,   -1,  208,   -1,   -1,  211,   -1,
  213,   41,   42,   43,   -1,   45,   -1,   47,  221,   41,
   42,   43,   -1,   45,   -1,   47,   -1,   -1,   -1,   -1,
   -1,  273,  274,  275,   42,   43,   -1,   45,   -1,   47,
   42,   43,   -1,   45,   -1,   47,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=279;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'","';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"IF","THEN","ELSE","END_IF","OUT","FUN",
"RETURN","I8","F32","WHEN","CTE_ENTERA","CTE_FLOTANTE","ID","CONTINUE",
"CADENA_MULT","ASIGNACION","MAYOR_IGUAL","MENOR_IGUAL","DISTINTO","CONST",
"TOF32","BREAK","FOR",
};
final static String yyrule[] = {
"$accept : Programa",
"Programa : Nombre_programa '{' ListSentencias '}'",
"Programa : Nombre_programa ListSentencias '}'",
"Programa : Nombre_programa ListSentencias",
"Programa : error '}'",
"Nombre_programa : ID",
"ListSentencias : SentenciaControl ListSentencias",
"ListSentencias : SentenciaDeclarativa ListSentencias",
"ListSentencias : SentenciaEjecutable ListSentencias",
"ListSentencias : SentenciaEjecutable",
"ListSentencias : SentenciaControl",
"ListSentencias : SentenciaDeclarativa",
"SentenciaDeclarativa : Tipo ListVariables ';'",
"SentenciaDeclarativa : Tipo error ';'",
"SentenciaDeclarativa : Funcion",
"SentenciaDeclarativa : CONST ListCte ';'",
"SentenciaDeclarativa : WhenCondicion",
"SentenciaDeclarativa : ListVariables ';'",
"SentenciaDeclarativa : Tipo ';'",
"ListCte : AsigCte",
"ListCte : ListCte ',' AsigCte",
"AsigCte : ID ASIGNACION CTE_ENTERA",
"AsigCte : ID ASIGNACION signo CTE_ENTERA",
"AsigCte : ID ASIGNACION CTE_FLOTANTE",
"AsigCte : ID ASIGNACION signo CTE_FLOTANTE",
"AsigCte : ASIGNACION cte",
"AsigCte : ID ASIGNACION",
"AsigCte : ID cte",
"Tipo : I8",
"Tipo : F32",
"ListVariables : ID ',' ListVariables",
"ListVariables : ID",
"ListVariables : ID ListVariables",
"HeaderFuncion : Fun '(' Parametro ',' Parametro ')' ':' Tipo",
"HeaderFuncion : Fun '(' Parametro ')' ':' Tipo",
"HeaderFuncion : Fun '(' ')' ':' Tipo",
"HeaderFuncion : FUN '(' Parametro ',' Parametro ')' ':' Tipo",
"HeaderFuncion : FUN '(' Parametro ')' ':' Tipo",
"HeaderFuncion : FUN '(' ')' ':' Tipo",
"HeaderFuncion : Fun '(' Parametro ',' Parametro ')' ':'",
"HeaderFuncion : Fun '(' Parametro ')' ':'",
"HeaderFuncion : Fun '(' ')' ':'",
"Fun : FUN ID",
"Funcion : HeaderFuncion '{' Cuerpo '}'",
"Funcion : HeaderFuncion '{' '}'",
"Funcion : '{' Cuerpo '}'",
"Funcion : HeaderFuncion '{' ListSentencias '}'",
"Parametro : Tipo ID",
"Parametro : ID",
"Cuerpo : SentenciaEjecutable Cuerpo",
"Cuerpo : SentenciaDeclarativa Cuerpo",
"Cuerpo : SentenciaReturn",
"SentenciaEjecutable : Seleccion",
"SentenciaEjecutable : Asig",
"SentenciaEjecutable : Salida ';'",
"Asig : ID ASIGNACION Expresion ';'",
"Asig : ID ASIGNACION ID ';'",
"Asig : ID ASIGNACION cte ';'",
"Asig : ID ASIGNACION signo cte ';'",
"Asig : ID error ';'",
"HeaderWhen : WHEN Condicion THEN",
"HeaderWhen : Condicion THEN",
"HeaderWhen : WHEN Condicion",
"WhenCondicion : HeaderWhen '{' ListSentencias '}'",
"WhenCondicion : error '}'",
"Salida : OUT '(' CADENA_MULT ')'",
"Salida : '(' CADENA_MULT ')'",
"ParametroReal : cte",
"ParametroReal : signo cte",
"ParametroReal : ID",
"Expresion : ID signo ID",
"Expresion : ID signo cte",
"Expresion : Expresion signo ID",
"Expresion : Expresion signo cte",
"Expresion : ID '*' ID",
"Expresion : ID '/' ID",
"Expresion : ID '*' cte",
"Expresion : ID '/' cte",
"Expresion : Expresion '*' ID",
"Expresion : Expresion '/' ID",
"Expresion : Expresion '*' cte",
"Expresion : Expresion '/' cte",
"Expresion : ConversionExplicita",
"Expresion : cte signo ID",
"Expresion : cte '*' ID",
"Expresion : cte '/' ID",
"Expresion : ID ListParametrosInv",
"ListParametrosInv : '(' ')'",
"ListParametrosInv : '(' ParametroReal ')'",
"ListParametrosInv : '(' ParametroReal ',' ParametroReal ')'",
"ConversionExplicita : TOF32 '(' Expresion ')'",
"ConversionExplicita : TOF32 '(' ')'",
"signo : '+'",
"signo : '-'",
"cte : CTE_FLOTANTE",
"cte : CTE_ENTERA",
"SentenciaReturn : RETURN '(' ID ')' ';'",
"SentenciaReturn : RETURN '(' cte ')' ';'",
"SentenciaReturn : RETURN '(' Expresion ')' ';'",
"SentenciaReturn : RETURN '(' ID ')'",
"SentenciaReturn : RETURN '(' cte ')'",
"SentenciaReturn : RETURN '(' Expresion ')'",
"SentenciaReturn : RETURN ';'",
"ListSentenciasIf : SentenciaEjecutable ListSentenciasIf",
"ListSentenciasIf : SentenciaCorte ListSentenciasIf",
"ListSentenciasIf : SentenciaEjecutable",
"ListSentenciasIf : SentenciaCorte",
"HeaderIf : IF Condicion THEN",
"HeaderIf : error THEN",
"SentenciasIf : SentenciaEjecutable",
"SentenciasIf : '{' ListSentenciasIf '}'",
"CuerpoIf : SentenciasIf",
"CuerpoElse : ELSE CuerpoIf END_IF",
"CuerpoElse : ELSE CuerpoIf error",
"Seleccion : HeaderIf CuerpoIf END_IF",
"Seleccion : HeaderIf CuerpoIf CuerpoElse",
"Seleccion : HeaderIf CuerpoIf error",
"Seleccion : error ELSE",
"Seleccion : error END_IF",
"Condicion : '(' Comparacion ')'",
"Condicion : '(' Comparacion",
"Condicion : Comparacion ')'",
"Comparador : '<'",
"Comparador : '>'",
"Comparador : MAYOR_IGUAL",
"Comparador : MENOR_IGUAL",
"Comparador : '='",
"Comparador : DISTINTO",
"Comparacion : Expresion Comparador Expresion",
"Comparacion : Expresion Comparador cte",
"Comparacion : Expresion Comparador signo cte",
"Comparacion : cte Comparador Expresion",
"Comparacion : signo cte Comparador Expresion",
"Comparacion : Expresion Comparador ID",
"Comparacion : ID Comparador Expresion",
"Comparacion : cte Comparador ID",
"Comparacion : signo cte Comparador ID",
"Comparacion : ID Comparador cte",
"Comparacion : ID Comparador signo cte",
"Comparacion : ID Comparador ID",
"Comparacion : cte Comparador cte",
"Comparacion : Comparador error",
"Comparacion : error ID",
"Comparacion : error cte",
"ListSentenciasFor : ListSentenciasFor SentenciaEjecutable",
"ListSentenciasFor : ListSentenciasFor SentenciaControl",
"ListSentenciasFor : ListSentenciasFor SentenciaCorte",
"ListSentenciasFor : SentenciaControl",
"ListSentenciasFor : SentenciaEjecutable",
"ListSentenciasFor : SentenciaCorte",
"HeaderFor : FOR '(' AsigFor ';' CondicionFor ';' signo CTE_ENTERA ')'",
"HeaderFor : FOR '(' AsigFor ';' CondicionFor ';' CTE_ENTERA ')'",
"HeaderForID : ID ASIGNACION HeaderFor",
"AsigFor : ID ASIGNACION CTE_ENTERA",
"AsigFor : ID ASIGNACION ID",
"CuerpoFor : '{' ListSentenciasFor '}'",
"CuerpoFor : '{' '}' ';'",
"SentenciaControl : HeaderFor CuerpoFor",
"SentenciaControl : ID ':' HeaderFor CuerpoFor",
"SentenciaControl : HeaderForID CuerpoFor ELSE cte ';'",
"SentenciaControl : HeaderForID CuerpoFor ELSE signo cte ';'",
"CondicionFor : ID Comparador ID",
"CondicionFor : ID Comparador Expresion",
"CondicionFor : ID Comparador CTE_ENTERA",
"CondicionFor : ID Comparador signo CTE_ENTERA",
"SentenciaCorte : SentenciaReturn",
"SentenciaCorte : BREAK ';'",
"SentenciaCorte : BREAK cte ';'",
"SentenciaCorte : BREAK signo cte ';'",
"SentenciaCorte : CONTINUE ':' ID ';'",
};

//#line 366 "Gramatica.y"

public static final List<ErrorLinea> errores_sintacticos = new ArrayList<>();
public static final List<ErrorLinea> errores_yacc = new ArrayList<>();
public static final List<ErrorLinea> errores_lexicos = new ArrayList<>();
public static final List<ErrorLinea> errores_semanticos = new ArrayList<>();
public static final NumeroLinea linea = new NumeroLinea();
public static final TablaSimbolos tablaDeSimbolos= new TablaSimbolos(errores_semanticos, linea);
public static final EstructuraTercetos estructuraTercetosPrincipal= new EstructuraTercetos("programa_principal");
public static List<EstructuraTercetos> listEstructurasSeguimiento=new ArrayList<>();
public static List<EstructuraTercetos> listEstructurasTercetos=new ArrayList<>();
public static EstructuraTercetos estructuraActual;

public static boolean dentroDeFor=false;
public static boolean esperandoBreakcte=false;
public static boolean dentroDeFun=false;

private String tipo; //guarda el tipo de la lista de id que se estan declarando

public static String ambito = "";

//-----------------------------------------------------------------------

AnalizadorLexico lexico;



	public void addListEstructuraSeguimiento(EstructuraTercetos ET){
		listEstructurasSeguimiento.add(ET);
	}

	public EstructuraTercetos getEstructuraPendiente(){
		return(listEstructurasSeguimiento.get(listEstructurasSeguimiento.size()-1));
	}

	public Parser(AnalizadorLexico lexico){
		this.lexico = lexico;
	}

	private int yylex(){
		Simbolo simbolo = lexico.yyLex();

		if ((simbolo.getToken() != 0) || (simbolo.getToken() != -1))
			yylval = new ParserVal(simbolo.getLexema());
		
		return simbolo.getToken();

	}

	public void AgregarErrorSintactico(String error){
	    ErrorLinea err= new ErrorLinea(error, linea.getNumeroLinea());
		errores_sintacticos.add(err);
	}
	
	public void yyerror(String error){
	    System.out.println(error);
	    ErrorLinea err= new ErrorLinea(error, linea.getNumeroLinea());
		errores_yacc.add(err);
	}

	public static void main(String[] args) {
	    try{
	        String contenido = new String(Files.readAllBytes(Paths.get("src/com/tp/compiladores/CodigosEjemplo/codigo.txt")));
	        AnalizadorLexico AL=new AnalizadorLexico(tablaDeSimbolos, contenido, errores_lexicos, linea);
    	    Parser parser= new Parser(AL);
            System.out.println("parse: "+parser.yyparse());
			
        }
        catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
        }
		 if (!errores_lexicos.isEmpty()){
		 	System.out.println("Errores lexicos");
		 	for(ErrorLinea e: errores_lexicos)
		 	    e.imprimirError();
		}
		if (!errores_sintacticos.isEmpty()){
			System.out.println("Errores Sintacticos");
			for(ErrorLinea e: errores_sintacticos)
		 	    e.imprimirError(); 
		}
		if (!errores_yacc.isEmpty()){
			System.out.println("Errores Yacc");
			for(ErrorLinea e: errores_yacc)
		 	    e.imprimirError();
		}
		if (!errores_semanticos.isEmpty()){
			System.out.println("Errores Semanticos");
			for(ErrorLinea e: errores_semanticos)
		 	    e.imprimirError();
		}
		System.out.println("Tabla de simbolos:");
		
		tablaDeSimbolos.imprimir();

		System.out.println("Tercetos:");
		int n=listEstructurasTercetos.size();
		
		for(int i=0; i<n; i++){
			listEstructurasTercetos.get(i).imprimir();
		}
	}


    public void chequearRangoEntero(String entero){
            int number = Integer.parseInt("-"+entero);
            if(number<-128){
                ErrorLinea err=new ErrorLinea("Constante entera fuera de rango", linea.getNumeroLinea());
                errores_lexicos.add(err);
            }

    }

    public void desconcatenarAmbito(){
		String [] a = ambito.split(":");
		String nuevoAmbito = a[0];
		int len = a.length -1;
		for (int i = 1 ; i < len ; i++)
			nuevoAmbito = nuevoAmbito + ":" + a[i];
		ambito = nuevoAmbito;
	}

//#line 778 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 32 "Gramatica.y"
{desconcatenarAmbito();}
break;
case 2:
//#line 33 "Gramatica.y"
{AgregarErrorSintactico("Se espera '{' ");}
break;
case 3:
//#line 34 "Gramatica.y"
{AgregarErrorSintactico("Se esperan '{' '}' ");}
break;
case 4:
//#line 35 "Gramatica.y"
{AgregarErrorSintactico("Se espera ';'");}
break;
case 5:
//#line 38 "Gramatica.y"
{ambito= val_peek(0).sval; tablaDeSimbolos.add(new Simbolo(val_peek(0).sval, 269,"nombre_programa"));
					 estructuraActual=estructuraTercetosPrincipal;
					 listEstructurasSeguimiento.add(estructuraActual);
					 listEstructurasTercetos.add(estructuraActual);
					 }
break;
case 13:
//#line 55 "Gramatica.y"
{AgregarErrorSintactico("Se espera identificador");}
break;
case 17:
//#line 59 "Gramatica.y"
{AgregarErrorSintactico("Se espera el tipo de la variable");}
break;
case 18:
//#line 60 "Gramatica.y"
{AgregarErrorSintactico("Se espera identificador de la variable");}
break;
case 21:
//#line 67 "Gramatica.y"
{tablaDeSimbolos.add(new Simbolo(val_peek(2).sval+":"+ambito, 269, "constante", "I8")); estructuraActual.crearTerceto(val_peek(1).sval, tablaDeSimbolos.getRefSimbolo(val_peek(2).sval, ambito), val_peek(0).sval);}
break;
case 22:
//#line 68 "Gramatica.y"
{tablaDeSimbolos.add(new Simbolo(val_peek(3).sval+":"+ambito, 269, "constante", "I8")); estructuraActual.crearTerceto(val_peek(2).sval, tablaDeSimbolos.getRefSimbolo(val_peek(3).sval, ambito), val_peek(1).sval + val_peek(0).sval);}
break;
case 23:
//#line 69 "Gramatica.y"
{tablaDeSimbolos.add(new Simbolo(val_peek(2).sval+":"+ambito, 269, "constante", "F32")); estructuraActual.crearTerceto(val_peek(1).sval, tablaDeSimbolos.getRefSimbolo(val_peek(2).sval, ambito), val_peek(0).sval);}
break;
case 24:
//#line 70 "Gramatica.y"
{tablaDeSimbolos.add(new Simbolo(val_peek(3).sval+":"+ambito, 269, "constante", "F32")); estructuraActual.crearTerceto(val_peek(2).sval, tablaDeSimbolos.getRefSimbolo(val_peek(3).sval, ambito), val_peek(1).sval + val_peek(0).sval);}
break;
case 25:
//#line 71 "Gramatica.y"
{AgregarErrorSintactico("Se espera un identificador");}
break;
case 26:
//#line 72 "Gramatica.y"
{AgregarErrorSintactico("Se espera una constante ");}
break;
case 27:
//#line 73 "Gramatica.y"
{AgregarErrorSintactico("Se espera '=:' ");}
break;
case 28:
//#line 76 "Gramatica.y"
{estructuraActual.setTipo("I8");}
break;
case 29:
//#line 77 "Gramatica.y"
{estructuraActual.setTipo("F32");}
break;
case 30:
//#line 80 "Gramatica.y"
{tablaDeSimbolos.add(new Simbolo(val_peek(2).sval+":"+ambito,269,"variable",estructuraActual.getTipo()));}
break;
case 31:
//#line 81 "Gramatica.y"
{tablaDeSimbolos.add(new Simbolo(val_peek(0).sval+":"+ambito,269,"variable", estructuraActual.getTipo()));}
break;
case 32:
//#line 82 "Gramatica.y"
{AgregarErrorSintactico("Se espera ',' ");}
break;
case 33:
//#line 85 "Gramatica.y"
{tablaDeSimbolos.setTipo(ambito, estructuraActual.getTipo()); }
break;
case 34:
//#line 86 "Gramatica.y"
{tablaDeSimbolos.setTipo(ambito, estructuraActual.getTipo());}
break;
case 35:
//#line 87 "Gramatica.y"
{tablaDeSimbolos.setTipo(ambito, estructuraActual.getTipo());}
break;
case 36:
//#line 89 "Gramatica.y"
{AgregarErrorSintactico("Se espera el identificador de la funcion ");}
break;
case 37:
//#line 90 "Gramatica.y"
{AgregarErrorSintactico("Se espera el identificador de la funcion ");}
break;
case 38:
//#line 91 "Gramatica.y"
{AgregarErrorSintactico("Se espera el identificador de la funcion ");}
break;
case 39:
//#line 92 "Gramatica.y"
{AgregarErrorSintactico("Se espera el tipo de retorno de la funcion");}
break;
case 40:
//#line 93 "Gramatica.y"
{AgregarErrorSintactico("Se espera el tipo de retorno de la funcion");}
break;
case 41:
//#line 94 "Gramatica.y"
{AgregarErrorSintactico("Se espera el tipo de retorno de la funcion");}
break;
case 42:
//#line 97 "Gramatica.y"
{estructuraActual=new EstructuraTercetos(val_peek(0).sval+":"+ambito);
			 ambito= ambito+":"+val_peek(0).sval; tablaDeSimbolos.add(new Simbolo(ambito, 269, "identificador_funcion"));
			 listEstructurasSeguimiento.add(estructuraActual);
			 listEstructurasTercetos.add(estructuraActual);
			 }
break;
case 43:
//#line 104 "Gramatica.y"
{desconcatenarAmbito(); 
									   listEstructurasSeguimiento.remove(listEstructurasSeguimiento.size()-1);
									   estructuraActual=listEstructurasSeguimiento.get(listEstructurasSeguimiento.size()-1);
									   }
break;
case 44:
//#line 108 "Gramatica.y"
{AgregarErrorSintactico("Se espera el cuerpo de la funcion");}
break;
case 45:
//#line 109 "Gramatica.y"
{AgregarErrorSintactico("Se espera el header de la funcion");}
break;
case 46:
//#line 110 "Gramatica.y"
{AgregarErrorSintactico("Se espera el retorno de la funcion");}
break;
case 47:
//#line 114 "Gramatica.y"
{tablaDeSimbolos.add(new Simbolo(val_peek(0).sval+":"+ambito,269,"parametro", estructuraActual.getTipo()));}
break;
case 48:
//#line 115 "Gramatica.y"
{AgregarErrorSintactico("Se espera tipo de parametro");}
break;
case 55:
//#line 128 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(2).sval, tablaDeSimbolos.getRefSimbolo(val_peek(3).sval, ambito), estructuraActual.getRefTerceto(1));}
break;
case 56:
//#line 129 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(2).sval, tablaDeSimbolos.getRefSimbolo(val_peek(3).sval, ambito), tablaDeSimbolos.getRefSimbolo(val_peek(1).sval, ambito));}
break;
case 57:
//#line 130 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(2).sval, tablaDeSimbolos.getRefSimbolo(val_peek(3).sval, ambito), val_peek(1).sval);}
break;
case 58:
//#line 131 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(3).sval, tablaDeSimbolos.getRefSimbolo(val_peek(4).sval, ambito), val_peek(2).sval + val_peek(1).sval);}
break;
case 59:
//#line 132 "Gramatica.y"
{AgregarErrorSintactico("Se espera '=:'");}
break;
case 60:
//#line 135 "Gramatica.y"
{ambito= ambito+":"+"when";}
break;
case 61:
//#line 136 "Gramatica.y"
{AgregarErrorSintactico("Se espera un if o un when");}
break;
case 62:
//#line 137 "Gramatica.y"
{AgregarErrorSintactico("Se espera la palabra reservada then");}
break;
case 64:
//#line 141 "Gramatica.y"
{AgregarErrorSintactico("Se espera ';'");}
break;
case 66:
//#line 146 "Gramatica.y"
{AgregarErrorSintactico("Se espera un out");}
break;
case 70:
//#line 155 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, tablaDeSimbolos.getRefSimbolo(val_peek(2).sval, ambito), tablaDeSimbolos.getRefSimbolo(val_peek(0).sval, ambito));}
break;
case 71:
//#line 156 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, tablaDeSimbolos.getRefSimbolo(val_peek(2).sval, ambito), val_peek(0).sval);}
break;
case 72:
//#line 157 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, estructuraActual.getRefTerceto(1), tablaDeSimbolos.getRefSimbolo(val_peek(0).sval, ambito));}
break;
case 73:
//#line 158 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, estructuraActual.getRefTerceto(1), val_peek(0).sval);}
break;
case 74:
//#line 159 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, tablaDeSimbolos.getRefSimbolo(val_peek(2).sval, ambito), tablaDeSimbolos.getRefSimbolo(val_peek(0).sval, ambito));}
break;
case 75:
//#line 160 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, tablaDeSimbolos.getRefSimbolo(val_peek(2).sval, ambito), tablaDeSimbolos.getRefSimbolo(val_peek(0).sval, ambito));}
break;
case 76:
//#line 161 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, tablaDeSimbolos.getRefSimbolo(val_peek(2).sval, ambito), val_peek(0).sval);}
break;
case 77:
//#line 162 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, tablaDeSimbolos.getRefSimbolo(val_peek(2).sval, ambito), val_peek(0).sval);}
break;
case 78:
//#line 163 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, estructuraActual.getRefTerceto(1), tablaDeSimbolos.getRefSimbolo(val_peek(0).sval, ambito));}
break;
case 79:
//#line 164 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, estructuraActual.getRefTerceto(1), tablaDeSimbolos.getRefSimbolo(val_peek(0).sval, ambito));}
break;
case 80:
//#line 165 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, estructuraActual.getRefTerceto(1), val_peek(0).sval);}
break;
case 81:
//#line 166 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, estructuraActual.getRefTerceto(1), val_peek(0).sval);}
break;
case 83:
//#line 168 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, val_peek(2).sval, tablaDeSimbolos.getRefSimbolo(val_peek(0).sval, ambito));}
break;
case 84:
//#line 169 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, val_peek(2).sval, tablaDeSimbolos.getRefSimbolo(val_peek(0).sval, ambito));}
break;
case 85:
//#line 170 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, val_peek(2).sval, tablaDeSimbolos.getRefSimbolo(val_peek(0).sval, ambito));}
break;
case 91:
//#line 180 "Gramatica.y"
{AgregarErrorSintactico("Se espera expresion");}
break;
case 95:
//#line 189 "Gramatica.y"
{chequearRangoEntero(val_peek(0).sval);}
break;
case 99:
//#line 195 "Gramatica.y"
{AgregarErrorSintactico("Falta ;");}
break;
case 100:
//#line 196 "Gramatica.y"
{AgregarErrorSintactico("Falta ;");}
break;
case 101:
//#line 197 "Gramatica.y"
{AgregarErrorSintactico("Falta ;");}
break;
case 102:
//#line 198 "Gramatica.y"
{AgregarErrorSintactico("Falta expresion de retorno");}
break;
case 108:
//#line 208 "Gramatica.y"
{yyerror("falta cerrar parentesis");}
break;
case 111:
//#line 216 "Gramatica.y"
{estructuraActual.desapilarYCompletar(2);
						estructuraActual.crearTerceto("BI", null, null);}
break;
case 113:
//#line 220 "Gramatica.y"
{yyerror("falta palabra reservada end_if");}
break;
case 114:
//#line 223 "Gramatica.y"
{estructuraActual.desapilarYCompletar(1);}
break;
case 115:
//#line 224 "Gramatica.y"
{estructuraActual.desapilarYCompletar(1);}
break;
case 116:
//#line 225 "Gramatica.y"
{yyerror("falta palabra reservada end_if");}
break;
case 117:
//#line 226 "Gramatica.y"
{AgregarErrorSintactico("Se espera '{' '}' ");}
break;
case 118:
//#line 227 "Gramatica.y"
{AgregarErrorSintactico("Se espera '{' '}' ");}
break;
case 119:
//#line 230 "Gramatica.y"
{estructuraActual.crearTerceto("BF", estructuraActual.getRefTerceto(1), null);}
break;
case 120:
//#line 231 "Gramatica.y"
{AgregarErrorSintactico("Falta cerrar parentesis");}
break;
case 121:
//#line 232 "Gramatica.y"
{AgregarErrorSintactico("Falta abrir parentesis");}
break;
case 128:
//#line 243 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, estructuraActual.getRefTerceto(2), estructuraActual.getRefTerceto(1));}
break;
case 129:
//#line 244 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, estructuraActual.getRefTerceto(1), val_peek(0).sval);}
break;
case 130:
//#line 245 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(2).sval, estructuraActual.getRefTerceto(1), val_peek(1).sval + val_peek(0).sval );}
break;
case 131:
//#line 246 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, val_peek(2).sval, estructuraActual.getRefTerceto(1));}
break;
case 132:
//#line 247 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, val_peek(3).sval + val_peek(2).sval, estructuraActual.getRefTerceto(1));}
break;
case 133:
//#line 248 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, estructuraActual.getRefTerceto(1), tablaDeSimbolos.getRefSimbolo(val_peek(0).sval, ambito));}
break;
case 134:
//#line 249 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, tablaDeSimbolos.getRefSimbolo(val_peek(2).sval, ambito), estructuraActual.getRefTerceto(1));}
break;
case 135:
//#line 250 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, val_peek(2).sval, tablaDeSimbolos.getRefSimbolo(val_peek(0).sval, ambito));}
break;
case 136:
//#line 251 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, val_peek(3).sval + val_peek(2).sval, tablaDeSimbolos.getRefSimbolo(val_peek(0).sval, ambito));}
break;
case 137:
//#line 252 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, tablaDeSimbolos.getRefSimbolo(val_peek(2).sval, ambito), val_peek(0).sval);}
break;
case 138:
//#line 253 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(2).sval, tablaDeSimbolos.getRefSimbolo(val_peek(3).sval, ambito), val_peek(1).sval + val_peek(0).sval);}
break;
case 139:
//#line 254 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, tablaDeSimbolos.getRefSimbolo(val_peek(2).sval, ambito), tablaDeSimbolos.getRefSimbolo(val_peek(0).sval, ambito));}
break;
case 140:
//#line 255 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, val_peek(2).sval, val_peek(0).sval);}
break;
case 141:
//#line 256 "Gramatica.y"
{yyerror("falta expresion en la comparacion");}
break;
case 142:
//#line 257 "Gramatica.y"
{yyerror("falta comparador");}
break;
case 143:
//#line 258 "Gramatica.y"
{yyerror("falta comparador");}
break;
case 150:
//#line 271 "Gramatica.y"
{estructuraActual.addNumCondicionFor();
				 estructuraActual.crearTerceto("BF", estructuraActual.getRefTerceto(1), null);
				 estructuraActual.crearTerceto(val_peek(2).sval, estructuraActual.getIdFor(), val_peek(1).sval);
				 estructuraActual.crearTerceto("=:", estructuraActual.getIdFor(), estructuraActual.getRefTerceto(1));
				 }
break;
case 151:
//#line 277 "Gramatica.y"
{estructuraActual.addNumCondicionFor();
				 estructuraActual.crearTerceto("BF", estructuraActual.getRefTerceto(1), null);
				 estructuraActual.crearTerceto("+", estructuraActual.getIdFor(), val_peek(1).sval);
				 estructuraActual.crearTerceto("=:", estructuraActual.getIdFor(), estructuraActual.getRefTerceto(1));
				 }
break;
case 153:
//#line 288 "Gramatica.y"
{estructuraActual.addIdFor(tablaDeSimbolos.getRefSimbolo(val_peek(2).sval, ambito));
		estructuraActual.crearTerceto(val_peek(1).sval, estructuraActual.getIdFor(), val_peek(0).sval);
	 	}
break;
case 154:
//#line 292 "Gramatica.y"
{estructuraActual.addIdFor(tablaDeSimbolos.getRefSimbolo(val_peek(2).sval, ambito));
		estructuraActual.crearTerceto(val_peek(1).sval, estructuraActual.getIdFor(), tablaDeSimbolos.getRefSimbolo(val_peek(0).sval, ambito));
	 	}
break;
case 155:
//#line 298 "Gramatica.y"
{estructuraActual.desapilarYCompletar(2);
					 estructuraActual.crearTerceto("BI", "[" + estructuraActual.getNumeroTercetoCondicionFor() + "]", null);
					 estructuraActual.popIdFor();}
break;
case 156:
//#line 302 "Gramatica.y"
{AgregarErrorSintactico("Se esperan sentencias ejecutables");}
break;
case 159:
//#line 308 "Gramatica.y"
{
					estructuraActual.crearTerceto("=:", estructuraActual.getIdAsigFor(), val_peek(1).sval);
					estructuraActual.completarTercetosBreakCte(1);
					}
break;
case 160:
//#line 313 "Gramatica.y"
{
					estructuraActual.crearTerceto("=:", estructuraActual.getIdAsigFor(), val_peek(2).sval + val_peek(1).sval);
					estructuraActual.completarTercetosBreakCte(1);
				}
break;
case 161:
//#line 320 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, tablaDeSimbolos.getRefSimbolo(val_peek(2).sval, ambito), tablaDeSimbolos.getRefSimbolo(val_peek(0).sval, ambito));}
break;
case 162:
//#line 321 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, tablaDeSimbolos.getRefSimbolo(val_peek(2).sval, ambito), estructuraActual.getRefTerceto(1));}
break;
case 163:
//#line 322 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(1).sval, tablaDeSimbolos.getRefSimbolo(val_peek(2).sval, ambito), val_peek(0).sval);}
break;
case 164:
//#line 323 "Gramatica.y"
{estructuraActual.crearTerceto(val_peek(2).sval, tablaDeSimbolos.getRefSimbolo(val_peek(3).sval, ambito), val_peek(1).sval+val_peek(0).sval);}
break;
case 166:
//#line 327 "Gramatica.y"
{
				if(dentroDeFor){
					estructuraActual.crearTerceto("BI", null, null);
					estructuraActual.guardarTercetoBreak();
				}
				else{
					errores_semanticos.add(new ErrorLinea("No existe ID para la asignacion", this.linea.getNumeroLinea()));
					System.out.println("No existe ID para la asignacion");
				}
			  }
break;
case 167:
//#line 337 "Gramatica.y"
{
				if((dentroDeFor) && (esperandoBreakcte)){
					estructuraActual.crearTerceto("=:", estructuraActual.getIdAsigFor(), val_peek(1).sval);
					estructuraActual.crearTerceto("BI", null, null);
					estructuraActual.guardarTercetoBreakCte();
				}
				else{
					errores_semanticos.add(new ErrorLinea("No existe ID para la asignacion", this.linea.getNumeroLinea()));
					System.out.println("No existe ID para la asignacion");
				}
				}
break;
case 168:
//#line 348 "Gramatica.y"
{
				if((dentroDeFor) && (esperandoBreakcte)){
					estructuraActual.crearTerceto("=:", estructuraActual.getIdAsigFor(), val_peek(2).sval + val_peek(1).sval);
					estructuraActual.crearTerceto("BI", null, null);
					estructuraActual.guardarTercetoBreakCte();
				}
				else{
					errores_semanticos.add(new ErrorLinea("No existe ID para la asignacion", this.linea.getNumeroLinea()));
					System.out.println("No existe ID para la asignacion");
			  	}
			  }
break;
case 169:
//#line 359 "Gramatica.y"
{

			  }
break;
//#line 1430 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
