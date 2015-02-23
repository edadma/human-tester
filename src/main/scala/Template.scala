package ca.hyperreal.human_tester

import util.matching.Regex
import collection.mutable.HashMap
import util.Random._
import util.parsing.combinator.RegexParsers


abstract class Template
{
	val slot = """\$(\p{Alpha}+):(\p{Alpha}+)|\$\{(\p{Alpha}+):(\p{Alpha}+)\}"""r
	
	def slots( template: String ) =
	{
	val map = new HashMap[String, String]
	
		for (m <- slot.findAllMatchIn( template ))
		{
		val (k, v) =
			if (m.group(1) == null)
				m.group(3) -> m.group(4)
			else
				m.group(1) -> m.group(2)
				
			if (map contains k)
				sys.error( s"duplicate slot variable '$k' at index ${m.start}" )
		
			map(k) = v
		}
		
		map.toList
	}
	
	def replace( template: String, replacements: collection.Map[String, Any] ) =
		slot.replaceAllIn( template,
			{ m =>
			val k =
				if (m.group(1) == null)
					m.group(3)
				else
					m.group(1)
				
				if (!replacements.contains( k ))
					sys.error( s"key not in replacements: '$k'" )
					
				replacements(k).toString
			} )
	
	def generate( template: String, answer: String ): (String, String)
}

object ArithmeticTemplate extends Template
{
	def generate( template: String, answer: String ) =
	{
	val m = new HashMap[String, Any]
	
		for ((n, t) <- slots( template ))
		{
			t match
			{
				case "pdigit" =>
					var v = 0
					
					do
					{
						v = nextInt( 9 ) + 1
					} while (m.values.exists( _ == v ))
					
					m(n) = v
			}
		}
		
		replace( template, m ) -> new Calculator( m )( answer ).toString
	}
	
	class Calculator( vars: collection.Map[String, Any] ) extends RegexParsers
	{
		def number: Parser[Int] = """\d+""".r ^^ { _.toInt }
		def variable: Parser[Int] = """\p{Alpha}+""".r ^^ { vars(_).asInstanceOf[Int] }
		def factor: Parser[Int] = number | variable | "(" ~> expr <~ ")"
		def term  : Parser[Int] = factor ~ rep( "*" ~ factor | "/" ~ factor) ^^ {
			case number ~ list => (number /: list) {
				case (x, "*" ~ y) => x * y
				case (x, "/" ~ y) => x / y
			}
		}
		
		def expr  : Parser[Int] = term ~ rep("+" ~ term | "-" ~ term) ^^ {
			case number ~ list => list.foldLeft(number) { // same as before, using alternate name for /:
				case (x, "+" ~ y) => x + y
				case (x, "-" ~ y) => x - y
			}
		}

		def apply(input: String): Int = parseAll(expr, input) match {
			case Success(result, _) => result
			case failure : NoSuccess => scala.sys.error(failure.msg)
		}
	}
}