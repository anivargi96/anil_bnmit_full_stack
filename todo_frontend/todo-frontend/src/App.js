import React, { useEffect, useState } from "react";
import TodoItem from "./components/TodoItem";
import "./App.css";

function App() {
  const [todos, setTodos] = useState([]);
  const [task, setTask] = useState("");
  const [message, setMessage] = useState("");

  const baseUrl = "http://localhost:9001/todos";

  useEffect(() => {
    fetchTodos();
  }, []);

  const fetchTodos = async () => {
    const response = await fetch(baseUrl);
    const data = await response.json();
    setTodos(data);
  };

  const addTodo = async () => {
    if (!task.trim()) return;

    await fetch(baseUrl, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ task, completed: false }),
    });

    setTask("");
    fetchTodos();
  };

  const deleteTodo = async (id) => {
    await fetch(`${baseUrl}/${id}`, {
      method: "DELETE",
    });
    fetchTodos();
  };

  const summarizeAndSend = async () => {
    const response = await fetch(`${baseUrl}/summarize`, {
      method: "POST",
    });
    const result = await response.text();
    setMessage(result);
  };

  return (
    <div className="app-container">
      <h1>Todo summary application</h1>

      <div className="input-section">
        <input
          type="text"
          placeholder="Enter new task..."
          value={task}
          onChange={(e) => setTask(e.target.value)}
        />
        <button onClick={addTodo}>add</button>
      </div>

      <ul className="todo-list">
        {todos.map((todo) => (
          <TodoItem key={todo.id} todo={todo} onDelete={deleteTodo} />
        ))}
      </ul>

      <button onClick={summarizeAndSend} className="summary-button">
         Send to Slack 
      </button>

      {message && <p className="message">{message}</p>}
    </div>
  );
}

export default App;
