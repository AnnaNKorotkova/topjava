var filtersForm = $("#filter");

$(function () {
    makeEditable({
            ajaxUrl: "ajax/meals/",
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

function applyFilter() {
    updateTable(context.ajaxUrl + "filter", filtersForm);
}

function clearFilter() {
    filtersForm[0].reset();
    updateTable(context.ajaxUrl + "filter", filtersForm);
}

function deleteMeal(mealId) {
    $.ajax({
        type: "DELETE",
        url: context.ajaxUrl + mealId,
    }).done(function () {
        updateTable(context.ajaxUrl + "filter", filtersForm)
        successNoty("Deleted");
    });
}