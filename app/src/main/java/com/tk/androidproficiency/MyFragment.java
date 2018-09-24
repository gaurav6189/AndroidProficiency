package com.tk.androidproficiency;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MyFragment extends Fragment {


    public MyFragment() {
    }

    private RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    ProficiencyAdapter proficiencyAdapter;
    private List<DataModel> dataModelList;
    private String title;
    Toolbar toolbar;
    TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        toolbar = rootView.findViewById(R.id.toolbar);
        textView = rootView.findViewById(R.id.title_header);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        dataModelList = new ArrayList<>();
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        proficiencyAdapter = new ProficiencyAdapter(getContext(), dataModelList);
        makeJsonRequest();

    }

    private void makeJsonRequest() {
        String url = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (dataModelList != null) {
                                dataModelList.clear();
                            }
                            JSONObject jsonObject = new JSONObject(response);
                            title = jsonObject.getString("title");
                            JSONArray jsonArray = jsonObject.getJSONArray("rows");
                            for (int i = 0; jsonArray.length() > i; i++) {
                                DataModel dataModel = new DataModel();
                                dataModel.setTitle(jsonArray.getJSONObject(i).getString("title"));
                                dataModel.setDescription(jsonArray.getJSONObject(i).getString("description"));
                                dataModel.setImage(jsonArray.getJSONObject(i).getString("imageHref"));
                                dataModelList.add(dataModel);
                            }
                            proficiencyAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(proficiencyAdapter);
                            textView.setText(title);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });


        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

}
