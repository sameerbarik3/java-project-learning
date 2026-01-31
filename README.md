🐾 PetCare Scheduler – Java Console Application 

📌 Project Overview

PetCare Scheduler is a console-based Java application designed to help pet owners manage pet registrations and schedule important care appointments such as vet visits, vaccinations, and grooming. The application ensures data persistence using file handling and generates useful reports for upcoming and overdue appointments.

This project demonstrates strong understanding of Core Java, Object-Oriented Programming, Collections, File I/O, Date & Time API, and Exception Handling.

🎯 Features

Register pets with unique IDs

Schedule appointments (vet visit, vaccination, grooming)

View all registered pets

View appointment history for individual pets

Display upcoming and past appointments

Generate reports:

Pets with appointments in the next 7 days

Pets overdue for vet visits (no visit in last 6 months)

Persistent data storage using files

Robust input validation and exception handling

🛠️ Technologies Used

Java

Object-Oriented Programming (OOP)

Collections Framework (ArrayList, HashMap)

Java Date & Time API (LocalDate, LocalDateTime, DateTimeFormatter)

File I/O (FileReader, FileWriter, BufferedReader, BufferedWriter)

Exception Handling

Console-based UI

    1. Defined the 'Pet' and 'Appointment' classes to capture registration and scheduling data using encapsulation. 

    2.Built logic to record and store pet data and associate each 'Appointment' object with the corresponding 'Pet' instance. 

    3.Used 'LocalDateTime' from 'java.time' to validate that appointments occur in the future. 

    4.Created reporting logic to identify pets with upcoming appointments in the next 7 days and those overdue for vet visits by 6 months or more. 

    5.Applied 'HashMap<String, Pet>' and 'ArrayList<Appointment>' to store and navigate records efficiently. 

    6.Used file I/O and serialization ('ObjectOutputStream', 'ObjectInputStream') to save and reload application data, demonstrating data persistence, across sessions. 

By completing  project, I learned i can design and implement Java applications using 'class' structures, encapsulation, 'HashMap', 'ArrayList', exception handling, 'Scanner' input, and file-based persistence. You can build robust, user-driven systems that store and manage data, respond to user choices, and produce meaningful reports.

** Run BY : 1.cd /home/project/PetCareScheduler
       2. javac *.java
       3.java PetCareScheduler and enjoy **
       
👨‍💻 Author

Sameer Barik
(Java Developer | Fresher)
⭐ If you like this project, don’t forget to star the repository!
