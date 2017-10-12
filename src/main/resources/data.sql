-- USERS
INSERT INTO users(id, email, name, avatar_path, password_hash) VALUES (1, 'foo@foo.com', 'foo', '1.png', '$2a$04$hMUeqxP71wfuyW8jLBq8NeaKRcB42eRY/WC0DzLup4jUp.AT/4ami');
INSERT INTO users(id, email, name, avatar_path, password_hash) VALUES (2, 'bar@bar.com', 'bar', '2.png', '$2a$04$UPOMdrZUMKK6lLOjwJZ5duhvpnfcNtbm9ouHYF2ZpcON4TOFE4nsO');
INSERT INTO users(id, email, name, avatar_path, password_hash) VALUES (3, 'baz@baz.com', 'baz', '3.png', '$2a$04$y95ct.TTvfN3zSUZx7gxEOy./N32qO.wglE7X0EgzKyFy95C2nWB.');

-- QUESTIONS
INSERT INTO questions(id, header, content, user_id)
VALUES (1, 'What is a NullPointerException, and how do I fix it?',
'What are Null Pointer Exceptions (<code>java.lang.NullPointerException</code>) and what causes them?

What methods/tools can be used to determine the cause so that you stop the exception ' ||
'from causing the program to terminate prematurely?', 1);

