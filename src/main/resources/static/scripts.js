$(document).ready(function (e) {
    $('#input_text').focus();
    produce();
    clear();
});

function produce() {
    $("#produce").click(function () {

        $('#message').html('');

        $('#output_text').val('');
        M.textareaAutoResize($('#output_text'));

        var input = $('#input_text').val();

        if ($('#new_line').is(":checked")) input = input.replace(/[\n\r]/g, '');
        if ($('#slash').is(":checked")) input = input.replace(/\\/g, '');
        if ($('#open_bracket').is(":checked")) input = input.replace('"{', '{');
        if ($('#close_bracket').is(":checked")) input = input.replace('}"', '}');

        try {
            var jsonObject = JSON.parse(input);
            var newString = JSON.stringify(jsonObject, null, 4);

            $('#output_text').focus();
            $('#output_text').val(newString);
            M.textareaAutoResize($('#output_text'));

            var Model = new Object();
            Model.value = newString;

            console.log(Model);

            $.ajax({
                url: "/easy-mock/receive-json",
                type: "POST",
                data: JSON.stringify(Model),
                contentType: "application/json",
                success: function (response) {
                    $('#message').html('<h4 class="success">Success! <a target="_blank" href="http://localhost:8888/easy-mock/get-json">Click here</a></h4>')
                }
            })
        } catch (e) {
            $('#message').html('<h4 class="error">Invalid json input!</h4>');
        }
    });
}

function clear() {
    $('#clear').click(function () {
        $.ajax({
            url: "/easy-mock/clear",
            type: "POST",
            success: function (response) {
                location.reload(true);
            }
        });
    });
}

function resize() {
    M.textareaAutoResize($('#input_text'));
}