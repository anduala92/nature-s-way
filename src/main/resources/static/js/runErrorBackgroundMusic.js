function addSourceToVideo(element, src, type) {
    let source = document.createElement('source');

    source.src = src;
    source.type = type;

    element.appendChild(source);
}

let music = document.getElementById('errorMusic');

addSourceToVideo(music, 'https://res.cloudinary.com/anduala/video/upload/v1616872721/Music/yt1s.com_-_Windows_error_Remix_dqwdke.mp3', 'audio/mpeg');
music.play();