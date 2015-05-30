$(document).ready(function () {
    $(function() {
        $.get("geolocations.json", function(data) 
        {
            $.each(data, function(index, geoObj) 
            {
                $("#locations").append("<li>" + geoObj.latitude + ", " + geoObj.longitude +"</li>");
            });
        });
    });
});

