import React, {useEffect, useState} from 'react';
import png from './img1.png';
import './App.css';

const fileUploadHandler = (file) => {
    if (file !== null) {
        console.log(file);
    }
}

function App() {

    const [selectedFile, setSelectedFile] = useState(null);
    const [uploadText, setUploadText] = useState("Upload file here");

    //useEffect()

    return (
        <div className="App">
            <header className="App-header">
                <img src={png} className="App-logo" alt="logo" style={{zIndex:0}}/>
                <p style={{zIndex:1}}>
                    Hello World
                </p>
                <a
                    className="App-link"
                    href="https://reactjs.org"
                    target="_blank"
                    rel="noopener noreferrer"
                >
                    Learn React
                </a>
                <input type="file" onChange={event => {
                    //console.log(event.target.files[0].type);
                    if (event.target.files[0].type === "image/png" || event.target.files[0].type === "image/jpeg") {
                        setSelectedFile(event.target.files[0]);
                        setUploadText("Correct file type");
                    } else {
                        setSelectedFile(null);
                        setUploadText("Wrong file type");
                    }
                }}
                />
                <button onClick={() => fileUploadHandler(selectedFile)}>Upload</button>
                <p>
                    {uploadText}
                </p>
            </header>
        </div>
    );
}

export default App;
