package com.tk.androidproficiency;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

public class ProficiencyAdapter extends RecyclerView.Adapter<ProficiencyAdapter.MyViewHolder> {
    @NonNull

    private List<DataModel> dataModelList;
    private Context context;
    private LayoutInflater inflater;
    ImageLoader imageLoader;

    public ProficiencyAdapter(Context context, List<DataModel> dataModelList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.dataModelList = dataModelList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.proficiency_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataModel dataModel = dataModelList.get(position);
        imageLoader = VolleyImageLoader.getInstance(context).getImageLoader();
        holder.hintTitle.setText(dataModel.getTitle());
        holder.description.setText(dataModel.getDescription());
        imageLoader.get(dataModel.getImage(), ImageLoader.getImageListener(holder.titleImage,
                R.drawable.placeholder, R.drawable.placeholder));
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView hintTitle, description;
        private ImageView titleImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            hintTitle = itemView.findViewById(R.id.hint_title);
            description = itemView.findViewById(R.id.description);
            titleImage = itemView.findViewById(R.id.title_image);
        }
    }
}
