import React, { useState } from "react";
const EditTask = ({ task, saveEditedTask }) => {
  const [newName, setNewName] = useState(task.name);
  const handleSubmit = (e) => {
    e.preventDefault();
    if (!newName.trim()) return;
    saveEditedTask({ ...task, name: newName });
  };
  return (
    <div className="edit-container">
      <h3>Edit Task</h3>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          value={newName}
          onChange={(e) => setNewName(e.target.value)}
          placeholder="Edit your task"
          autoFocus
          required
        />
        <button type="submit">Save</button>
      </form>
    </div>
  );
};
export default EditTask;





