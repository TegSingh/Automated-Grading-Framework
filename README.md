# Automated Grading Framework: 

## Features: 

1. User login and privacy: Uses ID and password assigned during signup
2. Get student submissions as files for manual review
3. Get assignments graded automatically
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

1. Login using the ID given in instructor_list and student_list provided in the Server directory
2. Make sure to provide correct inputs. The code handles exceptions but wrong inputs changes the flow of control
3. All assignment files are stored inside the **submissions directory** for both Server and Client(if client is an instructor)
4. **Logging out** will lead to loss of information like posted assignments
5. Students submit their assignments and get them **automatically graded**. A grade is shown to the student and also updated in the database
6. The database is temporary so some information apart from the user details will be lost if the client is terminated. The only information that is permanent is the information specified in the **instructor_list.txt** and **student_list.txt** files