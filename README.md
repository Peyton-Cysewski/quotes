# Random Quotes

## Lab-08 GSON

*Author: Peyton Cysewski*

----

## Description
This console application reada from a long list of famous quotes that are located inside of a JSON file. The file is then parsed using the GSON package which allows for the conversion from JSON to Java. Once parsed, the application randomly selects a single quote and displays it in the terminal.

---

### Getting the Code
Clone this repository to your local machine.

```
$ git clone https://github.com/Peyton-Cysewski/quotes.git
```

### How it Works
The GSON package provides many ways to convert to and from JSON in Java. The specific method I used was to read in data from the file as a stream, meaning as a long array of bytes. The GSON provides a JsonReader that is able to interpret the bytes into primitive data types from property/value pairs. I simply had to check the name of the property to see if it was what I was looking for. If it was, then the JsonReader could convert it to a string. From there, it fill up an array of quotes from which one is randomly selected and returned from the function.

---

### Change Log  
1.0: *Initial Release* - 23 September 2020