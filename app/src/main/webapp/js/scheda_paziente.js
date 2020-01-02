$(()=>{

    //Mostra la card della visita/esame selezionato di default
    let selectedVisitaSpecialistica = $('#visitaSpecialistica').val();
    $('#descrizioneVisitaSpecialistica-' + selectedVisitaSpecialistica).show();

    let selectedEsameSSP = $('#esameSpecialistico').val();
    $('#descrizioneEsameSSP-' + selectedEsameSSP).show();

});

// Nascondi tutte le card tranne quella corrispondente alla visita/esame selezionato
$('#visitaSpecialistica').on('change', function() {
    $('#visitaSpecialisticaDescriptionContainer').children().hide();
    $('#descrizioneVisitaSpecialistica-' + this.value).show();
});
$('#esameSpecialistico').on('change', function() {
    $('#visitaSSPDescriptionContainer').children().hide();
    $('#descrizioneEsameSSP-' + this.value).show();
});