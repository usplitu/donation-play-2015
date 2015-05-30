function initialize() {
    var locations = [
        ['DESCRIPTION', 41.926979, 12.517385, 3],
        ['DESCRIPTION', 43.914873, 7.506486, 2],
        ['DESCRIPTION', 61.918574, 12.507201, 1],
        ['DESCRIPTION', 61.918574, -25.507201, 1]
    ];

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
}

function loadScript() {
    var script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = 'https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&' + 'callback=initialize';
    document.body.appendChild(script);
}

window.onload = loadScript;