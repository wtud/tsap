class Main
{
	static {
	      System.loadLibrary("hello"); // Load native library at runtime
	                                   // hello.dll (Windows) or libhello.so (Unixes)
	   }
	 
	   // Declare a native method sayHello() that receives nothing and returns void
	   private native void hello();
	 
	   // Test Driver
	   public static void main(String[] args) {
	      new Main().hello();  // invoke the native method
	   }
}