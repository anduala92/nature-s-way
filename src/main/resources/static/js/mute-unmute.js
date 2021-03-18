$("video").prop('muted', true);

$("#mute-video").click( function (){
    if( $("video").prop('muted') ) {
        $("video").prop('muted', false);
    } else {
        $("video").prop('muted', true);
    }
});