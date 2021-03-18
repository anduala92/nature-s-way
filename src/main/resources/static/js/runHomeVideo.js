function addSourceToVideo(element, src, type) {
    let source = document.createElement('source');

    source.src = src;
    source.type = type;

    element.appendChild(source);
}

let video = document.getElementById('newVideo');

addSourceToVideo(video, 'https://res.cloudinary.com/anduala/video/upload/v1615420555/videos/parga-greece_g5qcrb.mp4', 'video/mp4');
video.play();