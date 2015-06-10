/**
 * CSE 305 ---> Assignment-2 | Part-1 & 2
 * 
 * @author Amey Mahajan (50090016) and Brijesh Rakholia (50085215)
 * @email: ameysanj@buffalo.edu, brijeshr@buffalo.edu
 *
 *
 *This assignment is about top-down parsing and type-checking for a simple programming language, called TinyPL.
 *
 */



import java.util.HashMap;

public class TinyPL {
	Program p;
	public static HashMap<Character, String> ST = new HashMap<Character, String>();

	public static void main(String args[]) {
		System.out.println("Enter TinyPL program below:\n");
		Lexer.lex();
		try {
			new Program();
		} catch (TypeMismatch t) {
			System.out.println(t);
		} catch (TypeUnknown u) {
			System.out.println(u);
		} catch (Redeclaration r) {
			System.out.println(r);
		}
	}
}

@SuppressWarnings("serial")
class TypeMismatch extends Exception {
	String message;

	public TypeMismatch(String s) {
		super(s);
		message = s;
	}
}

@SuppressWarnings("serial")
class TypeUnknown extends Exception {
	String message;

	public TypeUnknown(String s) {
		super(s);
		message = s;
	}
}

@SuppressWarnings("serial")
class Redeclaration extends Exception {
	String message;

	public Redeclaration(String s) {
		super(s);
		message = s;
	}
}

class Program {
	Decls ds;
	Stmts ss;

	public Program() throws TypeMismatch, Redeclaration, TypeUnknown {
		Lexer.lex(); // begin
		ds = new Decls();
		ss = new Stmts();
		// end assumed here
	}
}

class Decls {
	Idlist i;
	Idlist r;
	Idlist b;
	String type;

	public Decls() throws Redeclaration {
		if (Lexer.nextToken == Token.KEY_INT) {
			Lexer.lex();
			type = "int";
			i = new Idlist(type);
			Lexer.lex(); // ;
		}
		if (Lexer.nextToken == Token.KEY_REAL) {
			Lexer.lex();
			type = "real";
			r = new Idlist(type);
			Lexer.lex(); // ;
		}

		if (Lexer.nextToken == Token.KEY_BOOL) {
			Lexer.lex();
			type = "bool";
			b = new Idlist(type);
			Lexer.lex(); // ;
		}

	}
}

class Idlist {
	char id = Lexer.ident;
	Idlist idl;
	int counter;
	String type;

	public Idlist(String t) throws Redeclaration {
		type = t;
		if (!TinyPL.ST.containsKey(id)) {
			TinyPL.ST.put(id, type);
		} else if (TinyPL.ST.containsKey(id)) {
			throw new Redeclaration("" + id);
		}
		Lexer.lex();
		if (Lexer.nextToken == Token.COMMA) {
			Lexer.lex();
			idl = new Idlist(type);
		}
	}
}

class Stmt {
	Assign a;
	Cond c;
	Loop l;
	Cmpd cd;
	String type;

	public Stmt() throws TypeMismatch, TypeUnknown {
		if (Lexer.nextToken == Token.ID) {
			a = new Assign();
		} else if (Lexer.nextToken == Token.KEY_IF) {
			c = new Cond();
		} else if (Lexer.nextToken == Token.KEY_WHILE) {
			l = new Loop();
		} else if (Lexer.nextToken == Token.LEFT_BRACE) {
			cd = new Cmpd();
		}
	}
}

class Stmts {
	Stmt s;
	Stmts ss;

	public Stmts() throws TypeMismatch, TypeUnknown {
		if (Lexer.nextToken == Token.ID || Lexer.nextToken == Token.KEY_IF
				|| Lexer.nextToken == Token.KEY_WHILE
				|| Lexer.nextToken == Token.LEFT_BRACE) {
			s = new Stmt();
		}
		if (Lexer.nextToken == Token.ID || Lexer.nextToken == Token.KEY_IF
				|| Lexer.nextToken == Token.KEY_WHILE
				|| Lexer.nextToken == Token.LEFT_BRACE) {
			ss = new Stmts();
		}
	}
}

class Cmpd {
	Stmts s;

	public Cmpd() throws TypeMismatch, TypeUnknown {
		Lexer.lex(); // skip over '{'
		s = new Stmts();
		Lexer.lex(); // skip over '}'
	}
}

class Cond {
	Expr b;
	Stmt s1;
	Stmt s2;
	String type;
	
	public Cond() throws TypeMismatch, TypeUnknown {
		Lexer.lex(); // skip over 'if'
		// ( handled by Expr
		b = new Expr();
		s1 = new Stmt();
		// ) handled by Expr
		type = b.type;
		if(b.type != "bool") {
			throw new TypeMismatch(" if condition must have a bool type.");
		}
		if (Lexer.nextToken == Token.KEY_ELSE) {
			Lexer.lex(); // skip over 'else'
			s2 = new Stmt();
		}
	}

}

