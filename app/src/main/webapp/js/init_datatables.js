$(document).ready(function() {
    $('.datatable').DataTable({
        dom: "<'row'<'col-sm-4'l><'col-sm-4 pag-fix'p><'col-sm-4'f>>" +
    "<'row'<'col-sm-12'tr>>",
        language:{
            "decimal":        ",",
            "emptyTable":     "Nessun risultato",
            "info":           "",
            "infoEmpty":      "",
            "infoFiltered":   "(filtrata da _MAX_ risultati totali)",
            "infoPostFix":    "",
            "thousands":      ".",
            "lengthMenu":     "Mostra _MENU_ risultati",
            "loadingRecords": "Caricamento...",
            "processing":     "Elaborazione...",
            "search":         "Cerca:",
            "zeroRecords":    "Nessun risultato corrispondente alla ricerca",
            "paginate": {
                "first":      "Prima",
                "last":       "Ultima",
                "next":       "Successiva",
                "previous":   "Precedente"
            },
            "aria": {
                "sortAscending":  ": attivare per ordinare colonna crescente",
                "sortDescending": ": attivare per ordinare colonna decrescente"
            }
        }
    });
} );