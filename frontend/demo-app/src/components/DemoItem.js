import React from 'react';
import './DemoList.css'; // Assuming CSS is shared with DemoList component

const DemoItem = ({ item, index, deleteItem }) => {
  return (
    <li className="demo-item">
      {item}
      <button onClick={() => deleteItem(index)}>Delete</button>
    </li>
  );
};

export default DemoItem;