# Automated Grading Framework: 

## Features: 

1. User login and privacy: Uses ID and password assigned during signup
2. Get student submissions as files for manual review
3. Get assignments graded automatically (both short answer and MCQs) through simple language processing
4. Server keeps track of the instructor and students
5. Students can check pending assignments for a course
6. Students can answer questions through the software using interactive terminal messages
7. A same client cannot log in twice. This is checked through appropriate error handling

## Instructions: 

Compile the complete code using the following command: 
```bash
javac -d output Server/*.java Assignment/*.java Client/*.java Student/*.java Instructor/*.java
```

Run the Server:
```bash
java -cp output Server.Server
```

Run the Client:
```bash
java -cp output Client.Client
```

## User Instructions: