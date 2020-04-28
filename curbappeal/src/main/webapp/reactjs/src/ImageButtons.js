import React from 'react';
import axios from "axios";

// global variables to be used between functions
let listOfPositions = [];
let currentPosition = 0;

/**
 * lowers the value of the image's position by swapping it with the next image lower than it
 * @author Fizzor
 * @param props the array containing all images as well as the image to be updated
 */
function lowerPosition(props) {
    // first checks to make sure the list is greater than 1 and the image is not the first image in the database
    if(props.allImages.length === 1){
        console.log('length = 1');
    }
    else if(props.image.position === 1){
        console.log('position = 1');
    }
    else {
        // finds and stores the values of the two positions to be swapped
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
        // executes two separate calls to the database to update the images
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

// checks to see if the given position exists in the array of positions in the database
function checkIndex(pos){
    return currentPosition === pos
}

// appends the list of positions to a new variable
function appendPositions(item) {
    listOfPositions.push(item.position);
}

/**
 * raises the value of the image's position by swapping it with the next image higher than it
 * @author Fizzor
 * @param props the array containing all images as well as the image to be updated
 * @param lastImage is the last image in the database
 */
function raisePosition(props, lastImage) {
    // first checks to make sure the list is greater than 1 and the image is not the last image in the database
    if(props.allImages.length === 1){
        console.log('length = 1');
    }
    else if(props.image.position === lastImage.position){
        console.log('position = last');
    }
    else {
        // finds and stores the values of the two positions to be swapped
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
        // executes two separate calls to the database to update the images
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

/**
 * Deletes the given image from the database
 * @author Fizzor
 * @param props the array containing all images as well as the image to be deleted
 */
function cloudinaryDelete(props) {
    axios.delete(`http://localhost:8080/images/${props.image.id}`)
        .then(res => {
                console.log(res);
                props.forceUpdate();
            }
        );
}

/**
 * Creates the html for the three buttons to update and delete images
 * @author Fizzor
 * @param props the array containing all images as well as the image to be updated
 */
const ImageButtons = (props) => {
    // creates and stores a new variable with the value of the last image in the database
    let lastImage = props.allImages.pop();
    props.allImages.push(lastImage);
    return (
        <div className="image-buttons">
            {/* checks to see if the image is the first image in the database and doesn't create the button if it is */}
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
            {/* checks to see if the image is the last image in the database and doesn't create the button if it is */}
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