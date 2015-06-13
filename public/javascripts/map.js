$(document).ready(function () 
{
	var map;
	var markerLatLng = [];
	
	function initialize() {
		// create basic map without markers
		map();
		// get marker locations and render on map
	    retrieveMarkerLocations();
	}

	/**
	 * Use ajax call to get markers
	 * pass returned array marker locations to callback method
	 * Here is the format in which marker data stored
	 * geoObj[0] is user (donor) firstName.             
     * geoObj[1] is latitude                              
     * geoObj[2] is longitude  
     * We use geoObj[0] in the infoWindow. Click on marker reveals the name of donor (user)
	 */
	function retrieveMarkerLocations()
	{
		var latlng = [];
	    $(function() {
	        $.get("donorlocation/geolocations", function(data) {
	        }).done(function(data) {
		           $.each(data, function(index, geoObj) 
				   {
				         console.log(geoObj[0] + " " + geoObj[1] + " " + geoObj[2]);
				   });
	        	callback(data);
	        });
	    });
   }
	    
    /**
     * we've got the marker location from data in ajax call
     * we now put data into an array
     * the format is 'firstName, xx.xxxx, yy.yyyyy' -> (firstName, lat, lng)
     * then invoke 'fitBounds' to render the markers, centre map and create infoWindow to display firstName
     */
	function callback(data)
	{
		latlngStr = [];
		$.each(data, function(index, geoObj) 
		{
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
     * and thence to a google.maps.LatLng object
     * @param str str is list of strings : username, lat, lon    
     * str[0] is user (donor) firstName                
     * str[1] is latitude                              
     * str[2] is longitude                             
     * 
     * @param The object 'str' holding an individual marker data set
     * @return A google.maps.LatLng object containing the marker coordinates.
     */
	function getLatLng(str)
	{	
	
		var lat = Number(str[1]);
		var lon = Number(str[2]);
		return new google.maps.LatLng(lat, lon);
	}
	 
	/**
	 * creates and positions markers
	 * sets zoom so that all markers visible
	 */
	function fitBounds(latlngStr)
	{
		var bounds = new google.maps.LatLngBounds();
	    var infowindow = new google.maps.InfoWindow();
	    
	    for (i = 0; i < latlngStr.length; i++) 
	    {
	        marker = new google.maps.Marker({
	            position: getLatLng(latlngStr[i]),
	            map: map
	        });
            /*respond to click on marker by displaying user (donor) name */
	        google.maps.event.addListener(marker, 'click', (function (marker, i) {
	            return function () {
	            	infowindow.setContent(latlngStr[i][0]);
	                infowindow.open(map, marker);
	            }
	        })(marker, i));
	        
	        bounds.extend(marker.position);
	    }

	    map.fitBounds(bounds);
	}
	
	google.maps.event.addDomListener(window, 'load', initialize);

});
