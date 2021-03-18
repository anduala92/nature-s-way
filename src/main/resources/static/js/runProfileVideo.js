function addSourceToVideo(element, src, type) {
    let source = document.createElement('source');

    source.src = src;
    source.type = type;

    element.appendChild(source);
}

let video = document.getElementById('newVideo');

addSourceToVideo(video, 'https://res.cloudinary.com/anduala/video/upload/v1615483228/videos/primorsko-beach_nao8cn.mp4', 'video/mp4');
video.play();