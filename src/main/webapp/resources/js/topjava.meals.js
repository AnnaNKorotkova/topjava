// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "ajax/meals/filter",
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
                        "asc"
                    ]
                ]
            })
        }
    );
});

function formFilter() {
    $.ajax({
        type: "GET",
        url: context.ajaxUrl,
        data: formMeal.serialize()
    }).done(function (data) {
        context.datatableApi.clear().rows.add(data).draw();
    });
}

function clearFilter() {
    $("#filter")[0].reset();
    updateTable();
}

