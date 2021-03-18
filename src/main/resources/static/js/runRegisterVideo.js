function addSourceToVideo(element, src, type) {
    let source = document.createElement('source');

    source.src = src;
    source.type = type;

    element.appendChild(source);
}

let video = document.getElementById('newVideo');

addSourceToVideo(video, 'https://res.cloudinary.com/anduala/video/upload/v1615307228/videos/veleka-beach_jhate1.mp4', 'video/mp4');
video.play();