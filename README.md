# Full-Stack Wallet Application (Spring Boot + Next.js + PostgreSQL)

## 📌 Project Overview
This project is a full-stack wallet application built with:
- **Backend:** Spring Boot (Java 17, Gradle)
- **Frontend:** Next.js (React 18)
- **Database:** PostgreSQL
- **Containerization:** Docker & Docker Compose

---

## 🚀 Getting Started
### Prerequisites
Ensure you have the following installed:
- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)
- [Java 17+](https://adoptium.net/)
- [Node.js 18+](https://nodejs.org/en/download/)

---

## 🔧 Project Setup
### 1️⃣ Clone the Repository
```sh
git clone https://github.com/your-username/client-wallet.git
cd client-wallet
```

### 2️⃣ Build and Start the Containers
```sh
docker-compose up --build
```

---

## 🔗 Access the Application
- **Frontend (Next.js):** [http://localhost:3000](http://localhost:3000)
- **Backend (Spring Boot API):** [http://localhost:8080](http://localhost:8080)
- **PostgreSQL:** `localhost:5432` (Use the credentials from `.env` file)

---

## 📜 Documentation
- **Backend Documentation (JavaDoc):** Open `backend/documentation/index.html` in a browser.
- **Frontend Documentation (JSDoc):** Unable to generate JSDoc from files, but it is propperly filled.

---

## 🛠️ Development & Debugging
### Running Backend Locally (Without Docker)
```sh
cd backend
./gradlew build
java -jar build/libs/*.jar
```

### Running Frontend Locally (Without Docker)
```sh
cd frontend
npm install
npm run dev
```

### Viewing Logs
```sh
docker-compose logs -f backend
```

### Stopping Containers
```sh
docker-compose down
```

---

## 📌 Notes
- Ensure ports **5432**, **8080**, and **3000** are not in use before starting.
- If the database does not start properly, try:
  ```sh
  docker volume rm client-wallet_db_data
  ```

