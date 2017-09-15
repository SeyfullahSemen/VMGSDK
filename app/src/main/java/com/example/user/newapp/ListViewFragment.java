package com.example.user.newapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vmg.BaseUtils.VMGBase;

public class ListViewFragment extends Fragment {
    private ListView listView;
    private WebView webber;
    private VMGBase frag;

    public ListViewFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_list_view, container, false);
        listView = (ListView) v.findViewById(R.id.listView);
        webber = (WebView) v.findViewById(R.id.webber); // get the id of the custom webview
        frag = new VMGBase(getActivity(), webber);

        return v;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setValues(listView);
        frag.startVMG(6194);
    }

    private void setValues(ListView listView) {
        int size = 50;
        String[] vals = new String[size];

        for (int i = 0; i < vals.length; i++) {
            vals[i] = "VMG Demo " + i;
        }

        ArrayAdapter adapter = new ArrayAdapter<>(getActivity(),
                R.layout.list_row, R.id.listViewText, vals);
        listView.setAdapter(adapter);
    }
}
