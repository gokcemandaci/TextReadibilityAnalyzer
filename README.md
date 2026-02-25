📖 Java Readability Analyzer

Java Readability Analyzer is a Maven-based Java application designed to analyze text files and calculate readability metrics. The program evaluates textual complexity and provides readability scores based on standard formulas.

🚀 Features

📄 Reads text input from files

🔎 Analyzes text structure (sentences, words, characters, etc.)

📊 Calculates readability scores

⚙️ Built using Java and Maven

🧩 Simple and modular structure

🛠️ Technologies Used

Java

Maven

Standard Java I/O & String processing

📂 Project Structure
JavaReadabilityAnalyzer/
 ├── pom.xml
 ├── src/
 │    └── main/
 │         └── java/
 │              └── TextReadabilityAnalyzer.java
 ├── README.md
 └── .gitignore
⚙️ How to Build & Run
1️⃣ Clone the repository
git clone https://github.com/your-username/your-repository-name.git
cd your-repository-name
2️⃣ Compile the project
mvn compile
3️⃣ Run the application
mvn exec:java

(If exec plugin is not configured, you can run the compiled class manually from the target folder.)

📌 How It Works

The program reads text input from a file.

It analyzes:

Number of sentences

Number of words

Number of characters

It applies readability formulas.

The readability score is printed to the console.

🎯 Purpose of the Project

This project was developed to demonstrate:

File handling in Java

String processing techniques

Basic text analysis algorithms

Maven project structure

📜 License

This project is created for educational purposes.
