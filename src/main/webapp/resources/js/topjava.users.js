$(function () {
    makeEditable({
            ajaxUrl: "ajax/admin/users/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
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

function checkEnable(checkBox, userId) {
    $(checkBox).change(function () {
        $.ajax({
            url: context.ajaxUrl + userId,
            type: "POST",
            data: ({status: $(this).is(":checked") ? "true" : "false"})
        }).done(function (data) {
            $(checkBox).parent().parent().attr('check', data);
        });
    });
}