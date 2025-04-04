import React from 'react';
import InsertFile from './components/InsertFile';

/**
 * App component is the root component of the React application.
 * It renders the InsertFile component which handles file uploads.
 */
function App() {
  return (
    <div className="App">
      <h1>Upload File</h1>
      <InsertFile />
    </div>
  );
}

export default App;