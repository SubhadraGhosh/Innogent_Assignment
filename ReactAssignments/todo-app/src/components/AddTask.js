import React from "react";
import {useState} from "react"
const AddTask = ({addTask})=>{
    const[taskName,setTaskName] = useState("");
    const submit = (e) => {
    e.preventDefault();
    addTask(taskName);
    setTaskName("");
  };
  return (
    <form onSubmit={submit}>
      <input value={taskName} onChange={e => setTaskName(e.target.value)} placeholder="Enter task" />
      <button type="submit">Add</button>
    </form>
  );
};
export default AddTask;
