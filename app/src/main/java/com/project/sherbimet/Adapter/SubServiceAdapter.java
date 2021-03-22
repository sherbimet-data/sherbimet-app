package com.project.sherbimet.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.sherbimet.Listner.SubServiceItemClickListner;
import com.project.sherbimet.Model.SubService;
import com.project.sherbimet.R;

import java.util.ArrayList;

public class SubServiceAdapter extends RecyclerView.Adapter<SubServiceAdapter.ViewHolder> {

    Context context;
    ArrayList<SubService> listSubService;

    SubServiceItemClickListner subServiceItemClickListner;

    public SubServiceItemClickListner getSubServiceItemClickListner() {
        return subServiceItemClickListner;
    }

    public void setSubServiceItemClickListner(SubServiceItemClickListner subServiceItemClickListner) {
        this.subServiceItemClickListner = subServiceItemClickListner;
    }

    public SubServiceAdapter(Context context, ArrayList<SubService> listSubService) {
        this.context = context;
        this.listSubService = listSubService;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View mView = LayoutInflater.from(context).inflate(R.layout.subservice_row_item,parent,false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubService subService = listSubService.get(position);
        String name = subService.getSub_service_name();
        holder.tvSubServiceText.setText(name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubServiceItemClickListner listner = getSubServiceItemClickListner();
                listner.setOnItemClicked(listSubService,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSubService.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvSubServiceText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSubServiceText = itemView.findViewById(R.id.tvSubServiceText);

        }
    }

}
