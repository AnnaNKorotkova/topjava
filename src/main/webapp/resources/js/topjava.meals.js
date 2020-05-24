var mealAjaxUrl = "ajax/profile/meals/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "ajax/profile/meals/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: mealAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (data, type, row) {
                        if (type === "display") {
                             var dataObject = new Date(data);
                             return dataObject.toLocaleString();
                        }
                        return data;
                    }
                },
                {
                    "data": "description",
                    "render": function (data, type, row) {
                        if (type === "display") {
                            return data ;
                        }
                        return data;
                    }
                },
                {
                    "data": "calories",
                    "render": function (data, type, row) {
                        if (type === "display") {
                            return data ;
                        }
                        return data;
                    }
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "createdRow": function (row, data, dataIndex) {
                $(row).attr("data-mealExcess", data.excess);

            },
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        }),
        updateTable: updateFilteredTable
    });
});

function save() {
    $.ajax({
        type: "POST",
        url: context.ajaxUrl,
        data: withReplaceSpaceOnT()
    }).done(function () {
        $("#editRow").modal("hide");
        context.updateTable();
        successNoty("common.saved");
    });
}

function withReplaceSpaceOnT() {
    var dateTimeRow = $("#dateTime")
    dateTimeRow.val(dateTimeRow.val().replace(" ", "T"));
    return form.serialize();
}
