# 1.VMG Android SDK

Welcome to our android SDK. What this SDK does, it allows you to play "outstream" video advertisment from VMG SSP ad server. 
To get a feeling of how it works, we included a demo to see an example of how to make use of this library.
It is a very easy to use library. This library is compatible for every screen size and even for tablets. We made our library as simple as possible so you don't need to worry
much about the code you need to implement, when your main focus should be the app you are devloping.

## 1.2 Run the demo
In order to run the demo app, you need to clone this repository and save it in a folder. Open Android studio and open the cloned repository and let the gradle build.
Once the gradle is finished, click the run button and run the app. We hope you enjoy this little demo.

## 1.3 How to download the VMG SDK library

The VMG SDK library is available as a gradle dependencie, which you can add to the gradle file of your android app.
This gradle dependencie includes everything so you can run your "outstream" advertisment.

// onderzoeken hoe ik de library als dependencie kan toevoegen
# 2.How to integrate the library into your app?

 In the following sections, we will explain how to use the library and how to implement the library code inside your app. Just follow the simple steps 
 and everything will be allright.
 
## 2.1 Integrate the SDK

The first thing you need todo is compile the library as a gradle dependency, **in section 1.3 you can read how to do that**. After that is done, 
you need to add the following line of code inside your **MainActivity**, in the `onCreate()` method.

`VMGConfig.loadConfig(getApplicationContext(), configurationId);`

This line of code will make sure that the configuration is loaded through the lifetime of the app.
after you have added the line of code above, create a **Fragment**. Add the following lines of code:

create a new private instance of the **VMGBase** class.
`private VMGBase vmgBase;`

after that is done, add the following line of code inside the `onCreate()` or inside the `onViewCreated()` method.
`vmgBase = new VMGBase(getActivity(), webView);`.
This will instantiate the **VMGBase class** and we need to give it two arguments:

* the context (getActivity())
* The WebView you want to load the advertisment in

You must add these lines of code in every **Fragment** You want to load your advertisment.
This line of code also makes sure you can use the methods that you need to start the advertisment. Let's see how we can use that method.

## 2.2 The start method
After completing section 2.1, now it is time to see how to start the advertisment. 

Just add this line of code.
` vmgBase.startVMG(PlacementId); `. 
This line of code will start the advertisment. The only thing you need to add is the **placementId**. After you made a call to this method, you don't need to worry about anything,
because the rest will be done by this method.

## 2.3 VMG scroll event
You add the following lines of code If you have an advertisment that needs to open inRead when the user of the app is scrolling through the app. 
 ```java
 private NestedScrollView scrollview;
 private RelativeLayout rootLayout;
 
 @Override
 public View onViewCreated(LayoutInflater inflater, ViewGroup container, Bundle savedinstances){
    final View view = inflater.inflate(R.layout.layout_you_want_to_inflate,container,false);
    
    scrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener(){
            @Override
            public void onScrollChange(NestedScrollView v , int scrollX, int scrollY, int oldScrollX,int oldScrollY){
                vmgBase.VMGScrollEvent(scrollY, scrollX,rootLayout);
             }
         });
         
    return view;
}
 ```
Above you see how to add the **scrollEvent** inside the **Fragment class** you want to load the advertisment.

# 3.Full example
We will show you a full example of how it is done. First we start with the line of code we need to add in the **MainActivity**
```java
    public class MainActivity extends AppCompatActivity {
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        VMGConfig.loadConfig(getApplicationContext(), configurationId);
 }

```
We added the line of code in the **MainActivity** now we need to add some code in the **Fragment** we want to load the add in.

```java
    public class YourFragment extends Fragment {
    private WebView webView;
    private VMGBase vmgBase;
    private NestedScrollView scrollView;
    private RelativeLayout rootLayout;
   
     public YourFragment() {
    }

   
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                             
        final View view = inflater.inflate(R.layout.fragment_your, container, false);
        
        scrollView = v.findViewById(R.id.scrollView);
        parentLayout = v.findViewById(R.id.parentLayout);
        webView = v.findViewById(R.id.webView);
        
        vmgBase = new VMGBase(getActivity(), webView);

        vmgBase.startVMG(placementId);
        
        scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            
                vmgBase.VMGScrollEvent(scrollY, scrollX, rootLayout);
            }
        });


        return view;
    }
}

```
As you can see it is just a little bit of code that you need to include. We hope these examples have helped you to add the technology into your app.

# 4.Author
Seyfullah Semen -> seyfullah.semen@videomediagroup.com

