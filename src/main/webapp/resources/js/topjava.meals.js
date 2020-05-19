var filtersForm = $("#filter");
$(function () {
    makeEditable({
            ajaxUrl: "ajax/meals/filter",
            ajaxSaveUrl: "ajax/meals/",
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
    updateTable(filtersForm);
}

function clearFilter() {
    filtersForm[0].reset();
    updateTable();
}
