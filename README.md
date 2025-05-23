 Todo Summary Assistant
A full-stack productivity application that allows users to manage personal to-dos and generated. The summary is automatically sent to a Slack channel with a single click.

 Features
- get Add, and delete personal to-dos.
- View list of all current to-dos.
- Click a button to:
  - Summarize all pending to-dos using OpenAI
  - Send the summary to a configured Slack channel.
- Success/failure toast notifications for Slack integration.

---

 Frontend
- Reactjs
   CSS
  HTML

 Backend
- Spring Boot (Java)
- MySQL Database
- JPA + Spring Data (CRUD Repository)
- OpenAI API integration
- Slack Webhook integration

 Deployment
- Frontend: Vercel
- Backend: Render / Railway / Localhost
- Database: MySQL

---

todo-summary-assistant/
├── backend/ Spring Boot 
│ ├── controller/
│ ├── service/
│ ├── repository/
│ ├── model/
│ └── application.properties
├── frontend/ React App
│ ├── src/
| --Componets
    |--TodoItems
│ └── package.json

