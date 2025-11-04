import './App.css';
import AddTask from "./components/AddTask";
import TaskList from "./components/TaskList";
import EditTask from "./components/EditTask";
import { useState } from 'react';

function App() {
  const [tasks, setTasks] = useState([]);
  const [editingTask, setEditingTask] = useState(null);

  const addTask = (taskName) => {
    if (!taskName.trim()) return;
    const newTask = { id: Date.now(), name: taskName, completed: false };
    setTasks(prev => [...prev, newTask]);
  };
  const deleteTask = (id) => {
    setTasks(tasks.filter(task => task.id !== id));
  };
  const startEditing = (task) => {
    setEditingTask(task);
  };
  const updateTask = (updatedTask) => {
    setTasks(tasks.map(t => (t.id === updatedTask.id ? updatedTask : t)));
    setEditingTask(null);
  };
  return (
    <div className="app-container">
      <h1>To-Do Task</h1>
      <AddTask addTask={addTask} />
      {editingTask ? (
        <EditTask task={editingTask} saveEditedTask={updateTask} />
      ) : (
        <TaskList
          tasks={tasks}
          onEdit={startEditing}
          onDelete={deleteTask}
        />
      )}
    </div>
  );
}
export default App;







