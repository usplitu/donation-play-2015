//$(document).ready(function () {
function initialize() {
//    var locations2 = [
//        ['DESCRIPTION', 41.926979, 12.517385, 3],
//        ['DESCRIPTION', 43.914873, 7.506486, 2],
//        ['DESCRIPTION', 61.918574, 12.507201, 1],
//        ['DESCRIPTION', 61.918574, -25.507201, 1]
//    ];
    // trace
//    for (var i = 0; i < locations2.length; i +=1)
//    	console.log('locations2 ', 'index ', i, ' latitude ', locations2[i][1], ' longitude ', locations2[i][2]);
    // end trace
	getLocations(); // ajax call for marker lat|long coords
    var userlat = $("#latitude").val();
    var userlon = $("#longitude").val();
    getLocations();
    // trace
    var locations = []; // we require ajax data assignent to locations somehow
    for (var i = 0; i < locations.length; i +=1)
    	console.log('locations ','index ', i, ' latitude ', locations[i][1], ' longitude ', locations[i][2]);
    // end trace
    map = new google.maps.Map(document.getElementById('map'), {
        mapTypeId: google.maps.MapTypeId.ROADMAP
    });

    var infowindow = new google.maps.InfoWindow();

    var bounds = new google.maps.LatLngBounds();

    for (i = 0; i < locations.length; i++) {
        marker = new google.maps.Marker({
            position: new google.maps.LatLng(locations[i][1], locations[i][2]),
            map: map
        });

        bounds.extend(marker.position);
    }

    map.fitBounds(bounds);

    var listener = google.maps.event.addListener(map, "idle", function () {
        map.setZoom(3);
        google.maps.event.removeListener(listener);
    });
    
    setLocations();
}

function loadScript() {
    var script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = 'https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&' + 'callback=initialize';
    document.body.appendChild(script);
}

function setLocations()
{
	var length = $("#latitudes").children().length;
	var e = $("#latitudes").children();
//	console.log("size list: ", length)
	//alert(length);
}

function getLocations()
{
//		var data;
        $.ajax({url: "geolocations.json", 
        	   dataType: 'json',	
		       success: function(data) 
		       {   
//		    	   var locations = [];
		           $.each(data, function(index, geoObj) 
		           {
		             console.log('geolocations() ', 'index ' , index, ' latitude ', geoObj.latitude, ' longitude ', geoObj.longitude);
		             $("#latitudes").append("<li>" + geoObj.latitude + "</li>");		             
		             $("#longitudes").append("<li>" + geoObj.longitude + "</li>");
		           });
		        }       		
    }); 
    
}		        
window.onload = loadScript;
