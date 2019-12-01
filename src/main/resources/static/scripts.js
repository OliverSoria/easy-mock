var newString;

$(document).ready(function (e) {
    $('#input_text').focus();
    preview();
    produce();
    clear();
});

function preview() {
    $('#preview').click(function () {
        $('#message').text("");

        $('#output_text').val('');
        M.textareaAutoResize($('#output_text'));

        var input = $('#input_text').val();


        if ($('#new_line').is(":checked")) input = input.replace(/[\n\r]/g, '');
        if ($('#slash').is(":checked")) input = input.replace(/\\/g, '');
        if ($('#open_bracket').is(":checked")) input = input.replace('"{', '{');
        if ($('#close_bracket').is(":checked")) input = input.replace('}"', '}');

        try {
            var jsonObject = JSON.parse(input);
            newString = JSON.stringify(jsonObject, null, 4);

            $('#output_text').focus();
            $('#output_text').val(newString);
            M.textareaAutoResize($('#output_text'));
        } catch (e) {
            $('#message').text("Invalid json input!");
        }
    });
}

function produce() {


    $("#produce").click(function () {
        var Model = new Object();
        Model.value = newString;

        console.log(Model);

        $.ajax({
            url:"/easy-mock/receive-json",
            type:"POST",
            data: JSON.stringify(Model),
            contentType: "application/json",
            success: function(response){
               // $("#testdata").html(response);
            }
        })
    });


}

function clear() {
    $('#clear').click(function () {
        location.reload(true);
    });
}