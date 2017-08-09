/**
 * This is the VMG mraid here we define the behaviours our ad needs to have and needs to do
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 *
 *
 */
(function () {
    // this is a variable where we define our mraid window
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

    // these are the resize properties that our mraid file will have for the custom close
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
    // these are the resize properties for when the ad comes in a state where resizing is needed
    var resizeProperties = {
        "width": 0,
        "height": 0,
        "customClosePosition": RESIZE_PROPERTIES_CUSTOM_CLOSE_POSITION.TOP_RIGHT,
        "offsetX": 0,
        "offsetY": 0,
        "allowOffscreen": true
    };

    /*
    these are the default states where the ad will be in when it starts up
    it will be loading and it will not be viewable etc.
    */
    var state = STATES.LOADING;
    var isViewable = false;
    var placementType = PLACEMENT_TYPES.INLINE;
    var isResizeReady = false;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    }; // end of addEventListener();

    // this is the function that will fire the ready event
    mraid.fireReadyEvent = function () {
        mraid.fireEvent(mraid.EVENTS.READY);

    }; // end of fireReadyEvent();

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
    }; // end of fireEvent();


    // this will return the default position of our add
    mraid.getDefaultPosition = function () {
        console.log("the default position is " + defaultPosition);
        return defaultPosition;
    }; // end of getDefaultPosition();

// this will get the state that our mraid is in
    mraid.getState = function () {
        console.log("The state is  " + state);
        return state;

    };// end of getState();

// this will get the version of our mraid
    mraid.getVersion = function () {
        console.log("This mraid has a version " + VERSION);
        return VERSION;
    }; // end of getVersion();


// this function checks whether the ad is viewable or not
    mraid.isViewable = function() {
        return isViewable;
		console.log(isViewable);
	};// end of isViewable();

// this function will get the resize properties for example the resizing of the ad changes and you want to see the properties of how it is laid out now
   // you can simply add this getter to see the situation
        mraid.getResizeProperties = function(){
    console.log("getting properties"+resizeProperties.height);
    return resizeProperties;

} ; //end of getResizeProperties()


    ////////////////////////////////// {FUNCTIONS FOR FUNCTIONALITY} ////////////////////////////////////////////////////////////////////////
    // this is the function that will open up a new window with the given url in a new browser
    mraid.open = function (URL) {
        // if there is no url defined than it will give you a message that there is no URL
        if (!URL) {
            console.log("There needs to be a URL");
        } else {// else it will open up a new window with the specified URL
//            call("open?url=" + encodeURIComponent(URL));
            window.open(URL);
        }
    }; // end of open function
// this is the close function which will be called when the ad is not visible etc.
    mraid.close = function () {
        console.log("mraid.close"); // this is for debugging reasons
        call("close"); // this will make a call to the call function which will create a iFrame

    };// end close function


// this is the removeListenere function which has the ability to remove an event when it is not used
    mraid.removeEventListener = function (event, listener) { // this function has two parameters
        console.log("mraid.removeeventListener " + event + ",  " + String(listener)); // this is for debugging reasons
        if (event === "") { // if event is not given than it will give a message that the event is not included
            console.log("event is required ");
        }
        if (eventListeners.hasOwnProperty(event)) { // if the given event is in the eventListeners object

            if (listener) { // then it will check whether the listener is not null

                for (var i = 0; i < eventListeners.length; i++) { // it will then loop through the events

                    if (listener === eventListeners[i]) { // and if the given listener is equal to the ones in the object

                        eventListeners.splice(i, 1); // after that it will add the listener to the object

                        break;// and it will break out of the for loop

                    }
                }

                if (i === eventListeners.length) {
                    console.log("listener " + listener + " not found for event " + event);
                }
                // if the object length is equal to 0, so it is empty, then it will delete the event that is given with the parameters
                if (eventListeners.length === 0) {
                    delete eventListeners[event];
                } else {
                    delete eventListeners[event];
                }
            }

        } else {
            console.log("No listeners registered for event " + event);
        }
    }; // end of removeListener function

// this is the resize function which will make it possible to resize our ad when needed
    mraid.resize = function () {
        console.log("mraid.resize"); // this is for debugging reasons

        call("resize"); // make a call to the function call();

    };
    // with the help of this function you can add properties to how the add needs to react to a certian size change
    mraid.setResizeProperties = function(properties){

	console.log("this is the mraid setResizeProperties"); //this is for debugging reasons
        // set the variable to false
        isResizeReady = false;
        // this is the array with the must have properties
	var theProperties = ["width","height","offsetX","offsetY"];
// loop through the array
	for(var i = 0; i < theProperties.length; i++){
		if(!properties.hasOwnProperty(theProperties[i])){ // if the given properties does not have all the needed properties
			console.log("the required property is missing");// then the system will give a message that properties are missing
			return;// return nothing
		}
	}
	// this is the array for the changes to the x and y of the ad
	var changes = {"x":0,"y":0};
    // this an array with optional extras you can add to your add when resized
        var optionalProps = ["width","height","offsetX","offsetY","customClosePosition","allowOffscreen"];
	for (var i = 0; i < optionalProps.length; i++) { // loop through the array
            var propname = optionalProps[i]; // save the optional property in a variable
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
	isResizeReady = true; // set the resize ready boolean to true




}; // end of resize function

// this is the event that will change the viewbility of the ad
mraid.fireViewableChangeEvent = function(newIsViewable) {
		console.log("mraid.fireViewableChangeEvent " + newIsViewable);
		if (isViewable !== newIsViewable) {
			isViewable = newIsViewable;
			fireEvent(mraid.EVENTS.VIEWABLECHANGE, isViewable);
		}
	};// end of fireviewableChangeEvent


// this is a special funnction that will make sure we can fire events without doing it this stuff in all of our functions
// we simply made one function that we can call in other functions
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
	};// end of fireevent function

// this is the call function which we call when we need to open a new browser etc.
    function call(command) {
        var iframe = document.createElement("IFRAME");
        iframe.setAttribute("src", "mraid://" + command);
        document.documentElement.appendChild(iframe);
        iframe.parentNode.removeChild(iframe);
        iframe = null;
    }// end of call function







})();