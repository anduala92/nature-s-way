function addSourceToVideo(element, src, type) {
    let source = document.createElement('source');

    source.src = src;
    source.type = type;

    element.appendChild(source);
}

let music = document.getElementById('newMusic');

addSourceToVideo(music, 'https://res.cloudinary.com/anduala/video/upload/v1616605245/Music/Thunderball_-_Vai_Vai_a5rdim.mp3', 'audio/mpeg');
music.play();