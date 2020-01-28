$(document).ready(function() {
    $.fn.DataTable.ext.pager.numbers_length = 5;
    let table = $('.datatable').DataTable({
        responsive: {
            breakpoints: [
                { name: 'xl', width: Infinity },
                { name: 'lg',  width: 1200 },
                { name: 'md',  width: 992 },
                { name: 'sm',  width: 768 },
                { name: 'xs',   width: 576 }
            ]
        },
        noWrap: true,
        order: [[$('th.default-sort').index(), 'asc']],
        dom: "<'row'<'col-lg-4 mb-2 mb-lg-0'l><'col-lg-4 pag-fix mb-2 mb-lg-0'p><'col-lg-4'f>>" +
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
    table.$('[data-toggle="popover"]').popover();
} );