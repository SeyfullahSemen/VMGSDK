# VMG Android SDK

The VMG SDK allows you to play outstream video advertisment from VMG SSP ad server. This repository includes a demo to show how to make use of this library.

Below follows a quick integration guide to help you implement our SDK in your app. For your integration you'll need some, unique to you, idenfitiers for the app configuration and placements. If you haven't received these yet, contact your account manager to acquire these. 

![VMG Logo](http://www.videomediagroup.com/wp-content/uploads/2016/01/logo-transparant-website.png)

## Demo

Checkout the repository and the demo app will simply build and run, take a look trough the code to see how the SDK is integrated and functions. 

## Integration

In order to use this library you can add it to your project as a gradle dependency. You can add this gradle dependency inside your gradle file.
Once you have added this dependency, you can use the methods of the classes inside the library. This library contains all the classes you need to load a advertisment in your application. 

### 1. Add our Maven repository
```java
repositories {
	maven {
        url "http://dl.bintray.com/videomediagroup/VMG"
    }
}
```

After this step you can add our gradle dependency inside your `Build.gradle` file in the `Module:app`. Add our library like this:

```java
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.VMG.Vmgsdklibrary:vmgsdklib:1.0.2'
}
```
There is no need to add special permissions in your `AndroidManifest.XML`, this will be done by our library.
 
### 2. Initialise SDK with config

Inside your `MainActivity`, in the `onCreate()` method add the following code:

```java
VMGConfig.loadConfig(getApplicationContext(), <app_id>);
```

This will make sure that the SDK configuration is loaded and available.

### 3. Add a fragment

First add a webview to your fragment layout file and give it the size properties that you want it to have. Next create a new private variable for the of the `VMGBase` class

```java
private VMGBase vmgBase;
```

After that, inside the `onCreate()` or `onViewCreated()` initialize the fragment with a `placement_id`.

```java
vmgBase = new VMGBase(getActivity(), webView, <placement_id>);
```

### 4. Inside a scrollView

Inside scrollviews we need to get informed of the scroll sate of the view. By doing so, the SDK will control the ad view expansion and playback state. To do this, call the `VMGScrollEvent` method on the fragment with the scroll position and root layout as arguments. 


```java
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_example, container, false);
        
        nestedScrollView = view.findViewById(R.id.scrollview);
        rootLayout = view.findViewById(R.id.rootLayout);
        webView = view.findViewById(R.id.webView);
        
        vmgBase = new VMGBase(getActivity(), webView, <placement_id>);
        
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                vmgBase.VMGScrollEvent(scrollY, scrollX, rootLayout);
            }
        });


        return view;
	}
```


In this case the `rootLayout` is the layout you use as the parent in your `layout XML file`, e.g.:
```java
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/rootLayout" 
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:animateLayoutChanges="true"
    tools:context="com.example.SDKDemo.ScrollPage">

```

### 5. Full example
We will show you a full example of how it is done. First we start with the line of code we need to add in the `MainActivity`
```java
    public class MainActivity extends AppCompatActivity {
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        VMGConfig.loadConfig(getApplicationContext(), <app_id>);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
                             
        final View view = inflater.inflate(R.layout.fragment_your, container, false);
        
        scrollView = view.findViewById(R.id.scrollView);
        rootLayout = view.findViewById(R.id.rootLayout);
        webView = view.findViewById(R.id.webView);
        
        vmgBase = new VMGBase(getActivity(), webView, <placement_id>);

        
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

