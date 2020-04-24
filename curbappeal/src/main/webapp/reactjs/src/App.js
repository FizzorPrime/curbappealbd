import React, {useEffect, useReducer, useState} from 'react';
import './App.css';
import axios from 'axios';
import ImageCard from "./ImageCard";

function App() {

    const [allImages, setAllImages] = useState([]);
    const [_count, forceUpdate] = useReducer(x => x + 1, 0);

    const myWidget = window.cloudinary.createUploadWidget({
            cloudName: 'fizzor',
            uploadPreset: 'default-unsigned'
        }, (error, result) => {
            if (!error && result && result.event === "success") {
                console.log('Done! Here is the image info: ', result.info);
                postImage(result.info);
            }
        }
    );

    function postImage(file) {
        let body = {
            name : file.original_filename,
            type : file.format,
            url : file.url
        };
        let currentImages = allImages;
        console.log(JSON.stringify(body));
        axios.post('http://localhost:8080/images', JSON.stringify(body),{
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(response => {
                currentImages.push(response.data);
                currentImages = currentImages.sort((a,b) => a.position - b.position);
                setAllImages(currentImages);
            }).then(() => {
                myWidget.close();
        })
            .catch(err => console.error(err));
        forceUpdate();
    }

    function cloudinaryUpload(e) {
        e.preventDefault();
        myWidget.open();
    }

    useEffect(() => {
        axios.get('http://localhost:8080/images')
            .then(res => {
                let sortedImages = res.data.sort((a,b) => a.position - b.position);
                setAllImages(sortedImages);
                }
            );

    }, [_count]);

    return (
        <div className="App">

            <button id="upload_widget" className="cloudinary-button" onClick={(e) => {cloudinaryUpload(e)}}>Upload Image</button>
            <div className="gallery">
                {
                    !allImages.length
                    ?
                        <p>Loading...</p>
                    :
                        <>
                            {
                                allImages.map((image,index) => <ImageCard key={image.id} image={image} allImages={allImages} forceUpdate={forceUpdate} index={index} />)
                            }
                        </>
                }
            </div>
        </div>
    );
}

export default App;
