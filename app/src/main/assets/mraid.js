/**
 * This is the VMG mraid here we define the behaviours our ad needs to have and needs to do
 *
 * @returns {undefined}
 */
(function () {
    // this is a variable where we define our mraid
    var mraid = window.mraid = {};
    // here we define our version of our mraid
    var VERSION = "2.0";
    // here are the events that we will use in our mraid
    var EVENTS = mraid.EVENTS = {
        "ERROR": "error",
        "READY": "ready",
        "SIZECHANGE": "sizeChange",
        "STATECHANGE": "stateChange",
        "VIEWABLECHANGE": "viewableChange"
    };
    // this are the states that our mraid can get in
    var STATES = mraid.STATES = {
        "LOADING": "loading",
        "DEFAULT": "default",
        "EXPANDED": "expanded",
        "RESIZED": "resized",
        "HIDDEN": "hidden"
    };
    // this are the placement types that we have
    var PLACEMENT_TYPES = mraid.PLACEMENT_TYPES = {
        "INLINE": "inline",
        "INTERSTITIAL": "interstitial"
    };
    var RESIZE_PROPERTIES_CUSTOM_CLOSE_POSITION = mraid.RESIZE_PROPERTIES_CUSTOM_CLOSE_POSITION = {
        "TOP_LEFT": "top-left",
        "TOP_CENTER": "top-center",
        "TOP_RIGHT": "top-right",
        "CENTER": "center",
        "BOTTOM_LEFT": "bottom-left",
        "BOTTOM_CENTER": "bottom-center",
        "BOTTOM_RIGHT": "bottom-right"
    };
    // this is the default position of our add
    var defaultPosition = {
        "x": 0,
        "y": 0,
        "width": 340,
        "height": 255
    };

    var resizeProperties = {
        "width": 0,
        "height": 0,
        "customClosePosition": RESIZE_PROPERTIES_CUSTOM_CLOSE_POSITION.TOP_RIGHT,
        "offsetX": 0,
        "offsetY": 0,
        "allowOffscreen": true
    };

    // this is the default state that our mraid is in
    var state = STATES.LOADING;
    // this is the default placement type
    var isViewable = false;
    var placementType = PLACEMENT_TYPES.INLINE;
    var isResizeReady = false;

    // this is our array of the events
    var eventListeners = {};
    // this is the function where we add our events
    mraid.addEventListener = function (event, listener) {
        for (var i = 0; i < EVENTS.length; i++) {
            if (event !== EVENTS[i]) {
                console.log("this is an unknown event " + event + " addListener");
            }
        }
        if (eventListeners[event] === undefined) { // if there is no event then the array will stay null
            eventListeners[event] = [];
        }
        // else the listener will get pushed in the array
        eventListeners[event].push(listener);

    };
    // this is the function that will fire the ready event
    mraid.fireReadyEvent = function () {
        mraid.fireEvent(mraid.EVENTS.READY);

    };
    // this is the function that will fire a event
    mraid.fireEvent = function (event) {
        if (eventListeners[event] !== undefined) {
            console.log("other FIREEE", eventListeners[event]);
            var listeners = eventListeners[event];

            for (var i in listeners) {
                console.log(i);
                var listener = listeners[i];
                listener();
                console.log("FIREEEEE");
            }

        }
    };


    // this will return the default position of our add
    mraid.getDefaultPosition = function () {
        console.log("the default position is " + defaultPosition);
        return defaultPosition;
    };
// this will get the state that our mraid is in
    mraid.getState = function () {
        console.log("The state is  " + state);
        return state;

    };
// this will get the version of our mraid
    mraid.getVersion = function () {
        console.log("This mraid has a version " + VERSION);
        return VERSION;
    };

    mraid.getResizeProperties = function () {
        console.log("mraid.getResizeProperties");
        return resizeProperties;
    };

    mraid.isViewable = function() {

		return isViewable;
		console.log(isViewable);
	};

        mraid.getResizeProperties = function(){
    console.log("getting properties"+resizeProperties.height);
    return resizeProperties;

} ;



    mraid.open = function (URL) {
        // if there is no url defined than it will give you a message that there is no URL
        if (!URL) {
            console.log("There needs to be a URL");
        } else {// else it will open up a new window with the specified URL
//            call("open?url=" + encodeURIComponent(URL));
            window.open(URL);
        }
    };

    mraid.close = function () {
        console.log("mraid.close");
        call("close");

    };

    mraid.removeEventListener = function (event, listener) {
        console.log("mraid.removeeventListener " + event + ",  " + String(listener));
        if (event === "") {
            console.log("event is required ");
        }
        if (eventListeners.hasOwnProperty(event)) {

            if (listener) {

                for (var i = 0; i < eventListeners.length; i++) {

                    if (listener === eventListeners[i]) {

                        eventListeners.splice(i, 1);

                        break;

                    }
                }
                if (i === eventListeners.length) {
                    console.log("listener " + listener + " not found for event " + event);
                }
                if (eventListeners.length === 0) {
                    delete eventListeners[event];
                } else {
                    delete eventListeners[event];
                }
            }

        } else {
            console.log("No listeners registered for event " + event);
        }
    };


    mraid.resize = function () {
        console.log("mraid.resize");

        call("resize");

    };

    mraid.setResizeProperties = function(properties){

	console.log("this is the mraid setResizeProperties");

        isResizeReady = false;
	var theProperties = ["width","height","offsetX","offsetY"];

	for(var i = 0; i < theProperties.length; i++){
		if(!properties.hasOwnProperty(theProperties[i])){
			console.log("the required property is missing");
			return;
		}
	}
	var changes = {"x":0,"y":0};

        var optionalProps = ["width","height","offsetX","offsetY","customClosePosition","allowOffscreen"];
	for (var i = 0; i < optionalProps.length; i++) {
            var propname = optionalProps[i];
            if (properties.hasOwnProperty(optionalProps[i])) {
                resizeProperties[propname] = properties[propname];
            }
        }

        var parameters = "width="+resizeProperties.width+
					 "&height="+resizeProperties.height+
					 "&offsetX="+(resizeProperties.offsetX+changes.x)+
					 "&offsetY="+(resizeProperties.offsetY+changes.y)+
					 "&customClosePosition="+resizeProperties.customClosePosition+
					 "&allowOffScreen="+resizeProperties.allowOffscreen;

	call("setResizeProperties?"+parameters);
	isResizeReady = true;




};

mraid.fireViewableChangeEvent = function(newIsViewable) {
		console.log("mraid.fireViewableChangeEvent " + newIsViewable);
		if (isViewable !== newIsViewable) {
			isViewable = newIsViewable;
			fireEvent(mraid.EVENTS.VIEWABLECHANGE, isViewable);
		}
	};



    function fireEvent(event) {
		var args = Array.prototype.slice.call(arguments);
		args.shift();
		console.log("fireEvent " + event + " [" + args.toString() + "]");
		var listeners = eventListeners[event];
		if (listeners) {
			var len = listeners.length;
			console.log(len + " listener(s) found");
			for (var i = 0; i < len; i++) {
				listeners[i].apply(null, args);
			}
		} else {
			console.log("no listeners found");
		}
	};

    function call(command) {
        var iframe = document.createElement("IFRAME");
        iframe.setAttribute("src", "mraid://" + command);
        document.documentElement.appendChild(iframe);
        iframe.parentNode.removeChild(iframe);
        iframe = null;
    }







})();