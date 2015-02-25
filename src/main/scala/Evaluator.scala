package ca.hyperreal.human_tester

import math._


object Evaluator
{
	def apply( expr: AST, vars: collection.Map[String, Any] ): Any =
	{
		def neval( e: AST ) = apply( e, vars ).asInstanceOf[Int]

		def beval( e: AST ) = apply( e, vars ).asInstanceOf[Boolean]

		def eval( e: AST ) = apply( e, vars )
		
		expr match
		{
			case StringAST( s ) => s
			case NumberAST( n ) => n
			case VariableAST( v ) => vars( v )
			case OperationAST( "+", a, b ) =>
				val x = eval( a )
				val y = eval( b )
				if (x.isInstanceOf[String] || y.isInstanceOf[String])
					x.toString + y.toString
				else
					x.asInstanceOf[Int] + y.asInstanceOf[Int]
			case OperationAST( "-", a, b ) => neval( a ) - neval( b )
			case OperationAST( "*", a, b ) => neval( a ) * neval( b )
			case OperationAST( "/", a, b ) => neval( a ) / neval( b )
			case OperationAST( "rem", a, b ) => neval( a ) % neval( b )
			case OperationAST( "=", a, b ) => eval( a ) == eval( b )
			case OperationAST( "!=", a, b ) => eval( a ) != eval( b )
			case OperationAST( c@("<"|">"|"<="|">="), a, b ) =>
				val l = eval( a )
				val r = eval( b )
				val comp =
					if (l.isInstanceOf[String] && r.isInstanceOf[String])
						l.asInstanceOf[String].compare( r.asInstanceOf[String] )
					else
						l.asInstanceOf[Int].compare( r.asInstanceOf[Int] )
						
					c match
					{
						case "<" => comp < 0
						case ">" => comp > 0
						case "<=" => comp <= 0
						case ">=" => comp >= 0
					}
			case OperationAST( "if", cond, yes, no ) =>
				if (beval( cond ))
					eval( yes )
				else
					eval( no )
			case OperationAST( "isqrt", a ) => sqrt( neval(a) ).toInt
			case ConjunctionAST( conjuncts ) => conjuncts forall (apply( _, vars).asInstanceOf[Boolean] )
		}
	}
}