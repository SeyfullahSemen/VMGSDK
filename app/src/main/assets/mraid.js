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
         // this is the default position of our add
         var defaultPosition = {
             "x": 0,
             "y": 0,
             "width": 340,
             "height": 300
         };

         // this is the default state that our mraid is in
         var state = STATES.LOADING;
         // this is the default placement type
         var placementType = PLACEMENT_TYPES.INLINE;

         var screenSize = {
         		"width" : 0,
         		"height" : 0
         	};

         // this is our array of the events
         var eventListeners = {};
         // this is the function where we add our events
         mraid.addEventListener = function (event, listener) {
           for (var i = 0; i < EVENTS.length; i++) {
                      if(event !== EVENTS[i]){
                     console.log("this is an unknown event "+event+" addListener");
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

         // this is to check whether it is viewable or not
         mraid.isViewable = function () {
             return true;
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

         mraid.getScreenSize = function (){
             console.log("the screen size is  "+screenSize);
             return screenSize;
         }




          mraid.open = function(URL) {
              // if there is no url defined than it will give you a message that there is no URL
             if (!URL) {
                 console.log("There needs to be a URL");
             } else {// else it will open up a new window with the specified URL
     //            call("open?url=" + encodeURIComponent(URL));
                 window.open(URL);
             }
         };


       mraid.removeEventListener  = function(event , listener){
             console.log("mraid.removeeventListener "+event+ ",  "+String(listener));
             if (event === "") {
                 console.log("event is required ");
             }
             if (eventListeners.hasOwnProperty(event)) {
                 if (listener) {
                     for (var i = 0; i < eventListeners.length; i++) {
                         if (listener === eventListeners[i] ) {
                             eventListeners.splice(i,1);
                             break;
                             console.log("Deleted");
                         }
                     }
                     if (i === eventListeners.length) {
                         console.log("listener "+listener+" not found for event "+event);
                     }if (eventListeners.length === 0) {
                         delete eventListeners[event];
                     }else {
                         delete eventListeners[event];
                     }
                 }

             }else {
                 console.log("No listeners registered for event "+event);
             }
         };


         function call(command) {
             var iframe = document.createElement("IFRAME");
             iframe.setAttribute("src", "mraid://" + command);
             document.documentElement.appendChild(iframe);
             iframe.parentNode.removeChild(iframe);
             iframe = null;
         };




     })();