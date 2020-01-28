$('.basicAutoSelect').autoComplete({
    resolverSettings: {
        url: '/rest/pazienti-service'
    },
    formatResult: function (item) {
        console.log(item);
        if(!item.image || item.image.length === 0){
            item.image = "/images/profile_placeholder.svg";
        }
        return {
            value: item.id,
            text: item.text,
            html: [
                $('<img>').attr('src', item.image).css("height", 36).css("width", 36).addClass("profile-picture-thumbnail"), ' ',
                item.text
            ]
        };
    },
    events: {
        searchPost: (resultFromServer) => {
            let formattedResults = [];
            resultFromServer.forEach((item) => {
                formattedResults.push({
                    id : item.id,
                    image: item.fotoThumb,
                    text : item.nome + " " + item.cognome
                });
            });
            return formattedResults;
        }
    }
});

if(specialista){
    $('.basicAutoSelect').on('autocomplete.select', (evt, itm)=>{
        window.location.href = "/medico-specialista/scheda-paziente?id=" + itm.id;
    });
} else{
    $('.basicAutoSelect').on('autocomplete.select', (evt, itm)=>{
        window.location.href = "/medico-base/scheda-paziente?id=" + itm.id;
    });
}

