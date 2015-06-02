var listenerHandler;
var pos = [];
var posIndex = 0;
var polygon;
var lineCoords = [];

var map;
function initialize() {
  var mapProp = {
    center:new google.maps.LatLng(51.508742,-0.120850),
    zoom:5,
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

//function draw()
//{
//	while (listenerHandler)
//	{
//		
//	}
//

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

function stop()
{
	polyline(pos.length - 1, 0); // close the polygon: last to first points
	// it would be better to somehow convert existing polyline to polygon
	// but for the moment this will do - overlaying polyline with polygon
    polygon();
	google.maps.event.removeListener(listenerHandler);
	//listenerHandler = null;
	registerIsInsidePolyListener();
}
/**
 * Use data (pos[]) to draw polygon
 */
function polygon()
{
	
	
	// log the coordinates
	// draw polygon defined by coordinates
	for (var j = 0; j < pos.length; j += 1)
	{
		console.log(pos[j].lat() + " " + pos[j].lng());
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
google.maps.event.addDomListener(window, 'load', initialize);

