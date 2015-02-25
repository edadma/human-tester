package ca.hyperreal.human_tester

import util.matching.Regex
import collection.mutable.HashMap
import util.Random._


abstract class Template
{
	protected val slot = """\$(\p{Alpha}+):(\p{Alpha}+)|\$\{(\p{Alpha}+):(\p{Alpha}+)\}"""r
	protected val types = new HashMap[String, IndexedSeq[Any]]
	
	def slots( template: String ) =
	{
	val map = new HashMap[String, IndexedSeq[Any]]
	
		for (m <- slot.findAllMatchIn( template ))
		{
		val (k, v) =
			if (m.group(1) == null)
				m.group(3) -> m.group(4)
			else
				m.group(1) -> m.group(2)
				
			if (map contains k)
				sys.error( s"duplicate slot variable '$k' at index ${m.start}" )
		
			map(k) = shuffle( types(v) )
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
	
	def generate( template: String, constraints: String, answer: String ): (String, String)
}

object ArithmeticTemplate extends Template
{
	types("tint") = 1 to 9
	types("tprime") = (2 to 9) filter prime
	types("tcomp") = (4 to 15) filterNot prime
	types("sint") = 1 to 30
	types("sprime") = (2 to 29) filter prime
	types("scomp") = (4 to 30) filterNot prime
	
	def prime( n: Int ) = n > 1 && !((2 to math.sqrt( n ).toInt) exists (n % _ == 0))
		
	def generate( template: String, constraints: String, answer: String ) =
	{
	val constraintsAST: AST = if (constraints eq null) null else Expression( constraints )
	val answerAST = Expression( answer )
	val m = new HashMap[String, Any]
	val vars =
		for ((n, s) <- slots( template ))
			yield
			{
			val c = nextInt( s.size )
			
				Var( n, s, c, c )
			}
		
		def satisfy
		{
			for (v <- vars)
				m(v.name) = v.set(v.index)
				
			if (constraints != null && !Evaluator( constraintsAST, m ).asInstanceOf[Boolean])
			{
			var i = vars.length - 1
			
				while ({vars(i).index = (vars(i).index + 1) % vars(i).set.length; vars(i).index == vars(i).choice})
				{
					if (i == 0)
						sys.error( s"unsatisfiable constraint(s) '$constraints' for template '$template'" )
					
					i -= 1
				}
				
				satisfy
			}
		}
		
		satisfy
		
		replace( template, m ) -> Evaluator( answerAST, m ).toString
	}
	
	case class Var( name: String, set: IndexedSeq[Any], choice: Int, var index: Int )
}