INSERT INTO questions(id, header, content, user_id)
VALUES (2, 'How do I compare strings in Java?',
'I''ve been using the <code>==</code> operator in my program to compare all my strings so far. ' ||
'However, I ran into a bug, changed one of them into <code>.equals()</code> instead, and it ' ||
'fixed the bug.

Is <code>==</code> bad? When should it and should it not be used? What''s the difference?', 2);

INSERT INTO questions(id, header, content, user_id)
VALUES (3, 'Is Java “pass-by-reference” or “pass-by-value”?',
'I always thought Java was pass-by-reference; however I''ve seen a couple of blog posts ' ||
'that claim it''s not. I don''t think I understand the distinction they''re making.

What is the explanation?', 3);

-- ANSWERS
INSERT INTO answers(id, content, question_id, user_id)
VALUES (1, 'When you declare a reference variable (i.e. an object) you are really creating a pointer to an object. Consider the following code where you declare a variable of primitive type int:
<code class="block">
int x;
x = 10;

</code>
In this example the variable x is an int and Java will initialize it to 0 for you. When you assign it to 10 in the second line your value 10 is written into the memory location pointed to by x.

But, when you try to declare a reference type something different happens. Take the following code:

<code class="block">
Integer num;
num = new Integer(10);

</code>
The first line declares a variable named num, but, it does not contain a primitive value. Instead it contains a pointer (because the type is Integer which is a reference type). Since you did not say as yet what to point to Java sets it to null, meaning "I am pointing at nothing".

In the second line, the new keyword is used to instantiate (or create) an object of type Integer and the pointer variable num is assigned this object. You can now reference the object using the dereferencing operator . (a dot).

The Exception that you asked about occurs when you declare a variable but did not create an object. If you attempt to dereference num BEFORE creating the object you get a NullPointerException. In the most trivial cases the compiler will catch the problem and let you know that "num may not have been initialized" but sometimes you write code that does not directly create the object.

For instance you may have a method as follows:

<code class="block">
public void doSomething(SomeObject obj){
   //do something to obj
}

</code>

in which case you are not creating the object obj, rather assuming that is was created before the doSomething method was called. Unfortunately it is possible to call the method like this:

<code class="block">
doSomething(null);

</code>

in which case obj is null. If the method is intended to do something to the passed-in object, it is appropriate to throw the NullPointerException because it''s a programmer error and the programmer will need that information for debugging purposes.

Alternatively, there may be cases where the purpose of the method is not solely to operate on the passed in object, and therefore a null parameter may be acceptable. In this case, you would need to check for a null parameter and behave differently. You should also explain this in the documentation. For example, doSomething could be written as:

<code class="block">
/**
  * @param obj An optional foo for ____. May be null, in which case
  *  the result will be ____. */
public void doSomething(SomeObject obj){
    if(obj != null){
       //do something
    } else {
       //do something else
    }
}

</code>
', 1, 2);

INSERT INTO answers(id, content, question_id, user_id)
VALUES (2, '<code>==</code> tests for reference equality (whether they are the same object).

<code>.equals()</code> tests for value equality (whether they are logically "equal").

<code>Objects.equals()</code> checks for nulls before calling <code>.equals()</code> so ' ||
'you don''t have to (available as of JDK7, also available in Guava).

Consequently, if you want to test whether two strings have the same value you ' ||
'will probably want to use <code>Objects.equals()</code>.
<code class="block">
// These two have the same value
new String("test").equals("test") // --> true

// ... but they are not the same object
new String("test") == "test" // --> false

// ... neither are these
new String("test") == new String("test") // --> false

// ... but these are because literals are interned by
// the compiler and thus refer to the same object
"test" == "test" // --> true

// ... but you should really just call Objects.equals()
Objects.equals("test", new String("test")) // --> true
Objects.equals(null, "test") // --> false

</code>
You almost always want to useObjects.equals(). In the rare situation ' ||
'where you know you''re dealing with interned strings, you can use <code>==</code>.', 2, 3);

INSERT INTO answers(id, content, question_id, user_id)
VALUES (3, 'NullPointerExceptions are exceptions that occur when you try to use a ' ||
'reference that points to no location in memory (null) as though it were referencing an object. Calling a method on a null reference or trying to access a field of a null reference will trigger a NullPointerException. These are the most common, but other ways are listed on the NullPointerException javadoc page.

Probably the quickest example code I could come up with to illustrate a ' ||
'NullPointerException would be:

<code class="block">
public class Example {
    public static void main(String[] args) {
        Object obj = null;
        obj.hashCode();
    }
}

</code>
On the first line inside main I''m explicitly setting the Object reference ' ||
'obj equal to null. This means I have a reference, but it isn''t pointing to ' ||
'any object. After that, I try to treat the reference as though it points to an ' ||
'object by calling a method on it. This results in a NullPointerException ' ||
'because there is no code to execute in the location that the reference is pointing.

(This is a technicality, but I think it bears mentioning: A reference that points to null isn''t the same as a C pointer ' ||
'that points to an invalid memory location. A null pointer is literally not ' ||
'pointing anywhere, which is subtly different than pointing to a location that ' ||
'happens to be invalid.)', 3, 1);

INSERT INTO answers(id, content, question_id, user_id)
VALUES (4, 'Java is always pass-by-value. Unfortunately, they decided to call the ' ||
'location of an object a "reference". When we pass the value of an object, we are ' ||
'passing the reference to it. This is confusing to beginners.

It goes like this:

<code class="block">
public static void main( String[] args ) {
    Dog aDog = new Dog("Max");
    // we pass the object to foo
    foo(aDog);
    // aDog variable is still pointing to the "Max" dog when foo(...) returns
    aDog.getName().equals("Max"); // true, java passes by value
    aDog.getName().equals("Fifi"); // false
}

public static void foo(Dog d) {
    d.getName().equals("Max"); // true
    // change d inside of foo() to point to a new Dog instance "Fifi"
    d = new Dog("Fifi");
    d.getName().equals("Fifi"); // true
}

</code>
In this example <code>aDog.getName()</code> will still return "Max". ' ||
'The value aDog within main is not changed in the function foo with ' ||
'the Dog "Fifi" as the object reference is passed by value. If it were ' ||
'passed by reference, then the aDog.getName() in main would return "Fifi" ' ||
'after the call to foo.

Likewise:

<code class="block">
public static void main( String[] args ) {
    Dog aDog = new Dog("Max");
    foo(aDog);
    // when foo(...) returns, the name of the dog has been changed to "Fifi"
    aDog.getName().equals("Fifi"); // true
}

public static void foo(Dog d) {
    d.getName().equals("Max"); // true
    // this changes the name of d to be "Fifi"
    d.setName("Fifi");
}

</code>
In the above example, FiFi is the dog''s name after call to <code>foo(aDog)</code> ' ||
'because the object''s name was set inside of <code>foo(...)</code>. Any operations ' ||
'that foo performs on d are such that, for all practical purposes, they are performed ' ||
'on aDog itself (except when d is changed to point to a different Dog ' ||
'instance like <code>d = new Dog("Boxer"))</code>.', 3, 2);

INSERT INTO answers(id, content, question_id, user_id)
VALUES (5, '<code>==</code> tests object references, <code>.equals()</code> tests ' ||
'the string values.

Sometimes it looks as if <code>==</code> compares values, ' ||
'because Java does some behind-the-scenes stuff to make sure identical in-line ' ||
'strings are actually the same object.

For example:

<code class="block">
String fooString1 = new String("foo");
String fooString2 = new String("foo");

// Evaluates to false
fooString1 == fooString2;

// Evaluates to true
fooString1.equals(fooString2);

// Evaluates to true, because Java uses the same object
"bar" == "bar";
</code>
But beware of nulls!

<code>==</code> handles null strings fine, but calling <code>.equals()</code> ' ||
'from a null string will cause an exception:

<code class="block">
String nullString1 = null;
String nullString2 = null;

// Evaluates to true
nullString1 == nullString2;

// Throws an Exception
nullString1.equals(nullString2);

</code>', 2, 1);

INSERT INTO answers(id, content, question_id, user_id)
VALUES (6, 'The <code>==</code> operator checks to see if the two strings are exactly ' ||
'the same object.

The <code>.equals()</code> method will check if the two strings have the same value.', 2, 3);

-- OTHER
INSERT INTO likes(question_id, user_id) VALUES(1, 2);
INSERT INTO likes(question_id, user_id) VALUES(1, 3);
INSERT INTO likes(question_id, user_id) VALUES(2, 1);
INSERT INTO likes(question_id, user_id) VALUES(3, 2);

UPDATE answers SET is_correct = TRUE WHERE id = 2;
UPDATE answers SET is_correct = TRUE WHERE id = 4;

UPDATE questions SET ts = (NOW() - INTERVAL '2 days') WHERE id = 1;
UPDATE answers SET ts = (NOW() - INTERVAL '2 days') WHERE question_id =1;
UPDATE questions SET ts = (NOW() - INTERVAL '1 day') WHERE id = 2;
UPDATE answers SET ts = (NOW() - INTERVAL '1 day') WHERE question_id = 2;

SELECT setval('users_id_seq', (SELECT max(id) FROM users));
SELECT setval('questions_id_seq', (SELECT max(id) FROM questions));
SELECT setval('answers_id_seq', (SELECT max(id) FROM answers));