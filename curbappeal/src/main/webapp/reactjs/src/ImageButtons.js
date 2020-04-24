import React from 'react';
import axios from "axios";

let listOfPositions = [];
let currentPosition = 0;

function lowerPosition(props) {
    if(props.allImages.length === 1){
        console.log('length = 1');
    }
    else if(props.image.position === 1){
        console.log('position = 1');
    }
    else {
        listOfPositions = [];
        props.allImages.forEach(appendPositions)
        currentPosition = props.image.position;
        let currentIndex = listOfPositions.findIndex(checkIndex);
        let newPosition = listOfPositions[currentIndex - 1];
        let otherPosition = currentPosition;
        let body = {
            id : props.image.id,
            name : props.image.name,
            type : props.image.type,
            url : props.image.url,
            position : newPosition
        };
        axios.put(`http://localhost:8080/images/${props.image.id}`, JSON.stringify(body),{
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(response => {
                console.log(response);
            });
        body = {
            id : props.allImages[currentIndex - 1].id,
            name : props.allImages[currentIndex - 1].name,
            type : props.allImages[currentIndex - 1].type,
            url : props.allImages[currentIndex - 1].url,
            position : otherPosition
        };
        axios.put(`http://localhost:8080/images/${props.allImages[currentIndex - 1].id}`, JSON.stringify(body),{
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(response => {
                console.log(response);
                props.forceUpdate();
            });
    }
}

function checkIndex(pos){
    return currentPosition === pos
}

function appendPositions(item) {
    listOfPositions.push(item.position);
}

function raisePosition(props, lastImage) {
    if(props.allImages.length === 1){
        console.log('length = 1');
    }
    else if(props.image.position === lastImage.position){
        console.log('position = last');
    }
    else {
        listOfPositions = [];
        props.allImages.forEach(appendPositions)
        currentPosition = props.image.position;
        let currentIndex = listOfPositions.findIndex(checkIndex);
        let newPosition = listOfPositions[currentIndex + 1];
        let otherPosition = currentPosition;
        let body = {
            id : props.image.id,
            name : props.image.name,
            type : props.image.type,
            url : props.image.url,
            position : newPosition
        };
        axios.put(`http://localhost:8080/images/${props.image.id}`, JSON.stringify(body),{
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(response => {
                console.log(response);
            });
        body = {
            id : props.allImages[currentIndex + 1].id,
            name : props.allImages[currentIndex + 1].name,
            type : props.allImages[currentIndex + 1].type,
            url : props.allImages[currentIndex + 1].url,
            position : otherPosition
        };
        axios.put(`http://localhost:8080/images/${props.allImages[currentIndex + 1].id}`, JSON.stringify(body),{
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(response => {
                console.log(response);
                props.forceUpdate();
            });
    }
}

function cloudinaryDelete(props) {
    axios.delete(`http://localhost:8080/images/${props.image.id}`)
        .then(res => {
                console.log(res);
                props.forceUpdate();
            }
        );
}

const ImageButtons = (props) => {
    let lastImage = props.allImages.pop();
    props.allImages.push(lastImage);
    return (
        <div className="image-buttons">
            {
                props.image.position !== props.allImages[0].position
                    ?
                    <>
                        {
                            <button onClick={() => {
                                lowerPosition(props)
                            }}>&#10094;&#10094;</button>
                        }
                    </>
                    :
                    <></>
            }
            <button onClick={() => {
                cloudinaryDelete(props)
            }}>Delete</button>
            {
                props.image.position !== lastImage.position
                ?
                <>
                    {
                        <button onClick={() => {
                            raisePosition(props, lastImage)
                        }}>&#10095;&#10095;</button>
                    }
                </>
                :
                <></>
            }
        </div>
    )
}

export default ImageButtons;