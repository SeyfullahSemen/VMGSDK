# VMG Android SDK

Welcome to our android SDK. What this SDK does, it allows you to play "outstream" video advertisment from VMG SSP ad server. 
To get a feeling of how it works, we included a demo to see an example of how to make use of this library.
It is a very easy to use library.

## Run the demo
In order to run the demo app, you need to clone this repository and save it in a folder. Open Android studio and open the cloned repository and let the gradle build.
Once the gradle is finished, click the run button and run the app.

## How to download the VMG SDK library

The VMG SDK library is available as a gradle dependencie, which you can add to the gradle file of your android app
This gradle dependencie includes everything so you can run your "outstream" advertisment.

// onderzoeken hoe ik de library als dependencie kan toevoegen

## How to ingrate SDK with an app

The first thing you need todo is compile the library as a gradle dependencie. After that is done, 
you need to add the following line of code inside your **MainActivity**, in the `onCreate()` method.

`VMGConfig.loadConfig(getApplicationContext(), configurationId);`

This line of code will make sure that the configuration is loaded through the lifetime of the app.
after you have added the line of code above, create a **Fragment**. After you have created the new **Fragment**,
add the following lines of code:

create a new private instance of the **VMGBase** class.
`private VMGBase vmgBase;`

after that is done, add the following line of code inside the `onCreate()` or inside the `onViewCreated()` method.
`vmgBase = new VMGBase(getActivity(), webView);`.
This will instanciate the class which we added **private**. You must at these lines of code in every **Fragment** You want to load your advertisment.
This line of code also makes sure you can use the methodes you need to start the advertisment. Let's see how we can use that method.

` vmgBase.startVMG(AdvertismentId); `. 
This line of code will start the advertisment. The only thing you need to add is the id of the advertisment. After you made a call to this method, you don't need to worry about anything,
because the rest will be done by this method.

### VMG scroll event





