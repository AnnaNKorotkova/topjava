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
     $(checkBox).off('change');
    var isEnable = $(checkBox).is(":checked");
    $(checkBox).change(function () {
        $.ajax({
            url: context.ajaxUrl + userId,
            type: "POST",
            data: ({status: isEnable}),
        })
            .done(function () {
            $(checkBox).parent().parent().attr('check', isEnable);
        })
            .fail(function () {
                $(checkBox).prop('checked', !isEnable);
            });
    });
}