$(document).ready(function () 
{
	var map;
	var markerLatLng = [];
	
	function initialize() {
		// create basic map without markers
		map();
		// pull markers and render on map
	    retrieveMarkerLocations();
	}

	/**
	 * Use ajax call to get markers
	 * pass returned array marker locations to callback method
	 */
	function retrieveMarkerLocations()
	{
		var latlng = [];
	    $(function() {
	        $.get("geolocations/json", function(data) {
	        }).done(function(data) {
	        	callback(data);
	        });
	    });
   }
	    
    /**
     * we've got the marker location from data in ajax cll
     * we now put into an array
     * the format is 'xx.xxxx, yy.yyyyy' -> (lat, lng)
     * then invoke 'fitBounds' to render the markers and centre map
     */
	function callback(data)
	{
		latlngStr = [];
		$.each(data, function(index, geoObj) 
		{
			console.log(geoObj.latitude + " " + geoObj.longitude);
            latlngStr.push(geoObj);
        });
        fitBounds(latlngStr);
	}
	
	/**
	 * The basic map, no markers, no centre specified
	 * Canvas id on html is 'googleMap'
	 */
	function map() {
	  var mapProp = {
	    mapTypeId:google.maps.MapTypeId.ROADMAP
	  };
	  map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
	}
	
    /**
     * A helper function to convert the latlng string to individual numbers
     * and thence to a LatLng object which is returned
     */
	function getLatLng(str)
	{
		//      To split "xxx.xxx, yyy.yy" -> "lat, lon"
		//		var s = str.latlng.split(",");
		//		var lat = Number(s[0]); 
		//		var lon = Number(s[1]);
		// str object with latitude and longitude fields
		var lat = Number(str.latitude);
		var lon = Number(str.longitude);
		return new google.maps.LatLng(lat, lon);
	}
	 
	/**
	 * creates and positions markers
	 * sets zoom so that all markers visible
	 */
	function fitBounds(latlngStr)
	{
		var bounds = new google.maps.LatLngBounds();

	    for (i = 0; i < latlngStr.length; i++) {
	        marker = new google.maps.Marker({
	            position: getLatLng(latlngStr[i]),
	            map: map
	        });

	        bounds.extend(marker.position);
	    }

	    map.fitBounds(bounds);
	}
	
	google.maps.event.addDomListener(window, 'load', initialize);

});
