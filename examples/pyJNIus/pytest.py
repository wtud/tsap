from jnius import autoclass

System = autoclass('java.lang.System')
System.out.println('Jeej!');
print dir(System)
print ' --- '

Stack = autoclass('java.util.Stack')
print dir(Stack)
stack = Stack()
stack.push('hello')
stack.push('world')
print stack.pop()
print stack.pop()
print ' --- '

OwnCode = autoclass('org.pyJNIustest.Test');
print dir(OwnCode)
X = OwnCode()




