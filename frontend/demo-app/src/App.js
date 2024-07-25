import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import DemoList from './components/DemoList';
import './App.css';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<DemoList />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;