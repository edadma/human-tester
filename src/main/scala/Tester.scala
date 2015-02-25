package ca.hyperreal.human_tester

import collection.mutable.{ArrayBuffer, HashMap}
import util.Random._


object Tester
{
	private val spaces = new HashMap[String, ArrayBuffer[(Template, String, String, String)]]
	private val templates = HashMap[String, Template]( "arithmetic" -> ArithmeticTemplate )
	
	spaces("default") = ArrayBuffer(
		(ArithmeticTemplate, "What is $a:tint plus $b:tint?", "a != b", "a + b"),
		(ArithmeticTemplate, "What is the sum of $a:tint, $b:tint and $c:tint?", "a < b < c", "a + b + c"),
		(ArithmeticTemplate, "What is $a:tint times $b:tint?", "1 < a < b, a*b <= 30", "a*b"),
		(ArithmeticTemplate, "What number comes before $a:tint?", null, "a - 1"),
		(ArithmeticTemplate, "What number comes after $a:tint?", null, "a + 1"),
		(ArithmeticTemplate, "Does $a:tint come after $b:tint?", "a > b", "if( a = b + 1, 'yes', 'no' )"),
		(ArithmeticTemplate, "Does $a:tint come before $b:tint?", "a < b", "if( a = b - 1, 'yes', 'no' )"),
		(ArithmeticTemplate, "Is $a:tint less than $b:tint?", "a != b", "if( a < b, 'yes', 'no' )"),
		(ArithmeticTemplate, "Is $a:tint greater than $b:tint?", "a != b", "if( a > b, 'yes', 'no' )"),
		(ArithmeticTemplate, "Is $a:tcomp divisible by $b:tprime?", "a > b", "if( a rem b = 0, 'yes', 'no' )"),
		(ArithmeticTemplate, "What is $a:tcomp divided by $b:tprime?", "a rem b = 0", "a/b"),
		(ArithmeticTemplate, "What number is between $a:tint and $b:tint", "a = b - 2", "a + 1"),
		(ArithmeticTemplate, "What number should come next: $a:sint, $b:sint, $c:sint, __?", "a = b - 3, b = c - 3", "c + 3"),
		(ArithmeticTemplate, "What number should come next: $a:sint, $b:sint, $c:sint, __?", "a = b + 3, b = c + 3, c > 3", "c - 3")
		)
	
	def test( space: String ) =
	{
		require( spaces contains space, s"there is no question/answer space '$space'" )
		
	val (t, q, c, a) = spaces(space)( nextInt(spaces(space).length) )
	
		t.generate( q, c, a )
	}
	
	def question( space: String, template: String, question: String, constraints: String, answer: String )
	{
		require( templates contains template, "unknown template '$template'" )
		
	val buf =
		if (spaces contains space)
			spaces(space)
		else
		{
		val b = new ArrayBuffer[(Template, String, String, String)]
		
			spaces(space) = b
			b
		}
		
		buf.append( (templates(template), question, constraints, answer) )
	}
	
	def template( name: String, t: Template )
	{
		require( !(templates contains name), s"duplicate template '$name'" )
		
		templates(name) = t
	}
}