class Loop {
	Expr b;
	Stmt s;
	String type;
	
	public Loop() throws TypeMismatch, TypeUnknown {
		Lexer.lex(); // skip over 'while'
		// assume Expr scans over (
		b = new Expr();
		// assume Expr scans over )
		type = b.type;
		if(b.type != "bool") {
			throw new TypeMismatch(" while condition must have a bool type.");
		}
		
		s = new Stmt();
	}
}

class Assign {
	Var v;
	Expr e;
	String type;

	public Assign() throws TypeMismatch, TypeUnknown {
		v = new Var();
		type = v.type;
		Lexer.lex(); // =
		e = new Expr();
		if (v.type != e.type) {
			throw new TypeMismatch(v.type + " " + e.op);
		}
		Lexer.lex(); // ;
	}
}

class Var {
	char v;
	String type;

	public Var() throws TypeUnknown {
		v = Lexer.ident;
		if (TinyPL.ST.containsKey(v)) {
			type = (String) TinyPL.ST.get(v);
		} else {
			throw new TypeUnknown(v + " on LHS of assignment");
		}
		Lexer.lex();
	}
}

class Expr {
	Term t;
	Expr e;
	String op = "";
	String type;

	public Expr() throws TypeMismatch, TypeUnknown {
		t = new Term();
		type = t.type;
		if (Lexer.nextToken == Token.ADD_OP || Lexer.nextToken == Token.SUB_OP
				|| Lexer.nextToken == Token.OR_OP) {
			op = Token.toString(Lexer.nextToken);
			Lexer.lex();
			e = new Expr();
			if (e.type != t.type) {
				throw new TypeMismatch(t.type + " " + op + " " + e.type);
			}
		}
	}
}

class Term {
	Factor f;
	Term t;
	String op = "";
	String type;

	public Term() throws TypeMismatch, TypeUnknown {
		f = new Factor();
		type = f.type;
		if (Lexer.nextToken == Token.MULT_OP || Lexer.nextToken == Token.DIV_OP
				|| Lexer.nextToken == Token.AND_OP) {
			op = Token.toString(Lexer.nextToken);
			Lexer.lex();
			t = new Term();
			if (t.type != f.type) {
				throw new TypeMismatch(f.type +" "+ op +" "+ t.type);
			}
		}
	}
}

class Literal {
}

class Int_Lit extends Literal {
	int i; // intValue
	static String type;

	public Int_Lit() {
		i = Lexer.intValue;
		type = "int";
	}

}

class Real_Lit extends Literal {
	double r; // realValue
	static String type;

	public Real_Lit() {
		r = Lexer.realValue;
		type = "real";
	}

}

class Bool_Lit extends Literal {
	boolean b; // true or false

	public Bool_Lit(boolean b2) {
		b = b2;
	}
}

class Id_Lit extends Literal {
	char v; // identifier
	static String type;

	public Id_Lit() throws TypeUnknown {
		v = Lexer.ident;
		if (!TinyPL.ST.containsKey(v)) {
			throw new TypeUnknown(v + " in expression");
		}
		type = TinyPL.ST.get(v);
	}
}

class Factor { // factor -> number | '(' expr ')'
	Expr e;
	Expr e2;
	Factor f;
	Literal l;
	String op;
	String type;

	public Factor() throws TypeMismatch, TypeUnknown {
		switch (Lexer.nextToken) {
		case Token.INT_LIT: // int literal
			l = new Int_Lit();
			type = Int_Lit.type;
			Lexer.lex();
			break;
		case Token.REAL_LIT: // real literal
			l = new Real_Lit();
			type = Real_Lit.type;
			Lexer.lex();
			break;
		case Token.TRUE_LIT: // true
			l = new Bool_Lit(true);
			Lexer.lex();
			type = "bool";
			break;
		case Token.FALSE_LIT: // false
			l = new Bool_Lit(false);
			Lexer.lex();
			type = "bool";
			break;
		case Token.ID:
			l = new Id_Lit();
			type = Id_Lit.type;
			Lexer.lex();
			break;
		case Token.NEG_OP:
			op = "!";
			Lexer.lex();
			f = new Factor();
			type = f.type;
			break;
		case Token.LEFT_PAREN: // '('
			Lexer.lex();
			e = new Expr();
			type = e.type;
			if (Lexer.nextToken == Token.RIGHT_PAREN)
				Lexer.lex();
			else {
				op = Token.toString(Lexer.nextToken); // ==, <=, >=, <, >, !=
				Lexer.lex();
				
				e2 = new Expr();
				type = "bool";
				Lexer.lex(); // skip over )
			}
			break;
		default:
			break;
		}
	}
}
