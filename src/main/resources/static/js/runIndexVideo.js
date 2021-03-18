function addSourceToVideo(element, src, type) {
    let source = document.createElement('source');

    source.src = src;
    source.type = type;

    element.appendChild(source);
}

let video = document.getElementById('newVideo');

addSourceToVideo(video, 'https://res.cloudinary.com/anduala/video/upload/v1615308268/videos/lepite-beach_x1jgyl.mp4', 'video/mp4');
video.play();