var listenerHandler;
var pos = []; // array of lat, lng representing the polyline start and endpoints created by clicking map
var posIndex = 0; // index variable associate with pos[]
var polygon; // the polygon defined by the sequence of polylines
var markers = []; // array of all user's markers (unfiltered)


var map;
var latlng = [];

/**
 * Render the basic google map
 * Invoke method to retrieve marker locations and associate user from server side
 */
function initialize() {
	// create basic map without markers
	rendermap();
	// get marker locations and render on map
    retrieveMarkerLocations();
}

/**
 * The basic map, no markers, no centre specified
 * Canvas id on html is 'googleMap'
 */
function rendermap() {
  var mapProp = {
    mapTypeId:google.maps.MapTypeId.ROADMAP
  };
  map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
}

/**
 * registers click listener
 * click to capture lat,lng clicked point
 * store data in array (pos[])
 */
function start()
{
	$('#usertable').empty();
	listenerHandler = google.maps.event.addListener(map, 'click', function (e) {
		    //alert("Latitude: " + e.latLng.lat() + "\r\nLongitude: " + e.latLng.lng());
		    pos[posIndex] = e.latLng;
			if (posIndex > 0)
			{
		       polyline(posIndex - 1, posIndex);
			}
			posIndex += 1;
		});	

}

/**
 * create and render a polyline on map
 * attaches beginning to end previous polyline if such exists
 * @param prevIndex
 * @param index
 */
function polyline(prevIndex, index)
{
	var coords = [
	     new google.maps.LatLng(pos[prevIndex].lat(), pos[prevIndex].lng()),
	     new google.maps.LatLng(pos[index].lat(), pos[index].lng())
	     ];
	
	var line = new google.maps.Polyline({
	    path: coords,
	    geodesic: true,
	    strokeColor: '#FF0000',
	    strokeOpacity: 1.0,
	    strokeWeight: 2
    });
	
	line.setMap(map);

}

/**
 * Stop drawing the sequence of polylines
 * Update listeners
 * Invoke drawPolygon method
 */
function stop()
{
	polyline(pos.length - 1, 0); // close the polygon: last to first points
	// it would be better to somehow convert existing polyline to polygon
	// but for the moment this will do - overlaying polyline with polygon
    drawPolygon();
	google.maps.event.removeListener(listenerHandler);
	registerIsInsidePolyListener();
}

/**
 * Use data (pos[]) to draw polygon
 */
function drawPolygon()
{	
	var lineCoords = [];
	// log the coordinates
	// draw polygon defined by coordinates
	for (var j = 0; j < pos.length; j += 1)
	{
		console.log(pos[j].lat + " " + pos[j].lng);
		lineCoords[j] = new google.maps.LatLng(pos[j].lat(), pos[j].lng());
	}
	// make last point same as first to close the polygon
	lineCoords[pos.length] = new google.maps.LatLng(pos[0].lat(), pos[0].lng());

	polygon = new google.maps.Polyline({
		    path: lineCoords,
		    geodesic: true,
		    strokeColor: '#FF0000',
		    strokeOpacity: 1.0,
		    strokeWeight: 2
		  });

    polygon.setMap(map);
    registerIsInsidePolyListener();
}

/**
 * registers listener to determine if clicked point inside or outside polygon
 * https://developers.google.com/maps/documentation/javascript/examples/poly-containsLocation
 * https://developers.google.com/maps/documentation/javascript/geometry
 * https://developers.google.com/maps/documentation/javascript/3.exp/reference
 */
function registerIsInsidePolyListener()
{
	  var isInsideListenerHandler = google.maps.event.addListener(map, 'click', function(e) {
		    if (google.maps.geometry.poly.containsLocation(e.latLng, polygon)) {
		      alert("inside");
		    } else {
		      alert("outside");
		    }
	  });
	
}

/**
 * Use ajax call to get users and their geolocations
 * pass returned array marker locations to callback method
 * Here is the format in which marker data stored
 * geoObj[0] is user (donor) firstName.             
 * geoObj[1] is latitude                              
 * geoObj[2] is longitude  
 * We use geoObj[0] in the infoWindow. Click on marker reveals the name of donor (user)
 */
function retrieveMarkerLocations()
{
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

	latlng = data; // store the array of data in a global for later use
	fitBounds(latlng); // then invoke fitBounds to zoom and display markers within view
	populateTable(); // with the name + gps of users
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
        markers[i] = marker;
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
 * (re)initialize array of locations falling within poly overlay
 * Recall latlng[i][0] contains name of user (donor)
 * and latlng[i][1] and latlng[i][2] the latitude & longitude respectively
 * In this method the markers falling within polyon are rendered
 * and those outside are not displayed
 */
function setMarkersInsidePoly()
{
	for (var i = 0; i < latlng.length; i += 1)
	{
		var point = new google.maps.LatLng(latlng[i][1], latlng[i][2]);
		if (google.maps.geometry.poly.containsLocation(point, polygon))
		{
			markers[i].setVisible(true);
			populateTableRow(latlng[i]);
		}
		else
		{
			markers[i].setVisible(false);
		}
	}
}

/**
 * renders table row comprising user and its gps coordinates
 * @param data the array comprising donor name + gps (lat, lng)
 */
function populateTableRow(data)
{
	var donor = "<td>" + data[0] + "</td>";
	var gps   = "<td>" + data[1] + " " + data[2] + "</td>";
	$('#usertable').append("<tr>" + donor + gps + "</tr>");
}


/**
 * Populates table with complete user list + it's gps coords
 */
function populateTable()
{
	$.each(latlng, function(i, val) {
		populateTableRow(val);
	});
}

/**
 * Clears table row data
 * Restores table data with complete unfiltered user list
 */
function reset()
{
	$('#usertable').empty();
	populateTable();
}
google.maps.event.addDomListener(window, 'load', initialize);
