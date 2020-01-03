$(()=>{
    if(window.location.hash) {
        var hash = window.location.hash;
        $(hash).modal('show');
        window.location.hash = '';
    }
});