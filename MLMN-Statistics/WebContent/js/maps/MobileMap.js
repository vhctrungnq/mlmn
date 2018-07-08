// util object to store information
var Mapping = function () {
	this._map = [];
};

Mapping.prototype.add = function (obj) {
    this._map.push(obj);
};

Mapping.prototype.find = function (field, value) {
    var tracker = null;

    $.each(this._map, function (index, obj) {
        if (obj[field] === value) {
            tracker = obj;
            return true;
        }
    });

    return tracker;
};

Mapping.prototype.remove = function (obj) {
    var index = this._map.indexOf(obj);
	this._map.splice(index, 1);
};

Mapping.prototype.getAll = function () {
    return this._map;
};

// Define the overlay, derived from google.maps.OverlayView
function Label(options) {
    // Initialization
    this.setValues(options);

    // Label specific
    var span = this._span = document.createElement('span');
    span.style.cssText = 'position: relative; left: -50%; top: -65px; white-space: nowrap; border: 0; padding: 0px; font-size: 12px; color:black;';

    var div = this._div = document.createElement('div');
    div.appendChild(span);
    div.style.cssText = 'position: absolute; display: none';

    this._text = options.text;
};

Label.prototype = new google.maps.OverlayView;

// Implement onAdd
Label.prototype.onAdd = function () {
    var pane = this.getPanes().overlayLayer;
    pane.appendChild(this._div);

    // Ensures the label is redrawn if the text or position is changed.
    var me = this;
    this._listeners = [
        google.maps.event.addListener(this, 'position_changed', function () { me.draw(); }),
        google.maps.event.addListener(this, 'text_changed', function () { me.draw(); })
    ];
};

// Implement onRemove
Label.prototype.onRemove = function () {
    this._div.parentNode.removeChild(this._div);

    // Label is removed from the map, stop updating its position/text.
    for (var i = 0, I = this._listeners.length; i < I; ++i) {
        google.maps.event.removeListener(this._listeners[i]);
    }
};

// Implement draw
Label.prototype.draw = function () {
    var projection = this.getProjection();
    var position = projection.fromLatLngToDivPixel(this.get('position'));

    var div = this._div;
    div.style.left = position.x + 'px';
    div.style.top = position.y + 'px';
    div.style.display = 'block';

    this._span.innerHTML = this._text;
};

// object to control map for the mobile web
var MobileMap = function (options) {

    // google map object
    this._map = null;
    // mapping object to store tracker information (position, name, id...)
	this._mapping = null;

	var defaults = {
		mapDivId: 'map_canvas',
		zoom: 14,
		panControl: false,
		scaleControl: false,
		streetViewControl: false,
		center: new google.maps.LatLng(21.028,105.8524), // Load longitude and latitude first of map
		mapTypeId: google.maps.MapTypeId.ROADMAP
	};
	
    var options = $.extend(defaults, options);
    this.options = options;
};

MobileMap.prototype.initialize = function (trackers) {
	this._map = new google.maps.Map(document.getElementById('map_canvas'), this.options);
	var self = this;
	
	self._mapping = new Mapping();
	$.each(trackers, function(index, tracker) {
	    self._mapping.add(tracker);
	});
};

// draw car of use
MobileMap.prototype.setupTrackersPosition = function (positions) {
    var self = this,
		image = 'images/arrow_0.png',
		signal = "Yes",
		map = this._map;

    $(positions).each(function (index, item) {
        var tracker = self._mapping.find("uin", item.uin);
        if (tracker == null) {
            return;
        }
        
		// clear marker and label
		if(tracker.marker) {
			tracker.marker.setMap(null);
		}
		if(tracker.label) {
			tracker.label.setMap(null);
		}
		
		var getArrowImg = function (direction, signal, cmd) {
			if (cmd > 0) {
				return "websource/comm/arrow/alarm.png";
			}
			if (parseInt(signal) == 0) {
				return "websource/comm/arrow/nosignal.png";
			}
			direction = parseFloat(direction);
			if (isNaN(direction)) {
				direction = 0;
			}
			var i = 15;
			var n = parseInt((direction + i / 2) / i) * i;
			return "images/arrow_" + n + ".png";
		};
		
        image = getArrowImg(item.ori, 1, 0);
		
        // update tracker position
		$.extend(tracker, item);

        var latlng = new google.maps.LatLng(item.lat, item.lng);
        var marker = new google.maps.Marker({
            position: latlng,
            map: map,
            icon: image
        });
        
        var contentString;
        var geocoder = new google.maps.Geocoder();
		geocoder.geocode({'latLng': latlng}, function(results, status) {
			if (status == google.maps.GeocoderStatus.OK) {
				if (results[0]) {
					var address = results[0].formatted_address;
					// TODO: open infor dialog here, right after get address
					contentString ='<table border="0" cellpadding="0" cellspacing="0"><tr><td width="100%" class="EWTitle" nowrap>' + "Thông tin chung" +
		            '<\/td><\/tr>' +
		            '<tr><td nowrap>' +
		            
		            '<table border="0" cellpadding="0" cellspacing="0" class="EWContent">' +
		            '<tr><td><b>Mã thiết bị : </b></td><td>'+ item.name + " (" + item.uin + ")" + '</td></tr>' +
		    		'<tr><td><b>Thời gian : </b></td><td>'+ item.gpstime + '</td></tr>' +
		    		'<tr><td><b>Tín hiệu : </b></td><td>'+ signal + '</td></tr>' +
		    		'<tr><td><b>Tốc độ : </b></td><td>'+ item.speed + '</td></tr>' +
		    		'<tr><td><b>Địa chỉ : </b></td><td>'+ address + '</td></tr>' +
		    		'</table>';
		    		
		            + '<\/td><\/tr><\/table>';
				}
			} else {
				console.log("Geocoder failed due to: " + status);
			}
		});
        google.maps.event.addListener(marker, 'click', function() {
		
            var infowindow = new google.maps.InfoWindow({
    	        content: contentString
    	    });
            
			infowindow.open(map,marker);
		});
        
        var label = new Label({ map: map, text: tracker.name });
        label.bindTo('position', marker, 'position');
        label.bindTo('text', marker, 'position');
		
		$.extend(tracker, { "marker" : marker, "label" : label });
    });
}


MobileMap.prototype.getTracker = function (trackerUin) {
    var tracker = this._mapping.find("uin", trackerUin);
    return tracker;
};

MobileMap.prototype.panToTracker = function (siteid, lat, lng) {
	if (siteid == null) {
        return;
    }
	var image = '../js/maps/images/broadcast-station-icon.png',map = this._map;
	
    var point = new google.maps.LatLng(lat, lng);
    var marker = new google.maps.Marker({
        position: point,
        map: map,
        icon: image
    });
    
    var label = new Label({ map: map, text: siteid });
    label.bindTo('position', marker, 'position');
    label.bindTo('text', marker, 'position');
    this._map.setCenter(point);
	
    
};