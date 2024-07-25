import React, { useState } from 'react';
import DemoItem from './DemoItem';
import './DemoList.css';

function DemoList() {
  const [items, setItems] = useState([]);
  const [newItem, setNewItem] = useState('');

  const addItem = () => {
    if (newItem.trim() !== '') {
      setItems([...items, newItem]);
      setNewItem('');
    }
  };

  const deleteItem = (index) => {
    const newItems = items.filter((_, i) => i !== index);
    setItems(newItems);
  };

  return (
    <div className="demo-list">
      <h2>Demo List</h2>
      <input
        type="text"
        value={newItem}
        onChange={(e) => setNewItem(e.target.value)}
        placeholder="Add a new item"
      />
      <button onClick={addItem}>Add</button>
      <ul>
        {items.map((item, index) => (
          <DemoItem key={index} item={item} index={index} deleteItem={deleteItem} />
        ))}
      </ul>
    </div>
  );
}

export default DemoList;
