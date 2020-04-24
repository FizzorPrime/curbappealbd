import React from 'react';
import ImageButtons from "./ImageButtons";

const ImageCard = (props) => {
    return (
        <div className="image-card">
            <p>{props.image.name}.{props.image.type}</p>
            <img src={props.image.url} alt={props.image.name + '.' + props.image.type}/>
            <ImageButtons image={props.image} allImages={props.allImages} forceUpdate={props.forceUpdate}/>
        </div>
    )
}

export default ImageCard;