function initialize() 
{
	init.getLocations
}

var init = {
    getLocations: function (renderMap) {
        $.ajax({
            url: "geolocations.json",
            success: function(data) 
            {   
               $.each(data, function(index, geoObj) 
               {
                 console.log('geolocations() ', 'index ' , index, ' latitude ', geoObj.latitude, ' longitude ', geoObj.longitude);
                 $("#latitudes").append("<li>" + geoObj.latitude + "</li>");                 
                 $("#longitudes").append("<li>" + geoObj.longitude + "</li>");
               });
            }
        }).done(function (data) {       
            renderMap(data);
        });
    }
};

function renderMap(data)
{
  $.each(data, function(index, geoObj) 
  {
    console.log('geolocations() ', 'index ' , index, ' latitude ', geoObj.latitude, ' longitude ', geoObj.longitude);
    $("#latitudes").append("<li>" + geoObj.latitude + "</li>");                 
    $("#longitudes").append("<li>" + geoObj.longitude + "</li>");
  });

}

function loadScript() {
    var script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = 'https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&' + 'callback=initialize';
    document.body.appendChild(script);
}


window.onload = loadScript;
