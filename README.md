# HrPolicyRag – AI-Powered HR Policy Chatbot

An AI-powered Retrieval-Augmented Generation (RAG) application built using **Java, Spring Boot, and Spring AI**. It allows users to ask questions about HR policies and receive relevant answers based on the contents of an uploaded PDF document.

Instead of relying only on the LLM's general knowledge, the application retrieves relevant information from the HR policy document using vector similarity search and uses that context to generate answers.

## 🚀 Features

- **PDF Document Ingestion:** Extracts text from HR policy PDF documents.
- **Text Chunking:** Splits large documents into smaller chunks for efficient retrieval.
- **Vector Embeddings:** Converts text chunks into numerical vectors using Google Gemini Embeddings.
- **Vector Database:** Stores and retrieves document embeddings using Qdrant.
- **Semantic Search:** Finds relevant document content based on the meaning of user questions.
- **AI-Powered Answers:** Uses Groq LLM to generate answers using retrieved HR policy context.
- **REST APIs:** Exposes endpoints for document ingestion and RAG-based question answering.

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Backend programming language |
| Spring Boot 4.1.1 | Backend application framework |
| Spring AI 2.0.2 | AI and RAG integration |
| Groq | LLM provider for generating answers |
| Google Gemini Embeddings | Text embedding generation |
| Qdrant | Vector database |
| Apache Tika | PDF text extraction |
| Maven | Dependency management and build tool |
| Docker | Running Qdrant locally |
| REST API | Communication with the application |

## 🏗️ Application Architecture

```text
             User Question
                   |
                   v
             REST Controller
                   |
                   v
             Rag Controller
                   |
                   v
          Google Gemini Embeddings
                   |
                   v
             Qdrant Vector DB
                   |
                   v
          Retrieve Relevant Chunks
                   |
                   v
          Build Contextual Prompt
                   |
                   v
              Groq LLM
                   |
                   v
             AI Generated Answer
                   |
                   v
                User
```

### Document Ingestion Flow

```text
          HR Policy PDF
                |
                v
        Apache Tika Reader
                |
                v
          Extract PDF Text
                |
                v
           Text Chunking
                |
                v
       Google Gemini Embeddings
                |
                v
          Store in Qdrant
```

## ⚙️ Prerequisites

Make sure you have the following installed:

- Java 21
- Maven
- Docker Desktop
- Git
- IntelliJ IDEA or another Java IDE

You also need API keys from:

- **Groq:** https://console.groq.com/keys
- **Google AI Studio:** https://aistudio.google.com/apikey

## 📥 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/asharsyed713-commits/HrPolicyRag.git
```

Navigate into the project:

```bash
cd HrPolicyRag
```

### 2. Configure API Keys

Set the following environment variables in your IDE's Run Configuration:

```text
GROQ_API_KEY=your_groq_api_key
GEMINI_API_KEY=your_gemini_api_key
```

**Important:** Never hardcode API keys in source code or commit them to GitHub.

### 3. Start Qdrant Using Docker

Run the following command:

```bash
docker run -d \
  --name qdrant \
  -p 6333:6333 \
  -p 6334:6334 \
  -v qdrant_storage:/qdrant/storage \
  qdrant/qdrant
```

Qdrant dashboard:

http://localhost:6333/dashboard

### 4. Configure the Application

Update `src/main/resources/application.properties` with your local Qdrant configuration and provider settings.

Make sure the API keys are loaded through environment variables rather than stored directly in the properties file.

### 5. Add the HR Policy PDF

Place your PDF document inside:

```text
src/main/resources/hr_policy_file.pdf
```

### 6. Run the Spring Boot Application

Using Maven:

```bash
./mvnw spring-boot:run
```

Or run `HrPolicyApplication.java` directly from your IDE.

The application starts at:

```text
http://localhost:8080
```

## 🔌 API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/save` | Extracts, chunks, embeds, and stores PDF content in Qdrant |
| GET/POST | `/rag/chat` | Accepts a question and generates an answer using retrieved HR policy context |

*Note: Check the controller implementation for the exact request parameters and payload format.*

## 🧪 Testing the Application

### Step 1: Ingest HR Policy Data

Call:

```http
POST http://localhost:8080/api/save
```

This reads the PDF, creates chunks, generates embeddings, and stores them in Qdrant.

### Step 2: Ask Questions

Use your RAG chat endpoint to ask questions such as:

- What is the company's leave policy?
- How many annual leaves are available?
- What is the maternity leave policy?
- What is the company's work-from-home policy?

The application retrieves relevant information from the HR policy document and uses it to generate an answer.

## 📂 Project Structure

```text
HrPolicyRag/
│
├── src/
│   ├── main/
│   │   ├── java/com/hrpolicy/
│   │   │   ├── config/
│   │   │   │   └── AiConfig.java
│   │   │   │
│   │   │   ├── controller/
│   │   │   │   ├── MyChatController.java
│   │   │   │   └── RagController.java
│   │   │   │
│   │   │   ├── rag/
│   │   │   │   └── DataLoader.java
│   │   │   │
│   │   │   └── HrPolicyApplication.java
│   │   │
│   │   └── resources/
│   │       ├── ai-prompt/
│   │       │   └── rag-prompt.st
│   │       ├── application.properties
│   │       └── hr_policy_file.pdf
│   │
│   └── test/
│
├── .gitignore
├── pom.xml
└── README.md
```

## 🧠 What I Learned

Through this project, I explored and implemented:

- Retrieval-Augmented Generation (RAG)
- Document loading and PDF text extraction
- Text chunking and overlapping
- Vector embeddings
- Semantic similarity search
- Vector database integration with Qdrant
- Spring AI integration with external LLM providers
- Building REST APIs using Spring Boot
- Docker-based local infrastructure

## 🔮 Future Improvements

- Add a React-based chatbot frontend.
- Implement real-time streaming responses.
- Add conversation history and session management.
- Display source documents and references in AI answers.
- Support uploading multiple PDF documents.
- Add document metadata filtering.
- Implement authentication and authorization.
- Deploy the application to a cloud platform.

## 👨‍💻 Author

**Ashar Farogh**

Java Backend Developer | Spring Boot | Microservices | Generative AI | RAG

GitHub: https://github.com/asharsyed713-commits

---

⭐ If you find this project interesting, feel free to explore the repository and share your feedback.
