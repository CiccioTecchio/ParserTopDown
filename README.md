# TopDownParser

A top down parser for this grammar

G = {  
N = {Program, Stmt, Expr, Term},  
T = {ID, IF, THEN, RELOP, NUMBER, ;, ASSIGN},  
S = Program,  
P = {  
 		Program -> Stmt Program1  
		Program1 -> SEMICOL Stmt Program1 | epsilon  
		Stmt -> IF Expr THEN Stmt | ID ASSIGN Expr  
		Expr -> Term Expr1  
		Expr1 -> RELOP Term | epsilon  
		Term -> ID | NUMBER  
	}  
}  
