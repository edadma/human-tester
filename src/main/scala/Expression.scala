package ca.hyperreal.human_tester

import util.parsing.combinator.RegexParsers


object Expression extends RegexParsers
{
	def string: Parser[AST] = """'[^']*'""".r ^^ {s => StringAST( s.substring(1, s.length - 1) )}
	
	def number: Parser[AST] = """\d+""".r ^^ (n => NumberAST( n.toInt ))
	
	def variable: Parser[AST] = """\p{Alpha}+""".r ^^ (VariableAST( _ ))
	
	def function: Parser[AST] = """\p{Alpha}+""".r ~ ("(" ~> repsep(comparison, ",") <~ ")") ^^ {
		case f ~ args => OperationAST( f, args: _* )}
	
	def primary: Parser[AST] = function | string | number | variable | "(" ~> expr <~ ")"
	
	def multiplicative: Parser[AST] = primary ~ rep(("*"|"/"|"rem") ~ primary) ^^ {
		case number ~ list => (number /: list) {
			case (x, f ~ y) => OperationAST( f, x, y )
		}
	}
	
	def additive: Parser[AST] = multiplicative ~ rep(("+"|"-") ~ multiplicative) ^^ {
		case number ~ list => (number /: list) {
			case (x, f ~ y) => OperationAST( f, x, y )
		}
	}

	def comparison: Parser[AST] =
		additive ~ rep1(("="|"!="|"<="|">="|"<"|">") ~ additive) ^^ {
			case x ~ list =>
				var v = x
				
				ConjunctionAST( for (c ~ b <- list)
					yield
					{
					val a = v
						
						v = b
							
						OperationAST( c, a, b )
					} )
			} |
		additive

	def expr: Parser[AST] =
		comparison ~ rep("," ~> comparison) ^^ {
			case x ~ Nil => x
			case x ~ list => ConjunctionAST( x +: list )
			}
		
	def apply( input: String ) =
		parseAll( expr, input ) match
		{
			case Success( result, _ ) => result
			case NoSuccess( msg, _ ) => sys.error( msg )
		}
}