package ca.hyperreal.human_tester

import util.parsing.combinator.RegexParsers


class Evaluator( vars: collection.Map[String, Any] ) extends RegexParsers
{
	def string: Parser[Any] = """'[^']*'""".r ^^ {s => s.substring( 1, s.length - 1 )}
	
	def number: Parser[Any] = """\d+""".r ^^ {_.toInt}
	
	def variable: Parser[Any] = """\p{Alpha}+""".r ^^ {vars(_)}
	
	def function: Parser[Any] = """\p{Alpha}+""".r ~ ("(" ~> repsep(comparison, ",") <~ ")") ^^ {
		case "if" ~ List(cond: Boolean, yes, no) =>
			if (cond)
				yes
			else
				no
		}
	
	def primary: Parser[Any] = function | string | number | variable | "(" ~> expr <~ ")"
	
	def multiplicative: Parser[Any] = primary ~ rep(("*"|"/"|"mod") ~ primary) ^^ {
		case number ~ list => (number /: list) {
			case (x, "*" ~ y) => x.asInstanceOf[Int] * y.asInstanceOf[Int]
			case (x, "/" ~ y) => x.asInstanceOf[Int] / y.asInstanceOf[Int]
			case (x, "mod" ~ y) => x.asInstanceOf[Int] % y.asInstanceOf[Int]
		}
	}
	
	def additive: Parser[Any] = multiplicative ~ rep(("+"|"-") ~ multiplicative) ^^ {
		case number ~ list => (number /: list) {
			case (x, "+" ~ y) =>
				if (x.isInstanceOf[String] || y.isInstanceOf[String])
					x.toString + y.toString
				else
					x.asInstanceOf[Int] + y.asInstanceOf[Int]
			case (x, "-" ~ y) => x.asInstanceOf[Int] - y.asInstanceOf[Int]
		}
	}

	def comparison: Parser[Any] =
		additive ~ rep1(("="|"!="|"<="|">="|"<"|">") ~ additive) ^^ {
			case x ~ list =>
				var v = x
				
				list forall {
					case c ~ b =>
						val a = v
						
							v = b
							
							(c, a, b) match
							{
								case ("=", _, _) => a == b
								case ("!=", _, _) => a != b
								case ("<", l: Int, r: Int) => l < r
								case (">", l: Int, r: Int) => l > r
								case ("<=", l: Int, r: Int) => l <= r
								case (">=", l: Int, r: Int) => l >= r
								case ("<", l: String, r: String) => l < r
								case (">", l: String, r: String) => l > r
								case ("<=", l: String, r: String) => l <= r
								case (">=", l: String, r: String) => l >= r
							}
				}
			} |
		additive

//	def conjunction: Parser[Any] = 

	def expr: Parser[Any] =
		comparison ~ rep("," ~> comparison) ^^ {
			case (x: Boolean) ~ (list: List[Boolean]) =>
				def conj( l: List[Boolean] ): Boolean =
					if (l isEmpty)
						true
					else if (l.head)
						conj( l.tail )
					else
						false
						
				conj( x +: list )
			case x ~ Nil =>
				x
			}
		
	def apply( input: String ) =
		parseAll( expr, input ) match
		{
			case Success( result, _ ) => result
			case NoSuccess( msg, _ ) => sys.error( msg )
		}
}