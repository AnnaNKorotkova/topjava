var filtersForm = $("#filter");
$(function () {
    makeEditable({
            ajaxUrl: "ajax/meals/",
            ajaxFilterUrl: "ajax/meals/filter",
            datatableApi: $("#mdatatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "dsc"
                    ]
                ]
            })
        }
    );
});

function clearFilter() {
    filtersForm[0].reset();
    updateTable();
}

function updateTable() {
    $.get(context.ajaxFilterUrl, filtersForm.serialize())
        .done(function (data) {
            reDraw(data);
        })
}

