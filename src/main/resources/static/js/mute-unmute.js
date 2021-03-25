$("video").prop('muted', true);
$("audio").prop('muted', true);

$("#mute-sound").click( function (){
    if($("video").prop('muted') ) {
        $("video").prop('muted', false);
    } else {
        $("video").prop('muted', true);
    }
    if($("audio").prop('muted') ) {
        $("audio").prop('muted', false);
    } else {
        $("audio").prop('muted', true);
    }
});
