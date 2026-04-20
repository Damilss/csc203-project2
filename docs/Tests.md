# Documation and testing

Regarding documenting your code, please reference to the “Good Coding Etiquette – Code Comments.pdf”. This is important, so other students know what your code does and how.

For testing your artifacts, please use simple if/else structures and/or print the results of our artifacts on the screen stating the expected outcome and the resulting outcome. IntelliJ provides a unit test framework for testing code, but for this project we won’t be using it. If the artifacts need data for testing, use mock data that you will develop to cover all the test cases until your artifacts get integrated into the project. You should have as many test cases as possible to cover any scenario that may happen when using the final product.

Example of how to test a method:
```
System.out.println( “Testing moveRock() method. Current position ( %d, %d ).” , getEntityPosition().x, getEntityPosition().y );
System.out.println( “Expected output one of: ( 3, 7 ), ( 3, 6 ), ( 3, 8 ), ( 4, 6 ), ( 4, 8 ), ( 5, 7 ), ( 5, 6 ), ( 5, 8 )” ); 
System.out.printf( “Actual output: (%d, %d).”, getEntityPosition().x, getEntityPosition().y );
```