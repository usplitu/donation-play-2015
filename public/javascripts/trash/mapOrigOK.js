
$(document).ready(function () {
	var map;
	var markersLatLng = [];
	function initialize() {
		var markers = arrayUserMarker();
	    //var map;
	    //var marker;
	    //var userlat = $("#latitude").val();
	    //var userlon = $("#longitude").val();
	    //var latlng = new google.maps.LatLng(userlat, userlon);
	
	    var mapOptions = {
	        zoom : 8,
	        //center : new google.maps.LatLng(userlat, userlon),
	        //center: new google.maps.LatLng(50.820645,-0.137376)
	        mapTypeId : google.maps.MapTypeId.ROADMAP
	    };
	    var mapDiv = document.getElementById('map_canvas');
	    map = new google.maps.Map(mapDiv,mapOptions);
	    mapDiv.style.width = '500px';
	    mapDiv.style.height = '195px';
	    // place all markers
		var markers =  arrayUserMarker();
	    for (var i; i < markers.length; i +=1)
	 	{
	    	markers[i].setMap(map);	    
	   	}
	    fitBounds(markers); // set zoom so that all markers visible
	}
	
	function arrayUserMarker()
	{
		var markers =  [];
	    $(function() {
	        $.get("geolocations.json", function(data) 
	        {
	            $.each(data, function(index, geoObj) 
	            {
	            	var latlng = new google.maps.LatLng(geoObj.latitude, geoObj.longitude);
	            	markersLatLng.push(latlng); // required later to show all markers on map
			        var marker = new google.maps.Marker({
			            map : map,
			            draggable : false,
			            position : latlng,
			            title : geoObj.latitude + ", " + geoObj.longitude
		            });
			        markers.push(marker);
	            });
	        });
	    });
	    return markers;	
	}
	
	function fitBounds(markers)
	{
		var bounds = new google.maps.LatLngBounds();
		for(var i = 0; i < markers.length; i += 1) 
		{
			bounds.extend(markers[i].getPosition());
		}
		map.fitBounds(bounds);	
	}
	
	google.maps.event.addDomListener(window, 'load', initialize);
});