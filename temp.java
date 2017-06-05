class temp
{
	public static void main(String arg[])
	{
		System.out.println("\ua432" );		
		System.out.println( data.util.getProperty("ravi=ravi the great", "ravi") );
		System.out.println( data.util.getProperty("ravi=ravi the great\ua432arun=the gadha", "ravi") );
		System.out.println( data.util.getProperty("ravi=ravi the great\ua432arun=the gadha", "arun") );
		System.out.println( data.util.getProperty("ravi=ravi\ua432\ua432the\ua432\ua432great", "ravi") );
		System.out.println( data.util.getProperty("ravi=ravi\ua432\ua432the\ua432\ua432great\ua432arun=the gadha", "ravi") );
		System.out.println( data.util.getProperty("ravi=ravi\ua432\ua432the\ua432\ua432great\ua432arun=the gadha", "arun") );
		
		
	}
}