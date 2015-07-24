UnsafeMe
=======

A simple library to serialize objects using the critical, but very fast 
[Unsafe class](http://www.docjar.com/docs/api/sun/misc/Unsafe.html). 
Use this [documentary from Mishadoff](http://mishadoff.com/blog/java-magic-part-4-sun-dot-misc-dot-unsafe/) for 
an explanation.

##Warning
This library is a prototype. It is not stable nor should you use it in any production code. Do not use it in critical
application. The reason is simple: Any error with the Unsafe class will result not in an exception, but in a VM crash!
Use at own risk.

##License
MIT