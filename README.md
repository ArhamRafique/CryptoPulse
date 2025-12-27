# 🚀 CryptoPulse – Bitcoin Price Tracker App

CryptoPulse is a modern Android application that provides **real-time Bitcoin price tracking**, **historical trends**, and **market insights**.  
The app is built with a **scalable Clean Architecture** using the latest Android development tools and best practices.

---

## 📱 Features

- 📊 Real-time Bitcoin price updates  
- 📈 Historical price charts & trends  
- ⚡ Fast & reactive UI with Jetpack Compose  
- 🔄 Coroutines & Flow for async data handling  
- 🧠 Clean Architecture + MVVM  
- 🔐 Secure API integration  
- 🌙 Light & Dark mode support  

---

## 🛠 Tech Stack

- **Language:** Kotlin  
- **UI:** Jetpack Compose  
- **Architecture:** MVVM + Clean Architecture  
- **Networking:** Retrofit + REST API  
- **Async:** Coroutines & Flow  
- **Dependency Injection:** Hilt  
- **Local Storage:** Room (optional caching)  
- **Testing:** JUnit  
- **CI/CD:** GitHub Actions  

---

## 📂 Project Structure

```text
com.example.cryptopulse
│
├── data
│   ├── remote        # API services & DTOs
│   ├── local         # Room database (if used)
│   └── repository    # Repository implementations
│
├── domain
│   ├── model         # Business models
│   ├── repository    # Repository interfaces
│   └── usecase       # App business logic
│
├── presentation
│   ├── ui            # Jetpack Compose screens
│   ├── viewmodel     # ViewModels
│   └── navigation    # Navigation graph
│
└── MainActivity.kt
````

---

## 🔑 API Usage

* This app uses a **public cryptocurrency API** (e.g. CoinGecko or CoinMarketCap)
* API Base URL is configured in the **data/remote** layer
* API keys (if required) are stored securely and **excluded from GitHub** using `.gitignore`

---

## 🚀 Getting Started

1. Clone the repository

   ```bash
   git clone https://github.com/yourusername/CryptoPulse.git
   ```

2. Open the project in **Android Studio**

3. Sync Gradle

4. Add your API key (if required)

5. Run the app ▶️

---

## 📜 License

This project is licensed under the **MIT License**.

---

## 👨‍💻 Author

**Arham Rafique**
Android Developer | Kotlin | Jetpack Compose

⭐ If you like this project, don’t forget to **star the repository**!



