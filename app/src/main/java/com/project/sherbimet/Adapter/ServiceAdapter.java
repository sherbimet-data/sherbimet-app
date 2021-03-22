package com.project.sherbimet.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.sherbimet.ApiHelper.WebURL;
import com.project.sherbimet.Listner.ServiceItemClickListner;
import com.project.sherbimet.Model.Service;
import com.project.sherbimet.R;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.viewHolder> {
    Context context;
    ArrayList<Service> listservice;

    ServiceItemClickListner serviceItemClickListner;

    public ServiceItemClickListner getServiceItemClickListner() {
        return serviceItemClickListner;
    }

    public void setServiceItemClickListner(ServiceItemClickListner serviceItemClickListner){
        this.serviceItemClickListner = serviceItemClickListner;
    }

    public ServiceAdapter(Context context, ArrayList<Service> listservice) {
        this.context = context;
        this.listservice = listservice;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.service_row_item,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Service service = listservice.get(position);

        String name = service.getService_name();
        holder.tv1.setText(name);

        Glide.with(context).load(WebURL.KEY_IMAGE_URL+service.getService_photo_path()).into(holder.iv1);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ServiceItemClickListner listner = getServiceItemClickListner();
                listner.setOnItemClicked(listservice,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listservice.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder{
    ImageView iv1;
    TextView tv1;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            iv1=itemView.findViewById(R.id.iv1);
            tv1=itemView.findViewById(R.id.tv1);

        }
    }

}
