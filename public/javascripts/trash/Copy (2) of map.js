
$(document).ready(function () {
	var map;
	var markers =  [];
	var markersLatLng = [];
	function initialize() {
		var markers = arrayUserMarker();
	    //var map;
	    var marker;
	    var userlat = $("#latitude").val();
	    var userlon = $("#longitude").val();
	    var latlng = new google.maps.LatLng(userlat, userlon);
	
	    var mapOptions = {
	        zoom : 8,
	        center : new google.maps.LatLng(userlat, userlon),
	        mapTypeId : google.maps.MapTypeId.ROADMAP
	    };
	    var mapDiv = document.getElementById('map_canvas');
	    map = new google.maps.Map(mapDiv,mapOptions);
	    mapDiv.style.width = '500px';
	    mapDiv.style.height = '195px';
	    // place all markers
	    for (var i; i < markers.length; i +=1)
	 	{
	    	markers[i].setMap(map);	    
	   	}
	    //fitBounds(); // set zoom so that all markers visible
	}
	
	function arrayUserMarker()
	{
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
	
//	function fitBounds()
//	{
//		var bounds = new google.maps.LatLngBounds();
//		for(var i = 0; i < markers.length; i += 1) 
//		{
//			bounds.extend(markers[i].getPosition());
//		}
//		map.fitBounds(bounds);		
//	}
	
	function fitBounds()
	{
		

	}
	
	google.maps.event.addDomListener(window, 'load', initialize);
});